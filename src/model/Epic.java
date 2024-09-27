package model;

import enums.TaskStatus;
import enums.TaskTypes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtasksList = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description, TaskStatus status, Duration duration, LocalDateTime startTime,
                LocalDateTime endTime) {
        super(name, description, status, duration, startTime);
        this.setTaskType(TaskTypes.EPIC);
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasksList;
    }

    public void setSubtasks(Subtask subtasks) {
        this.subtasksList.add(subtasks.getId());
    }

    @Override
    public String toString() {
        return getId() +
                "," + getTaskType() +
                "," + getName() +
                "," + getStatus() +
                "," + getDescription() +
                ", " + getDuration() +
                ", " + getStartTime() +
                ", " + getId() +
                ", " + getEndTime();
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
