package exception;

public class TaskIntersectionException extends RuntimeException {
    public TaskIntersectionException() {
        super("Задача пересекается с существующеми");
    }
}
