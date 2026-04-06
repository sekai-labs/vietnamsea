package media

import "errors"

var (
	ErrNotFound         = errors.New("media: file not found")
	ErrImmutable        = errors.New("media: file is immutable")
	ErrInvalidMime      = errors.New("media: mime type not allowed")
	ErrChecksumMismatch = errors.New("media: checksum mismatch")
	ErrFileTooLarge     = errors.New("media: file exceeds size limit")
)
