package media

import (
	"context"
	domain "media/internal/core/domain/media"

	"github.com/google/uuid"
)

type Repository interface {
	Create(ctx context.Context, media *domain.Media) error

	GetByID(ctx context.Context, id uuid.UUID) (*domain.Media, error)

	GetByOwner(
		ctx context.Context,
		ownerType string,
		ownerID uuid.UUID,
	) ([]domain.Media, error)

	Delete(ctx context.Context, id uuid.UUID) error
}
