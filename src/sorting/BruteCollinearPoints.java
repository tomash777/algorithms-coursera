package sorting;

import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


/**
 * Created by tomasz on 20.04.17.
 */
public class BruteCollinearPoints {

    private int M;
    private LineSegment[] segments;

    private void resize(int newCapacity) {
        LineSegment[] copy = new LineSegment[newCapacity];
        for (int i = 0; i < M; i++) copy[i] = segments[i];
        segments = copy;
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null || Arrays.asList(points).contains(null)) throw new java.lang.NullPointerException();
        int N = points.length;
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
        Point[] myPoints = new Point[N];
        for (int i = 0; i < N; i++)
        {
            myPoints[i] = points[i];
        }

        Arrays.sort(myPoints);
        M = 0;
        segments = new LineSegment[8];
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = j + 1; k < N; k++) {
                    Point x = myPoints[i];
                    Point y = myPoints[j];
                    Point z = myPoints[k];
                    if (x.slopeTo(y) == x.slopeTo(z))
                        for (int l = k + 1; l < N; l++) {
                            Point t = myPoints[l];
                            if (x.slopeTo(y) == x.slopeTo(t)) {
                                if (M == segments.length) resize(2 * segments.length);
                                segments[M++] = new LineSegment(x, t);
                            }
                        }
                }
        resize(M);

    }// finds all line segments containing 4 points

    public int numberOfSegments() {
        return M;
    }      // the number of line segments

    public LineSegment[] segments() {
        return segments;
    }

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

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}