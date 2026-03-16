package owner

import "github.com/google/uuid"

type MediaOwner struct {
	ID        uuid.UUID
	MediaID   uuid.UUID
	OwnerType string
	OwnerID   uuid.UUID
	Field     string
	CreatedAt string
	UpdatedAt string
}
