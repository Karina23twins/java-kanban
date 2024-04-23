package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Утилитарный класс Managers")
class ManagersTest {
    Managers managers = new Managers();

    @Test
    @DisplayName("Класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров")
    public void shouldReturnInitializedCopiesOfManagers() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getDefault();

        assertNotNull(historyManager);
        assertNotNull(taskManager);
    }
}