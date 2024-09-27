package test.http.handlers;

import com.google.gson.Gson;
import controller.InMemoryTaskManager;
import controller.TaskManager;
import enums.TaskStatus;
import http.HttpTaskServer;
import http.handlers.ListTypeTokens;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtaskHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public SubtaskHandlerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.removeAllTasks();
        manager.removeAllSubtasks();
        manager.removeAllEpics();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testGetSubtask() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        Subtask subtask1 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.NEW,
                Duration.ofHours(1), LocalDateTime.now());
        manager.createSubtask(subtask1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Subtask> subtasks = gson.fromJson(response.body(), new ListTypeTokens.SubtaskListTypeToken().getType());
        assertEquals(1, subtasks.size());
    }

    @Test
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        Subtask subtask1 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.NEW,
                Duration.ofHours(1), LocalDateTime.now());
        manager.createSubtask(subtask1);
        String subtaskToGson = gson.toJson(subtask1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subtaskToGson))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
    }

    @Test
    public void testDeleteSubtask() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        Subtask subtask1 = new Subtask(epic1.getId(), "subtask2", "desc2", TaskStatus.NEW,
                Duration.ofHours(1), LocalDateTime.now());
        manager.createSubtask(subtask1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/subtasks/" + subtask1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> listTasks = manager.getTasks();

        assertEquals(200, response.statusCode());
        assertEquals(0, listTasks.size(), "Некорректное количество задач");
    }
}