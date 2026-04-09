from dataclasses import dataclass

from app.ingestion.chunker import chunk_text
from app.ingestion.dedup import dedup_chunks
from app.ingestion.embedder import embed_chunks
from app.repository.metadata_repository import GraphTriple
from app.repository.vector_repository import VectorRecord


@dataclass
class IngestionPayload:
    doc_id: str
    source: str
    text: str
    triples: list[GraphTriple]


@dataclass
class IngestionOutput:
    vector_records: list[VectorRecord]
    triples: list[GraphTriple]
    chunk_count: int


def run_ingestion_pipeline(payload: IngestionPayload) -> IngestionOutput:
    chunks = dedup_chunks(chunk_text(payload.text))
    vectors = embed_chunks(chunks)

    records: list[VectorRecord] = []
    for idx, chunk in enumerate(chunks):
        chunk_id = f"{payload.doc_id}:{idx}"
        vector = vectors[idx] if idx < len(vectors) else []
        records.append(
            VectorRecord(
                chunk_id=chunk_id,
                text=chunk,
                source=payload.source,
                vector=vector,
            )
        )

    return IngestionOutput(
        vector_records=records,
        triples=payload.triples,
        chunk_count=len(records),
    )
