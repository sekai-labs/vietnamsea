-- name: CreateMediaFile :one
INSERT INTO media_files (
    file_name,
    mime_type,
    size,
    checksum,
    status,
    uploaded_by,
    original_name,
    duration_ms
)
VALUES (
    sqlc.arg(file_name),
    sqlc.arg(mime_type),
    sqlc.arg(size),
    sqlc.arg(checksum),
    sqlc.arg(status),
    sqlc.arg(uploaded_by),
    sqlc.arg(original_name),
    sqlc.arg(duration_ms)
)
RETURNING
    id,
    file_name,
    mime_type,
    size,
    checksum,
    status,
    uploaded_by,
    original_name,
    duration_ms,
    created_at;

-- name: GetMediaFileByID :one
SELECT
    id,
    file_name,
    mime_type,
    size,
    checksum,
    status,
    uploaded_by,
    original_name,
    duration_ms,
    created_at
FROM media_files
WHERE id = sqlc.arg(id);

-- name: GetMediaFileDetailByID :one
SELECT
    mf.id,
    mf.file_name,
    mf.mime_type,
    mf.size,
    mf.checksum,
    mf.status,
    mf.uploaded_by,
    mf.original_name,
    mf.duration_ms,
    mf.created_at,
    policy.visibility,
    primary_object.id AS primary_storage_object_id,
    primary_object.storage_key AS primary_storage_key,
    primary_provider.id AS primary_storage_provider_id,
    primary_provider.name AS primary_storage_provider_name,
    primary_provider.provider_type AS primary_provider_type,
    primary_provider.bucket AS primary_bucket,
    primary_provider.region AS primary_region,
    primary_provider.endpoint AS primary_endpoint
FROM media_files AS mf
LEFT JOIN LATERAL (
    SELECT
        map.visibility
    FROM media_access_policies AS map
    WHERE map.media_id = mf.id
    ORDER BY map.created_at DESC
    LIMIT 1
) AS policy ON TRUE
LEFT JOIN LATERAL (
    SELECT
        mso.id,
        mso.storage_provider_id,
        mso.storage_key
    FROM media_storage_objects AS mso
    WHERE mso.media_id = mf.id
      AND mso.is_primary = TRUE
    ORDER BY mso.created_at DESC
    LIMIT 1
) AS primary_object ON TRUE
LEFT JOIN storage_providers AS primary_provider
    ON primary_provider.id = primary_object.storage_provider_id
WHERE mf.id = sqlc.arg(id);

-- name: ListMediaFilesByStatus :many
SELECT
    id,
    file_name,
    mime_type,
    size,
    checksum,
    status,
    uploaded_by,
    original_name,
    duration_ms,
    created_at
FROM media_files
WHERE status = sqlc.arg(status)
ORDER BY created_at DESC
LIMIT sqlc.arg(page_limit)
OFFSET sqlc.arg(page_offset);

-- name: ListUploadedMediaFiles :many
SELECT
    id,
    file_name,
    mime_type,
    size,
    checksum,
    status,
    uploaded_by,
    original_name,
    duration_ms,
    created_at
FROM media_files
WHERE uploaded_by = sqlc.arg(uploaded_by)
ORDER BY created_at DESC
LIMIT sqlc.arg(page_limit)
OFFSET sqlc.arg(page_offset);

-- name: ListOwnedMediaFiles :many
SELECT
    mf.id,
    mf.file_name,
    mf.mime_type,
    mf.size,
    mf.checksum,
    mf.status,
    mf.uploaded_by,
    mf.original_name,
    mf.duration_ms,
    mf.created_at
FROM media_files AS mf
INNER JOIN media_owners AS mo
    ON mo.media_id = mf.id
WHERE mo.owner_type = sqlc.arg(owner_type)
  AND mo.owner_id = sqlc.arg(owner_id)
ORDER BY mf.created_at DESC
LIMIT sqlc.arg(page_limit)
OFFSET sqlc.arg(page_offset);

-- name: CountOwnedMediaFiles :one
SELECT COUNT(*)
FROM media_owners
WHERE owner_type = sqlc.arg(owner_type)
  AND owner_id = sqlc.arg(owner_id);

-- name: UpdateMediaFileStatus :one
UPDATE media_files
SET status = sqlc.arg(status)
WHERE id = sqlc.arg(id)
RETURNING
    id,
    file_name,
    mime_type,
    size,
    checksum,
    status,
    uploaded_by,
    original_name,
    duration_ms,
    created_at;

-- name: SetMediaFileChecksum :one
UPDATE media_files
SET checksum = sqlc.arg(checksum)
WHERE id = sqlc.arg(id)
RETURNING
    id,
    file_name,
    mime_type,
    size,
    checksum,
    status,
    uploaded_by,
    original_name,
    duration_ms,
    created_at;

-- name: DeleteMediaFile :exec
DELETE FROM media_files
WHERE id = sqlc.arg(id);
