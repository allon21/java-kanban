package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.TaskManager;
import exception.TaskIntersectionException;
import exception.TaskNotFoundExeption;
import model.Epic;
import model.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public EpicHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    handleGetRequest(exchange);
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    break;
                case "DELETE":
                    handleDeleteRequest(exchange);
                    break;
                default:
                    sendText(exchange, "Пока нет такого метода", 500);
                    break;
            }
        } catch (TaskNotFoundExeption e) {
            sendText(exchange, e.getMessage(), 404);
        } catch (TaskIntersectionException e) {
            sendText(exchange, e.getMessage(), 406);
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (pathParts.length == 3) {
            int epicId = Integer.parseInt(pathParts[2]);
            taskManager.removeEpicsById(epicId);
        }

        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(requestBody, Epic.class);
        try {
            if (epic.getId() != null) {
                taskManager.epicUpdate(epic);
            } else {
                taskManager.createEpic(epic);
            }
            exchange.sendResponseHeaders(201, 0);
            exchange.close();
        } catch (TaskIntersectionException e) {
            sendText(exchange, e.getMessage(), 406);
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        if (pathParts.length == 2) {
            List<Epic> epic = taskManager.getEpics();
            sendText(exchange, gson.toJson(epic), 200);
        } else if (pathParts.length == 3) {
            handleGetEpicById(exchange, pathParts[2]);
        } else if (pathParts.length == 4 && pathParts[3].equals("subtask")) {
            handleGetSubtaskByEpic(exchange, pathParts[2]);
        } else {
            sendText(exchange, "Задача ненайдена", 404);
        }
    }

    private void handleGetSubtaskByEpic(HttpExchange exchange, String epicId) throws IOException {
        Epic epic = taskManager.getEpicById(Integer.parseInt(epicId));
        if (epic != null) {
            List<Subtask> subtasks = new ArrayList<>();
            for (int id : epic.getSubtasks()) {
                subtasks.add(taskManager.getSubtaskById(id));
            }
            sendText(exchange, gson.toJson(subtasks), 200);
        } else {
            sendText(exchange, "Эпика нет", 404);
        }

    }

    private void handleGetEpicById(HttpExchange exchange, String id) throws IOException {
        Epic epic = taskManager.getEpicById(Integer.parseInt(id));
        if (epic != null) {
            sendText(exchange, gson.toJson(epic), 200);
        } else {
            sendText(exchange, "Задача ненайдена", 404);
        }
    }
}
