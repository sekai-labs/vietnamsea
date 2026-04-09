from dataclasses import dataclass
from threading import Lock


@dataclass
class GraphTriple:
    subject: str
    predicate: str
    object: str
    source: str


@dataclass
class DocumentRecord:
    doc_id: str
    source: str
    content: str


class InMemoryMetadataRepository:
    def __init__(self) -> None:
        self._lock = Lock()
        self._documents: dict[str, DocumentRecord] = {}
        self._triples: list[GraphTriple] = []

    def upsert_document(self, doc: DocumentRecord) -> None:
        with self._lock:
            self._documents[doc.doc_id] = doc

    def add_triples(self, triples: list[GraphTriple]) -> None:
        with self._lock:
            self._triples.extend(triples)

    def search_graph_facts(self, query: str, top_k: int = 5) -> list[GraphTriple]:
        tokens = {t for t in query.lower().split() if t}
        with self._lock:
            triples = list(self._triples)

        if not tokens:
            return triples[: max(1, top_k)]

        scored: list[tuple[GraphTriple, int]] = []
        for triple in triples:
            haystack = f"{triple.subject} {triple.predicate} {triple.object}".lower()
            score = sum(1 for token in tokens if token in haystack)
            if score > 0:
                scored.append((triple, score))

        scored.sort(key=lambda item: item[1], reverse=True)
        return [item[0] for item in scored[: max(1, top_k)]]


metadata_repository = InMemoryMetadataRepository()
