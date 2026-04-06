package media

type Status string

const (
	StatusPending     Status = "pending"
	StatusUploading   Status = "uploading"
	StatusProcessing  Status = "processing"
	StatusReady       Status = "ready"
	StatusQuarantined Status = "quarantined"
	StatusLocked      Status = "locked"
	StatusDeleted     Status = "deleted"
)

func (s Status) IsTerminal() bool {
	return s == StatusReady || s == StatusQuarantined ||
		s == StatusLocked || s == StatusDeleted
}
