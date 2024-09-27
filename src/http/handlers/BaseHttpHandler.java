package http.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {

    protected void sendText(HttpExchange exchange, String text, int rCode) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(rCode, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }
/*

    private void sendHasInteractions(HttpExchange exchange) throws IOException {
        sendText(exchange,"Задача пересекается с существующеми", 409);
    }

    private void sendNotFound(HttpExchange exchange) throws IOException {
        sendText(exchange,"Объект не найден", 404);
    }

    private void sendSuccess(HttpExchange exchange) throws IOException {
        sendText(exchange,"Объект не найден", 404);
    }*/
}

