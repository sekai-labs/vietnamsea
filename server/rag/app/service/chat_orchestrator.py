from app.api.v1.chat.dto import ChatResponse
from app.repository.chat_repository import chat_repository
from app.service.generation_service import generation_service
from app.service.retrieval_service import retrieval_service


class ChatOrchestrator:
    async def run(
        self, query: str, session_id: str | None, user_id: str | None
    ) -> ChatResponse:
        ensured_session_id = await chat_repository.ensure_session(
            session_id=session_id, user_id=user_id
        )

        await chat_repository.add_message(
            session_id=ensured_session_id,
            role="user",
            content=query,
        )

        evidence = retrieval_service.retrieve(query)
        answer = generation_service.generate(query=query, evidence=evidence)

        await chat_repository.add_message(
            session_id=ensured_session_id,
            role="assistant",
            content=answer,
            metadata={
                "citations": evidence.citations,
                "graph_facts": evidence.graph_facts,
            },
        )

        return ChatResponse(
            session_id=ensured_session_id,
            answer=answer,
            citations=evidence.citations,
            graph_facts=evidence.graph_facts,
        )


chat_orchestrator = ChatOrchestrator()
