package media

import (
	"context"
	"media/internal/adapter/postgres/sqlc"
	domain "media/internal/core/domain/media"
)

func (r *Repository) Create(ctx context.Context, m *domain.Media) error {
	_, err := r.q.CreateMediaFile(ctx, sqlc.CreateMediaFileParams{
		FileName: m.FileName,
		MimeType: string(m.MimeType),
		Size:     m.Size,
		Status:   string(m.Status),
	})

	return err
}
