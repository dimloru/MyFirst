package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;


    public CustomTree() {
        this.root = new Entry<>("Groot");

    }

    public boolean add (String s) {
        Entry<String> node = searchNode();

        if (node == null) {
            refreshBranches();
            node = searchNode();
        }

        if (node.availableToAddLeftChildren) {
            node.leftChild = new Entry<>(s);
            node.leftChild.parent = node;
            node.availableToAddLeftChildren = false;
        } else if (node.availableToAddRightChildren) {
            node.rightChild = new Entry<>(s);
            node.rightChild.parent = node;
            node.availableToAddRightChildren = false;
        }

        return true;
    }

    private void refreshBranches() {
        Queue<Entry<String>> refreshQueue = new LinkedList<>();
        Entry<String> refreshNode = root;
        while(refreshNode != null) {
            if (refreshNode.leftChild != null) {
                refreshQueue.offer(refreshNode.leftChild);
            } else {
                refreshNode.availableToAddLeftChildren = true;
            }

            if (refreshNode.rightChild != null) {
                refreshQueue.offer(refreshNode.rightChild);
            } else {
                refreshNode.availableToAddRightChildren = true;
            }

            refreshNode = refreshQueue.poll();
        }
    }

    private Entry<String> searchNode() {
        Queue<Entry<String>> queue = new LinkedList<>();
        Entry<String> node = root;
        while(node != null && !node.isAvailableToAddChildren()) {
            if (node.leftChild != null) queue.offer(node.leftChild);
            if (node.rightChild != null) queue.offer(node.rightChild);
            node = queue.poll();
        }
        return node;
    }


    public String getParent(String s) {
        Queue<Entry<String>> queue = new LinkedList<>();
        Entry<String> node = root;
        while (node != null && !node.elementName.equals(s)) {
            if (node.leftChild != null) queue.offer(node.leftChild);
            if (node.rightChild != null) queue.offer(node.rightChild);
            node = queue.poll();
        }

        if (node == null) return "null"; //parent not found
        return node.parent.elementName;
    }

    @Override
    public int size() {
        Stack<Entry<String>> stack = new Stack<>();
        Entry<String> top = root;
        int count = -1; // не учитывам root по условию задачи
        while (top != null) {
            count++;
            if (top.rightChild != null) stack.push((top.rightChild));
            if (top.leftChild != null) stack.push(top.leftChild);

            if (!stack.empty()) {
                top = stack.pop();
            } else {
                top = null;
            }
        }
        return count;
    }

    public boolean remove(Object o) { //в условии Object
        String s;
        try {
            s = (String) o;
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException();
        }

        Queue<Entry<String>> queue = new LinkedList<>();
        Entry<String> node = root;

        if (s == null) {
            System.out.println("Wrong input parameter. Removed nothing");
            return false;
        }

        while (node != null && !node.elementName.equals(s)) {
            if (node.leftChild != null) queue.offer(node.leftChild);
            if (node.rightChild != null) queue.offer(node.rightChild);
            node = queue.poll();
        }

        if (node == null) {
            System.out.println("No such element found");
            return false;
        }


        if (node.parent.leftChild != null && node.elementName.equals(node.parent.leftChild.elementName)) {
            node.parent.leftChild = null;
            // тут можно добавить разрешение добавлять элементы вместо отрезанной ветви
        } else {
            node.parent.rightChild = null;
        }

        return true;

    }


    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }


    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;

        }

        void checkChildren() {
            if (leftChild != null) availableToAddLeftChildren = false;
            if (rightChild != null) availableToAddRightChildren = false;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
