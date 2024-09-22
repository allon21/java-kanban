package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import enums.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();;
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();;
    private TreeSet<Task> tasksListPriorityByStartTime = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private static int idCounter = 0;

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(tasksListPriorityByStartTime);
    }

    public boolean isOverlapping(Task existTask, Task newTask) {
        return existTask.getStartTime().isBefore((newTask.getEndTime())) &&
                newTask.getStartTime().isBefore(existTask.getEndTime());
    }

    public boolean isTaskTimeValid(Task task) {
        for (Task existTask : tasksListPriorityByStartTime) {
            if (isOverlapping(existTask, task)) {
                return false;
            }
/*
            if (existTask.getEndTime().isBefore(task.getStartTime())) {
                continue;
            }*/
        }
        return true;
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
        Task task = tasks.get(id);
        if (task != null){
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null){
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null){
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void createTask(Task task) {
        if (!isTaskTimeValid(task)){
            throw new IllegalArgumentException("Задача пересакается по времени.");
        }
        task.setId(++idCounter);
        tasks.put(idCounter, task);
        addTask(task);
    }

    public void addTask(Task task){
        if (task.getStartTime() != null){
            tasksListPriorityByStartTime.add(task);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        if (!isTaskTimeValid(epic)){
            throw new IllegalArgumentException("Задача пересакается по времени.");
        }
        epic.setId(++idCounter);
        epics.put(idCounter, epic);
        addTask(epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (!isTaskTimeValid(subtask)){
            throw new IllegalArgumentException("Задача пересакается по времени.");
        }
        Epic epic = epics.get(subtask.getEpicId());
        subtask.setId(++idCounter);
        if (epic != null) {
            epic.setSubtasks(subtask);
            subtasks.put(subtask.getId(), subtask);
            addTask(subtask);
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
       tasks.values().forEach(task -> tasksListPriorityByStartTime.remove(task));
       tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.values().forEach(epic -> epic.getSubtasks().clear());
        subtasks.values().forEach(subtask -> tasksListPriorityByStartTime.remove(subtask));
        epics.values().forEach(epic -> tasksListPriorityByStartTime.remove(epic));
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.values().forEach(subtask -> tasksListPriorityByStartTime.remove(subtask));
        subtasks.clear();
        epics.values().forEach(this::checkStatus);
    }

    @Override
    public void removeTaskById(int id) {
        Task removedTask = tasks.remove(id);
        if (removedTask != null){
            historyManager.remove(id);
            tasksListPriorityByStartTime.remove(removedTask);
        }
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
