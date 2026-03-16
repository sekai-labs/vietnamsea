package storageobj

import "errors"

var (
	ErrNotFound          = errors.New("storageobj: not found")
	ErrNoPrimary         = errors.New("storageobj: no primary object for media")
	ErrDuplicateProvider = errors.New("storageobj: provider already holds this media")
)
