import model.Epic;
import model.Subtask;
import model.Task;
import service.Managers;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task task1 = taskManager.createTask(new Task("Задача №1", "описание"));
        Task task2 = taskManager.createTask(new Task("Задача №2", "описание2"));

        Epic epic1 = taskManager.createEpic(new Epic("Эпик №1", "описание эпика"));
        Subtask subtask11 = taskManager.createSubtask(new Subtask("Подзадача эпик1",
                "описание подзадачи", epic1.getId()));
        Subtask subtask12 = taskManager.createSubtask(new Subtask("Подзадача2 эпик1",
                "описание подзадачи2", epic1.getId()));
        Subtask subtask13 = taskManager.createSubtask(new Subtask("Подзадача3 эпик1",
                "описание подзадачи3", epic1.getId()));

        Epic epic2 = taskManager.createEpic(new Epic("Эпик №2", "описание эпика2"));

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask11.getId());
        taskManager.getSubtaskById(subtask12.getId());
        taskManager.getSubtaskById(subtask13.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic2.getId());

        System.out.println("История просмотренных задач: " + taskManager.getHistoryManager().getHistory());

        taskManager.getHistoryManager().remove(1);
        System.out.println("История задач после удаления: " + taskManager.getHistoryManager().getHistory());

        taskManager.removeEpicById(3);
        System.out.println("История задач без эпика: " + taskManager.getHistoryManager().getHistory());
    }
}
