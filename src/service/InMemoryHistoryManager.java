package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> counterOfViewTasks = new ArrayList<>();

    @Override
    public void add(Task task) {
        counterOfViewTasks.add(task);
        if (counterOfViewTasks.size() > 10) {
            counterOfViewTasks.remove(0);
        }
    }
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(counterOfViewTasks);
    }
}
