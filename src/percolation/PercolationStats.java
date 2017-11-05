package percolation;

import edu.princeton.cs.algs4.*;

public class PercolationStats {

    private double[] fractions;
    private int trialsNumber;

    private double[] makeFractions(int n, int trials)
    {
        double [] fractions = new double[trials];
        Percolation perc;
        double a = n*n;

        for (int i = 0; i < trials; i++)
        {
            perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = 0;
                int col = 0;
                do {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                } while (perc.isOpen(row, col));
                perc.open(row, col);
            }
            fractions[i] = perc.numberOfOpenSites()/a;
        }
        return fractions;
    }

    public PercolationStats(int n, int trials)
    {
        trialsNumber = trials;
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("size out of bound");
        fractions = makeFractions(n, trials);
    }
    // perform trials independent experiments on an n-by-n grid

    public double mean() { return StdStats.mean(fractions);}

    public double stddev()
    {
        if (trialsNumber == 1 ) return Double.NaN;
        else return StdStats.stddev(fractions);

    }

    public double confidenceLo()
    {
        return mean() - 1.96*stddev()/Math.sqrt(trialsNumber);
    }
    public double confidenceHi()
    {
        return mean() + 1.96*stddev()/Math.sqrt(trialsNumber);
    }

    public static void main(String[] args)
    {
        int n = StdIn.readInt();
        int T = StdIn.readInt();

        PercolationStats percStats = new PercolationStats(n,T);
        StdOut.println("mean = " + percStats.mean());
        StdOut.println("stddev = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
    }
}















