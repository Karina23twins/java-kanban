package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int idCounter = 0;

    private int generateId() {
        return ++idCounter;
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> getSubtasks = new ArrayList<>();
        for (Subtask st : subtasks.values()) {
            getSubtasks.add(st);
        }
        return getSubtasks;
    }

    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> getEpics = new ArrayList<>();
        for (Epic e : epics.values()) {
            getEpics.add(e);
        }
        return getEpics;
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearSubtasks() {
        subtasks.clear();

        for (Epic epicClear : epics.values()) {
            epicClear.getSubtasksOfEpic().clear();
            updateStatus(epicClear);
        }
    }

    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Subtask createSubtask(Subtask subtask) {
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());

        epic.addSubtask(subtask.getId());
        updateStatus(epic);

        epics.put(epic.getId(), epic);

        return subtask;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        Subtask subtaskOld = subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());

        updateStatus(epic);
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());

        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());

        epics.put(epic.getId(), saved);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(int id) {
        Subtask oldSubtask = subtasks.remove(id);

        Epic oldEpic = epics.get(oldSubtask.getEpicId());

        oldEpic.removeSubtask(oldSubtask.getId());
        updateStatus(oldEpic);
    }

    public void removeEpicById(int id) {
        Epic oldEpic = epics.remove(id);

        ArrayList<Integer> oldSubtasks = oldEpic.getSubtasksOfEpic();
        for (Integer i : oldSubtasks) {
            subtasks.remove(i);
        }
    }

    public ArrayList<Subtask> getSubTaskForEpics(Epic epic) {
        ArrayList<Subtask> getSubTaskForEpics = new ArrayList<>();

        for (Integer i : epic.getSubtasksOfEpic()) {
            getSubTaskForEpics.add(subtasks.get(i));
        }
        return getSubTaskForEpics;

    }

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
}


