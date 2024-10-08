package controller;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getListOfAllTasks();

    List<Task> getPrioritizedTasks();

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void subtaskUpdate(Subtask subtask);

    void taskUpdate(Task task);

    void epicUpdate(Epic epic);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    void removeTaskById(int id);

    void removeEpicsById(int id);

    void removeSubtaskById(int id);

    void checkStatus(Epic epic);

    List<Task> getHistory();


}
