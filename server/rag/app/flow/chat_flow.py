from app.api.v1.chat.dto import ChatResponse
from app.service.chat_orchestrator import chat_orchestrator


async def run_chat(
    query: str, session_id: str | None, user_id: str | None
) -> ChatResponse:
    return await chat_orchestrator.run(
        query=query,
        session_id=session_id,
        user_id=user_id,
    )
