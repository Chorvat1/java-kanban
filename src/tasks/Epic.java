package tasks;

import enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void addSubtaskById(int subtaskId) {
        subtaskList.add(subtaskId); // Добавление id подзадачи
    }

    public void deleteSubtaskById(int subtaskId) {
        subtaskList.remove(Integer.valueOf(subtaskId)); // Удаление id подзадачи из списка
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Epic epic = (Epic) object;
        return getId() == epic.getId() &&
                Objects.equals(getName(), epic.getName()) &&
                Objects.equals(getDescription(), epic.getDescription());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }
}

