import controller.FileBackedTaskManager;
import enums.TaskStatus;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        System.out.println("Приехали!");
/*        String TestFilePath = "src/resources/TestSaveFile";
        FileBackedTaskManager manager = new FileBackedTaskManager(new File(TestFilePath));
        Duration duration = Duration.ofHours(1);
        LocalDateTime startTime = LocalDateTime.now();
        Task task1 = new Task("Task1", "desc1", TaskStatus.NEW, duration , startTime);
        Task task2 = new Task("Task2", "desc2", TaskStatus.IN_PROGRESS, Duration.ZERO, startTime);
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Epic1", "descEpic", TaskStatus.IN_PROGRESS, duration, startTime.plusHours(1), startTime.plusHours(22));
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.IN_PROGRESS, Duration.ofMinutes(1), startTime.plusMinutes(80));
        Subtask subtask1 = new Subtask(epic1.getId(),"subtask1", "desc1", TaskStatus.NEW, Duration.ofMinutes(10), startTime.plusMinutes(90));
        //manager.createSubtask(subtask2);
        manager.createSubtask(subtask1);*/
    }
}
