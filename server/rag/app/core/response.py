from pydantic import BaseModel

from typing import TypeVar, Generic

T = TypeVar("T")


class BaseResponse(BaseModel, Generic[T]):
    content: T
    messages: list[str]
    code: str
    success: bool

    def __init__(self) -> None:
        super().__init__()


class PaginationObject(BaseModel):
    page: int
    size: int
    totalPages: int
    totalElements: int
