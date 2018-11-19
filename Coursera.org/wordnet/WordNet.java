import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;

public class WordNet {
    private int V;
    private Digraph graph;
    private boolean[] complete;                                 //TODO: just for check cycle
    private boolean[] marked;                                   //TODO: just for check cycle, connected
    private RedBlackBST<String, ArrayList<Integer>> nouns;     //TODO:represent nouns
    private RedBlackBST<Integer, String> synSet;                  //TODO:represent synsets
    private SAP sap;
    public WordNet(String synsets, String hypernyms) {
        if(synsets ==null || hypernyms ==null)
            throw new IllegalArgumentException();
        processNoun(synsets);                                   //TODO: Initial nouns and synSet
        processHyper(hypernyms);                                //TODO: Initial graph
        complete = new boolean[V];
        marked = new boolean[V];
        sap = new SAP(graph);
        //TODO: Check is a ROOT DAG?
        if(!isRootDAG())
            throw new IllegalArgumentException();
    }

    public Iterable<String> nouns() {
        return nouns.keys();
    }

    public boolean isNoun(String word) {
        if(word==null)
            throw new IllegalArgumentException();
        return nouns.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if(nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if(!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        if(nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if(!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        int id = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        return synSet.get(id);
    }

    //TODO: Just for serving constructor!
    private String[] processComma(String str) {
        String[] parts = str.split(",");
        return parts;
    }

    private String[] processSpace(String str) {
        String[] parts = str.split(" ");
        return parts;
    }

    private void processNoun(String synsets) {
        V = 0;
        synSet = new RedBlackBST<Integer, String>();
        nouns = new RedBlackBST<String, ArrayList<Integer>>();
        In synset = new In(synsets);                        //TODO: read input
        while(synset.hasNextLine()) {
            V++;
            String[] info = processComma(synset.readLine());
            int id = Integer.parseInt(info[0]);             //TODO: ID of synset
            synSet.put(id, info[1]);                        //TODO: Synsets
            String[] nounSyn = processSpace(info[1]);
            for(int i = 0 ; i < nounSyn.length; i++) {
                if (!nouns.contains(nounSyn[i])) {
                    ArrayList<Integer> tmp = new ArrayList<Integer>();
                    tmp.add(id);
                    nouns.put(nounSyn[i], tmp);
                } else
                    nouns.get(nounSyn[i]).add(id);          //TODO: value is not necessary
            }
        }
    }

    private void processHyper(String hypernyms) {
        graph = new Digraph(V);
        In hypernym = new In(hypernyms);
        while(hypernym.hasNextLine()) {
            String[] hyper = processComma(hypernym.readLine());
            for(int i = 1; i < hyper.length; i++)
                graph.addEdge(Integer.parseInt(hyper[0]), Integer.parseInt(hyper[i]));
        }
    }

    private boolean checkConnected() {
        int count = 0;
        for(int i = 0; i < V; i++) {
            if(graph.outdegree(i)==0)
                count++;
            if(count>1)
                return false;
        }
        return true;
    }

    private boolean isRootDAG() {
        for(int v = 0; v < V; v++) {
            if(!marked[v]) {
                if(dfs(v))
                    return false;    //TODO: Exist a Directed Cycle, FAIL!
            }
        }
        if(!checkConnected())    //TODO: Graph is not connected, FAIL!
            return false;
        return true;
    }

    private boolean dfs(int v) {
        boolean check  = false;
        marked[v] = true;
        for(int w : graph.adj(v)) {
            if(!marked[w])
                check = check||dfs(w);
            else if(!complete[w])
                return true;        //TODO: Exist a Direct Cycle
        }
        complete[v] = true;         //TODO: Check is this method complete
        return check;
    }
}
