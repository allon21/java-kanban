package test.controller;

import controller.Managers;
import controller.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager;
    private Epic epic1;
    private Epic epic2;
    private Subtask subtask1;
    private Subtask subtask2;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        task1 = new Task("Задача 1", "Описание", TaskStatus.NEW);
        task2 = new Task("Задача 2", "Описание", TaskStatus.NEW);
        taskManager.createTask(task2);
        epic1 = new Epic("Эпик 1", "Описание", TaskStatus.NEW);
        epic2 = new Epic("Эпик 2", "Описание", TaskStatus.NEW);
        taskManager.createEpic(epic2);
        subtask1 = new Subtask(epic2.getId(),"Подзадача 1", "Описание", TaskStatus.IN_PROGRESS);
        subtask2 = new Subtask(epic2.getId(),"Подзадача 2", "Описание", TaskStatus.NEW);
        taskManager.createSubtask(subtask2);
    }

    @Test
    void shouldCreateAndReturnSameTaskById() {
        taskManager.createTask(task1);
        assertEquals(task1, taskManager.getTaskById(task1.getId()), "InMemoryTaskManager " +
                "должен создать и вернуть по её id ту же задачу.");
    }

    @Test
    void shouldCreateAndReturnSameSubtaskById() {
        taskManager.createSubtask(subtask1);
        assertEquals(subtask1, taskManager.getSubtaskById(subtask1.getId()), "InMemoryTaskManager " +
                "должен создать и вернуть по её id ту же подзадачу.");
    }

    @Test
    void shouldCreateAndReturnSameEpicById() {
        taskManager.createEpic(epic1);
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()), "InMemoryTaskManager " +
                "должен создать и вернуть по её id тот же эпик.");
    }

}