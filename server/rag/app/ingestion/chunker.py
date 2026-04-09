def chunk_text(text: str, chunk_size: int = 700, overlap: int = 120) -> list[str]:
    normalized = " ".join(text.split())
    if not normalized:
        return []

    if len(normalized) <= chunk_size:
        return [normalized]

    chunks: list[str] = []
    start = 0
    text_len = len(normalized)
    safe_overlap = max(0, min(overlap, chunk_size // 2))

    while start < text_len:
        end = min(start + chunk_size, text_len)
        chunk = normalized[start:end].strip()
        if chunk:
            chunks.append(chunk)

        if end >= text_len:
            break
        start = max(0, end - safe_overlap)

    return chunks
