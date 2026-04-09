from collections.abc import AsyncIterator
from contextlib import asynccontextmanager

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.api.v1.v1 import router as v1_router
from app.client.qdrant_client import ensure_qdrant_collection
from app.core.config import get_settings
from app.prompt.prompt_loader import load_prompt_config
from app.repository.chat_repository import init_chat_repository


@asynccontextmanager
async def lifespan(_: FastAPI) -> AsyncIterator[None]:
    # Preload prompt configuration and ensure vector collection exists before serving traffic.
    load_prompt_config()
    ensure_qdrant_collection()
    await init_chat_repository()
    yield


def create_app() -> FastAPI:
    settings = get_settings()

    app = FastAPI(
        title=settings.app_name,
        version=settings.app_version,
        description=settings.app_description,
        lifespan=lifespan,
    )

    app.add_middleware(
        CORSMiddleware,
        allow_origins=(
            ["*"] if settings.cors_origins == ["*"] else settings.cors_origins
        ),
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )

    app.include_router(v1_router)
    return app


app = create_app()
