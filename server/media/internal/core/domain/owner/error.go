package owner

import "errors"

var (
	ErrInvalidType = errors.New("owner: invalid owner type")
	ErrNotFound    = errors.New("owner: not found")
)
