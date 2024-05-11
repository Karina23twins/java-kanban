package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    HashMap<Integer, Node> history = new HashMap<>();
    Node head;
    Node tail;

    @Override
    public void add(Task task) {
        if (history.containsKey(task.getId())) {
            Node node = history.get(task.getId());
            removeNode(node);
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task item) {
        final Node t = tail;
        final Node newNode = new Node(t, item, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }

        history.put(item.getId(), newNode);

        if (t != null) {
            history.put(t.item.getId(), t);
        }
    }

    private List<Task> getTasks() {
        ArrayList<Task> list = new ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }

    private void removeNode(Node node) {
        history.remove(node.item.getId());

        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }

    private static class Node {
        Node prev;
        Task item;
        Node next;

        public Node(Node prev, Task item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}