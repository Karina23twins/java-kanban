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
    @DisplayName("HistoryManager сохраняет не более 10 задач")
    public void shouldSaveMaximumOfTenTasks() {
        Task task1 = inMemoryTaskManager.createTask(new Task("задача1", "описание", TaskStatus.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("задача2", "описание", TaskStatus.NEW));
        Task task3 = inMemoryTaskManager.createTask(new Task("задача3", "описание", TaskStatus.NEW));
        Task task4 = inMemoryTaskManager.createTask(new Task("задача4", "описание", TaskStatus.NEW));
        Task task5 = inMemoryTaskManager.createTask(new Task("задача5", "описание", TaskStatus.NEW));
        Task task6 = inMemoryTaskManager.createTask(new Task("задача6", "описание", TaskStatus.NEW));
        Task task7 = inMemoryTaskManager.createTask(new Task("задача7", "описание", TaskStatus.NEW));
        Task task8 = inMemoryTaskManager.createTask(new Task("задача8", "описание", TaskStatus.NEW));
        Task task9 = inMemoryTaskManager.createTask(new Task("задача9", "описание", TaskStatus.NEW));
        Task task10 = inMemoryTaskManager.createTask(new Task("задача10", "описание", TaskStatus.NEW));
        Task task11 = inMemoryTaskManager.createTask(new Task("задача11", "описание", TaskStatus.NEW));

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        historyManager.add(task5);
        historyManager.add(task6);
        historyManager.add(task7);
        historyManager.add(task8);
        historyManager.add(task9);
        historyManager.add(task10);
        historyManager.add(task11);

        List<Task> testTasks = historyManager.getHistory();
        assertEquals(10, testTasks.size());
    }
}