package exception;

public class TaskNotFoundExeption extends RuntimeException {
    public TaskNotFoundExeption() {
        super("Задача не найдена");
    }

}
