package service;

import model.*;

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
        ArrayList<Task> getTasks = new ArrayList<>();
        for (Task t : tasks.values()) {
            getTasks.add(t);
        }
        return getTasks;
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
            epicClear.getListOfSubtask().clear();
            epicClear.updateStatus();
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

        Epic epic = epics.get(subtask.getEpic().getId());
        epic.addSubtask(subtask);
        epic.updateStatus();

        epics.put(epic.getId(), epic);

        return subtask;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        Subtask subtaskOld = subtasks.put(subtask.getId(), subtask);

        Epic epic = subtask.getEpic();

        epic.removeSubtask(subtaskOld);
        epic.addSubtask(subtask);

        epic.updateStatus();
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

        Epic oldEpic = epics.get(oldSubtask.getEpic().getId());
        oldEpic.removeSubtask(oldSubtask);
        oldEpic.updateStatus();
    }

    public void removeEpicById(int id) {
        Epic oldEpic = epics.remove(id);

        ArrayList<Subtask> oldSubtasks = oldEpic.getListOfSubtask();
        for (Subtask oldSubtask : oldSubtasks) {
            subtasks.remove(oldSubtask.getId());
        }
    }

    public ArrayList<Subtask> getSubTaskForEpics(Epic epic) {
        return epic.getListOfSubtask();
    }
}

