package variant

import (
	"time"

	"github.com/google/uuid"
)

type MediaVariant struct {
	Id                uuid.UUID
	MediaId           uuid.UUID
	VariantName       string
	StorageProviderId uuid.UUID
	StorageKey        string
	Width             int8
	Height            int8
	Size              int8
	Format            string
	Quality           int8
	Status            string
	CreatedAt         time.Time
	UpdatedAt         time.Time
}
