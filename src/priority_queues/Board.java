package priority_queues;


import java.util.Arrays;
import java.util.Stack;

/**
 * Created by tomasz on 24.04.17.
 */
public final class Board {

    private final int N;
    private final int[][]a;

    public Board(int[][] blocks)
    {
        N = blocks[0].length;
        a = new int[N][N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                a[i][j] = blocks[i][j];
    }
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {return N;}                 // board dimension n
    public int hamming()
    {
        int h = 0;
        for(int i = 0; i < N - 1; i++)
            for(int j = 0; j < N; j++)
                if(a[i][j] != i*N + j + 1) h++;
        for(int j = 0; j < N - 1; j++)
            if (a[N-1][j] != N*(N-1) + j + 1) h++;
        return h;
    }
    // number of blocks out of place
    public int manhattan()
    {
        int m = 0;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                if(a[i][j]!= 0)
                {
                    int k = (a[i][j] - 1 )/N;
                    int l =  a[i][j] - k*N - 1;
                    m += Math.abs(k-i) + Math.abs(l-j);
                }
        return m;
    }
    // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {return hamming() == 0;}
    // is this board the goal board?

    private void exch (int[][]a, int i1, int j1, int i2, int j2)
    {
        int tmp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = tmp;
    }
    public Board twin()
    {
        int[][]b = new int[N][N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                b[i][j] = a[i][j];
        if(b[0][0] == 0 || b[0][1] == 0) exch(b, 1,0, 1,1);
        else exch(b, 0, 0, 0, 1);
        return new Board(b);
    }
    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)
    {
        if(y == this ) return true;
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        for (int i = 0; i < N; i++)
            if(!Arrays.equals(a[i],that.a[i])) return false;
        return true;

    }
    public Iterable<Board> neighbors()
    {
        Stack<Board> neighbors = new Stack<Board>();
        int[][]b = new int[N][N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                b[i][j] = a[i][j];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                if(b[i][j] == 0)
                {
                    if (i > 0){
                        exch(b, i, j, i - 1, j);
                        neighbors.push(new Board(b));
                        exch(b, i, j, i - 1, j);
                    }

                    if (j > 0){
                        exch(b, i, j, i , j - 1);
                        neighbors.push(new Board(b));
                        exch(b, i, j, i , j - 1);
                    }

                    if (i < N - 1){
                        exch(b, i, j, i + 1, j);
                        neighbors.push(new Board(b));
                        exch(b, i, j, i + 1, j);
                    }

                    if (j < N - 1){
                        exch(b, i, j, i , j + 1);
                        neighbors.push(new Board(b));
                        exch(b, i, j, i , j + 1);
                    }

                }
        return neighbors;
    }
    // all neighboring boards



    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", a[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }              // string representation of this board (in the output format specified below)

    public static void main(String[] args){}// unit tests (not graded)
}
