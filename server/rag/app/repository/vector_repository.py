from dataclasses import dataclass

from qdrant_client.http.models import PointStruct

from app.client.qdrant_client import (
    ensure_qdrant_collection,
    search_points,
    upsert_points,
)


@dataclass
class VectorRecord:
    chunk_id: str
    text: str
    source: str
    vector: list[float]


class InMemoryVectorRepository:
    def __init__(self) -> None:
        ensure_qdrant_collection()

    def upsert(self, records: list[VectorRecord]) -> None:
        points: list[PointStruct] = []
        for record in records:
            points.append(
                PointStruct(
                    id=record.chunk_id,
                    vector=record.vector,
                    payload={
                        "text": record.text,
                        "source": record.source,
                        "chunk_id": record.chunk_id,
                    },
                )
            )

        if points:
            upsert_points(points)

    def search(
        self, query_vector: list[float], top_k: int = 5
    ) -> list[tuple[VectorRecord, float]]:
        hits = search_points(query_vector=query_vector, top_k=max(1, top_k))
        results: list[tuple[VectorRecord, float]] = []

        for hit in hits:
            payload = hit.payload or {}
            results.append(
                (
                    VectorRecord(
                        chunk_id=str(payload.get("chunk_id") or hit.id),
                        text=str(payload.get("text", "")),
                        source=str(payload.get("source", "unknown")),
                        vector=query_vector,
                    ),
                    float(hit.score or 0.0),
                )
            )

        return results


vector_repository = InMemoryVectorRepository()
