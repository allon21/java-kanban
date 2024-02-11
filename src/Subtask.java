import java.util.Objects;

public class Subtask extends Task {


    private int epicId;

    public Subtask(String name, String description, int id, TaskStatus status) {

        super(name, description, id, status);
    }

    public Subtask(int id, String name, String description, TaskStatus status) {
        super(name, description, status);
        this.epicId = id;
    }

    public int getEpicId() {

        return epicId;
    }

    public void setEpicId(int epicId) {

        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", taskName='" + getName() + '\'' +
                ", taskDescription='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

}
