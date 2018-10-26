import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node end;
    private int size;

    private class Node {
        Item item;
        Node left;
        Node right;
    }

    public Deque() {
        front = null;
        end = null;
        size = 0;
    }

    private void throwArgException(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Bad Argument");
    }

    private void throwElementException() {
        if (isEmpty())
            throw new NoSuchElementException("Bad Action");
    }

    public boolean isEmpty() {
        return (front == null);
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        throwArgException(item);
        if (isEmpty()) {
            front = new Node();
            end = front;
            front.item = item;
            front.left = null;
            front.right = null;
        } else {
            Node oldfront = front;
            front = new Node();
            front.item = item;
            front.right = oldfront;
            front.left = null;
            oldfront.left = front;
        }
        size++;
    }

    public void addLast(Item item) {
        throwArgException(item);
        if (isEmpty()) {
            front = new Node();
            end = front;
            front.item = item;
            front.right = null;
            front.left = null;
        } else {
            Node oldEnd = end;
            end = new Node();
            end.item = item;
            end.left = oldEnd;
            end.right = null;
            oldEnd.right = end;
        }
        size++;
    }

    public Item removeFirst() {
        throwElementException();
        Item item = front.item;
        if (size == 1) {
            front = null;
            end = null;
        } else {
            front = front.right;
            if (front != null) {
                front.left = null;
            }
        }
        size--;
        return item;
    }

    public Item removeLast() {
        throwElementException();
        Item item = end.item;
        if (size == 1) {
            end = null;
            front = null;
        } else {
            end = end.left;
            if (end != null) {
                end.right = null;
            }
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = front;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.right;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        test.addLast(8);
        test.addFirst(7);
        test.addFirst(5);
        test.addLast(7);
        test.removeLast();
        test.removeLast();
        test.removeLast();

        for (int i : test) {
            System.out.println(i);
        }
        System.out.println(test.size());
    }

}