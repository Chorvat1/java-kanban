package tasks;

import static org.junit.jupiter.api.Assertions.*;

import enums.Status;
import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubtaskTest {

    private InMemoryTaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    @Test
    void testSubtaskEqualityById() {
        Subtask subtask1 = new Subtask(2,"Subtask1", "description1", Status.DONE,5);
        Subtask subtask2 = new Subtask(2,"Subtask2", "description2",Status.NEW,5);
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);

        // Проверяем, что подзадачи равны эпикам по id
        assertEquals(subtask1, taskManager.getSubtaskByID(subtask1.getId()));
        assertEquals(subtask2, taskManager.getSubtaskByID(subtask2.getId()));
    }
}