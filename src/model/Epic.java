package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> listOfSubtask = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description, TaskStatus.NEW);
    }

    public ArrayList<Subtask> getListOfSubtask() {
        return listOfSubtask;
    }

    public void addSubtask(Subtask subtask) {
        listOfSubtask.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        listOfSubtask.remove(subtask);
    }

    public TaskStatus updateStatus() {

        for (Subtask sbt : listOfSubtask) {
            if (listOfSubtask.isEmpty() || sbt.status == TaskStatus.NEW) {
                status = TaskStatus.NEW;
            } else {
                status = TaskStatus.IN_PROGRESS;
                break;
            }
        }

        for (Subtask sbt : listOfSubtask) {
            if (status != TaskStatus.NEW && sbt.status == TaskStatus.DONE) {
                status = TaskStatus.DONE;
            } else if (status != TaskStatus.NEW) {
                status = TaskStatus.IN_PROGRESS;
                break;
            }
        }
        return status;
    }
}



