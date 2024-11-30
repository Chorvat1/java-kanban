package tasks;

import managers.InMemoryTaskManager;
import managers.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import enums.Status;

class EpicTest {

    private InMemoryTaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    @Test
    void testEpicAndSubtaskEqualityById() {
        Epic epic1 = new Epic("Epic1", "description1");
        Epic epic2 = new Epic("Epic2", "description2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        // Проверяем, что подзадачи равны эпикам по id
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()));
        assertEquals(epic2, taskManager.getEpicById(epic2.getId()));
    }
}