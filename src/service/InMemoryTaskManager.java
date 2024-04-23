package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private int idCounter = 0;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    private int generateId() {
        return ++idCounter;
    }

  /*  @Override
    public HashMap<Integer, Task> getTasksForTest */

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> getSubtasks = new ArrayList<>();
        for (Subtask st : subtasks.values()) {
            getSubtasks.add(st);
        }
        return getSubtasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> getEpics = new ArrayList<>();
        for (Epic e : epics.values()) {
            getEpics.add(e);
        }
        return getEpics;
    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearSubtasks() {
        subtasks.clear();

        for (Epic epicClear : epics.values()) {
            epicClear.getSubtasksOfEpic().clear();
            updateStatus(epicClear);
        }
    }

    @Override
    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());

        epic.addSubtask(subtask.getId());
        updateStatus(epic);

        epics.put(epic.getId(), epic);

        return subtask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());

        updateStatus(epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());

        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());

        epics.put(epic.getId(), saved);
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask oldSubtask = subtasks.remove(id);

        Epic oldEpic = epics.get(oldSubtask.getEpicId());

        oldEpic.removeSubtask(oldSubtask.getId());
        updateStatus(oldEpic);
    }

    @Override
    public void removeEpicById(int id) {
        Epic oldEpic = epics.remove(id);

        ArrayList<Integer> oldSubtasks = oldEpic.getSubtasksOfEpic();
        for (Integer i : oldSubtasks) {
            subtasks.remove(i);
        }
    }

    @Override
    public ArrayList<Subtask> getSubTaskForEpics(Epic epic) {
        ArrayList<Subtask> getSubTaskForEpics = new ArrayList<>();

        for (Integer i : epic.getSubtasksOfEpic()) {
            getSubTaskForEpics.add(subtasks.get(i));
        }
        return getSubTaskForEpics;

    }

    @Override
    public TaskStatus updateStatus(Epic epic) {
        for (Integer i : epic.getSubtasksOfEpic()) {
            Subtask sbt = subtasks.get(i);
            if (getSubTaskForEpics(epic).isEmpty() || sbt.status == TaskStatus.NEW) {
                epic.status = TaskStatus.NEW;
            } else {
                epic.status = TaskStatus.IN_PROGRESS;
                break;
            }
        }
        for (Integer i : epic.getSubtasksOfEpic()) {
            Subtask sbt = subtasks.get(i);
            if (epic.status != TaskStatus.NEW && sbt.status == TaskStatus.DONE) {
                epic.status = TaskStatus.DONE;
            } else if (epic.status != TaskStatus.NEW) {
                epic.status = TaskStatus.IN_PROGRESS;
                break;
            }
        }
        return epic.status;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}



