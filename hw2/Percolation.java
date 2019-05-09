package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] status; // status if block is open
    private int[][] grid;
    private int head;
    private int tail;
    private int numberOfOpenSites;
    private int size;
    private WeightedQuickUnionUF WQU;
    private WeightedQuickUnionUF backWash;

    public Percolation(int N) { // create N-by-N status, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("status dimensions must be positive");
        }
        size = N;
        head = N * N;
        tail = N * N + 1;

        grid = new int[N][N];

        status = new boolean[N][N];
        for (int i = 0; i < N; i++) {       // initially all blocked
            for (int j = 0; j < N; j++) {
                status[i][j] = false;
            }
        }
        numberOfOpenSites = 0;
        WQU = new WeightedQuickUnionUF(N * N + 2);      // WeightedQuickUnionUF data structures
        backWash = new WeightedQuickUnionUF(N * N + 1);
    }

    private int xyTo1D(int row, int col) {
        return row * size + col;
    }

    public void open(int row, int col) {      // open the site (row, col) if it is not open already
        if (row < 0 || row >= size || col < 0 || col >= size)  {
            throw new java.lang.IndexOutOfBoundsException(
                    "status position is outside its prescribed range, cannot open");
        }

        if (!isOpen(row, col)) {
            status[row][col] = true;
            numberOfOpenSites += 1;

            int p = xyTo1D(row, col); // current opened cell index

            //connect head to open cell
            if (row == 0) {
                WQU.union(head, p);
                backWash.union(p, head);
            }

            //connect tail to open cell
            if (row == size - 1) {
                WQU.union(p, tail);
            }

            // Connect open neighbors
            // right
            if (col < size - 1 && isOpen(row, col + 1)) {
                WQU.union(p, xyTo1D(row, col + 1));
                backWash.union(p, xyTo1D(row, col + 1));
            }
            // left
            if (col > 0 && isOpen(row, col - 1)) {
                WQU.union(p, xyTo1D(row, col - 1));
                backWash.union(p, xyTo1D(row, col - 1));
            }
            // up
            if (row > 0 && isOpen(row - 1, col)) {
                WQU.union(p, xyTo1D(row - 1, col));
                backWash.union(p, xyTo1D(row - 1, col));
            }
            // down
            if (row < size - 1 && isOpen(row + 1, col)) {
                WQU.union(p, xyTo1D(row + 1, col));
                backWash.union(p, xyTo1D(row + 1, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {  // is the site (row, col) open?
        if (row < 0 || row >= size || col < 0 || col >= size)  {
            throw new java.lang.IndexOutOfBoundsException(
                    "status position is outside range");
        }
        return status[row][col];
    }

    public boolean isFull(int row, int col) {  // is the site (row, col) full?
        if (row < 0 || row >= size || col < 0 || col >= size)  {
            throw new java.lang.IndexOutOfBoundsException(
                    "status position is outside range");
        }

        int p = xyTo1D(row, col);
        return (isOpen(row, col) && backWash.connected(head, p));
    }

    public int numberOfOpenSites() {          // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {             // does the system percolate?
        return WQU.connected(head, tail);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) { }
}
