import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    public ArrayList<Integer> subtasksList = new ArrayList<>();


    public Epic(String name, String description, int id, TaskStatus status) {

        super(name, description, id, status);
    }

    public Epic(String name, String description, TaskStatus status) {

        super(name, description, status);
    }

    public ArrayList<Integer> getSubtasks() {

        return subtasksList;
    }

    public void setSubtasks(Subtask subtasks) {
        this.subtasksList.add(subtasks.getId());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasksId=" + subtasksList.toString() +
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
        Epic epic = (Epic) o;
        return Objects.equals(subtasksList, epic.subtasksList);
    }

}
