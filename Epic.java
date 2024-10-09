

import java.util.ArrayList;

// Класс Epic, расширяющий функциональность задачи (Task)
public class Epic extends Task {
    private ArrayList<Subtask> subtaskList = new ArrayList<>(); // Список подзадач

    // Конструктор для создания эпика
    public Epic(String name, String description) {
        super(name, description);
    }

    // Конструктор для обновления эпика
    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    // Метод для получения названия эпика
    public String getTitle() {
        return getName(); // Здесь мы используем getName() для получения названия
    }

    // Метод для установки нового названия эпика
    public void setTitle(String title) {
        setName(title); // Используем метод setName из класса Task
    }

    // Метод для добавления подзадачи к списку подзадач
    public void addSubtask(Subtask subtask) {
        subtaskList.add(subtask);
    }

    // Метод для очистки списка подзадач
    public void clearSubtasks() {
        subtaskList.clear();
    }

    // Геттер для получения списка подзадач
    public ArrayList<Subtask> getSubtaskList() {
        return subtaskList;
    }

    // Сеттер для установки нового списка подзадач
    public void setSubtaskList(ArrayList<Subtask> subtaskList) {
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
