package media

import (
	"media/internal/adapter/postgres/sqlc"
	"media/internal/core/domain/media"
)

func toDomain(m sqlc.MediaFile) *media.Media {
	return &media.Media{
		ID:       m.ID,
		FileName: m.FileName,
		MimeType: media.MimeType(m.MimeType),
		Size:     m.Size,
		Status:   media.Status(m.Status),
	}
}
