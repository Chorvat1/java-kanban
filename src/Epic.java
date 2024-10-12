

import java.util.ArrayList;
import java.util.List;

// Класс Epic, расширяющий функциональность задачи (Task)
public class Epic extends Task {
    private List<Integer> subtaskList = new ArrayList<>(); // Список подзадач

    // Конструктор для создания эпика
    public Epic(String name, String description) {
        super(name, description);
    }

    // Конструктор для обновления эпика
    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    // Метод для добавления подзадачи к списку подзадач
    public void addSubtask(Subtask subtask) {
        subtaskList.add(subtask.getId());
    }

    // Метод для очистки списка подзадач
    public void clearSubtasks() {
        subtaskList.clear();
    }

    // Геттер для получения списка подзадач
    public List<Integer> getSubtaskList() {
        return subtaskList;
    }

    // Сеттер для установки нового списка подзадач
    public void setSubtaskList(List<Integer> subtaskList) {
        this.subtaskList = subtaskList;
    }

    // Переопределение метода toString для более читаемого вывода информации об эпике
    @Override
    public String toString() {
        return "Epic{" +
                "name= " + getName() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", subtaskList.size = " + subtaskList.size() +
                ", status = " + getStatus() +
                '}';
    }
}
