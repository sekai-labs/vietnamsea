package media

import (
	"time"

	"github.com/google/uuid"
)

type Media struct {
	ID uuid.UUID

	FileName string
	MimeType MimeType
	Size     int64

	Checksum string

	Status       Status
	UploadBy     uuid.UUID
	OriginalName string
	DurationMs   int32
	CreatedAt    time.Time
}

func (m *Media) IsImage() bool {
	return m.MimeType.IsImage()
}

func (m *Media) IsVideo() bool {
	return m.MimeType.IsVideo()
}

func (m *Media) IsImmutable() bool {
	return m.Status == StatusLocked
}

func (m *Media) CanDelete() bool {
	return !m.Status.IsTerminal() && m.Status != StatusDeleted
}
