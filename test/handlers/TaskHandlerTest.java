package test.http.handlers;

import com.google.gson.Gson;
import controller.InMemoryTaskManager;
import controller.TaskManager;
import enums.TaskStatus;
import http.HttpTaskServer;
import http.handlers.ListTypeTokens;
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

class TaskHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public TaskHandlerTest() throws IOException {
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
    public void testGetTask() throws IOException, InterruptedException {
        Task task1 = new Task("task1 ", "task Description", TaskStatus.NEW, Duration.ZERO,
                LocalDateTime.now());
        manager.createTask(task1);
        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> tasks = gson.fromJson(response.body(), new ListTypeTokens.TaskListTypeToken().getType());
        assertEquals(1, tasks.size());
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        Task task1 = new Task("task1 ", "task Description", TaskStatus.NEW, Duration.ZERO,
                LocalDateTime.now());
        manager.createTask(task1);
        String taskToGson = gson.toJson(task1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskToGson))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException {
        Task task1 = new Task("task1 ", "task Description", TaskStatus.NEW, Duration.ZERO,
                LocalDateTime.now());
        manager.createTask(task1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/tasks/" + task1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> listTasks = manager.getTasks();

        assertEquals(200, response.statusCode());
        assertEquals(0, listTasks.size(), "Некорректное количество задач");
    }
}