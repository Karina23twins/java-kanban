import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = taskManager.createTask(new Task("Задача №1", "описание"));

        Task task2 = taskManager.createTask(new Task("Задача №2", "описание2"));

        Epic epic1 = taskManager.createEpic(new Epic("Эпик №1", "описание эпика"));

        Subtask subtask11 = taskManager.createSubtask(new Subtask("Подзадача эпик1",
                "описание подзадачи", epic1.getId()));

        Subtask subtask12 = taskManager.createSubtask(new Subtask("Подзадача2 эпик1",
                "описание подзадачи2", epic1.getId()));

        Epic epic2 = taskManager.createEpic(new Epic("Эпик №2", "описание эпика2"));

        Subtask subtask21 = taskManager.createSubtask(new Subtask("Подзадача эпик2",
                "описание подзадачи эпика", epic2.getId()));

        System.out.println("Список всех задач" + " " + taskManager.getTasks());
        System.out.println("Список всех эпиков" + " " + taskManager.getEpics());
        System.out.println("Список всех подзадач" + " " + taskManager.getSubtasks());

        Task task1Updated = new Task("Задача №1 в процессе", "изменение статуса",
                TaskStatus.IN_PROGRESS);
        task1Updated.setId(task1.getId());
        taskManager.updateTask(task1Updated);

        Task task2Updated = new Task("Задача №2 завершена", "изменение статуса",
                TaskStatus.DONE);
        task2Updated.setId(task2.getId());
        taskManager.updateTask(task2Updated);

        Subtask subtask11Updated = new Subtask("Подзадача эпик1 в процессе",
                "изменение статуса", TaskStatus.IN_PROGRESS, epic1.getId());
        subtask11Updated.setId(subtask11.getId());
        taskManager.updateSubtask(subtask11Updated);

        Subtask subtask12Updated = new Subtask("Подзадача2 эпик1 завершена",
                "изменение статуса", TaskStatus.DONE, epic1.getId());
        subtask12Updated.setId(subtask12.getId());
        taskManager.updateSubtask(subtask12Updated);

        Subtask subtask21Updated = new Subtask("Подзадача эпик2 завершена",
                "изменение статуса", TaskStatus.DONE, epic2.getId());
        subtask21Updated.setId(subtask21.getId());
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
