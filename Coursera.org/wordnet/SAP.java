import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private final Digraph graph;

    public SAP(Digraph graph) {
        if(graph==null)
            throw new IllegalArgumentException();
        this.graph = new Digraph(graph);
    }

    public int length(int v, int w) {
        if(v<0||v>=graph.V()||w<0||w>=graph.V())
            throw new IllegalArgumentException();
        int[] markedA = shortestPath(v);
        int[] markedB = shortestPath(w);
        int min = -1;
        for(int i = 0; i < graph.V(); i++) {
            if(markedA[i] == -1 || markedB[i] == -1)
                continue;
            if(min == -1)
                min = markedA[i] + markedB[i];
            if(min > markedA[i] + markedB[i])
                min = markedA[i] + markedB[i];
        }
        return min;
    }

    public int ancestor(int v, int w) {
        if(v<0||v>=graph.V()||w<0||w>=graph.V())
            throw new IllegalArgumentException();
        int[] markedA = shortestPath(v);
        int[] markedB = shortestPath(w);
        int min = -1;
        for(int i = 0; i < graph.V(); i++) {
            if (markedA[i] == -1 || markedB[i] == -1)
                continue;
            if(min == -1)
                min = i;
            if (markedA[min] + markedB[min] > markedA[i] + markedB[i])
                min = i;
        }
        return min;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if(v==null||w==null)
            throw new IllegalArgumentException();
        for(Integer i : v)
            if(i==null||i<0||i>=graph.V())
                throw new IllegalArgumentException();
        for(Integer i : w)
            if(i==null||i<0||i>=graph.V())
                throw new IllegalArgumentException();

        int[] markedA = shortestPath(v);
        int[] markedB = shortestPath(w);
        int min = -1;
        for(int i = 0; i < graph.V(); i++) {
            if(markedA[i] == -1 || markedB[i] == -1)
                continue;
            if(min == -1)
                min = markedA[i] + markedB[i];
            if(min > markedA[i] + markedB[i])
                min = markedA[i] + markedB[i];
        }
        return min;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if(v==null||w==null)
            throw new IllegalArgumentException();
        for(Integer i : v)
            if(i==null||i<0||i>=graph.V())
                throw new IllegalArgumentException();
        for(Integer i : w)
            if(i==null||i<0||i>=graph.V())
                throw new IllegalArgumentException();

        int[] markedA = shortestPath(v);
        int[] markedB = shortestPath(w);

        int min = -1;
        for(int i = 0; i < graph.V(); i++) {
            if (markedA[i] == -1 || markedB[i] == -1)
                continue;
            if(min == -1)
                min = i;
            if (markedA[min] + markedB[min] > markedA[i] + markedB[i])
                min = i;
        }
        return min;
    }

    //TODO: BFS multiple-source
    private int[] shortestPath(Iterable<Integer> sources) {
        int[] marked = new int[graph.V()];
        for(int i = 0; i < graph.V(); i++)
            marked[i] = -1;         //TODO: marked as unreachable vertex
        Queue<Integer> queue = new Queue<Integer>();
        int step = 0;
        queue.enqueue(-1);
        for(int i : sources) {
            queue.enqueue(i);
            marked[i] = step;
        }
        while(!queue.isEmpty()) {
            int head = queue.dequeue();
            if(head==-1) {
                step++;
                if(queue.size()!=0)     //TODO: infinity loop -1, -1, -1, ...
                    queue.enqueue(-1);
                continue;
            }
            for(int i : graph.adj(head)) {
                if(marked[i]==-1) {
                    queue.enqueue(i);
                    marked[i] = step;
                }
            }
        }
        return marked;
    }

    //TODO: BFS one source
    private int[] shortestPath(int source) {
        int[] marked = new int[graph.V()];
        for(int i = 0; i < graph.V(); i++)
            marked[i] = -1;         //TODO: marked as unreachable vertex
        Queue<Integer> queue = new Queue<Integer>();
        int step = 0;
        marked[source] = 0;
        queue.enqueue(-1);
        queue.enqueue(source);
        while(!queue.isEmpty()) {
            int head = queue.dequeue();
            if(head==-1) {
                step++;
                if(queue.size()!=0)     //TODO: infinity loop -1, -1, -1, ...
                    queue.enqueue(-1);
                continue;
            }
            for(int i : graph.adj(head)) {
                if(marked[i]==-1) {
                    queue.enqueue(i);
                    marked[i] = step;
                }
            }
        }
        return marked;
    }
}
