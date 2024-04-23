package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("TaskManager")
class InMemoryTaskManagerTest {
    TaskManager inMemoryTaskManager;
    Task task1;
    Epic epic1;
    Subtask testSubtask;


    @BeforeEach
    public void beforeEach() {
        EmptyHistoryManager emptyHistoryManager = new EmptyHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager(emptyHistoryManager);

        task1 = inMemoryTaskManager.createTask(new Task("задача1", "описание", TaskStatus.NEW));
        epic1 = inMemoryTaskManager.createEpic(new Epic("эпик1", "описание эпика"));
        testSubtask = inMemoryTaskManager.createSubtask(new Subtask("тестовая подзадача",
                "тестовое описание", epic1.getId()));
    }

    @Test
    @DisplayName("Экземпляры класса Task равны друг другу, если равен их id")
    public void shouldBeEqualsIfTaskHaveTheSameId() {
        Task task2 = new Task("задача1 в процессе", "изменение статуса",
                TaskStatus.IN_PROGRESS);
        task2.setId(task1.getId());

        assertEquals(task1.getId(), task2.getId());
        assertEquals(task1, task2);
    }

    @Test
    @DisplayName("Наследники класса Task равны друг другу, если равен их id")
    public void shouldBeEqualsIfSubtaskAndEpicHaveTheSameId() {
        Task subtask1 = inMemoryTaskManager.createSubtask(new Subtask("подзадача эпик1",
                "описание подзадачи", epic1.getId()));
        Task subtask2 = new Subtask("подзадача эпик1 в процессе",
                "изменение статуса", TaskStatus.IN_PROGRESS, epic1.getId());
        subtask2.setId(subtask1.getId());

        assertEquals(subtask1.getId(), subtask2.getId());
        assertEquals(subtask1, subtask2);

        Epic epic2 = new Epic("эпик2", "описание эпика2");
        epic2.setId(epic1.getId());
        epic2.addSubtask(testSubtask.getId());
        epic2.addSubtask(subtask1.getId());

        assertEquals(epic1.getId(), epic2.getId());
        assertEquals(epic1, epic2);
    }

    @Test
    @DisplayName("Объект Subtask нельзя сделать своим же эпиком")
    public void shouldNotSubtaskBeAnEpic() {
        testSubtask = new Subtask("тестовая подзадача 0", "тестовое описание 0", testSubtask.getId());
        assertEquals(0, testSubtask.getId());
    }

    @Test
    @DisplayName("InMemoryTaskManager добавляет новую задачу")
    public void shouldCreateTask() {
        assertEquals(1, task1.getId());
        assertEquals("задача1", task1.getName());
        assertEquals("описание", task1.getDescription());
        assertEquals(TaskStatus.NEW, task1.status);

        assertEquals(task1, inMemoryTaskManager.getTasks().get(0));
    }

    @Test
    @DisplayName("InMemoryTaskManager добавляет новую подзадачу")
    public void shouldCreateSubtask() {
        assertEquals(3, testSubtask.getId());
        assertEquals("тестовая подзадача", testSubtask.getName());
        assertEquals("тестовое описание", testSubtask.getDescription());
        assertEquals(TaskStatus.NEW, testSubtask.status);
        assertEquals(epic1.getId(), testSubtask.getEpicId());

        assertEquals(testSubtask, inMemoryTaskManager.getSubtasks().get(0));
    }

    @Test
    @DisplayName("InMemoryTaskManager добавляет новый эпик")
    public void shouldCreateEpic() {
        assertEquals(2, epic1.getId());
        assertEquals("эпик1", epic1.getName());
        assertEquals("описание эпика", epic1.getDescription());
        assertEquals(TaskStatus.NEW, epic1.status);

        assertEquals(epic1, inMemoryTaskManager.getEpics().get(0));
    }

    @Test
    @DisplayName("InMemoryTaskManager находит задачу по ID")
    public void shouldFindTaskById() {
        Task testTask = inMemoryTaskManager.getTaskById(1);
        assertEquals(testTask, task1);
    }

    @Test
    @DisplayName("InMemoryTaskManager находит подзадачу по ID")
    public void shouldFindSubtaskById() {
        Subtask test = inMemoryTaskManager.getSubtaskById(3);
        assertEquals(test, testSubtask);
    }

    @Test
    @DisplayName("InMemoryTaskManager находит эпик по ID")
    public void shouldFindEpicById() {
        Epic testEpic = inMemoryTaskManager.getEpicById(2);
        assertEquals(testEpic, epic1);
    }

    @Test
    @DisplayName("задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера")
    public void shouldNotConfrontIfTaskHaveTheSameId() {
        Task testTask = new Task("тестовая задача", "тестовое описание",
                TaskStatus.IN_PROGRESS);
        testTask.setId(1);
        inMemoryTaskManager.updateTask(testTask);

        assertEquals(task1.getId(), testTask.getId());
        assertEquals(task1, testTask);
    }

    @Test
    @DisplayName("проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер")
    public void shouldNotChangeTask() {
        Task testTask = new Task("тестовая задача", "тестовое описание",
                TaskStatus.NEW);
        Task testTask2 = inMemoryTaskManager.createTask(testTask);

        assertEquals(testTask.getId(), testTask2.getId());
        assertEquals("тестовая задача", testTask2.getName());
        assertEquals("тестовое описание", testTask2.getDescription());
        assertEquals(TaskStatus.NEW, testTask2.status);
    }

    @Test
    @DisplayName("InMemoryTaskManager получает список созданных задач")
    public void shouldgetTasks() {
        ArrayList<Task> testTasks = inMemoryTaskManager.getTasks();
        assertNotNull(testTasks);
        assertEquals(testTasks.get(0), task1);
    }

