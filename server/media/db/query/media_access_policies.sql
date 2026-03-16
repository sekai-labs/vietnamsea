-- name: CreateMediaAccessPolicy :one
INSERT INTO media_access_policies (
    media_id,
    visibility
)
VALUES (
    sqlc.arg(media_id),
    sqlc.arg(visibility)
)
RETURNING
    id,
    media_id,
    visibility,
    created_at,
    updated_at;

-- name: GetLatestMediaAccessPolicyByMediaID :one
SELECT
    id,
    media_id,
    visibility,
    created_at,
    updated_at
FROM media_access_policies
WHERE media_id = sqlc.arg(media_id)
ORDER BY created_at DESC
LIMIT 1;

-- name: ListMediaAccessPoliciesByMediaID :many
SELECT
    id,
    media_id,
    visibility,
    created_at,
    updated_at
FROM media_access_policies
WHERE media_id = sqlc.arg(media_id)
ORDER BY created_at DESC;

-- name: UpdateMediaAccessPolicy :one
UPDATE media_access_policies
SET
    visibility = sqlc.arg(visibility),
    updated_at = now()
WHERE id = sqlc.arg(id)
RETURNING
    id,
    media_id,
    visibility,
    created_at,
    updated_at;

-- name: DeleteMediaAccessPoliciesByMediaID :exec
DELETE FROM media_access_policies
WHERE media_id = sqlc.arg(media_id);
