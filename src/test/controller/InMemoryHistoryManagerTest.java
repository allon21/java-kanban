package test.controller;

import controller.Managers;
import controller.TaskManager;
import model.Task;
import enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    ArrayList<Task> historyList;
    TaskManager taskManager = Managers.getDefault();
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    void setUp(){
        task1  = new Task("Задача 1", "Описание", TaskStatus.NEW);
        task2  = new Task("Задача 2", "Описание", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        historyList = (ArrayList<Task>) taskManager.getHistory();
    }

    @Test
    void historyListShouldNotBeEmpty() {
        assertNotEquals(0, historyList.size(), "taskManager.getHistory() должен вернуть " +
                "не пустой список.");
    }

    @Test
    void historyListShouldBeSize3() {
        task3  = new Task("Задача 3", "Описание", TaskStatus.NEW);
        taskManager.createTask(task3);
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task1.getId());
        assertEquals(3, taskManager.getHistory().size(), "taskManager.getHistory() должен вернуть " +
                "список с 3 значениями, независимо сколько раз мы доставали одну и туже задачу.");
    }

    @Test
    void historyListShouldBeRemoveTask() {
        taskManager.removeTaskById(task1.getId());
        assertEquals(1, taskManager.getHistory().size(), "taskManager.getHistory() должен вернуть " +
                "список с 2 значениями, после удаления задачи.");
    }

    @Test
    void historyListShouldBeRemoveTask1() {
        taskManager.removeTaskById(task1.getId());
        taskManager.removeTaskById(task2.getId());
        assertEquals(0, taskManager.getHistory().size(), "taskManager.getHistory() должен вернуть " +
                "пустой список");
    }

}