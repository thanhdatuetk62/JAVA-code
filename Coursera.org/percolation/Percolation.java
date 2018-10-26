import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation
{
	private boolean[][] table;
	private int n;
	private WeightedQuickUnionUF UF;
	private WeightedQuickUnionUF UF_test;
	private int count;
	public Percolation(int n)
	{
		if(n<=0)
			throw new java.lang.IllegalArgumentException();
		this.n = n;
		count = 0;
		table = new boolean[n+2][n+2];
		UF = new WeightedQuickUnionUF(n*n+2);
		UF_test = new WeightedQuickUnionUF(n*n+2);
		for(int i=0;i<n+2;i++)
		{	
			for(int j=0; j<n+2; j++)
			{
				table[i][j] = false;
			}
		}
	}
	private int convert(int row, int col)
	{
		return (row-1)*n + col;
	}
	public void open(int row, int col)
	{
		if(row<1||row>n||col<1||col>n)
			throw new java.lang.IllegalArgumentException();
		if(table[row][col])
			return;
		table[row][col] = true;
		if(row == 1)
		{
			UF.union(convert(row, col), 0);
			UF_test.union(convert(row, col), 0);
		}
		if(row == n)
			UF.union(convert(row, col), n*n+1);

		if(table[row-1][col])
		{
			UF.union(convert(row, col), convert(row-1, col));
			UF_test.union(convert(row, col), convert(row-1, col));
		}
		if(table[row][col-1])
		{
			UF.union(convert(row, col), convert(row, col-1));
			UF_test.union(convert(row, col), convert(row, col-1));
		}
		if(table[row+1][col])
		{
			UF_test.union(convert(row, col), convert(row+1, col));
			UF.union(convert(row, col), convert(row+1, col));
		}
		if(table[row][col+1])
		{
			UF_test.union(convert(row, col), convert(row, col+1));
			UF.union(convert(row, col), convert(row, col+1));
		}
		count++;
	}
	public boolean isOpen(int row, int col)
	{
		if(row<1||row>n||col<1||col>n)
			throw new java.lang.IllegalArgumentException();
		return table[row][col];
	}
	public boolean isFull(int row, int col)
	{
		if(row<1||row>n||col<1||col>n)
			throw new java.lang.IllegalArgumentException();

		return UF_test.connected(convert(row, col), 0);
	}
	public int numberOfOpenSites()
	{
		return count;
	}
	public boolean percolates()
	{
		return(UF.connected(0, n*n+1));
	}
	public static void main(String[] args)
	{
		
	}
}
