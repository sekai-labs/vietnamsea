from dataclasses import dataclass

from app.client.embedding_client import get_embedding
from app.core.config import get_settings
from app.repository.metadata_repository import metadata_repository
from app.repository.vector_repository import vector_repository


@dataclass
class RetrievalResult:
    chunks: list[str]
    citations: list[str]
    graph_facts: list[str]


class RetrievalService:
    def __init__(self) -> None:
        self._settings = get_settings()
        self._embedding = get_embedding()

    def retrieve(self, query: str) -> RetrievalResult:
        query_vector = self._embedding.embed_query(query)
        vector_hits = vector_repository.search(
            query_vector, top_k=self._settings.rag_top_k
        )

        chunks = [hit[0].text for hit in vector_hits]
        citations = [hit[0].source for hit in vector_hits]

        facts = metadata_repository.search_graph_facts(
            query=query,
            top_k=self._settings.rag_graph_top_k,
        )
        graph_facts = [
            f"{fact.subject} -[{fact.predicate}]-> {fact.object}" for fact in facts
        ]

        return RetrievalResult(
            chunks=chunks,
            citations=citations,
            graph_facts=graph_facts,
        )


retrieval_service = RetrievalService()
