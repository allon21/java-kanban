package test.controller;

import controller.FileBackedTaskManager;
import enums.TaskStatus;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileBackedTaskManagerTest {
    private static final String TestFilePath = "src/resources/TestSaveFile";
    public FileBackedTaskManager manager;

    @BeforeEach
    void setUp() {
        try{
            Files.deleteIfExists(Paths.get(TestFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        manager = new FileBackedTaskManager(new File(TestFilePath));
    }

    @AfterEach
    void del() {
        try{
            Files.deleteIfExists(Paths.get(TestFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromFileTaskTest() {
        Task task1 = new Task("Task1" , "desc1", TaskStatus.NEW);
        Task task2 = new Task("Task2" , "desc2", TaskStatus.IN_PROGRESS);
        manager.createTask(task1);
        manager.createTask(task2);
    }

    @Test
    void loadFromFileSubtaskAndEpicTest() {
        Epic epic1 = new Epic("Epic1", "descEpic",TaskStatus.NEW);
        Subtask subtask1 = new Subtask(epic1.getId(),"Task1" , "desc1",TaskStatus.NEW);
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
    }
}


