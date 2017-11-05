package iterables;

import edu.princeton.cs.algs4.*;

/**
 * Created by tomasz on 14.04.17.
 */
public class Permutation {
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) queue.enqueue(StdIn.readString());
        while (k > 0) { StdOut.println(queue.dequeue()); k--; }
    }
}
