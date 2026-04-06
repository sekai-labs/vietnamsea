package storageobj

import (
	"time"

	"github.com/google/uuid"
)

type MediaExternalStorage struct {
	Id          uuid.UUID
	MediaId     uuid.UUID
	AccountId   uuid.UUID
	ExternalUrl string
	CreatedAt   time.Time
}

type MediaStorageObject struct {
	Id                uuid.UUID
	MediaId           uuid.UUID
	StorageProviderId uuid.UUID
	StorageKey        string
	IsPrimary         bool
	CreatedAt         time.Time
}
