from datetime import datetime, timezone
from typing import Annotated
from typing import Any
from uuid import uuid4

from beanie import Document, Indexed, init_beanie
from pydantic import Field
from pymongo import AsyncMongoClient

from app.core.config import get_settings


class ChatSession(Document):
    session_id: Annotated[str, Indexed(unique=True)]
    user_id: str | None = None
    created_at: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))
    updated_at: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))

    class Settings:
        name = "chat_sessions"
        indexes = ["updated_at"]


class ChatMessage(Document):
    session_id: Annotated[str, Indexed()]
    role: str
    content: str
    metadata: dict[str, Any] = Field(default_factory=dict)
    created_at: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))

    class Settings:
        name = "chat_messages"
        indexes = [[("session_id", 1), ("created_at", 1)]]


class ChatRepository:
    _initialized = False

    async def init(self) -> None:
        if self._initialized:
            return

        settings = get_settings()
        client = AsyncMongoClient(settings.mongo_uri)
        db = client[settings.mongo_db_name]

        ChatSession.Settings.name = settings.mongo_sessions_collection
        ChatMessage.Settings.name = settings.mongo_messages_collection

        await init_beanie(
            database=db,
            document_models=[ChatSession, ChatMessage],
        )
        self._initialized = True

    async def create_session(self, user_id: str | None = None) -> str:
        session_id = str(uuid4())
        now = datetime.now(timezone.utc)
        await ChatSession(
            session_id=session_id,
            user_id=user_id,
            created_at=now,
            updated_at=now,
        ).insert()
        return session_id

    async def ensure_session(
        self, session_id: str | None, user_id: str | None = None
    ) -> str:
        await self.init()
        if not session_id:
            return await self.create_session(user_id=user_id)

        found = await ChatSession.find_one(ChatSession.session_id == session_id)
        if found:
            return session_id

        now = datetime.now(timezone.utc)
        await ChatSession(
            session_id=session_id,
            user_id=user_id,
            created_at=now,
            updated_at=now,
        ).insert()
        return session_id

    async def add_message(
        self,
        session_id: str,
        role: str,
        content: str,
        metadata: dict[str, Any] | None = None,
    ) -> None:
        await self.init()
        now = datetime.now(timezone.utc)
        await ChatMessage(
            session_id=session_id,
            role=role,
            content=content,
            metadata=metadata or {},
            created_at=now,
        ).insert()

        session = await ChatSession.find_one(ChatSession.session_id == session_id)
        if session is not None:
            session.updated_at = now
            await session.save()


chat_repository = ChatRepository()


async def init_chat_repository() -> None:
    await chat_repository.init()
