package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;
    private static int idCounter = 0;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
        }
        subtasks.clear();
        epics.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            checkStatus(epic);
        }
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void createTask(Task task) {
        task.setId(++idCounter);
        tasks.put(idCounter, task);
    }

    public void createEpic(Epic epic) {
        epic.setId(++idCounter);
        epics.put(idCounter, epic);
    }

    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        subtask.setId(++idCounter);
        if (epic != null) {
            epic.setSubtasks(subtask);
            subtasks.put(subtask.getId(), subtask);
            checkStatus(epic);
        }
    }
    public void subtaskUpdate(Subtask subtask) {
        if (subtasks.get(subtask.getId()) != null) {
            int subtaskId = subtask.getId();
            int epicId = subtasks.get(subtaskId).getEpicId();
            subtask.setEpicId(epicId);
            subtasks.put(subtaskId, subtask);
            checkStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void taskUpdate(Task task) {
        if (tasks.get(task.getId()) != null) {
            int id = task.getId();
            tasks.put(id, task);
        }
    }

    public void epicUpdate(Epic epic) {
        if (epics.get(epic.getId()) != null) {
            int id = epic.getId();
            epics.put(id, epic);
        }
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeEpicsById(int id) {
        if (epics.containsKey(id)) {
            epics.get(id).getSubtasks().clear();
            epics.remove(id);
            checkStatus(epics.get(id));
        }
    }

    public void removeSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpicId();
            Subtask subtask = subtasks.remove(id);
            if (subtask != null) {
                subtasks.remove(id);
                epics.get(epicId).getSubtasks().remove(Integer.valueOf(id));
                checkStatus(epics.get(subtask.getEpicId()));
            }
        }
    }

    public void checkStatus(Epic epic) {
        int newCounter = 0;
        int doneCounter = 0;
        int inProgressCounter = 0;
        ArrayList<Integer> subtaskIdList = epic.getSubtasks();

        for (int subtaskId : subtaskIdList) {
            TaskStatus status = subtasks.get(subtaskId).getStatus();
            switch (status) {
                case DONE:
                    doneCounter++;
                    break;
                case NEW:
                    newCounter++;
                    break;
                case IN_PROGRESS:
                    inProgressCounter++;
                    break;
            }
        }

        if (doneCounter == subtaskIdList.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (newCounter == subtaskIdList.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else if (inProgressCounter == subtaskIdList.size()) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
