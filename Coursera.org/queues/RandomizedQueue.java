import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N = 0;
    private Item[] s;

    
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }
    private void throwArgException(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Bad Argument");
    }

    private void throwElementException() {
        if (isEmpty())
            throw new NoSuchElementException("Bad Action");
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        throwArgException(item);
        s[N++] = item;
        if (N == s.length)
            resize(2 * s.length);
    }

    public Item dequeue() {
        throwElementException();
        int random = StdRandom.uniform(N);
        Item item = s[random];
        s[random] = s[N-1];
        s[N-1] = null;
        N--;
        if (N == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    public Item sample() {
        throwElementException();
        int random = StdRandom.uniform(N);
        return s[random];
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i = N;
        private int[] permutation = StdRandom.permutation(N);

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (i == 0) {
                throw new NoSuchElementException();
            }
            return s[permutation[--i]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        System.out.println(test.isEmpty());
        for (int i = 0; i < 10; i++)
            test.enqueue(i);
        int count = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println(test.dequeue());
        }
      

    }
}