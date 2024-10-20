package tasks;

import enums.Status;

import java.util.Objects;

// Класс Subtask, расширяющий функциональность задачи (Task)
public class Subtask extends Task {
    private final int epicID; // ID связанного эпика

    // Конструктор для создания подзадачи
    public Subtask(String name, String description, int epicID) {
        super(name, description);
        this.epicID = epicID; // Устанавливаем ID эпика
    }

    // Конструктор для обновления подзадачи
    public Subtask(int id, String name, String description, Status status, int epicID) {
        super(id, name, description, status);
        this.epicID = epicID; // Устанавливаем ID эпика
    }

    // Геттер для получения ID эпика
    public int getEpicID() {
        return epicID;
    }

    // Переопределение метода toString для более читаемого вывода информации о подзадаче
    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", epicID=" + epicID +
                ", status=" + getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Subtask subtask = (Subtask) object;
        return getId() == subtask.getId() &&
                epicID == subtask.epicID &&
                Objects.equals(getName(), subtask.getName()) &&
                Objects.equals(getDescription(), subtask.getDescription());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), epicID);
    }
}
