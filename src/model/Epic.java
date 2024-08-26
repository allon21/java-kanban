package model;

import enums.TaskStatus;
import enums.TaskTypes;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtasksList = new ArrayList<>();
    public Epic(String name, String description, TaskStatus status) {

        super(name, description, status);
        this.setTaskType(TaskTypes.EPIC);
    }
    public ArrayList<Integer> getSubtasks() {

        return subtasksList;
    }

    public void setSubtasks(Subtask subtasks) {

        this.subtasksList.add(subtasks.getId());
    }

    @Override
    public String toString() {
        return super.toString();
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
