package controller;

import model.Node;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public Node<Task> first;
    public Node<Task> last;
    private final HashMap<Integer, Node<Task>> historyHashMap = new HashMap<>();

    public void linkLast(Task task) {
        Node<Task> existingNode = historyHashMap.get(task.getId());
        Node<Task> newNode = new Node<>(last, task, null);
        historyHashMap.put(task.getId(), newNode);
        if (existingNode != last) {
            removeNode(existingNode);
        }

        if (last != null) {
            last.setNext(newNode);
        }

        if (first == null) {
            first = newNode;
        }

        last = newNode;
    }

    @Override
    public void add(Task task) {
        int id = task.getId();
        if (historyHashMap.containsKey(id)) {
            remove(id);
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (historyHashMap.containsKey(id)) {
            removeNode(historyHashMap.get(id));
        }
    }

    private void removeNode(Node<Task> node) {
        if (node == null) {
            return;
        }

        Node<Task> prevNode = node.getPrev();
        Node<Task> nextNode = node.getNext();

        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            first = nextNode;
        }

        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            last = prevNode;
        }


        historyHashMap.remove(node.getData().getId());
    }

    @Override
    public List<Task> getHistory() {
        return getTask();
    }

    ArrayList<Task> getTask() {
        ArrayList<Task> historyList = new ArrayList<>();
        Node<Task> node = first;

        while (node != null) {
            historyList.add(node.getData());
            node = node.getNext();
        }


        return historyList;
    }
}
