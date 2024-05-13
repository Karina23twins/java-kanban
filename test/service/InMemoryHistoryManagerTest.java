package service;

import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HistoryManager")
class InMemoryHistoryManagerTest {
    HistoryManager historyManager = new InMemoryHistoryManager();
    TaskManager inMemoryTaskManager = new InMemoryTaskManager(historyManager);

    @Test
    @DisplayName("задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных")
    public void shouldSavePreviousVersionOfTask() {
        Task task1 = inMemoryTaskManager.createTask(new Task("задача1", "описание", TaskStatus.NEW));

        historyManager.add(task1);
        List<Task> testTasks = historyManager.getHistory();
        Task testTask = testTasks.get(0);

        assertEquals(task1.getId(), testTask.getId());
        assertEquals("задача1", testTask.getName());
        assertEquals("описание", testTask.getDescription());
        assertEquals(TaskStatus.NEW, testTask.status);
    }

    @Test
    @DisplayName("нет повторных просмотров задач в истории")
    public void shouldRemoveDuplicateViews() {
        Task task1 = inMemoryTaskManager.createTask(new Task("задача1", "описание", TaskStatus.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("задача2", "описание2", TaskStatus.NEW));

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        List<Task> testTasks = historyManager.getHistory();
        Task testTask1 = testTasks.get(0);
        Task testTask2 = testTasks.get(1);

        assertEquals(testTasks.size(), 2);
        assertEquals(testTask1.getName(), "задача2");
        assertEquals(testTask2.getName(), "задача1");
        assertEquals(testTask1.getDescription(), "описание2");
        assertEquals(testTask2.getDescription(), "описание");
    }

    @Test
    @DisplayName("корректно удаляет первую задачу из истории")
    public void shouldRemoveFirstTask() {
        Task task1 = inMemoryTaskManager.createTask(new Task("задача1", "описание", TaskStatus.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("задача2", "описание2", TaskStatus.NEW));
        Task task3 = inMemoryTaskManager.createTask(new Task("задача3", "описание3", TaskStatus.NEW));

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task1.getId());

        assertEquals(historyManager.getHistory(), List.of(task2, task3));
    }

    @Test
    @DisplayName("корректно удаляет последнюю задачу из истории")
    public void shouldRemoveLastTask() {
        Task task1 = inMemoryTaskManager.createTask(new Task("задача1", "описание", TaskStatus.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("задача2", "описание2", TaskStatus.NEW));
        Task task3 = inMemoryTaskManager.createTask(new Task("задача3", "описание3", TaskStatus.NEW));

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());

        assertEquals(historyManager.getHistory(), List.of(task1, task2));
    }

    @Test
    @DisplayName("корректно удаляет задачу из середины истории")
    public void shouldRemoveTaskFromTheMiddle() {
        Task task1 = inMemoryTaskManager.createTask(new Task("задача1", "описание", TaskStatus.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("задача2", "описание2", TaskStatus.NEW));
        Task task3 = inMemoryTaskManager.createTask(new Task("задача3", "описание3", TaskStatus.NEW));

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());

        assertEquals(historyManager.getHistory(), List.of(task1, task3));
    }
}
