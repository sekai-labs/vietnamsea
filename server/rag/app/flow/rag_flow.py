from app.api.v1.chat.dto import ChatResponse
from app.flow.chat_flow import run_chat
from app.flow.ingestion_flow import ingest_document
from app.repository.metadata_repository import GraphTriple


class RagFlow:
    @staticmethod
    def chat(query: str, session_id: str | None, user_id: str | None) -> ChatResponse:
        return run_chat(query=query, session_id=session_id, user_id=user_id)

    @staticmethod
    def ingest(
        doc_id: str, source: str, text: str, triples: list[GraphTriple]
    ) -> dict[str, int | str]:
        return ingest_document(doc_id=doc_id, source=source, text=text, triples=triples)


rag_flow = RagFlow()
