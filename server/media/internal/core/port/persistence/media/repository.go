package media

import (
	"context"
	domain "media/internal/core/domain/media"
)

type Repository interface {
	Create(ctx context.Context, media *domain.Media) error

	GetByID(ctx context.Context, id string) (*domain.Media, error)

	GetByOwner(
		ctx context.Context,
		ownerType string,
		ownerID string,
	) ([]domain.Media, error)

	Delete(ctx context.Context, id string) error
}
