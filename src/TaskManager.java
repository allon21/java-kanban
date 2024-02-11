
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;
    public static int idCounter = 0;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
        }
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        epics.clear();

    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {

        return (Epic) epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return (Subtask) subtasks.get(id);
    }

    public void createTask(Task task) {
        task.setId(++idCounter);
        tasks.put(idCounter, task);
    }

    public void createEpic(Epic epic) {
        epic.setId(++idCounter);
        epics.put(idCounter, epic);
    }

    public void createSubtask(Subtask subtask, Epic epic) {
        subtask.setId(++idCounter);

        if (epic == null) {
            return;
        } else {
            epic.setSubtasks(subtask);
            subtasks.put(subtask.getId(), subtask);
            checkStatus(epics.get(subtask.getEpicId()));

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
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    public void removeEpicsById(int id) {
        if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpicId();
            Subtask subtask = subtasks.remove(id);
            if (subtask != null) {
                subtasks.remove(id);
                epics.get(epicId).getSubtasks().remove(id);
                checkStatus(epics.get(subtask.getEpicId()));
            }
        }
    }

    public void removeSubtaskById(int id) {
        if (epics.containsKey(id)) {
            epics.get(id).getSubtasks().clear();
            epics.remove(id);
        }

    }

    public void checkStatus(Epic epic) {
        int newCounter = 0;
        int doneCounter = 0;
        int inProgressCounter = 0;

        ArrayList<Integer> subtaskList = epic.getSubtasks();

        for (int subId : subtaskList) {
            if (subtasks.get(subId).getStatus() == TaskStatus.DONE) {
                doneCounter++;
                if (doneCounter == subtaskList.size()) {
                    epic.setStatus(TaskStatus.DONE);
                }
            } else if (subtasks.get(subId).getStatus() == TaskStatus.NEW) {
                newCounter++;
                if (newCounter == subtaskList.size()) {
                    epic.setStatus(TaskStatus.NEW);
                }
            } else if (subtasks.get(subId).getStatus() == TaskStatus.IN_PROGRESS) {
                inProgressCounter++;
                if (epic.getStatus() != TaskStatus.DONE && inProgressCounter != 0) {
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }
            }
        }
    }
}
