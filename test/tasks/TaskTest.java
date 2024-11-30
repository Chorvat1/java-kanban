package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import managers.*;
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
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Проверяем, что задачи равны, если их id равны
        assertEquals(task1, taskManager.getTaskById(task1.getId()));
        assertEquals(task2, taskManager.getTaskById(task2.getId()));

    }

}