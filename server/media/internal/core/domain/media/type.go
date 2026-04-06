package media

type MimeType string

const (
	MimeJPEG MimeType = "image/jpeg"
	MimePNG  MimeType = "image/png"
	MimeWEBP MimeType = "image/webp"
	MimeMP4  MimeType = "video/mp4"
	MimeWEBM MimeType = "video/webm"
	MimePDF  MimeType = "application/pdf"
)

var allowedMimes = map[MimeType]bool{
	MimeJPEG: true, MimePNG: true, MimeWEBP: true,
	MimeMP4: true, MimeWEBM: true, MimePDF: true,
}

func (m MimeType) IsAllowed() bool  { return allowedMimes[m] }
func (m MimeType) IsImage() bool    { return m == MimeJPEG || m == MimePNG || m == MimeWEBP }
func (m MimeType) IsVideo() bool    { return m == MimeMP4 || m == MimeWEBM }
func (m MimeType) IsDocument() bool { return m == MimePDF }
