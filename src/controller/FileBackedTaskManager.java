package controller;

import enums.TaskStatus;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File saveFile;
    private File historySaveFile = new File("src/resources/historyFile.csv");

    public FileBackedTaskManager(File saveFile) {
        super();
        this.saveFile = saveFile;
    }

    private void save() {
        saveTaskToFile(saveFile, getListOfAllTasks());
        saveTaskToFile(historySaveFile, getHistory());
    }

    private void saveTaskToFile(File file, List<Task> tasks) {
        String title = "id, type, name, status, description, duration, startTime, epicID, endTime\n";
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(title);
            for (Task task : tasks) {
                fileWriter.write(task.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи файла: " + file.getAbsolutePath(), e);
        }
    }

  public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Task task = fileManager.fromString(line);
                    if (task != null) {
                        fileManager.createTask(task);
                    }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки файла", e);
        }
        return fileManager;
    }

    private Task fromString(String s) {
        String[] tasks = s.split(",");
        int epicId = 0;
        int id = Integer.parseInt(tasks[0].trim());
        String type = tasks[1].trim();
        String name = tasks[2].trim();
        TaskStatus status = TaskStatus.valueOf(tasks[3].trim());
        String descrip = tasks[4].trim();
        Duration duration = Duration.ofMinutes(Long.parseLong(tasks[5].trim()));
        LocalDateTime startTime = LocalDateTime.parse(tasks[6].trim());
        switch (type) {
            case "TASK":
                return new Task(name, descrip, status, duration, startTime);
            case "SUBTASK":
                epicId = Integer.parseInt(tasks[8].trim());
                return new Subtask(epicId, name, descrip, status, duration, startTime);
            case "EPIC":
                LocalDateTime endTime = LocalDateTime.parse(tasks[9]);
                return new Epic(name, descrip, status, duration, startTime, endTime);
            default:
                return null;
        }
    }

    @Override
    public List<Task> getListOfAllTasks() {
        List<Task> listOfAllTasks = super.getListOfAllTasks();
        return listOfAllTasks;
    }

    @Override
    public List<Task> getTasks() {
        List<Task> task = super.getTasks();
        return task;
    }

    @Override
    public List<Epic> getEpics() {
        List<Epic> epic = super.getEpics();
        return epic;
    }

    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> subtask = super.getSubtasks();
        return subtask;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void subtaskUpdate(Subtask subtask) {
        super.subtaskUpdate(subtask);
        save();
    }

    @Override
    public void taskUpdate(Task task) {
        super.taskUpdate(task);
        save();
    }

    @Override
    public void epicUpdate(Epic epic) {
        super.epicUpdate(epic);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicsById(int id) {
        super.removeEpicsById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }
}
