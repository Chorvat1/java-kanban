import Enum.Status;
import Tasks.Task;
import Tasks.Epic;
import Tasks.Subtask;
import Manager.TaskManager;
import Manager.Managers;

public class Main {
    // Создаем инстанс менеджера задач для хранения задач в памяти
    private static final TaskManager inMemoryTaskManager = Managers.getDefault();

    public static void main(String[] args) {
        // Вызов методов для добавления задач и их отображения
        addTasks(); // Добавляем задачи
        printAllTasks(); // Печатаем все задачи
        printViewHistory(); // Печатаем историю просмотров задач
    }

    // Метод для добавления задач в менеджер задач
    private static void addTasks() {
//        // Создаем экземпляр менеджера задач
//        TaskManager taskManager = new TaskManager();

        // Создаем задачу "Приготовить суп" с описанием
        Task washFloor = new Task("Приготовить суп", "С новым соусом");
        // Добавляем задачу в менеджер
        inMemoryTaskManager.addTask(washFloor);

        // Создаем задачу для обновления с новым статусом IN_PROGRESS
        Task washFloorToUpdate = new Task(washFloor.getId(), "Не забыть приготовить суп", "Продукты в холодильнике", Status.IN_PROGRESS);
        // Обновляем задачу и получаем обновленную задачу
        inMemoryTaskManager.updateTask(washFloorToUpdate); // Обновляем задачу

        // Создаем эпик "Запланировать отпуск" с описанием
        Epic flatRenovation = new Epic("Запланировать отпуск", "Нужно успеть до конца месяца");
        inMemoryTaskManager.addEpic(flatRenovation); // Добавляем эпик в менеджер

        // Создаем подзадачи для эпика
        Subtask flatRenovationSubtask1 = new Subtask("Выбрать страну", "Желательно восточную!", flatRenovation.getId());
        Subtask flatRenovationSubtask2 = new Subtask("Выбрать отель", "Обязательно 5 звезд", flatRenovation.getId());
        // Добавляем подзадачи в Manager.TaskManager
        inMemoryTaskManager.addSubtask(flatRenovationSubtask1);
        inMemoryTaskManager.addSubtask(flatRenovationSubtask2);

        System.out.println(flatRenovation); // Выводим обновленную информацию о эпике

        // Обновляем статус второй подзадачи на DONE
        flatRenovationSubtask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(flatRenovationSubtask2); // Обновляем подзадачу в Manager.TaskManager
        System.out.println(flatRenovation); // Выводим информацию о эпике после обновления подзадачи
    }

    // Метод для печати всех задач, эпиков и подзадач
    private static void printAllTasks() {
        System.out.println("Задачи:");
        // Перебираем и печатаем все задачи
        for (Task task : Main.inMemoryTaskManager.getTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        // Перебираем и печатаем все эпики и их подзадачи
        for (Epic epic : Main.inMemoryTaskManager.getEpics()) {
            System.out.println(epic);
            for (Task task : Main.inMemoryTaskManager.getEpicSubtasks(epic)) {
                System.out.println("--> " + task);
            }
        }

        System.out.println("Подзадачи:");
        // Перебираем и печатаем все подзадачи
        for (Task subtask : Main.inMemoryTaskManager.getSubtasks()) {
            System.out.println(subtask);
        }
    }

    // Метод для демонстрации истории просмотров задач
    private static void printViewHistory() {
        // Просматриваем несколько задач, чтобы подготовить историю просмотров
        Main.inMemoryTaskManager.getTaskByID(1);
        Main.inMemoryTaskManager.getTaskByID(2);
        Main.inMemoryTaskManager.getEpicByID(3);
        Main.inMemoryTaskManager.getTaskByID(1);
        Main.inMemoryTaskManager.getSubtaskByID(4);
        Main.inMemoryTaskManager.getSubtaskByID(5);
        Main.inMemoryTaskManager.getSubtaskByID(6);
        Main.inMemoryTaskManager.getEpicByID(3);
        Main.inMemoryTaskManager.getSubtaskByID(4);
        Main.inMemoryTaskManager.getTaskByID(2);
        Main.inMemoryTaskManager.getSubtaskByID(6);

        System.out.println();
        System.out.println("История просмотров:");

        // Печатаем последние просмотренные задачи
        for (Task task : Main.inMemoryTaskManager.getHistory()) {
            System.out.println(task);
        }
    }

}