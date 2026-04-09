from fastapi.routing import APIRouter

from app.api.v1.chat.chat import router as chat_router
from app.api.v1.health.health import router as health_router
from app.api.v1.ingest.ingest import router as ingest_router

router = APIRouter(prefix="/api/v1")
router.include_router(health_router)
router.include_router(chat_router)
router.include_router(ingest_router)
