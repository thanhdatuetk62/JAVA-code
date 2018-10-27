import java.util.ArrayList;

public class Board {
    private int[][] blocks;
    private int hammingScr=0;
    private int manhattanScr=0;
    private int dim;
    private int valueOfBlank;
    private boolean goal = false;

    public Board(int[][] b) {
        if(b==null) {
            throw new IllegalArgumentException("Bad array constructor.");
        }
        dim = b[0].length;
        this.blocks = new int[dim][dim];
        for(int i=0; i<dim ;i++) {
            for(int j=0; j<dim; j++) {
                this.blocks[i][j] = b[i][j];
            }
        }
        int count = 0;  //calculate goal
        for(int i=0; i<dim; i++) {
            for(int j=0; j<dim; j++) {
                if(blocks[i][j]==0) {
                    valueOfBlank = i*dim+j+1;
                    continue;
                }
                if(blocks[i][j]==i*dim+j+1) {
                    count++;
                    continue;
                }
                hammingScr++;
                manhattanScr += calculate(i, j);
            }
        }
        if(count==dim*dim-1)
            goal = true;
    }

    public int dimension() {
        return dim;
    }
    public int hamming() {
        return hammingScr;
    }
    public int manhattan() {
        return manhattanScr;
    }
    public boolean isGoal() {
        return goal;
    }
    public Board twin() {
        Board twin = new Board(this.blocks); //exchange (0,0) and (1,1)
        int i=0;int j=0;
        int k=0;int l=1;
        if(twin.blocks[i][j]==0) {
            i++;
            k++;
        }
        if(twin.blocks[k][l]==0) {
            k++;
            i++;
        }
        twin.swap(i, j, k, l);
        return twin;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Board) {
            Board that = (Board)obj;
            if(this.dim==that.dim) {
                for(int i=0; i<dim; i++) {
                    for(int j=0; j<dim; j++) {
                        if(this.blocks[i][j]!=that.blocks[i][j])
                            return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();

        int col = (valueOfBlank-1)%dim;
        int row = (valueOfBlank-col-1)/dim;

        if(isValid(row-1, col)) {
            swap(row, col, row-1, col);
            neighbors.add(new Board(this.blocks));
            swap(row-1, col, row, col);
        }
        if(isValid(row, col-1)) {
            swap(row, col, row, col-1);
            neighbors.add(new Board(this.blocks));
            swap(row, col-1, row, col);
        }
        if(isValid(row+1, col)) {
            swap(row, col, row+1, col);
            neighbors.add(new Board(this.blocks));
            swap(row+1, col, row, col);
        }
        if(isValid(row, col+1)) {
            swap(row, col, row, col+1);
            neighbors.add(new Board(this.blocks));
            swap(row, col+1, row, col);
        }
        return neighbors;
    }
    @Override
    public String toString() {
        String s = "";
        s += dim + "\n";
        for(int i=0; i<dim; i++) {
            for(int j=0; j<dim; j++) {
                s += " " + blocks[i][j];
            }
            s += "\n";
        }
        return s;
    }
    private void swap(int i1, int j1, int i2, int j2) {
        //re-calculate manhattan score and hamming score
       manhattanScr -= calculate(i1,j1);
       manhattanScr -= calculate(i2, j2);
       if(blocks[i1][j1]!=i1*dim+j1+1&&blocks[i1][j1]!=0)
           hammingScr--;
       if(blocks[i2][j2]!=i2*dim+j2+1&&blocks[i2][j2]!=0)
           hammingScr--;
        //swap values
        int temp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = temp;
        //re-calculate manhattan score and hamming score
        manhattanScr += calculate(i1, j1);
        manhattanScr += calculate(i2, j2);
        if(blocks[i1][j1]!=i1*dim+j1+1&&blocks[i1][j1]!=0)
            hammingScr++;
        if(blocks[i2][j2]!=i2*dim+j2+1&&blocks[i2][j2]!=0)
            hammingScr++;
        //
        if(blocks[i1][j1]==0) {
            valueOfBlank = i1*dim + j1 + 1;
        }
        if(blocks[i2][j2]==0) {
            valueOfBlank = i2*dim + j2 + 1;
        }
        if(hammingScr!=0)
            goal=false;
        else {
            goal=true;
        }
    }
    private boolean isValid(int i, int j) {
        return !(i<0||i>=dim||j<0||j>=dim);   //keep blocks in range
    }
    private int calculate(int i, int j) {
        if(blocks[i][j]==0)
            return 0;
        int col = (blocks[i][j]-1)%dim;
        int row = (blocks[i][j]-col-1)/dim;
        if(row>i) {
            if(col>j) {
                return (row-i) + (col-j);
            } else {
                return (row-i) + (j-col);
            }
        } else {
            if(col>j) {
                return (i-row) + (col-j);
            } else {
                return (i-row) + (j-col);
            }
        }
    }
    public static void main(String[] args) {
        int n = 3;
        int[][] blocks = new int[n][n];
        blocks[0][0] = 5;
        blocks[0][1] = 8;
        blocks[0][2] = 7;
        blocks[1][0] = 1;
        blocks[1][1] = 4;
        blocks[1][2] = 6;
        blocks[2][0] = 3;
        blocks[2][1] = 0;
        blocks[2][2] = 2;
        Board board = new Board(blocks);
        System.out.println("Origin:");
        System.out.println(board);
        System.out.println("Neighbors:");
        for(Board b : board.neighbors()) {
            System.out.println(b);
            System.out.print("Manhattan: " + b.manhattan());
            System.out.println(", Hamming: " + b.hamming());
        }
        System.out.println("Dimension: " + board.dimension());
        System.out.println("Goal? " + board.isGoal());
        System.out.print("Manhattan: " + board.manhattan());
        System.out.println(", Hamming: " + board.hamming());
        System.out.println("Twin: \n" + board.twin());
        System.out.println("Ori: \n" + board);
    }
}
