from functools import lru_cache

from langchain_core.language_models.chat_models import BaseChatModel

from app.core.config import get_settings


@lru_cache(maxsize=1)
def get_llm() -> BaseChatModel:
    settings = get_settings()
    provider = settings.llm_provider
    if provider == "openai":
        from langchain_openai import ChatOpenAI

        api_key = settings.openai_api_key
        if not api_key.get_secret_value().strip():
            raise ValueError("OPENAI_API_KEY is not set")

        return ChatOpenAI(
            model=settings.openai_model,
            temperature=0.3,
            api_key=api_key,
        )

    if provider == "gemini":
        from langchain_google_genai import ChatGoogleGenerativeAI

        api_key = settings.gemini_api_key
        if not api_key.get_secret_value().strip():
            raise ValueError("GEMINI_API_KEY is not set")

        return ChatGoogleGenerativeAI(
            model=settings.gemini_model,
            temperature=0,
            google_api_key=api_key,
        )

    if provider == "openrouter":
        from langchain_openrouter import ChatOpenRouter

        api_key = settings.openrouter_api_key
        if not api_key.get_secret_value().strip():
            raise ValueError("OPENROUTER_API_KEY is not set")

        return ChatOpenRouter(
            model=settings.openrouter_model,
            temperature=0.3,
            api_key=api_key,
        )

    raise ValueError(
        f"Unsupported LLM_PROVIDER: '{provider}'. Accepted values: 'openai', 'gemini', 'openrouter'."
    )
