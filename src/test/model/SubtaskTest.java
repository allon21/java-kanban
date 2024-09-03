package test.model;

import controller.Managers;
import controller.TaskManager;
import model.Subtask;
import enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubtaskTest {
    TaskManager taskManager;
    private Subtask subtask1;
    private Subtask subtask2;
    private Subtask subtask3;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        subtask1 = new Subtask("Подзадача 1", "Описание", TaskStatus.NEW);
        subtask2 = new Subtask("Подзадача 2", "Описание", TaskStatus.NEW);
        subtask3 = new Subtask("Подзадача 3", "Описание", TaskStatus.NEW);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
    }

    @Test
    void shouldTwoTasksBeEqualsIfSameId() {
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2, "Две подзадачи с одинаковым id, не равны друг другу");
    }

    @Test
    public void shouldEpicHaveSameNameWhenCreated() {
        String expectedName = subtask3.getName();
        taskManager.createSubtask(subtask3);
        assertEquals(expectedName, subtask3.getName(), "Неверное значение имени подзадачи после создания");
    }

    @Test
    public void shouldEpicHaveSameDescriptionWhenCreated() {
        String expectedDescription = subtask3.getDescription();
        taskManager.createSubtask(subtask3);
        assertEquals(expectedDescription, subtask3.getDescription(),
                "Неверное значение описания подзадачи после создания");
    }

    @Test
    public void shouldEpicHaveSameStatusWhenCreated() {
        TaskStatus expectedStatus = subtask3.getStatus();
        taskManager.createSubtask(subtask3);
        assertEquals(expectedStatus, subtask3.getStatus(),
                "Неверное значение статуса подзадачи после создания");
    }

    @Test
    public void shouldEpicNotBeNullAfterCreation() {
        taskManager.createSubtask(subtask3);
        assertNotNull(subtask3, "Подзадача не должен быть null после создания");
    }

}