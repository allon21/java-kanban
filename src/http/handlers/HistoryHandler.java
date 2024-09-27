package http.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.TaskManager;
import model.Task;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public HistoryHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(exchange.getRequestMethod().equals("GET")){
                List<Task> historyList = taskManager.getHistory();
                sendText(exchange, gson.toJson(historyList), 200);
            } else {
                sendText(exchange,"Команды нет",404);
            }
        }catch (JsonSyntaxException e) {
            sendText(exchange,e.getMessage(), 406);
        }catch (NumberFormatException e) {
            sendText(exchange,e.getMessage(), 404);
        }
    }
}
