package test.model;

import controller.Managers;
import controller.TaskManager;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    TaskManager taskManager;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        task1 = new Task("Задача 1", "Описание", TaskStatus.NEW);
        task2 = new Task("Задача 2", "Описание", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
    }

    @Test
    void shouldTwoTasksBeEqualsIfSameId() {
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2, "Две задачи с одинаковым id, не равны друг другу.");
    }
}