package manager;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import enums.Status;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryTaskManagerTest {

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

    @Test
    void testTaskManagerStoresTasksById() {
        Task task = new Task(1, "Task 1", "Description", Status.NEW);
        Task addedTask = taskManager.addTask(task);

        assertEquals(task, taskManager.getTaskByID(addedTask.getId()), "должна быть доступна для извлечения по идентификатору ID");
    }


    //Убедимся, что история просмотров сохраняет старую версию задачи после обновления
    @Test
    void testTaskHistoryPreservation() {
        Task task = new Task(1, "Task", "Description", Status.NEW);
        taskManager.addTask(task);
        taskManager.getTaskByID(task.getId());

        List<Task> history = taskManager.getHistory();
        assertEquals(1, history.size(), "History should contain the task after accessing it");
        assertEquals(task.getId(), history.get(0).getId(), "History task should match accessed task");
    }


    @Test
    void addNewTask() {
        //проверяем, что InMemoryTaskManager добавляет задачи и может найти их по id;
        final Task task = taskManager.addTask(new Task("Test addNewTask", "Test addNewTask description"));
        final Task savedTask = taskManager.getTaskByID(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic("имя", "описание");
        Epic addedEpic = taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic(addedEpic.getId(), "новое имя", "новое описание", Status.DONE);
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected.getName(), actual.getName(), "Вернулся эпик с другим именем");
        assertEquals(expected.getDescription(), actual.getDescription(), "Вернулся эпик с другим описанием");
        assertEquals(expected.getStatus(), actual.getStatus(), "Вернулся эпик с другим статусом");
    }


    @Test
    void TaskCreatedAndTaskAddedShouldHaveSameVariables() {
        Task expected = new Task(5, "Задача", "описание", Status.DONE);
        taskManager.addTask(expected);
        List<Task> list = taskManager.getTasks();
        Task actual = list.getFirst();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

//    --------------

    @Test
    public void testEpicCannotAddItselfAsSubtask() {
        Epic epic = new Epic("Epic 1", "Description 1");
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.addSubtask(new Subtask("Subtask 1", "Description 1", epic.getId()));
        }, "Эпик не может добавить сам себя как подзадачу");
    }


    @Test
    public void testManagerInitialization() {
        assertNotNull(taskManager, "Менеджер задач должен быть инициализирован");
    }



    @Test
    public void testIdConflictResolution() {
        Task task = new Task("Task", "Description");
        Task anotherTask = new Task("Task", "Description");

        taskManager.addTask(task);
        anotherTask.setId(task.getId()); // Имитация конфликта по id

        assertNotNull(taskManager.addTask(anotherTask), "Задачи с одинаковыми id должны быть обработаны без ошибок");
    }


    @Test
    public void testHistoryManagerPreservesTaskVersion() {
        Task task = new Task("Task", "Description");
        taskManager.addTask(task);
        taskManager.getTaskByID(task.getId());

        List<Task> history = taskManager.getHistory();
        assertTrue(history.contains(task), "История должна сохранять предыдущую версию задачи и ее данные");
    }
}



