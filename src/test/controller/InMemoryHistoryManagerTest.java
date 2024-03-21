package test.controller;

import controller.HistoryManager;
import controller.Managers;
import controller.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    ArrayList<Task> historyList;
    TaskManager taskManager = Managers.getDefault();
    Task task1;

    @BeforeEach
    void setUp(){
        task1  = new Task("Задача 1", "Описание", TaskStatus.NEW);
        Task task2  = new Task("Задача 2", "Описание", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        historyList = (ArrayList<Task>) taskManager.getHistory();
    }

    @Test
    void historyListShouldNotBeEmpty(){
        assertNotEquals(0, historyList.size() , "Список не должен быть пустым");
    }

    @Test
    void historyListShouldBeSize3(){
        Task task3  = new Task("Задача 3", "Описание", TaskStatus.NEW);
        taskManager.createTask(task3);
        taskManager.getTaskById(task3.getId());
        assertEquals(3, taskManager.getHistory().size(), "Неверное количество задач.");
    }
    @Test
    void historyListShouldBeRemoveTask(){
        taskManager.removeTaskById(task1.getId());
        assertEquals(2, taskManager.getHistory().size(), "Неверное количество задач.");
    }

}