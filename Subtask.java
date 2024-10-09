

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
}
