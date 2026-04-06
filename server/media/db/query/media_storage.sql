-- name: CreateStorageProvider :one
INSERT INTO storage_providers (
    name,
    provider_type,
    endpoint,
    region,
    bucket,
    is_active,
    priority
)
VALUES (
    sqlc.arg(name),
    sqlc.arg(provider_type),
    sqlc.arg(endpoint),
    sqlc.arg(region),
    sqlc.arg(bucket),
    sqlc.arg(is_active),
    sqlc.arg(priority)
)
RETURNING
    id,
    name,
    provider_type,
    endpoint,
    region,
    bucket,
    is_active,
    priority,
    created_at;

-- name: GetStorageProviderByID :one
SELECT
    id,
    name,
    provider_type,
    endpoint,
    region,
    bucket,
    is_active,
    priority,
    created_at
FROM storage_providers
WHERE id = sqlc.arg(id);

-- name: ListActiveStorageProviders :many
SELECT
    id,
    name,
    provider_type,
    endpoint,
    region,
    bucket,
    is_active,
    priority,
    created_at
FROM storage_providers
WHERE is_active = TRUE
ORDER BY priority ASC, created_at ASC;

-- name: CreateMediaStorageObject :one
INSERT INTO media_storage_objects (
    media_id,
    storage_provider_id,
    storage_key,
    is_primary
)
VALUES (
    sqlc.arg(media_id),
    sqlc.arg(storage_provider_id),
    sqlc.arg(storage_key),
    sqlc.arg(is_primary)
)
RETURNING
    id,
    media_id,
    storage_provider_id,
    storage_key,
    is_primary,
    created_at;

-- name: GetPrimaryMediaStorageObjectByMediaID :one
SELECT
    id,
    media_id,
    storage_provider_id,
    storage_key,
    is_primary,
    created_at
FROM media_storage_objects
WHERE media_id = sqlc.arg(media_id)
  AND is_primary = TRUE
ORDER BY created_at DESC
LIMIT 1;

-- name: ListMediaStorageObjectsByMediaID :many
SELECT
    id,
    media_id,
    storage_provider_id,
    storage_key,
    is_primary,
    created_at
FROM media_storage_objects
WHERE media_id = sqlc.arg(media_id)
ORDER BY is_primary DESC, created_at ASC;

-- name: SetPrimaryMediaStorageObject :exec
WITH target AS (
    SELECT
        sqlc.arg(media_id)::uuid AS media_id,
        sqlc.arg(storage_object_id)::uuid AS storage_object_id
)
UPDATE media_storage_objects AS mso
SET is_primary = (mso.id = target.storage_object_id)
FROM target
WHERE mso.media_id = target.media_id;

-- name: DeleteMediaStorageObjectsByMediaID :exec
DELETE FROM media_storage_objects
WHERE media_id = sqlc.arg(media_id);
