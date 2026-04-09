from app.ingestion.pipeline import IngestionPayload, run_ingestion_pipeline
from app.repository.metadata_repository import (
    DocumentRecord,
    GraphTriple,
    metadata_repository,
)
from app.repository.vector_repository import vector_repository


def ingest_document(
    doc_id: str, source: str, text: str, triples: list[GraphTriple]
) -> dict[str, int | str]:
    metadata_repository.upsert_document(
        DocumentRecord(
            doc_id=doc_id,
            source=source,
            content=text,
        )
    )

    output = run_ingestion_pipeline(
        IngestionPayload(
            doc_id=doc_id,
            source=source,
            text=text,
            triples=triples,
        )
    )

    vector_repository.upsert(output.vector_records)
    metadata_repository.add_triples(output.triples)

    return {
        "document_count": 1,
        "chunk_count": output.chunk_count,
        "triple_count": len(output.triples),
        "doc_id": doc_id,
    }
