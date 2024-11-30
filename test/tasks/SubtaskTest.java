package tasks;

import org.junit.jupiter.api.*;

class SubtaskTest {

    @Test
    void testSubtaskEqualityById() {
        Subtask subtask1 = new Subtask( "Subtask2", "description2",  5);
        Subtask subtask2 = new Subtask( "Subtask2", "description2",  5);
        Assertions.assertEquals(subtask1, subtask2,"Подзадачи должны быть равны друг другу");
    }

}