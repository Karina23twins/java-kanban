import model.Subtask;
import service.TaskManager;
import model.Task;
import model.TaskStatus;
import model.Epic;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = taskManager.createTask(new Task("Задача №1", "описание", TaskStatus.NEW));

        Task task2 = taskManager.createTask(new Task("Задача №2", "описание2", TaskStatus.NEW));

        Epic epic1 = taskManager.createEpic(new Epic("Эпик №1", "описание эпика"));

        Subtask subtask11 = taskManager.createSubtask(new Subtask("Подзадача эпик1",
                "описание подзадачи", TaskStatus.NEW, epic1));

        Subtask subtask12 = taskManager.createSubtask(new Subtask("Подзадача2 эпик1",
                "описание подзадачи2", TaskStatus.NEW, epic1));

        Epic epic2 = taskManager.createEpic(new Epic("Эпик №2", "описание эпика2"));

        Subtask subtask21 = taskManager.createSubtask(new Subtask("Подзадача эпик2",
                "описание подзадачи эпика", TaskStatus.NEW, epic2));

        System.out.println("Список всех задач" + " " + taskManager.getTasks());
        System.out.println("Список всех эпиков" + " " + taskManager.getEpics());
        System.out.println("Список всех подзадач" + " " + taskManager.getSubtasks());

        Task task1Updated = new Task(task1.getId(), "Задача №1 в процессе", "изменение статуса",
                TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task1Updated);

        Task task2Updated = new Task(task2.getId(), "Задача №2 завершена", "изменение статуса",
                TaskStatus.DONE);
        taskManager.updateTask(task2Updated);

        Subtask subtask11Updated = new Subtask(subtask11.getId(), "Подзадача эпик1 в процессе",
                "изменение статуса", TaskStatus.IN_PROGRESS, epic1);
        taskManager.updateSubtask(subtask11Updated);

        Subtask subtask12Updated = new Subtask(subtask12.getId(), "Подзадача2 эпик1 завершена",
                "изменение статуса", TaskStatus.DONE, epic1);
        taskManager.updateSubtask(subtask12Updated);

        Subtask subtask21Updated = new Subtask(subtask21.getId(), "Подзадача эпик2 завершена",
                "изменение статуса", TaskStatus.DONE, epic2);
        taskManager.updateSubtask(subtask21Updated);

        System.out.println("Список всех задач" + " " + taskManager.getTasks());
        System.out.println("Список всех эпиков" + " " + taskManager.getEpics());
        System.out.println("Список всех подзадач" + " " + taskManager.getSubtasks());

        taskManager.removeTaskById(task1.getId());
        taskManager.removeSubtaskById(subtask11.getId());

        System.out.println("Список всех задач" + " " + taskManager.getTasks());
        System.out.println("Список всех эпиков" + " " + taskManager.getEpics());
        System.out.println("Список всех подзадач" + " " + taskManager.getSubtasks());
    }
}
