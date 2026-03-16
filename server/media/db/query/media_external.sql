-- name: CreateExternalStorageAccount :one
INSERT INTO external_storage_accounts (
    id,
    partner_id,
    provider_type,
    bucket,
    region,
    endpoint
)
VALUES (
    sqlc.arg(id),
    sqlc.arg(partner_id),
    sqlc.arg(provider_type),
    sqlc.arg(bucket),
    sqlc.arg(region),
    sqlc.arg(endpoint)
)
RETURNING
    id,
    partner_id,
    provider_type,
    bucket,
    region,
    endpoint,
    created_at;

-- name: GetExternalStorageAccountByID :one
SELECT
    id,
    partner_id,
    provider_type,
    bucket,
    region,
    endpoint,
    created_at
FROM external_storage_accounts
WHERE id = sqlc.arg(id);

-- name: ListExternalStorageAccountsByPartnerID :many
SELECT
    id,
    partner_id,
    provider_type,
    bucket,
    region,
    endpoint,
    created_at
FROM external_storage_accounts
WHERE partner_id = sqlc.arg(partner_id)
ORDER BY created_at DESC;

-- name: CreateMediaExternalObject :one
INSERT INTO media_external_objects (
    media_id,
    account_id,
    external_url
)
VALUES (
    sqlc.arg(media_id),
    sqlc.arg(account_id),
    sqlc.arg(external_url)
)
RETURNING
    id,
    media_id,
    account_id,
    external_url,
    created_at;

-- name: ListMediaExternalObjectsByMediaID :many
SELECT
    id,
    media_id,
    account_id,
    external_url,
    created_at
FROM media_external_objects
WHERE media_id = sqlc.arg(media_id)
ORDER BY created_at ASC;

-- name: DeleteMediaExternalObjectsByMediaID :exec
DELETE FROM media_external_objects
WHERE media_id = sqlc.arg(media_id);
