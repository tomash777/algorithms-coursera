package trees;

import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Stack;

/**
 * Created by tomasz on 02.05.17.
 */
public class PointSET {

    private TreeSet<Point2D> points;
    private int N;

    public PointSET()
    {
        points = new TreeSet<Point2D>();
        N= 0;
    }
    public boolean isEmpty() { return N == 0;}
    public int size() {return N;}
    public void insert(Point2D p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        if (!contains(p)) N++;
        boolean a = points.add(p);
    }
    public boolean contains(Point2D p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        return points.contains(p);
    }

    public void draw()
    {
        for(Point2D q : points) q.draw();
    }
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) throw new java.lang.NullPointerException();
        Stack<Point2D> pointsIn = new Stack<Point2D>();
        for (Point2D p : points) if(rect.contains(p)) pointsIn.push(p);
        return pointsIn;
    }
    public Point2D nearest(Point2D p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        if(isEmpty()) return null;
        else {
            Point2D min = points.first();
            for(Point2D q : points)
                if (q.distanceSquaredTo(p) < min.distanceSquaredTo(p)) min = q;
            return min;
        }

    }
    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {}                // unit testing of the methods (optional)
}
