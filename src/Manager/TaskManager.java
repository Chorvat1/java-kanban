package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

// Класс Manager.TaskManager для управления задачами, эпиками и подзадачами

import java.util.*;

public interface TaskManager {


    // Метод для получения следующего уникального идентификатора
    int getNextID();


    // Метод для добавления новой задачи
    Task addTask(Task task);

    // Метод для добавления нового эпика
    Epic addEpic(Epic epic);

    // Метод для добавления новой подзадачи
    Subtask addSubtask(Subtask subtask);

    // Метод для обновления существующей задачи
    Task updateTask(Task task);

    // Метод для обновления существующего эпика
    Epic updateEpic(Epic epic);

    // Метод для обновления существующей подзадачи
    Subtask updateSubtask(Subtask subtask);

    // Метод для получения задачи по идентификатору
    Task getTaskByID(int id);

    // Метод для получения эпика по идентификатору
    Epic getEpicByID(int id);

    // Метод для получения подзадачи по идентификатору
    Subtask getSubtaskByID(int id);

    // Метод для получения всех задач
    List<Task> getTasks();

    // Метод для получения всех эпиков
    List<Epic> getEpics();

    // Метод для получения всех подзадач
    List<Subtask> getSubtasks();

    // Метод для получения подзадач эпика
    List<Subtask> getEpicSubtasks(Epic epic);

    // Метод для удаления всех задач
    void deleteTasks();

    // Метод для удаления всех эпиков и их подзадач
    void deleteEpics();

    // Метод для удаления всех подзадач
    void deleteSubtasks();

    // Метод для удаления задачи по идентификатору
    public Object deleteTaskByID(int id);

    // Метод для удаления эпика по идентификатору
    void deleteEpicByID(int id);

    // Метод для удаления подзадачи по идентификатору
    Object deleteSubtaskByID(int id);

    List<Task> getHistory(); // Новый метод для получения истории просмотров
}
