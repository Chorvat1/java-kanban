import enums.Status;
import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Приготовить суп", "С новым соусом");
        taskManager.addTask(task1);
        System.out.println(task1);

        Task task2 = new Task("Купить обои", "Съездить в Леруа");
        taskManager.addTask(task2);
        System.out.println(task2);

        Epic epic1 = new Epic("Запланировать отпуск", "Выбрать место отпуска");
        taskManager.addEpic(epic1);
        System.out.println(epic1);

        Epic epic2 = new Epic("Epic2", "Описание 2");
        taskManager.addEpic(epic2);
        System.out.println(epic2);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId());
        taskManager.addSubtask(subtask1);
        System.out.println(subtask1);

        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId());
        taskManager.addSubtask(subtask2);
        System.out.println(subtask2);

        System.out.println(epic1);
        System.out.println(epic2);

        // Редактирование cвойств

        task2.setDescription("Новое описание задачи");
        taskManager.updateTask(task2);
        System.out.println(task2);

        epic1.setName("Новое название Эпика");
        epic1.setDescription("Новое описание Эпика");
        taskManager.updateEpic(epic1);
        System.out.println(epic1);

        subtask2.setDescription("Изменение описание подзадачи");
        taskManager.updateSubtask(subtask2);
        System.out.println(subtask2);

        //Редактирование статусов
        task2.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task2);

        subtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask2);


        printAllTasks(taskManager);


    }


    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubtasksById(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
