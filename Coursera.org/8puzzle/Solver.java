import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private boolean solvable;
    private int moves;
    private ArrayList<Board> solution = null;
    private class InnerBoard {
        int manhhatan=-1;
        Board board;
        InnerBoard predecessor = null;
        int steps;
        InnerBoard(Board board, int steps) {
            this.board = board;
            this.steps = steps;
            if(manhhatan==-1) {
                this.manhhatan = board.manhattan();
            }
        }
        InnerBoard(Board board, InnerBoard predecessor, int steps) {
            this.board = board;
            this.steps = steps;
            this.predecessor = predecessor;
            if(manhhatan==-1) {
                this.manhhatan = board.manhattan();
            }
        }
    }
    private static class ManhattanOrder implements Comparator<InnerBoard> {
        public int compare(InnerBoard b1, InnerBoard b2) {
            return (b1.manhhatan+b1.steps)-(b2.manhhatan+b2.steps);
        }
    }
    public Solver(Board initial) {
        if(initial==null) {
            throw new IllegalArgumentException();
        }
        moves = 0;
        int steps = 0;
        int stepsTwin = 0;
        InnerBoard init = new InnerBoard(initial, steps);
        InnerBoard twin = new InnerBoard(initial.twin(), stepsTwin);
        MinPQ<InnerBoard> pq = new MinPQ<InnerBoard>(new ManhattanOrder());
        MinPQ<InnerBoard> pqTwin = new MinPQ<InnerBoard>(new ManhattanOrder());
        pq.insert(init);
        pqTwin.insert(twin);
        while(true) {
            //Origin WorkSpace
            InnerBoard fromOrigin = pq.delMin();
            InnerBoard fromTwin = pqTwin.delMin();
            if(fromOrigin.board.isGoal()) {
                solvable = true;
                solution = new ArrayList<Board>();
                moves = fromOrigin.steps;
                InnerBoard start = fromOrigin;
                solution.add(start.board);
                while(start.predecessor!=null) {
                    start = start.predecessor;
                    solution.add(0, start.board);
                }
                break;
            }
            if(fromTwin.board.isGoal()) {
                solvable = false;
                moves = -1;
                break;
            }
            steps = fromOrigin.steps+1;
            stepsTwin = fromTwin.steps+1;
            for(Board neighbor : fromOrigin.board.neighbors()) {
                if(fromOrigin.predecessor!=null&&neighbor.equals(fromOrigin.predecessor.board))
                    continue;
                pq.insert(new InnerBoard(neighbor, fromOrigin, steps));
            }
            //Twin WorkSpace
            for(Board neighbor : fromTwin.board.neighbors()) {
                if(fromTwin.predecessor!=null&&neighbor.equals(fromTwin.predecessor.board))
                    continue;
                pqTwin.insert(new InnerBoard(neighbor, fromTwin, stepsTwin));
            }
            //
        }
    }
    public boolean isSolvable() {
        return solvable;
    }
    public int moves() {
        return moves;
    }
    public Iterable<Board> solution() {
        return solution;
    }
    public static void main(String[] args) {
        // create initial board from file
        int n=3;
        int[][] blocks = new int[n][n];
        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 5;
        blocks[1][2] = 6;
        blocks[2][0] = 8;
        blocks[2][1] = 7;
        blocks[2][2] = 0;
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
