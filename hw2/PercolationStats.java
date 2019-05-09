package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] answer;
    private int c;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if ((N <= 0) || (T <= 0)) {
            throw new IllegalArgumentException("invalid arguments");
        }

        answer = new double[T];
        c = T;

        for (int i = 0; i < T; i++) {
            Percolation test = pf.make(N);
            while (!test.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!test.isOpen(row, col)) {
                    test.open(row, col);
                }
                if (test.percolates()) {
                    break;
                }
            }
            answer[i] = (float) test.numberOfOpenSites() / (float) (N * N);
        }

    }

    // sample mean of percolation threshold
    public double mean() {

        return StdStats.mean(answer);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return StdStats.stddev(answer);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {

        return mean() - ((1.96 * stddev()) / Math.sqrt(c));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {

        return mean() + ((1.96 * stddev()) / Math.sqrt(c));
    }

}
