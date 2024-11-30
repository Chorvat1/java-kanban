package managers;

import enums.Status;
import tasks.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    public Map<Integer, Task> tasks = new HashMap<>();
    public Map<Integer, Subtask> subtasks = new HashMap<>();
    public Map<Integer, Epic> epics = new HashMap<>();
    private int nextId = 0;

    public HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    public int getNextId() {
        return nextId++;
    }

    // Создание задач

    @Override
    public void addTask(Task task) {
        if (task != null) {
            task.setId(getNextId());
            tasks.put(task.getId(), task);
        }

    }

    @Override
    public void addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(getNextId());
            epics.put(epic.getId(), epic);
        }

    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask != null) {
            subtask.setId(getNextId());
            subtasks.put(subtask.getId(), subtask);

            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            actualizeEpicStatus(epic);
        }
    }

    // Редактирование задач

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        actualizeEpicStatus(epic);

    }


    // Получение задач

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Получение задач по идентификатору

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if(task == null) {
            System.out.println("Не удалось найти Task по идентификатору " + id);
        } else {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if(epic == null) {
            System.out.println("Не удалось найти Epic по идентификатору " + id);
        } else {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if(subtask == null) {
            System.out.println("Не удалось найти Subtask по идентификатору " + id);
        } else {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public List<Subtask> getEpicSubtasksById(int id) {
        List<Subtask> items = new ArrayList<>();
        Epic epic = getEpicById(id);
        if (epic == null) {
            System.out.println("Не удалось найти Epic по идентификатору " + id);
            return items;
        }
        List<Integer> subtaskIds = epic.getSubtaskList();
        for (Integer subtaskId : subtaskIds) {
            items.add(subtasks.get(subtaskId));
        }
        return items;

    }

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }


    // Удаление задач

    @Override
    public void removeTasks() {
        tasks.clear();
    }

    @Override
    public void removeEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeSubtasks() {
        subtasks.clear();
        for (Epic epic : getEpics()) {
            List<Integer> subtaskIds = epic.getSubtaskList();
            subtaskIds.clear();
        }
    }

    // Удаление задач по идентификатору

    @Override
    public void removeTaskById(int id) {
        Task task = getTaskById(id);
        if (task != null) {
            tasks.remove(task.getId());

        }


    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = getEpicById(id);
        if (epic != null) {
            List<Integer> subtaskIds = epic.getSubtaskList();
            for (Integer subtaskId : subtaskIds) {
                subtasks.remove(subtaskId);
                System.out.println("Подзадача с идентификатором " + subtaskId + " успешно удалена");
            }
            epics.remove(epic.getId());
            System.out.println("Epic c идентификатором " + epic.getId() + " успешно удалён");
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask subtask = getSubtaskById(id);
        if (subtask != null) {
            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            List<Integer> subtaskIds = epic.getSubtaskList();
            subtaskIds.remove(Integer.valueOf(subtask.getId()));
            System.out.println("Epic c идентификатором " + epic.getId() + " успешно обновлён");
            subtasks.remove(subtask.getId());
            System.out.println("Подзадача с идентификатором " + subtask.getId() + " успешно удалён");
            actualizeEpicStatus(epic);
        }


    }

    // Вспомогательные методы

    private void actualizeEpicStatus(Epic epic) {
        if (epic != null) {

            boolean isNew = true;
            boolean isDone = true;

            List<Integer> subtaskIds = epic.getSubtaskList();
            for (Integer subtaskId : subtaskIds) {
                Subtask otherSubtask = subtasks.get(subtaskId);
                if (isNew && otherSubtask.getStatus() == Status.NEW) {
                    isDone = false;
                } else if (isDone && otherSubtask.getStatus() == Status.DONE) {
                    isNew = false;
                } else {
                    isDone = false;
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                epic.setStatus(Status.NEW);
            } else if (isDone) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }

    }
}
