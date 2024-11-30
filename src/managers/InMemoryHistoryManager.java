package managers;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new LinkedList<>();
    private static final  int MAX_HISTORIES_TASK = 10;

    @Override
    public void add(Task task){
        if(task != null) {
        if(history.size() > MAX_HISTORIES_TASK) {
            history.removeFirst();
        }
        history.add(task);
        }
    }

    @Override
    public List<Task> getHistory(){
        return history;
    }


}
