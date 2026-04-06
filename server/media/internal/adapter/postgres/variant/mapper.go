package variant

import (
	"media/internal/adapter/postgres/sqlc"
	"media/internal/core/domain/variant"
)

func toDomain(v *sqlc.MediaVariant) *variant.MediaVariant {
	return &variant.MediaVariant{
		Id:                v.ID,
		MediaId:           v.MediaID,
		VariantName:       v.VariantName,
		StorageProviderId: v.StorageProviderID,
		StorageKey:        v.StorageKey,
		Width:             int8(v.Width.Int32),
		Height:            int8(v.Height.Int32),
		Size:              int8(v.Size.Int64),
		Format:            v.Format.String,
		Quality:           int8(v.Quality.Int16),
		Status:            v.Status,
		CreatedAt:         v.CreatedAt.Time,
		UpdatedAt:         v.UpdatedAt.Time,
	}
}
