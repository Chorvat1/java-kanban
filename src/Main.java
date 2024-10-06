public class Main {

    public static void main(String[] args) {
        // Создаем экземпляр менеджера задач
        TaskManager taskManager = new TaskManager();

        // Создаем задачу "Приготовить суп" с описанием
        Task washFloor = new Task("Приготовить суп", "С новым соусом");
        // Добавляем задачу в TaskManager и получаем созданную задачу
        Task washFloorCreated = taskManager.addTask(washFloor);
        System.out.println(washFloorCreated); // Выводим созданную задачу

        // Создаем задачу для обновления с новым статусом IN_PROGRESS
        Task washFloorToUpdate = new Task(washFloor.getId(), "Не забыть приготовить суп", "Продукты в холодильнике", Status.IN_PROGRESS);
        // Обновляем задачу и получаем обновленную задачу
        Task washFloorUpdated = taskManager.updateTask(washFloorToUpdate);
        System.out.println(washFloorUpdated); // Выводим обновленную задачу

        // Создаем эпик "Запланировать отпуск" с описанием
        Epic flatRenovation = new Epic("Запланировать отпуск", "Нужно успеть до конца месяца");
        taskManager.addEpic(flatRenovation); // Добавляем эпик в TaskManager
        System.out.println(flatRenovation); // Выводим информацию о эпике

        // Создаем подзадачи для эпика
        Subtask flatRenovationSubtask1 = new Subtask("Выбрать страну", "Желательно восточную!", flatRenovation.getId());
        Subtask flatRenovationSubtask2 = new Subtask("Выбрать отель", "Обязательно 5 звезд", flatRenovation.getId());
        // Добавляем подзадачи в TaskManager
        taskManager.addSubtask(flatRenovationSubtask1);
        taskManager.addSubtask(flatRenovationSubtask2);

        System.out.println(flatRenovation); // Выводим обновленную информацию о эпике

        // Обновляем статус второй подзадачи на DONE
        flatRenovationSubtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(flatRenovationSubtask2); // Обновляем подзадачу в TaskManager
        System.out.println(flatRenovation); // Выводим информацию о эпике после обновления подзадачи
    }
}