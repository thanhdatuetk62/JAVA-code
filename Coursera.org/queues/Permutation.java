import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        String s;
        RandomizedQueue<String> ranQ = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            s = StdIn.readString();
            ranQ.enqueue(s);
        }
        int n = ranQ.size();
        for(int i =0;i<n-k;i++) {
            ranQ.dequeue();
        }
        for(String key:ranQ) {
            StdOut.println(key);
        }
    }
}