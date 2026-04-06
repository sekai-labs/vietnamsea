package policy

import (
	"media/internal/adapter/postgres/sqlc"
	"media/internal/core/domain/policy"
)

func toDomain(p *sqlc.MediaAccessPolicy) *policy.MediaAccessPolicy {
	return &policy.MediaAccessPolicy{
		Id:         p.ID,
		MediaId:    p.MediaID,
		Visibility: p.Visibility,
		CreatedAt:  p.CreatedAt.Time,
		UpdatedAt:  p.UpdatedAt.Time,
	}
}
