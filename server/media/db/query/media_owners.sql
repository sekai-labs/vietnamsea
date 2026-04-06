-- name: CreateMediaOwner :one
INSERT INTO media_owners (
    media_id,
    owner_type,
    owner_id,
    field
)
VALUES (
    sqlc.arg(media_id),
    sqlc.arg(owner_type),
    sqlc.arg(owner_id),
    sqlc.arg(field)
)
RETURNING
    id,
    media_id,
    owner_type,
    owner_id,
    field,
    created_at,
    updated_at;

-- name: ListMediaOwnersByMediaID :many
SELECT
    id,
    media_id,
    owner_type,
    owner_id,
    field,
    created_at,
    updated_at
FROM media_owners
WHERE media_id = sqlc.arg(media_id)
ORDER BY created_at ASC;

-- name: DeleteMediaOwner :execrows
DELETE FROM media_owners
WHERE media_id = sqlc.arg(media_id)
  AND owner_type = sqlc.arg(owner_type)
  AND owner_id = sqlc.arg(owner_id);

-- name: DeleteMediaOwnersByMediaID :exec
DELETE FROM media_owners
WHERE media_id = sqlc.arg(media_id);
