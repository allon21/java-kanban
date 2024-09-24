package test;

import controller.FileBackedTaskManager;
import enums.TaskStatus;
import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    FileBackedTaskManager manager;

    @BeforeEach
    void setUp() {
        String TestFilePath = "src/resources/TestSaveFile";
        manager = new FileBackedTaskManager(new File(TestFilePath));
    }

    @Test
    void epicAllSubtaskNew() {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.NEW,
                Duration.ofHours(1), LocalDateTime.now());
        manager.createSubtask(subtask2);
        Subtask subtask1 = new Subtask(epic1.getId(),"subtask1", "desc1", TaskStatus.NEW,
                Duration.ofHours(2), LocalDateTime.now().plusHours(2));
        manager.createSubtask(subtask1);
        assertEquals(TaskStatus.NEW, epic1.getStatus(), "Epic должен быть со статусом NEW");
    }

    @Test
    void epicAllSubtaskInProgress() {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.IN_PROGRESS,
                Duration.ofHours(1), LocalDateTime.now());
        manager.createSubtask(subtask2);
        Subtask subtask1 = new Subtask(epic1.getId(),"subtask1", "desc1", TaskStatus.IN_PROGRESS,
                Duration.ofHours(2), LocalDateTime.now().plusHours(2));
        manager.createSubtask(subtask1);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Epic должен быть со статусом InProgress");
    }

    @Test
    void epicAllSubtaskDone() {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.DONE,
                Duration.ofHours(1), LocalDateTime.now());
        manager.createSubtask(subtask2);
        Subtask subtask1 = new Subtask(epic1.getId(),"subtask1", "desc1", TaskStatus.DONE,
                Duration.ofHours(2), LocalDateTime.now().plusHours(2));
        manager.createSubtask(subtask1);
        assertEquals(TaskStatus.DONE, epic1.getStatus(), "Epic должен быть со статусом DONE");
    }

    @Test
    void epicSubtaskDoneAndNew() {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.DONE,
                Duration.ofHours(1), LocalDateTime.now());
        manager.createSubtask(subtask2);
        Subtask subtask1 = new Subtask(epic1.getId(),"subtask1", "desc1", TaskStatus.NEW,
                Duration.ofHours(2), LocalDateTime.now().plusHours(2));
        manager.createSubtask(subtask1);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Epic должен быть со статусом InProgress");
    }
}