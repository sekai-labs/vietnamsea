package policy

import (
	"time"

	"github.com/google/uuid"
)

type MediaAccessPolicy struct {
	Id         uuid.UUID
	MediaId    uuid.UUID
	Visibility string
	CreatedAt  time.Time
	UpdatedAt  time.Time
}
