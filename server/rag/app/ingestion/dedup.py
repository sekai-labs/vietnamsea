import hashlib


def dedup_chunks(chunks: list[str]) -> list[str]:
    seen: set[str] = set()
    unique: list[str] = []

    for chunk in chunks:
        key = hashlib.sha256(chunk.encode("utf-8")).hexdigest()
        if key in seen:
            continue
        seen.add(key)
        unique.append(chunk)

    return unique
