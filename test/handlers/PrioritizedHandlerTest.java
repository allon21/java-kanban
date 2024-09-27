package test.http.handlers;

import com.google.gson.Gson;
import controller.InMemoryTaskManager;
import controller.TaskManager;
import enums.TaskStatus;
import http.HttpTaskServer;
import http.handlers.ListTypeTokens;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

class PrioritizedHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public PrioritizedHandlerTest() throws IOException {
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
    public void testGetHistory() throws IOException, InterruptedException {
        Task task1 = new Task("Test 1", "Testing task 1",
                TaskStatus.NEW, Duration.ofMinutes(5), LocalDateTime.now());
        Task task2 = new Task("Test 2", "Testing task 2",
                TaskStatus.NEW, Duration.ofMinutes(5), LocalDateTime.now().plusMinutes(10));

        manager.createTask(task1);
        manager.createTask(task2);

        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        List<Task> tasks = gson.fromJson(response.body(), new ListTypeTokens.TaskListTypeToken().getType());
        Assertions.assertEquals(2, tasks.size());
    }

}