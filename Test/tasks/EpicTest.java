package tasks;

import manager.InMemoryTaskManager;
import manager.Managers;
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
        Epic epic1 = new Epic(2,"Epic1", "description1", Status.DONE);
        Epic epic2 = new Epic(2,"Epic2", "description2",Status.NEW);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        // Проверяем, что подзадачи равны эпикам по id
        assertEquals(epic1, taskManager.getEpicByID(epic1.getId()));
        assertEquals(epic2, taskManager.getEpicByID(epic2.getId()));
    }
}