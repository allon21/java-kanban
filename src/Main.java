import controller.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();
        //тест

        //две задачи и эпик с одной подзадачей, эпик с двумя подзадачами
        Task task1 = new Task("Первая задача", "Описание 1", TaskStatus.NEW);
        Task task2 = new Task("Вторая задача", "Описание 2", TaskStatus.NEW);
        Epic epic1 = new Epic("Эпик", "Описание 1 Эпика", TaskStatus.NEW);
        Epic epic2 = new Epic("Эпик", "Описание 2 Эпика", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        Subtask subtask1 = new Subtask(epic1.getId(), "Первая подзадача эпика 1", "Описание подзадачи 1", TaskStatus.NEW);
        Subtask subtask2 = new Subtask(epic1.getId(), "Вторая подзадача эпика 1", "Описание подзадачи 2", TaskStatus.NEW);
        Subtask subtask3 = new Subtask(epic2.getId(), "Первая подзадача эпика 2", "Описание подзадачи 11", TaskStatus.NEW);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        taskManager.subtaskUpdate(subtask1);

        //печатаем
        System.out.println(task1);
        System.out.println(task2);
        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);

        //меняем статусы
        task1.setStatus(TaskStatus.DONE);
        task2.setStatus(TaskStatus.IN_PROGRESS);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        subtask3.setStatus(TaskStatus.DONE);
        epic2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.checkStatus(epic1);
        taskManager.checkStatus(epic2);

        //печатаем
        System.out.println(task1);
        System.out.println(task2);
        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);


    }
}
