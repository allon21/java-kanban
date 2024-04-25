package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import enums.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private static int idCounter = 0;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    @Override
    public List<Task> getListOfAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(getTasks());
        allTasks.addAll(getEpics());
        allTasks.addAll(getSubtasks());
        return allTasks;
    }


    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void createTask(Task task) {
        task.setId(++idCounter);
        tasks.put(idCounter, task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(++idCounter);
        epics.put(idCounter, epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        subtask.setId(++idCounter);
        if (epic != null) {
            epic.setSubtasks(subtask);
            subtasks.put(subtask.getId(), subtask);
            checkStatus(epic);
        }
    }

    @Override
    public void subtaskUpdate(Subtask subtask) {
        if (subtasks.get(subtask.getId()) != null) {
            int subtaskId = subtask.getId();
            int epicId = subtasks.get(subtaskId).getEpicId();
            subtask.setEpicId(epicId);
            subtasks.put(subtaskId, subtask);
            checkStatus(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public void taskUpdate(Task task) {
        if (tasks.get(task.getId()) != null) {
            int id = task.getId();
            tasks.put(id, task);
        }
    }

    @Override
    public void epicUpdate(Epic epic) {
        if (epics.get(epic.getId()) != null) {
            int id = epic.getId();
            epics.put(id, epic);
        }
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            checkStatus(epic);
        }
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicsById(int id) {
        if (epics.containsKey(id)) {
            epics.get(id).getSubtasks().clear();
            epics.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.remove(id);
            int epicId = subtasks.get(id).getEpicId();
            Subtask subtask = subtasks.remove(id);
            if (subtask != null) {
                subtasks.remove(id);
                epics.get(epicId).getSubtasks().remove(Integer.valueOf(id));
                checkStatus(epics.get(subtask.getEpicId()));
            }
        }
    }

    @Override
    public void checkStatus(Epic epic) {
        int newCounter = 0;
        int doneCounter = 0;
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
            }
        }

        if (doneCounter == subtaskIdList.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (newCounter == subtaskIdList.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
