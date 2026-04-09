from uuid import uuid4

from fastapi import APIRouter
from pydantic import BaseModel, Field

from app.flow.ingestion_flow import ingest_document
from app.repository.metadata_repository import GraphTriple

router = APIRouter(prefix="/ingest", tags=["ingest"])


class TripleInput(BaseModel):
    subject: str
    predicate: str
    object: str


class IngestRequest(BaseModel):
    source: str = Field(default="manual")
    text: str = Field(min_length=1)
    triples: list[TripleInput] = Field(default_factory=list)


@router.post("")
def ingest(payload: IngestRequest) -> dict[str, int | str]:
    doc_id = str(uuid4())

    triples = [
        GraphTriple(
            subject=item.subject,
            predicate=item.predicate,
            object=item.object,
            source=payload.source,
        )
        for item in payload.triples
    ]

    return ingest_document(
        doc_id=doc_id,
        source=payload.source,
        text=payload.text,
        triples=triples,
    )
