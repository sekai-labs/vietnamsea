package policy

import "errors"

var (
	ErrNotFound          = errors.New("policy: not found")
	ErrInvalidVisibility = errors.New("policy: invalid visibility value")
	ErrForbidden         = errors.New("policy: caller is not allowed to access this media")
)
