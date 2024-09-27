package http.handlers;

import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public class ListTypeTokens {
    public static class TaskListTypeToken extends TypeToken<List<Task>> {
    }

    public static class SubtaskListTypeToken extends TypeToken<List<Subtask>> {
    }

    public static class EpicListTypeToken extends TypeToken<List<Epic>> {
    }
}
