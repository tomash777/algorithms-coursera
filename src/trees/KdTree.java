package trees;

import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;
/**
 * Created by tomasz on 08.05.17.
 */
public class KdTree {

    private int N;
    private Node root;
    private static class Node
    {
        private Point2D point;
        private Node leftbottom, righttop;
        public Node(Point2D point)
        {
            this.point = point;
            this.leftbottom = null;
            this.righttop = null;
        }
    }

    public KdTree()
    {
        root = null;
        N = 0;
    }
    public boolean isEmpty() { return N == 0;}
    public int size() {return N;}
    public void insert(Point2D p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        root = putX(root, p);
    }

    private Node putX (Node node, Point2D p)
    {
        if (node == null) { N++; return new Node(p);}
        if (p.x() < node.point.x()) node.leftbottom  = putY(node.leftbottom,  p);
        else if (p.x() >= node.point.x() && !p.equals(node.point)) node.righttop = putY(node.righttop, p);
        return node;
    }

    private Node putY (Node node, Point2D p)
    {
        if (node == null) {N++; return new Node(p);}
        if (p.y() < node.point.y()) node.leftbottom  = putX(node.leftbottom,  p);
        else if (p.y() >= node.point.y() && !p.equals(node.point)) node.righttop = putX(node.righttop, p);
        return node;
    }

    public boolean contains(Point2D p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        return containsX(p, root);
    }

    private boolean containsX (Point2D p, Node x)
    {
        if (x == null) return false;
        if (x.point.equals(p)) return true;
        if (p.x() < x.point.x()) return containsY(p, x.leftbottom);
        else return containsY(p, x.righttop);
    }

    private boolean containsY (Point2D p, Node x)
    {
        if (x == null) return false;
        if (x.point.equals(p)) return true;
        if (p.y() < x.point.y()) return containsX(p, x.leftbottom);
        else return containsX(p, x.righttop);
    }


    public void draw()
    {
        drawX(root, 0.0, 1.0, 0.0, 1.0);
    }

    private void drawX(Node node, double left, double right, double down, double up)
    {
        if (node != null) {
            Point2D p = node.point;
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(p.x(), p.y());
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(p.x(), up, p.x(), down);
            drawY(node.leftbottom, left, p.x(), down, up);
            drawY(node.righttop, p.x(), right, down, up);
        }
    }

    private void drawY(Node node, double left, double right, double down, double up)
    {
        if (node != null) {
            Point2D p = node.point;
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(p.x(), p.y());
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(left, p.y(), right, p.y());
            drawX(node.leftbottom, left, right, down, p.y());
            drawX(node.righttop, left, right, p.y(), up);
        }
    }

    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) throw new java.lang.NullPointerException();
        Stack<Point2D> pointsIn = new Stack<Point2D>();
        rangeX(rect, pointsIn, root);
        return pointsIn;
    }

    private void rangeX(RectHV rect, Stack<Point2D> points, Node node)
    {
        if (node != null)
        {
            if (rect.contains(node.point)) points.push(node.point);
            if (rect.xmax() < node.point.x()) rangeY(rect, points, node.leftbottom);
            else if (rect.xmin() >= node.point.x()) rangeY(rect, points, node.righttop);
            else {
                rangeY(rect, points, node.leftbottom);
                rangeY(rect, points, node.righttop);
            }
        }
    }

    private void rangeY(RectHV rect, Stack<Point2D> points, Node node)
    {
        if (node != null)
        {
            if (rect.contains(node.point)) points.push(node.point);
            if (rect.ymax() < node.point.y()) rangeX(rect, points, node.leftbottom);
            else if (rect.ymin() >= node.point.y()) rangeX(rect, points, node.righttop);
            else {
                rangeX(rect, points, node.leftbottom);
                rangeX(rect, points, node.righttop);
            }
        }
    }

    public Point2D nearest(Point2D p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        if(isEmpty()) return null;
        else return nearestX(p, root.point, root, 0.0, 0.0, 1.0,1.0);
    }

    private Point2D nearestX (Point2D p, Point2D nearest, Node node, double left, double down, double right, double up)
    {
        if (node == null) return nearest;

        Point2D actualNearest = nearest;
        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(nearest)) actualNearest = node.point;

        RectHV rectLeft = new RectHV(left, down, node.point.x(), up);
        if (rectLeft.distanceSquaredTo(p) >= actualNearest.distanceSquaredTo(p))
            return nearestY(p, actualNearest, node.righttop, node.point.x(), down, right, up);

        RectHV rectRight = new RectHV(node.point.x(), down, right, up);
        if (rectRight.distanceSquaredTo(p) >= actualNearest.distanceSquaredTo(p))
            return nearestY(p, actualNearest, node.leftbottom, left, down, node.point.x(), up);

        if (p.x() < node.point.x()) {
            actualNearest = nearestY(p, actualNearest, node.leftbottom, left, down, node.point.x(), up);
            if (rectRight.distanceSquaredTo(p) < actualNearest.distanceSquaredTo(p))
                actualNearest = nearestY(p, actualNearest, node.righttop, node.point.x(), down, right, up);
            return actualNearest;
        }

        else {
            actualNearest = nearestY(p, actualNearest, node.righttop, node.point.x(), down, right, up);
            if (rectLeft.distanceSquaredTo(p) < actualNearest.distanceSquaredTo(p))
                actualNearest = nearestY(p, actualNearest, node.leftbottom, left, down, node.point.x(), up);
            return actualNearest;
        }
    }

    private Point2D nearestY (Point2D p, Point2D nearest, Node node, double left, double down, double right, double up)
    {
        if (node == null) return nearest;

        Point2D actualNearest = nearest;
        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(nearest)) actualNearest = node.point;

        RectHV rectDown = new RectHV(left, down, right, node.point.y());
        if (rectDown.distanceSquaredTo(p) >= nearest.distanceSquaredTo(p))
            return nearestX(p, actualNearest, node.righttop, left, node.point.y(), right, up);

        RectHV rectUp = new RectHV(left, node.point.y(), right, up);
        if (rectUp.distanceSquaredTo(p) >= nearest.distanceSquaredTo(p))
            return nearestX(p, actualNearest, node.leftbottom, left, down, right, node.point.y());

        if (p.y() < node.point.y()) {
            actualNearest = nearestX(p, actualNearest, node.leftbottom, left, down, right, node.point.y());
            if (rectUp.distanceSquaredTo(p) < actualNearest.distanceSquaredTo(p))
                actualNearest = nearestX(p, actualNearest, node.righttop, left, node.point.y(), right, up);
            return actualNearest;
        }

        else {
            actualNearest = nearestX(p, actualNearest, node.righttop, left, node.point.y(), right, up);
            if (rectDown.distanceSquaredTo(p) < actualNearest.distanceSquaredTo(p))
                actualNearest = nearestX(p, actualNearest, node.leftbottom, left, down, right, node.point.y());
            return actualNearest;
        }
    }




    public static void main(String[] args){}

}
