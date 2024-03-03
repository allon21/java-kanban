package test.model;

import controller.Managers;
import controller.TaskManager;
import model.Epic;
import model.Subtask;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager taskManager;
    private Epic epic1;
    private Epic epic2;
    private Epic epic3;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        epic1 = new Epic("Эпик 1", "Описание", TaskStatus.NEW);
        epic2 = new Epic("Эпик 2", "Описание", TaskStatus.NEW);
        epic3 = new Epic("Эпик 3", "Описание", TaskStatus.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
    }

    @Test
    void shouldTwoTasksBeEqualsIfSameId() {
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2, "Два эпика с одинаковым id, не равны друг другу");
    }

    @Test
    public void shouldEpicHaveSameNameWhenCreated() {
        String expectedName = epic3.getName();
        taskManager.createEpic(epic3);
        assertEquals(expectedName, epic3.getName(), "Неверное значение имени эпика после создания");
    }

    @Test
    public void shouldEpicHaveSameDescriptionWhenCreated() {
        String expectedDescription = epic3.getDescription();
        taskManager.createEpic(epic3);
        assertEquals(expectedDescription, epic3.getDescription(), "Неверное значение описания эпика после создания");
    }

    @Test
    public void shouldEpicHaveSameStatusWhenCreated() {
        TaskStatus expectedStatus = epic3.getStatus();
        taskManager.createEpic(epic3);
        assertEquals(expectedStatus, epic3.getStatus(), "Неверное значение статуса эпика после создания");
    }

    @Test
    public void shouldEpicNotBeNullAfterCreation() {
        taskManager.createEpic(epic3);
        assertNotNull(epic3, "Эпик не должен быть null после создания");
    }

}