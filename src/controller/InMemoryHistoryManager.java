package controller;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{

    private ArrayList<Task> historyList = new ArrayList<Task>();

    @Override
    public void add(Task task) {
        if (historyList.size() >= 10){
            historyList.remove(0);
        }

        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
