package media

import (
	"context"
	"media/internal/adapter/postgres/sqlc"
	domain "media/internal/core/domain/media"

	"github.com/google/uuid"
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

func (r *Repository) GetByID(ctx context.Context, id uuid.UUID) (*domain.Media, error) {
	m, err := r.q.GetMediaFileByID(ctx, id)
	if err != nil {
		return nil, err
	}
	return toDomain(m), nil
}

func (r *Repository) GetByOwner(
	ctx context.Context,
	ownerType string,
	ownerID uuid.UUID,
) ([]domain.Media, error) {
	mediaFiles, err := r.q.ListOwnedMediaFiles(ctx, sqlc.ListOwnedMediaFilesParams{
		OwnerType: ownerType,
		OwnerID:   ownerID,
	})
	if err != nil {
		return nil, err
	}

	result := make([]domain.Media, len(mediaFiles))
	for i, m := range mediaFiles {
		result[i] = *toDomain(m)
	}
	return result, nil
}

func (r *Repository) Delete(ctx context.Context, id uuid.UUID) error {
	return r.q.DeleteMediaFile(ctx, id)
}
