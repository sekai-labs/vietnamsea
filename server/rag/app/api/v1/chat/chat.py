from fastapi import APIRouter

from app.api.v1.chat.dto import ChatRequest, ChatResponse
from app.flow.chat_flow import run_chat

router = APIRouter(prefix="/chat", tags=["chat"])


@router.post("", response_model=ChatResponse)
async def chat(request: ChatRequest) -> ChatResponse:
    return await run_chat(
        query=request.query,
        session_id=request.session_id,
        user_id=request.user_id,
    )
