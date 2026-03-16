package media

import "media/internal/adapter/postgres/sqlc"

type Repository struct {
	q *sqlc.Queries
}

func New(q *sqlc.Queries) *Repository {
	return &Repository{
		q: q,
	}
}
