package controller;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{

    private LinkedList <Task> historyList = new LinkedList<Task>();

    @Override
    public void add(Task task) {
        if (!(task == null)){
            if (historyList.size() >= 10){
                historyList.removeFirst();
            }

            historyList.add(task);
        }

    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(historyList);
    }
}
