import json
from typing import Any


def parse_llm_json(text: str) -> Any:
    """
    Parse JSON from LLM output, stripping markdown code fences when present.

    LLMs sometimes wrap JSON in ```json ... ``` blocks even when instructed not to.
    This function handles both raw JSON and fenced variants transparently.

    Args:
        text: Raw string content from an LLM response.

    Returns:
        Parsed Python object (dict, list, etc.).

    Raises:
        json.JSONDecodeError: if the text is not valid JSON after stripping fences.
    """
    text = text.strip()
    if text.startswith("```"):
        lines = text.splitlines()
        inner = lines[1:-1] if lines[-1].strip() == "```" else lines[1:]
        text = "\n".join(inner)
    return json.loads(text.strip())
