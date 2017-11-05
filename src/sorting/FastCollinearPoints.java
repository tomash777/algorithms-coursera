package sorting;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by tomasz on 20.04.17.
 */
public class FastCollinearPoints {

    private int M;
    private LineSegment [] segments;
    private void resize (int newCapacity)
    {
        LineSegment[] copy = new LineSegment[newCapacity];
        for (int i = 0; i < M; i++) copy[i] = segments[i];
        segments = copy;
    }

    public FastCollinearPoints(Point[] points)
    {
        if(points == null || Arrays.asList(points).contains(null)) throw new java.lang.NullPointerException();
        int N = points.length;
        for(int i = 0; i< N; i++)
            for(int j = i+1; j< N; j++)
                if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
        M = 0;
        segments = new LineSegment[8];
        Point[] myPoints = new Point[N];
        for (int i = 0; i < N; i++)
        {
            myPoints[i] = points[i];
        }
        for (int i = 0; i < N; i++)
        {
            Point x = points[i];
            Arrays.sort(myPoints, x.slopeOrder());
            int j = 1;
            while (j < N - 2)
            {
                if (x.slopeTo(myPoints[j]) == x.slopeTo(myPoints[j + 2])) {
                    if (M == segments.length) resize(2 * segments.length);
                    int k;
                    if (j == N - 3) k = N;
                    else {
                        k = j + 3;
                        while (x.slopeTo(myPoints[k]) == x.slopeTo(myPoints[j]) && k < N - 1) k++;
                        if (k == N - 1 && x.slopeTo(myPoints[k]) == x.slopeTo(myPoints[j])) k = N;
                    }
                    Arrays.sort(myPoints, j, k);
                    Point y = myPoints[j];
                    Point z = myPoints[k-1];
                    if(x.compareTo(y) < 0)  segments[M++] = new LineSegment(x,z);
                    j = k;
                }
                else j++;
            }
        }
        resize(M);

    }

    public int numberOfSegments() {return M;}
    public LineSegment[] segments() {return segments;}

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
