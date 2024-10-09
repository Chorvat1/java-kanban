

// Класс TaskManager для управления задачами, эпиками и подзадачами

import java.util.*;

public class TaskManager {

    // Хранит все задачи
    private final Map<Integer, Task> tasks = new HashMap<>();
    // Хранит все эпики
    private final Map<Integer, Epic> epics = new HashMap<>();
    // Хранит все подзадачи
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    // Счетчик для уникальных идентификаторов задач
    private int nextID = 1;

    // Метод для получения следующего уникального идентификатора
    private int getNextID() {
        return nextID++;
    }

    // Метод для добавления новой задачи
    public Task addTask(Task task) {
        task.setId(getNextID()); // Установка идентификатора
        tasks.put(task.getId(), task); // Добавление задачи в мапу
        return task;
    }

    // Метод для добавления нового эпика
    public Epic addEpic(Epic epic) {
        epic.setId(getNextID()); // Установка идентификатора
        epics.put(epic.getId(), epic); // Добавление эпика в мапу
        return epic;
    }

    // Метод для добавления новой подзадачи
    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID()); // Установка идентификатора
        Epic epic = epics.get(subtask.getEpicID()); // Получаем эпик для подзадачи
        epic.addSubtask(subtask); // Добавляем подзадачу в эпик
        subtasks.put(subtask.getId(), subtask); // Добавляем подзадачу в мапу подзадач
        updateEpicStatus(epic); // Обновляем статус эпика
        return subtask;
    }

    // Метод для обновления существующей задачи
    public Task updateTask(Task task) {
        Integer taskID = task.getId(); // Получаем идентификатор задачи
        if (taskID == null || !tasks.containsKey(taskID)) {
            return null; // Если идентификатор не существует, возвращаем null
        }
        tasks.replace(taskID, task); // Обновляем задачу в мапе
        return task;
    }

    // Метод для обновления существующего эпика
    public Epic updateEpic(Epic epic) {
        Integer epicID = epic.getId();
        if (epicID == null || !epics.containsKey(epicID)) {
            return null;
        }
        // обновляем название и описание эпика
        Epic existingEpic = epics.get(epicID);
        existingEpic.setTitle(epic.getTitle());
        existingEpic.setDescription(epic.getDescription());
        // статус и список подзадач остаются без изменений
        return existingEpic;
    }

    // Метод для обновления существующей подзадачи
    public Subtask updateSubtask(Subtask subtask) {
        Integer subtaskID = subtask.getId(); // Получаем идентификатор подзадачи
        if (subtaskID == null || !subtasks.containsKey(subtaskID)) {
            return null; // Если идентификатор не существует, возвращаем null
        }
        int epicID = subtask.getEpicID(); // Получаем идентификатор эпика
        Subtask oldSubtask = subtasks.get(subtaskID); // Получаем старую подзадачу
        subtasks.replace(subtaskID, subtask); // Обновляем подзадачу в мапе
        // Обновляем подзадачу в списке подзадач эпика и проверяем статус эпика
        Epic epic = epics.get(epicID);
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(oldSubtask); // Удаляем старую подзадачу
        subtaskList.add(subtask); // Добавляем обновленную подзадачу
        epic.setSubtaskList(subtaskList); // Устанавливаем новый список подзадач
        updateEpicStatus(epic); // Обновляем статус эпика
        return subtask;
    }

    // Метод для получения задачи по идентификатору
    public Task getTaskByID(int id) {
        return tasks.get(id); // Возвращаем задачу по идентификатору
    }

    // Метод для получения эпика по идентификатору
    public Epic getEpicByID(int id) {
        return epics.get(id); // Возвращаем эпик по идентификатору
    }

    // Метод для получения подзадачи по идентификатору
    public Subtask getSubtaskByID(int id) {
        return subtasks.get(id); // Возвращаем подзадачу по идентификатору
    }

    // Метод для получения всех задач
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values()); // Возвращаем список всех задач
    }

    // Метод для получения всех эпиков
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values()); // Возвращаем список всех эпиков
    }

    // Метод для получения всех подзадач
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values()); // Возвращаем список всех подзадач
    }

    // Метод для получения подзадач эпика
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtaskList(); // Возвращаем список подзадач для данного эпика
    }

    // Метод для удаления всех задач
    public void deleteTasks() {
        tasks.clear(); // Очищаем все задачи
    }

    // Метод для удаления всех эпиков и их подзадач
    public void deleteEpics() {
        epics.clear(); // Очищаем все эпики
        subtasks.clear(); // Очищаем все подзадачи
    }

    // Метод для удаления всех подзадач
    public void deleteSubtasks() {
        subtasks.clear(); // Очищаем все подзадачи
        for (Epic epic : epics.values()) {
            epic.clearSubtasks(); // Очищаем подзадачи у каждого эпика
            epic.setStatus(Status.NEW); // Обновляем статус эпика на NEW
        }
    }

    // Метод для удаления задачи по идентификатору
    public void deleteTaskByID(int id) {
        tasks.remove(id); // Удаляем задачу по идентификатору
    }

    // Метод для удаления эпика по идентификатору
    public void deleteEpicByID(int id) {
        ArrayList<Subtask> epicSubtasks = epics.get(id).getSubtaskList(); // Получаем подзадачи эпика
        epics.remove(id); // Удаляем эпик по идентификатору
        for (Subtask subtask : epicSubtasks) {
            subtasks.remove(subtask.getId()); // Удаляем все подзадачи эпика
        }
    }

    // Метод для удаления подзадачи по идентификатору
    public void deleteSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id); // Получаем подзадачу по идентификатору
        int epicID = subtask.getEpicID(); // Получаем идентификатор эпика
        subtasks.remove(id); // Удаляем подзадачу
        // Обновляем список подзадач и статус эпика
        Epic epic = epics.get(epicID);
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(subtask); // Удаляем подзадачу из списка подзадач эпика
        epic.setSubtaskList(subtaskList); // Устанавливаем обновленный список подзадач
        updateEpicStatus(epic); // Обновляем статус эпика
    }

    // Вспомогательный private метод для контроля статуса эпика при удалении или изменении подзадач
    private void updateEpicStatus(Epic epic) {
        // Получаем список подзадач, связанных с эпиком
        ArrayList<Subtask> list = epic.getSubtaskList();
        if (list.isEmpty()) {
            epic.setStatus(Status.NEW); // Если у эпика нет подзадач, статус NEW
            return;
        }
        int allIsDoneCount = 0; // Счетчик завершенных подзадач
        int allIsInNewCount = 0; // Счетчик новых подзадач

        // Перебираем все подзадачи, чтобы подсчитать их статусы
        for (Subtask subtask : list) {
            // Если подзадача завершена, инкрементируем счетчик завершенных подзадач
            if (subtask.getStatus() == Status.DONE) {
                allIsDoneCount++;
            }
            // Если подзадача новая, инкрементируем счетчик новых подзадач
            if (subtask.getStatus() == Status.NEW) {
                allIsInNewCount++;
            }
        }

        // Обновляем статус эпика в зависимости от количества завершенных и новых подзадач
        if (allIsDoneCount == list.size()) {
            // Если все подзадачи завершены, устанавливаем статус эпика как DONE
            epic.setStatus(Status.DONE);
        } else if (allIsInNewCount == list.size()) {
            // Если все подзадачи новые, устанавливаем статус эпика как NEW
            epic.setStatus(Status.NEW);
        } else {
            // В противном случае, если есть подзадачи в процессе выполнения, устанавливаем статус как IN_PROGRESS
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
