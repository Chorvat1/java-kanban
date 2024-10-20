package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import enums.Status;
import manager.*;
import org.junit.jupiter.api.*;

class TaskTest {

    private InMemoryTaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }


    //Проверяет, что две задачи равны по ID.
    @Test
    void testTasksEqualityById() {
        Task task1 = new Task(1,"Task 1", "Description 1",Status.NEW);
        Task task2 = new Task(1,"Task 2", "Description 2",Status.DONE);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Проверяем, что задачи равны, если их id равны
        assertEquals(task1, taskManager.getTaskByID(task1.getId()));
        assertEquals(task2, taskManager.getTaskByID(task2.getId()));

    }


}