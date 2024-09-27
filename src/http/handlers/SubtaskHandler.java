package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.TaskManager;
import exception.TaskIntersectionException;
import exception.TaskNotFoundExeption;
import model.Subtask;
import model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public SubtaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    handleGetRequest(exchange);
                case "POST":
                    handlePostRequest(exchange);
                case "DELETE":
                    handleDeleteRequest(exchange);
                default:
                    sendText(exchange,"Пока нет такого метода", 500);
            }
        }catch (TaskNotFoundExeption e) {
            sendText(exchange,e.getMessage(), 404);
        }catch (TaskIntersectionException e) {
            sendText(exchange,e.getMessage(), 406);
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (pathParts.length == 3) {
            int subtaskId = Integer.parseInt(pathParts[2]);
            taskManager.removeSubtaskById(subtaskId);
        }

        exchange.sendResponseHeaders(201, 0);
        exchange.close();
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestMethod().getBytes(), StandardCharsets.UTF_8);
        Subtask subtask = gson.fromJson(requestBody, Subtask.class);
        try {
            if (subtask.getId() != null) {
                taskManager.subtaskUpdate(subtask);
            } else {
                taskManager.createSubtask(subtask);
            }
            exchange.sendResponseHeaders(201,0);
            exchange.close();
        } catch (TaskIntersectionException e) {
            sendText(exchange, e.getMessage(), 406);
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        if (pathParts.length == 2) {
            List<Subtask> subtasks = taskManager.getSubtasks();
            sendText(exchange, gson.toJson(subtasks),200);
        } else if (pathParts.length == 3) {
            handleGetSubtaskById(exchange, pathParts[2]);
        } else {
            sendText(exchange, "Задача ненайдена", 404);
        }
    }

    private void handleGetSubtaskById(HttpExchange exchange, String id) throws IOException {
        Subtask subtask = taskManager.getSubtaskById(Integer.parseInt(id));
        if (subtask != null) {
            sendText(exchange, gson.toJson(subtask), 200);
        } else {
            sendText(exchange, "Задача ненайдена", 404);
        }
    }
}
