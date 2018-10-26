import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats
{
	private double[] a;
	private int trials;
	private int n;
	private double mean;
	private double stddev;
	public PercolationStats(int n, int trials)
	{
		if(n<=0)
			throw new java.lang.IllegalArgumentException();
		if(trials<=0)
			throw new java.lang.IllegalArgumentException();
		this.n = n;
		this.trials = trials;
		a = new double[trials];
		int row;
		int col;
		for(int i = 0; i<trials; i++)
		{
			a[i] = calc(n);
		}
		this.mean = StdStats.mean(a);
		this.stddev = StdStats.stddev(a);
	}
	private double calc(int n)
	{
		Percolation perco = new Percolation(n);
		while(!perco.percolates())
		{
			perco.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
		}
		return (double)perco.numberOfOpenSites()/(n*n);
	}
	public double mean(){
		return mean;
	}
	public double stddev()
	{
		return stddev;
	}
	public double confidenceLo()
	{
		
		return mean - (1.96*stddev/Math.sqrt(trials));
	}
	public double confidenceHi()
	{
	
		return mean + (1.96*stddev/Math.sqrt(trials));
	}
	public static void main(String args[])
	{
		int n = StdIn.readInt();
		int T = StdIn.readInt();

	}
}