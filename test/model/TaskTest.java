package test.model;

import controller.Managers;
import controller.TaskManager;
import model.Task;
import enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTest {
    TaskManager taskManager;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        task1 = new Task("Задача 1", "Описание", TaskStatus.NEW);
        task2 = new Task("Задача 2", "Описание", TaskStatus.NEW);
        task3 = new Task("Задача 2", "Описание", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
    }

    @Test
    void shouldTwoTasksBeEqualsIfSameId() {
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2, "Две задачи с одинаковым id, не равны друг другу.");
    }

    @Test
    public void shouldEpicHaveSameNameWhenCreated() {
        String expectedName = task3.getName();
        taskManager.createTask(task3);
        assertEquals(expectedName, task3.getName(), "Неверное значение имени задачи после создания");
    }

    @Test
    public void shouldEpicHaveSameDescriptionWhenCreated() {
        String expectedDescription = task3.getDescription();
        taskManager.createTask(task3);
        assertEquals(expectedDescription, task3.getDescription(),
                "Неверное значение описания задачи после создания");
    }

    @Test
    public void shouldEpicHaveSameStatusWhenCreated() {
        TaskStatus expectedStatus = task3.getStatus();
        taskManager.createTask(task3);
        assertEquals(expectedStatus, task3.getStatus(),
                "Неверное значение статуса задачи после создания");
    }

    @Test
    public void shouldEpicNotBeNullAfterCreation() {
        taskManager.createTask(task3);
        assertNotNull(task3, "Задача не должна быть null после создания");
    }

}