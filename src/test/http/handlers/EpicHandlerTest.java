package test.http.handlers;

import com.google.gson.Gson;
import controller.InMemoryTaskManager;
import controller.TaskManager;
import enums.TaskStatus;
import http.HttpTaskServer;
import http.handlers.ListTypeTokens;
import model.Epic;
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

class EpicHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public EpicHandlerTest() throws IOException {
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
    public void testGetEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                null, LocalDateTime.now());
        manager.createEpic(epic1);
        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Epic> epics = gson.fromJson(response.body(), new ListTypeTokens.EpicListTypeToken().getType());
        assertEquals(1, epics.size());
    }

    @Test
    public void testAddEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
        manager.createEpic(epic1);
        String epicToGson = gson.toJson(epic1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicToGson))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

    }

    @Test
    public void testDeleteEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Epic 1", "Epic Description", TaskStatus.NEW, Duration.ZERO,
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
        manager.createEpic(epic1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8800/epics/" + epic1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> listTasks = manager.getTasks();

        assertEquals(200, response.statusCode());
        assertEquals(0, listTasks.size(), "Некорректное количество задач");
    }
}