    @Test
    @DisplayName("InMemoryTaskManager получает список созданных подзадач")
    public void shouldgetSubasks() {
        ArrayList<Subtask> testSubtasks = inMemoryTaskManager.getSubtasks();
        assertNotNull(testSubtasks);
        assertEquals(testSubtasks.get(0), testSubtask);
    }

    @Test
    @DisplayName("InMemoryTaskManager получает список созданных эпиков")
    public void shouldgetEpics() {
        ArrayList<Epic> testEpics = inMemoryTaskManager.getEpics();
        assertNotNull(testEpics);
        assertEquals(testEpics.get(0), epic1);
    }

    @Test
    @DisplayName("InMemoryTaskManager очищает список созданных задач")
    public void shouldClearTasks() {
        inMemoryTaskManager.clearTasks();
        ArrayList<Task> testTasks = inMemoryTaskManager.getTasks();
        assertEquals(0, testTasks.size());
    }

    @Test
    @DisplayName("InMemoryTaskManager очищает список созданных подзадач")
    public void shouldClearSubtasks() {
        inMemoryTaskManager.clearSubtasks();
        ArrayList<Subtask> testSubtasks = inMemoryTaskManager.getSubtasks();
        assertEquals(0, testSubtasks.size());
    }

    @Test
    @DisplayName("InMemoryTaskManager очищает список созданных эпиков")
    public void shouldClearEpics() {
        inMemoryTaskManager.clearEpics();
        ArrayList<Epic> testepics = inMemoryTaskManager.getEpics();
        assertEquals(0, testepics.size());
    }

    @Test
    @DisplayName("InMemoryTaskManager обновляет задачу")
    public void shouldUpdateTask() {
        Task task1Updated = new Task("Задача в процессе", "изменение статуса",
                TaskStatus.IN_PROGRESS);
        task1Updated.setId(task1.getId());
        inMemoryTaskManager.updateTask(task1Updated);

        assertEquals(task1.getId(), task1Updated.getId());

        Task testTask = inMemoryTaskManager.getTasks().get(0);
        assertEquals(task1.getId(), testTask.getId());

        assertEquals("Задача в процессе", testTask.getName());
        assertEquals("изменение статуса", testTask.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, testTask.status);
    }

    @Test
    @DisplayName("InMemoryTaskManager обновляет подзадачу")
    public void shouldUpdateSubtask() {
        Subtask subtaskUpdated = new Subtask("Подзадача в процессе",
                "изменение статуса", TaskStatus.IN_PROGRESS, epic1.getId());
        subtaskUpdated.setId(testSubtask.getId());
        inMemoryTaskManager.updateSubtask(subtaskUpdated);

        assertEquals(testSubtask.getId(), subtaskUpdated.getId());

        Subtask testSubtask2 = inMemoryTaskManager.getSubtasks().get(0);
        assertEquals(testSubtask.getId(), testSubtask2.getId());

        assertEquals("Подзадача в процессе", testSubtask2.getName());
        assertEquals("изменение статуса", testSubtask2.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, testSubtask2.status);
        assertEquals(epic1.getId(), testSubtask2.getEpicId());

        assertEquals(epic1.status, TaskStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("InMemoryTaskManager обновляет эпик")
    public void shouldUpdateEpic() {
        Epic testEpic = new Epic("Эпик №2", "описание эпика2");
        testEpic.setId(epic1.getId());
        inMemoryTaskManager.updateEpic(testEpic);

        assertEquals(epic1.getId(), testEpic.getId());

        Epic testEpic2 = inMemoryTaskManager.getEpics().get(0);
        assertEquals(epic1.getId(), testEpic2.getId());

        assertEquals("Эпик №2", testEpic2.getName());
        assertEquals("описание эпика2", testEpic2.getDescription());
        assertEquals(TaskStatus.NEW, testEpic2.status);

        assertEquals(epic1.getSubtasksOfEpic(), testEpic2.getSubtasksOfEpic());
    }

    @Test
    @DisplayName("InMemoryTaskManager удаляет задачу по ID")
    public void shouldRemoveTaskById() {
        inMemoryTaskManager.removeTaskById(task1.getId());
        ArrayList<Task> testTasks = inMemoryTaskManager.getTasks();

        assertEquals(0, testTasks.size());
    }

    @Test
    @DisplayName("InMemoryTaskManager удаляет подзадачу по ID")
    public void shouldRemoveSubtaskById() {
        inMemoryTaskManager.removeSubtaskById(testSubtask.getId());
        ArrayList<Subtask> testSubtasks = inMemoryTaskManager.getSubtasks();
        ArrayList<Integer> testEpics = epic1.getSubtasksOfEpic();

        assertEquals(0, testSubtasks.size());
        assertEquals(0, testEpics.size());
    }

    @Test
    @DisplayName("InMemoryTaskManager удаляет эпик по ID")
    public void shouldRemoveEpicById() {
        inMemoryTaskManager.removeEpicById(epic1.getId());
        ArrayList<Epic> testEpics = inMemoryTaskManager.getEpics();
        assertEquals(0, testEpics.size());
    }

    @Test
    @DisplayName("InMemoryTaskManager получает список подзадач эпика")
    public void shouldgetSubTaskForEpics() {
        ArrayList<Subtask> testSubtasks = inMemoryTaskManager.getSubTaskForEpics(epic1);
        assertEquals(1, testSubtasks.size());
        assertEquals(testSubtasks.get(0), testSubtask);
    }

    private class EmptyHistoryManager implements HistoryManager {
        @Override
        public void add(Task task) {
        }

        @Override
        public List<Task> getHistory() {
            return Collections.emptyList();
        }
    }
}

