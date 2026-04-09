from functools import lru_cache

from qdrant_client import QdrantClient
from qdrant_client.http.models import Distance, PointStruct, VectorParams

from app.core.config import get_settings


@lru_cache(maxsize=1)
def get_qdrant_client() -> QdrantClient:
    settings = get_settings()
    api_key = settings.qdrant_api_key.get_secret_value().strip()
    return QdrantClient(url=settings.qdrant_host, api_key=api_key or None)


def ensure_qdrant_collection() -> None:
    settings = get_settings()
    client = get_qdrant_client()
    collections = client.get_collections().collections
    if any(collection.name == settings.qdrant_collection for collection in collections):
        return

    client.create_collection(
        collection_name=settings.qdrant_collection,
        vectors_config=VectorParams(
            size=settings.qdrant_vector_size, distance=Distance.COSINE
        ),
    )


def upsert_points(points: list[PointStruct]) -> None:
    settings = get_settings()
    client = get_qdrant_client()
    client.upsert(collection_name=settings.qdrant_collection, points=points)


def search_points(query_vector: list[float], top_k: int) -> list:
    settings = get_settings()
    client = get_qdrant_client()
    return client.search(
        collection_name=settings.qdrant_collection,
        query_vector=query_vector,
        limit=top_k,
        with_payload=True,
    )
