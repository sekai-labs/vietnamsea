CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS storage_providers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    provider_type VARCHAR(50) NOT NULL,
    endpoint VARCHAR(255),
    region VARCHAR(50),
    bucket VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    priority SMALLINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS media_files (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    file_name VARCHAR(255) NOT NULL,
    mime_type VARCHAR(100) NOT NULL,

    size BIGINT NOT NULL,

    checksum CHAR(64),

    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    uploaded_by UUID,
    original_name VARCHAR(255),
    duration_ms INTEGER,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_media_status ON media_files(status);

CREATE TABLE media_storage_objects (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    media_id    UUID NOT NULL,
    storage_provider_id UUID NOT NULL,
    storage_key VARCHAR(512) NOT NULL,
    is_primary  BOOLEAN NOT NULL DEFAULT true,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_storage_media FOREIGN KEY (media_id)
        REFERENCES media_files(id) ON DELETE CASCADE,
    CONSTRAINT fk_storage_provider FOREIGN KEY (storage_provider_id)
        REFERENCES storage_providers(id)
);

CREATE TABLE IF NOT EXISTS external_storage_accounts (
    id UUID PRIMARY KEY,

    partner_id UUID NOT NULL,

    provider_type VARCHAR(50) NOT NULL,

    bucket VARCHAR(100) NOT NULL,

    region VARCHAR(50),
    endpoint VARCHAR(255),

    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_external_account_partner
    ON external_storage_accounts(partner_id);

CREATE TABLE IF NOT EXISTS media_external_objects (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    media_id    UUID NOT NULL,

    account_id UUID,

    external_url VARCHAR(1024) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_external_media
        FOREIGN KEY (media_id)
        REFERENCES media_files(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_external_account
        FOREIGN KEY (account_id)
        REFERENCES external_storage_accounts(id)
);

CREATE TABLE IF NOT EXISTS media_variants (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    media_id UUID NOT NULL,

    variant_name VARCHAR(50) NOT NULL,

    storage_provider_id UUID NOT NULL,

    storage_key VARCHAR(512) NOT NULL,

    width INTEGER,
    height INTEGER,

    size BIGINT,

    format VARCHAR(50),
    quality SMALLINT,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_variant_media
        FOREIGN KEY (media_id)
        REFERENCES media_files(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_variant_storage
        FOREIGN KEY (storage_provider_id)
        REFERENCES storage_providers(id),

    CONSTRAINT uq_media_variant
        UNIQUE (media_id, variant_name)
);

CREATE INDEX idx_variant_status ON media_variants(status);

CREATE TABLE IF NOT EXISTS media_owners (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    media_id UUID NOT NULL,

    owner_type VARCHAR(100) NOT NULL,
    owner_id UUID NOT NULL,

    field VARCHAR(100),

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_owner_media
        FOREIGN KEY (media_id)
        REFERENCES media_files(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS media_access_policies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    media_id UUID NOT NULL,

    visibility VARCHAR(50) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_policy_media
        FOREIGN KEY (media_id)
        REFERENCES media_files(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_visibility
        CHECK (visibility IN ('public','private','protected'))
);

CREATE INDEX idx_policy_media ON media_access_policies(media_id);


CREATE INDEX idx_media_owner
ON media_owners(owner_type, owner_id);

CREATE INDEX idx_media_owner_media
ON media_owners(media_id);

CREATE INDEX idx_media_variant_media
ON media_variants(media_id);

CREATE INDEX idx_storage_provider
ON media_storage_objects(storage_provider_id);

CREATE INDEX idx_external_media
ON media_external_objects(media_id);

CREATE INDEX idx_media_checksum
ON media_files(checksum);
