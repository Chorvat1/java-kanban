package tasks;

import enums.Status;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskList = new ArrayList<>(); // список подзадач

    // Конструктор
    public Epic(String name, String description) {
        super(name, description);
        setStatus(Status.NEW);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Integer> getSubtaskList() {
        return subtaskList;
    }

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
