package managers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Epic;
import tasks.Task;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    //Убеждаемся, что класс-менеджер всегда возвращает инициализированные экземпляры.
    @Test
    void testManagersInitialization() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Менеджер должен быть правильно инициализирован");
    }

    //Убедимся, что история просмотров сохраняет старую версию задачи после обновления
    @Test
    void testTaskHistoryPreservation() {
        Task task = new Task("Task", "Description");
        taskManager.addTask(task);
        taskManager.getTaskById(task.getId());

        List<Task> history = taskManager.getHistory();
        assertEquals(1, history.size(), "History should contain the task after accessing it");
        assertEquals(task.getId(), history.get(0).getId(), "History task should match accessed task");
    }


    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        taskManager.addTask(task);

        final Task savedTask = taskManager.getTaskById(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void TaskCreatedAndTaskAddedShouldHaveSameVariables() {
        Task expected = new Task( "Задача", "описание");
        taskManager.addTask(expected);
        List<Task> list = taskManager.getTasks();
        Task actual = list.getFirst();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    public void testManagerInitialization() {
        assertNotNull(taskManager, "Менеджер задач должен быть инициализирован");
    }

    @Test
    public void testHistoryManagerPreservesTaskVersion() {
        Task task = new Task("Task", "Description");
        taskManager.addTask(task);
        taskManager.getTaskById(task.getId());

        List<Task> history = taskManager.getHistory();
        assertTrue(history.contains(task), "История должна сохранять предыдущую версию задачи и ее данные");
    }

    @Test
    void removeEpicById() {
        Epic epic = new Epic("epic", "epicD");
        taskManager.addEpic(epic);
        taskManager.removeEpicById(epic.getId());
        assertNull(taskManager.getEpicById(epic.getId()));
    }


}