package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Эпик")
class EpicTest {
    Epic epic1 = new Epic("эпик1", "описание эпика");

    @Test
    @DisplayName("Добавление подзадач в эпик")
    public void shouldAddSubtask() {
        Subtask testSubtask = new Subtask("тестовая подзадача",
                "тестовое описание", epic1.getId());
        testSubtask.setId(1);
        epic1.addSubtask(testSubtask.getId());

        ArrayList<Integer> testSubtasksOfEpic = epic1.getSubtasksOfEpic();
        assertEquals(testSubtasksOfEpic.get(0), testSubtask.getId());
    }

    @Test
    @DisplayName("Удаление подзадачи из эпика")
    public void shouldRemoveSubtask() {
        Subtask testSubtask = new Subtask("тестовая подзадача",
                "тестовое описание", epic1.getId());
        testSubtask.setId(1);
        epic1.addSubtask(testSubtask.getId());
        ArrayList<Integer> testSubtasksOfEpic = epic1.getSubtasksOfEpic();

        epic1.removeSubtask(testSubtask.getId());

        assertEquals(0, testSubtasksOfEpic.size());
    }
}




