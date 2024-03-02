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

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        epic1 = new Epic("Задача 1", "Описание", TaskStatus.NEW);
        epic2 = new Epic("Задача 2", "Описание", TaskStatus.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
    }

    @Test
    void shouldTwoTasksBeEqualsIfSameId() {
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2, "Два эпика с одинаковым id, не равны друг другу");
    }
}