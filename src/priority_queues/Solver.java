package priority_queues;

import edu.princeton.cs.algs4.*;

import java.util.Comparator;

/**
 * Created by tomasz on 25.04.17.
 */
public final class Solver
{
    private final SearchNode goal;

    //private final Comparator<SearchNode> hammingOrder = new HammingOrder();
    //private final Comparator<SearchNode> manhattanOrder = new ManhattanOrder();

    private class SearchNode implements Comparable<SearchNode>
    {
        public SearchNode(Board board, int moves, SearchNode node)
        {
            this.board = board;
            this.moves = moves;
            this.previousNode = node;
        }
        private Board board;
        private int moves;
        private SearchNode previousNode;

        public int compareTo(SearchNode b)
        {
            if((this.board.manhattan() + this.moves) < (b.board.manhattan() + b.moves)) return -1;
            else if((this.board.manhattan() + this.moves) == (b.board.manhattan() + b.moves)) return 0;
            else return 1;
        }
    }
    /*
    private class ManhattanOrder implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode a, SearchNode b)
        {
            if((a.board.manhattan() + a.moves) < (b.board.manhattan() + b.moves)) return -1;
            else if((a.board.manhattan() + a.moves) == (b.board.manhattan() + b.moves)) return 0;
            else return 1;
        }
    }




    private class HammingOrder implements Comparator<SearchNode>
    {
        @Override
        public int compare(SearchNode a, SearchNode b)
        {
            if(a.board.hamming() < b.board.hamming()) return -1;
            else if(a.board.hamming() == b.board.hamming()) return 0;
            else return 1;
        }
    }
*/
    private SearchNode newTurn(MinPQ<SearchNode> queue)
    {
        SearchNode newNode = queue.delMin();
        Board newBoard = newNode.board;
        int moves = newNode.moves;
        Board previous;
        if(newNode.previousNode != null)  previous = newNode.previousNode.board;
        else previous = null;

        for (Board board : newBoard.neighbors())
        {
            if(!board.equals(previous)) queue.insert(new SearchNode(board, moves + 1, newNode));
        }
        return newNode;
    }

    public Solver(Board initial)
    {
        if(initial == null) throw new java.lang.NullPointerException();
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>();
        SearchNode newNode = new SearchNode(initial, 0, null);
        Board twin = initial.twin();
        SearchNode newNode1 = new SearchNode(twin, 0, null);
        queue.insert(newNode);
        twinQueue.insert(newNode1);

        while (!newNode.board.isGoal() && !newNode1.board.isGoal())
        {
            newNode = newTurn(queue);
            newNode1 = newTurn(twinQueue);
        }

        if(newNode.board.isGoal()) goal = newNode;
        else goal = null;
    }

    public boolean isSolvable() {return goal != null;}
    public int moves()
    {
       if(isSolvable()) return goal.moves;
       else return -1;
    }

    public Iterable<Board> solution()
    {
        if(isSolvable())
        {
            Queue<Board> solution = new Queue<Board>();
            Stack<Board> sequence = new Stack<Board>();
            SearchNode node = goal;
            while (node!= null)
            {
                sequence.push(node.board);
                node = node.previousNode;
            }
            Board board;
            while (!sequence.isEmpty())
            {
                board = sequence.pop();
                solution.enqueue(board);
            }
            return solution;

        }
        else return null;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
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
