-- name: UpsertMediaVariant :one
INSERT INTO media_variants (
    media_id,
    variant_name,
    storage_provider_id,
    storage_key,
    width,
    height,
    size,
    format,
    quality,
    status
)
VALUES (
    sqlc.arg(media_id),
    sqlc.arg(variant_name),
    sqlc.arg(storage_provider_id),
    sqlc.arg(storage_key),
    sqlc.arg(width),
    sqlc.arg(height),
    sqlc.arg(size),
    sqlc.arg(format),
    sqlc.arg(quality),
    sqlc.arg(status)
)
ON CONFLICT (media_id, variant_name)
DO UPDATE SET
    storage_provider_id = EXCLUDED.storage_provider_id,
    storage_key = EXCLUDED.storage_key,
    width = EXCLUDED.width,
    height = EXCLUDED.height,
    size = EXCLUDED.size,
    format = EXCLUDED.format,
    quality = EXCLUDED.quality,
    status = EXCLUDED.status,
    updated_at = now()
RETURNING
    id,
    media_id,
    variant_name,
    storage_provider_id,
    storage_key,
    width,
    height,
    size,
    format,
    quality,
    status,
    created_at,
    updated_at;

-- name: GetMediaVariantByName :one
SELECT
    id,
    media_id,
    variant_name,
    storage_provider_id,
    storage_key,
    width,
    height,
    size,
    format,
    quality,
    status,
    created_at,
    updated_at
FROM media_variants
WHERE media_id = sqlc.arg(media_id)
  AND variant_name = sqlc.arg(variant_name);

-- name: ListMediaVariantsByMediaID :many
SELECT
    id,
    media_id,
    variant_name,
    storage_provider_id,
    storage_key,
    width,
    height,
    size,
    format,
    quality,
    status,
    created_at,
    updated_at
FROM media_variants
WHERE media_id = sqlc.arg(media_id)
ORDER BY created_at ASC;

-- name: ListMediaVariantsByStatus :many
SELECT
    id,
    media_id,
    variant_name,
    storage_provider_id,
    storage_key,
    width,
    height,
    size,
    format,
    quality,
    status,
    created_at,
    updated_at
FROM media_variants
WHERE status = sqlc.arg(status)
ORDER BY created_at ASC
LIMIT sqlc.arg(page_limit)
OFFSET sqlc.arg(page_offset);

-- name: UpdateMediaVariantStatus :one
UPDATE media_variants
SET
    status = sqlc.arg(status),
    updated_at = now()
WHERE id = sqlc.arg(id)
RETURNING
    id,
    media_id,
    variant_name,
    storage_provider_id,
    storage_key,
    width,
    height,
    size,
    format,
    quality,
    status,
    created_at,
    updated_at;

-- name: DeleteMediaVariantsByMediaID :exec
DELETE FROM media_variants
WHERE media_id = sqlc.arg(media_id);
