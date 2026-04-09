from functools import lru_cache
from pathlib import Path

import yaml


@lru_cache(maxsize=1)
def load_prompt_config() -> dict:
    prompt_file = Path(__file__).resolve().parent / "version" / "v1.yaml"
    with prompt_file.open("r", encoding="utf-8") as handle:
        return yaml.safe_load(handle) or {}
