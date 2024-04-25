import controller.Managers;
import controller.TaskManager;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager taskManager = Managers.getDefault();
    }
}
