package test.controller;

import controller.HistoryManager;
import controller.Managers;
import controller.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    @Test
    void shouldBeInitializedManagers() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Должен возвращть проинициализированный taskManager.");
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Должен возвращть проинициализированный historyManager.");
    }
}