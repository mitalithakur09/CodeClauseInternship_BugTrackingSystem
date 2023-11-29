public class Bug {
    private int id;
    private String title;
    private String description;
    private String status;
    private int reporterId;

    public Bug() {
        // Default constructor
    }

    public Bug(String title, String description, String status, int reporterId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.reporterId = reporterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", reporterId=" + reporterId +
                '}';
    }
}

