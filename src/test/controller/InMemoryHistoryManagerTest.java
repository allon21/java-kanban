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
    private Subtask subtask;
    TaskManager taskManager = Managers.getDefault();;
    @BeforeEach
    void setUp(){
        task = new Task("Задача 1", "Описание", TaskStatus.NEW);
        taskManager.createTask(task);

        epic = new Epic("Эпик 1", "Описание", TaskStatus.NEW);
        taskManager.createEpic(epic);
        subtask = new Subtask(epic.getId(),"Подзадача 1", "Описание", TaskStatus.IN_PROGRESS);
        taskManager.createSubtask(subtask);
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
        taskManager.getSubtaskById(subtask.getId());
        Task expectedSubtask = subtask;
        subtask.setStatus(TaskStatus.IN_PROGRESS);
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
}