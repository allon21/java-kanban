package test.controller;

import controller.Managers;
import controller.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private Task task;
    private Epic epic;
    private Subtask subtask1;
    private Subtask subtask2;
    TaskManager taskManager = Managers.getDefault();;
    @BeforeEach
    void setUp(){
        task = new Task("Задача 1", "Описание", TaskStatus.NEW);
        taskManager.createTask(task);
        epic = new Epic("Эпик 1", "Описание", TaskStatus.NEW);
        taskManager.createEpic(epic);
        subtask1 = new Subtask(epic.getId(),"Подзадача 1", "Описание", TaskStatus.IN_PROGRESS);
        subtask2 = new Subtask(epic.getId(),"Подзадача 1", "Описание", TaskStatus.NEW);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
    }

    @Test
    void shouldSaveAndReturnSameTaskFromHistory(){
        taskManager.getTaskById(task.getId());
        Task expectedTask = task;
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(expectedTask, (taskManager.getHistory()).get(0), "InMemoryHistoryManager должен " +
                "сохранить и вернуть одну и ту же задачу");
    }
    @Test
    void shouldSaveAndReturnSameSubtaskFromHistory(){
        taskManager.getSubtaskById(subtask1.getId());
        Task expectedSubtask = subtask1;
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(expectedSubtask, (taskManager.getHistory()).get(0), "InMemoryHistoryManager должен " +
                "сохранить и вернуть одну и ту же подзадачу");
    }
    @Test
    void shouldSaveAndReturnSameEpicFromHistory(){
        taskManager.getEpicById(epic.getId());
        Task expectedEpic = epic;
        epic.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(expectedEpic, (taskManager.getHistory()).get(0), "InMemoryHistoryManager должен " +
                "сохранить и вернуть одну и ту же подзадачу");
    }

    @Test
    void shouldStatusEpicBeInProgressIfSubtasksStatusNewAndDone(){
        subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.checkStatus(epic);
        assertEquals(epic.getStatus(), TaskStatus.IN_PROGRESS, "Когда у эпика " +
                "есть подзадачи со статусами NEW и DONE одновременно , у эпика должен быть статус IN_PROGRESS");
    }
}