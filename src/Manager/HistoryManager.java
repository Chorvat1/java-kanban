package Manager;

import Tasks.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task); // Метод для добавления задач в историю

    List<Task> getHistory(); // Метод для получения истории
}
