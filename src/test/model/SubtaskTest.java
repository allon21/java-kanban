package test.model;

import controller.Managers;
import controller.TaskManager;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtaskTest {
    TaskManager taskManager;
    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        subtask1 = new Subtask("Эпик 1", "Описание", TaskStatus.NEW);
        subtask2 = new Subtask("Эпик 2", "Описание", TaskStatus.NEW);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
    }

    @Test
    void shouldTwoTasksBeEqualsIfSameId() {
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2, "Две подзадачи с одинаковым id, не равны друг другу");
    }

}