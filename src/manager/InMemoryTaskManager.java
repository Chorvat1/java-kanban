package manager;

import enums.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    // Хранит все задачи
    private final Map<Integer, Task> tasks = new HashMap<>();
    // Хранит все эпики
    private final Map<Integer, Epic> epics = new HashMap<>();
    // Хранит все подзадачи
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    // ссылка на менеджер истории
    private HistoryManager historyManager;

    // Счетчик для уникальных идентификаторов задач
    private int nextID = 1;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    // Метод для получения следующего уникального идентификатора

    public int getNextID() {
        return nextID++;
    }

    // Метод для добавления новой задачи
    @Override
    public Task addTask(Task task) {
        task.setId(getNextID()); // Установка идентификатора
        tasks.put(task.getId(), task); // Добавление задачи в мапу
        return task;
    }

    // Метод для добавления нового эпика
    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(getNextID()); // Установка идентификатора
        epics.put(epic.getId(), epic); // Добавление эпика в мапу
        return epic;
    }

    // Метод для добавления новой подзадачи
    @Override
    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID()); // Установка идентификатора
        Epic epic = epics.get(subtask.getEpicID()); // Получаем эпик для подзадачи
        epic.addSubtaskById(subtask.getId()); // Используем новый метод для добавления
        subtasks.put(subtask.getId(), subtask); // Добавляем подзадачу в мапу подзадач
        updateEpicStatus(epic); // Обновляем статус эпика
        return subtask;
    }

    @Override
    public Task updateTask(Task task) {
        int taskID = task.getId(); // Получаем идентификатор задачи
        if (!tasks.containsKey(taskID)) {
            return null; // Если идентификатор не существует, возвращаем null
        }
        // Создаем обновленную задачу с сохранением статуса
        tasks.replace(taskID, new Task(task.getId(), task.getName(), task.getDescription(), task.getStatus()));
        return tasks.get(taskID);
    }

    // Метод для обновления существующего эпика
    @Override
    public Epic updateEpic(Epic epic) {
        int epicID = epic.getId();
        if (!epics.containsKey(epicID)) {
            return null;
        }
        // обновляем название и описание эпика
        Epic existingEpic = epics.get(epicID);
        existingEpic.setName(epic.getName()); // Обновляем название
        existingEpic.setDescription(epic.getDescription());
        // статус и список подзадач остаются без изменений
        return existingEpic;
    }

    // Метод для обновления существующей подзадачи
    @Override
    public Subtask updateSubtask(Subtask subtask) {
        int subtaskID = subtask.getId(); // Получаем идентификатор подзадачи
        if (!subtasks.containsKey(subtaskID)) {
            return null; // Если идентификатор не существует, возвращаем null
        }
        subtasks.replace(subtaskID, subtask);
        Epic epic = epics.get(subtask.getEpicID());
        updateEpicStatus(epic);
        return subtask;
    }

    @Override
    public Task getTaskByID(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task); // добавляем задачу в историю, если она существует
        }
        return task;
    }

    // Метод для получения эпика по идентификатору
    @Override
    public Epic getEpicByID(int id) {
        return epics.get(id); // Возвращаем эпик по идентификатору
    }

    // Метод для получения подзадачи по идентификатору
    @Override
    public Subtask getSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask); // Добавляем в историю, если подзадача найдена
        }
        return subtask;
    }

    // Метод для получения всех задач
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values()); // Возвращаем список всех задач
    }

    // Метод для получения всех эпиков
    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values()); // Возвращаем список всех эпиков
    }

    // Метод для получения всех подзадач
    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values()); // Возвращаем список всех подзадач
    }

    // Метод для получения подзадач эпика
    @Override
    public List<Subtask> getEpicSubtasks(Epic epic) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        for (Integer subtaskList : epic.getSubtaskList()) {
            epicSubtasks.add(subtasks.get(subtaskList));
        }
        return epicSubtasks;
    }

    // Метод для удаления всех задач
    @Override
    public void deleteTasks() {
        tasks.clear(); // Очищаем все задачи
    }

    // Метод для удаления всех эпиков и их подзадач
    @Override
    public void deleteEpics() {
        epics.clear(); // Очищаем все эпики
        subtasks.clear(); // Очищаем все подзадачи
    }

    // Метод для удаления всех подзадач
    @Override
    public void deleteSubtasks() {
        subtasks.clear(); // Очищаем все подзадачи
        for (Epic epic : epics.values()) {
            epic.clearSubtasks(); // Очищаем подзадачи у каждого эпика
            epic.setStatus(Status.NEW); // Обновляем статус эпика на NEW
        }
    }

    // Метод для удаления задачи по идентификатору
    @Override
    public void deleteTaskByID(int id) {
        List<Integer> epicSubtaskIDs = epics.get(id).getSubtaskList(); // Получаем ID подзадач
        tasks.remove(id); // Удаляем задачу по идентификатору
    }

    // Метод для удаления эпика по идентификатору
    @Override
    public void deleteEpicByID(int id) {
        List<Subtask> epicSubtasks = getEpicSubtasks(epics.get(id)); // Получаем подзадачи эпика
        epics.remove(id); // Удаляем эпик по идентификатору
        for (Subtask subtask : epicSubtasks) {
            subtasks.remove(subtask.getId()); // Удаляем все подзадачи эпика
        }
    }

    // Метод для удаления подзадачи по идентификатору
    @Override
    public void deleteSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id); // Получаем подзадачу по идентификатору
        int epicID = subtask.getEpicID(); // Получаем идентификатор эпика
        subtasks.remove(id); // Удаляем подзадачу
        // Обновляем список подзадач и статус эпика
        Epic epic = epics.get(epicID);
        List<Integer> subtaskList = epic.getSubtaskList();
        subtaskList.remove(subtask); // Удаляем подзадачу из списка подзадач эпика
        epic.setSubtaskList(subtaskList); // Устанавливаем обновленный список подзадач
        updateEpicStatus(epic); // Обновляем статус эпика

    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // Вспомогательный private метод для контроля статуса эпика при удалении или изменении подзадач
    private void updateEpicStatus(Epic epic) {
        // Получаем список подзадач, связанных с эпиком
        List<Subtask> list = getEpicSubtasks(epic);
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
