from langchain_core.embeddings import Embeddings
from langchain_google_genai import GoogleGenerativeAIEmbeddings
from langchain_openai import OpenAIEmbeddings
from pydantic import SecretStr
import httpx

from app.core.config import get_settings

settings = get_settings()


class OpenRouterEmbeddings(Embeddings):
    def __init__(
        self,
        api_key: SecretStr,
        model: str,
        base_url: str,
        batch_size: int = 32,
        timeout: float = 60.0,
    ) -> None:
        self.api_key = api_key
        self.model = model
        self.base_url = base_url.rstrip("/")
        self.batch_size = batch_size
        self.timeout = timeout

    def _embed_batch(self, texts: list[str]) -> list[list[float]]:
        response = httpx.post(
            f"{self.base_url}/embeddings",
            headers={
                "Authorization": f"Bearer {self.api_key.get_secret_value()}",
                "Content-Type": "application/json",
            },
            json={"model": self.model, "input": texts},
            timeout=self.timeout,
        )
        response.raise_for_status()

        payload = response.json()
        data = payload.get("data")
        if not isinstance(data, list) or not data:
            raise ValueError("OpenRouter embeddings response has no data")

        vectors: list[list[float]] = []
        for item in data:
            vector = item.get("embedding") if isinstance(item, dict) else None
            if not isinstance(vector, list) or not vector:
                raise ValueError("OpenRouter embeddings response item missing vector")
            vectors.append([float(v) for v in vector])
        return vectors

    def embed_documents(self, texts: list[str]) -> list[list[float]]:
        vectors: list[list[float]] = []
        for i in range(0, len(texts), self.batch_size):
            batch = texts[i : i + self.batch_size]
            if not batch:
                continue
            vectors.extend(self._embed_batch(batch))
        return vectors

    def embed_query(self, text: str) -> list[float]:
        vectors = self._embed_batch([text])
        return vectors[0]


def get_embedding() -> Embeddings:
    if "openrouter" == settings.llm_provider:
        if not settings.openrouter_api_key.get_secret_value().strip():
            raise ValueError("OPENROUTER_API_KEY is required for OpenRouter embeddings")
        return OpenRouterEmbeddings(
            model=settings.openrouter_embedding_model,
            api_key=settings.openrouter_api_key,
            base_url=settings.openrouter_base_url,
        )
    elif "openai" == settings.llm_provider:
        if not settings.openai_api_key.get_secret_value().strip():
            raise ValueError("OPENAI_API_KEY is required for OpenAI embeddings")
        return OpenAIEmbeddings(
            model=settings.openai_embedding_model,
            api_key=settings.openai_api_key,
        )
    elif "gemini" == settings.llm_provider:
        if not settings.gemini_api_key.get_secret_value().strip():
            raise ValueError("GEMINI_API_KEY is required for Gemini embeddings")
        return GoogleGenerativeAIEmbeddings(
            model=settings.gemini_embedding_model,
            api_key=settings.gemini_api_key,
        )
    else:
        raise ValueError(
            f"Unsupported LLM_PROVIDER for embeddings: '{settings.llm_provider}'."
        )
