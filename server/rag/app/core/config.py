from dataclasses import dataclass
from functools import lru_cache
from pydantic import SecretStr
import os
from dotenv import load_dotenv

load_dotenv()


def _env_str(name: str, default: str) -> str:
    value = os.getenv(name)
    return value if value is not None else default


def _env_secret_str(name: str, default: str) -> SecretStr:
    value = os.getenv(name)
    return SecretStr(value) if value is not None else SecretStr(default)


def _env_int(name: str, default: int) -> int:
    raw = os.getenv(name)
    if raw is None:
        return default
    try:
        return int(raw)
    except ValueError:
        return default


def _env_bool(name: str, default: bool) -> bool:
    raw = os.getenv(name)
    if raw is None:
        return default
    return raw.strip().lower() in {"1", "true", "yes", "on"}


def _env_csv(name: str, default: str) -> list[str]:
    raw = os.getenv(name, default)
    return [item.strip() for item in raw.split(",") if item.strip()]


@dataclass(frozen=True)
class Settings:
    app_name: str
    app_version: str
    app_description: str
    host: str
    port: int
    reload: bool
    cors_origins: list[str]
    llm_provider: str
    openai_api_key: SecretStr
    openai_model: str
    openai_embedding_model: str
    gemini_api_key: SecretStr
    gemini_model: str
    gemini_embedding_model: str
    openrouter_api_key: SecretStr
    openrouter_model: str
    openrouter_embedding_model: str
    openrouter_base_url: str
    # Qdrant Config
    qdrant_host: str
    qdrant_collection_tasks: str
    qdrant_collection_projects: str
    qdrant_vector_size: int
    qdrant_api_key: SecretStr
    qdrant_collection: str
    mongo_uri: str
    mongo_db_name: str
    mongo_sessions_collection: str
    mongo_messages_collection: str
    rag_top_k: int
    rag_graph_top_k: int


@lru_cache(maxsize=1)
def get_settings() -> Settings:
    return Settings(
        app_name=_env_str("APP_NAME", "TaskSense AI Service"),
        app_version=_env_str("APP_VERSION", "0.1.0"),
        app_description=_env_str(
            "APP_DESCRIPTION",
            "RAG-based chatbot for task and project queries",
        ),
        host=_env_str("HOST", "0.0.0.0"),
        port=_env_int("PORT", 8000),
        reload=_env_bool("RELOAD", True),
        cors_origins=_env_csv("CORS_ORIGINS", "*"),
        llm_provider=_env_str("LLM_PROVIDER", "openai").lower(),
        openai_api_key=_env_secret_str("OPENAI_API_KEY", ""),
        openai_model=_env_str("OPENAI_MODEL", "gpt-4o-mini"),
        openai_embedding_model=_env_str(
            "OPENAI_EMBEDDING_MODEL", "text-embedding-3-small"
        ),
        gemini_api_key=_env_secret_str("GEMINI_API_KEY", ""),
        gemini_model=_env_str("GEMINI_MODEL", "gemini-2.5-flash"),
        gemini_embedding_model=_env_str(
            "GEMINI_EMBEDDING_MODEL", "models/text-embedding-004"
        ),
        openrouter_api_key=_env_secret_str("OPENROUTER_API_KEY", ""),
        openrouter_model=_env_str("OPENROUTER_MODEL", "gpt-4o-mini"),
        openrouter_embedding_model=_env_str(
            "OPENROUTER_EMBEDDING_MODEL", "openai/text-embedding-3-large"
        ),
        openrouter_base_url=_env_str(
            "OPENROUTER_BASE_URL", "https://openrouter.ai/api/v1"
        ),
        qdrant_host=_env_str("QDRANT_HOST", "http://localhost:6333"),
        qdrant_collection_tasks=_env_str("QDRANT_COLLECTION_TASKS", "tasks"),
        qdrant_collection_projects=_env_str("QDRANT_COLLECTION_PROJECTS", "projects"),
        qdrant_vector_size=_env_int("QDRANT_VECTOR_SIZE", 3072),
        qdrant_api_key=_env_secret_str("QDRANT_API_KEY", ""),
        qdrant_collection=_env_str("QDRANT_COLLECTION", "rag_chunks"),
        mongo_uri=_env_str("MONGO_URI", "mongodb://localhost:27017"),
        mongo_db_name=_env_str("MONGO_DB_NAME", "rag_db"),
        mongo_sessions_collection=_env_str(
            "MONGO_SESSIONS_COLLECTION", "chat_sessions"
        ),
        mongo_messages_collection=_env_str(
            "MONGO_MESSAGES_COLLECTION", "chat_messages"
        ),
        rag_top_k=_env_int("RAG_TOP_K", 5),
        rag_graph_top_k=_env_int("RAG_GRAPH_TOP_K", 5),
    )
