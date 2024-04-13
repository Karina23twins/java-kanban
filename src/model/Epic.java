package model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksOfEpic = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubtasksOfEpic() {
        return subtasksOfEpic;
    }

    public void addSubtask(Integer id) {
        subtasksOfEpic.add(id);
    }

    public void removeSubtask(Integer id) {
        subtasksOfEpic.remove(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksOfEpic, epic.subtasksOfEpic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksOfEpic);
    }
}



