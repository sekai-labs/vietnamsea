# RAG Service

## Run With Uvicorn

Install dependencies first:

```bash
uv sync
```

Run in development mode:

```bash
uv run uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

Run in production mode:

```bash
uv run uvicorn app.main:app --host 0.0.0.0 --port 8000
```
