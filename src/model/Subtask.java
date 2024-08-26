package model;

import enums.TaskStatus;
import enums.TaskTypes;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public Subtask(int epicId, String name, String description, TaskStatus status) {
        super(name, description, status);
        this.epicId = epicId;
        this.setTaskType(TaskTypes.SUBTASK);
    }

    public int getEpicId() {

        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getId() +
                "," + getTaskType() +
                "," + getName() +
                "," + getStatus() +
                "," + getDescription() +
                "," + getEpicId();
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
