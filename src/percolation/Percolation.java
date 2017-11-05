package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[] open;
    private WeightedQuickUnionUF board;

    private int xyto1D(int row, int col)
    {
        return (row - 1) * size + col;
    }

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("size out of bound");
        size = n;
        open = new boolean[n*n+2];

        for(int i=1;i<=n*n;i++)
        {
            open[i]=false;
        }
        open[0]=true;
        open[n*n+1]=true;

        board = new WeightedQuickUnionUF(n*n+2);

    }

    private void valid (int i)
    {
        if (i <= 0 || i > size) throw new IndexOutOfBoundsException("row index i out of bounds");
    }


    public void open(int row, int col)
    {
        valid(col);
        valid(row);
        open[xyto1D(row, col)] = true;
        int field = xyto1D(row, col);
        int left = xyto1D(row, col - 1);
        int right = xyto1D(row, col + 1);
        int up = xyto1D(row - 1, col);
        int down = xyto1D(row + 1, col);
        if (row == 1) board.union(0, field);
        if (row == size) board.union(field, size*size+1);
        if (row > 1 && open[up]) board.union(field, up);
        if (row < size && open[down]) board.union(field, down);
        if (col > 1 && open[left]) board.union(field, left);
        if (col < size && open[right]) board.union(field, right);
    }

    public boolean isOpen(int row, int col)

    {
        valid(row);
        valid(col);
        return open[xyto1D(row, col)];
    }

    public boolean isFull(int row, int col)
    {
      valid(row);
      valid(col);
      return board.connected(0, xyto1D(row, col));
    }

    public int numberOfOpenSites()
    {
        int n = 0;
        for (int i = 1; i <= size*size; i++)
        {
            if (open[i]) n++;
        }
        return n;
    }

    public boolean percolates() { return board.connected(0, size*size+1);}

    public static void main(String[] args){}  // test client (optional)

}
