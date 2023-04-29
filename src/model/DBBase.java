package model;

public abstract class DBBase {
    public enum BaseStatus{
        loaded, created, edited, deleted
    }
    private int id;
    private BaseStatus status = BaseStatus.loaded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BaseStatus getStatus() {
        return status;
    }

    public void setStatus(BaseStatus status) {
        if (this.status != BaseStatus.created || status == BaseStatus.deleted) {
            this.status = status;
        }
    }
}
