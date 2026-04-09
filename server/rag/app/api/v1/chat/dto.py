from pydantic import BaseModel, Field, field_validator


class ChatRequest(BaseModel):
    """Request payload for chatbot endpoint."""

    query: str = Field(..., min_length=1, max_length=1000, description="User question")
    session_id: str | None = Field(default=None, description="Existing chat session id")
    user_id: str | None = Field(default=None, description="Application user id")

    @field_validator("query")
    @classmethod
    def normalize_query(cls, value: str) -> str:
        stripped = value.strip()
        if not stripped:
            raise ValueError("query must not be blank")
        return stripped


class ChatResponse(BaseModel):
    session_id: str
    answer: str
    citations: list[str] = Field(default_factory=list)
    graph_facts: list[str] = Field(default_factory=list)
