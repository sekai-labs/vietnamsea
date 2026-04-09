from app.client.embedding_client import get_embedding


def embed_chunks(chunks: list[str]) -> list[list[float]]:
    if not chunks:
        return []
    return get_embedding().embed_documents(chunks)
