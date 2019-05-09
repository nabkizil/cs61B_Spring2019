package bearmaps;
import java.util.List;

public class KDTree implements PointSet {

    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;
    Node currBest;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node left;
        private Node right;

        private Node(Point givenP, boolean orientation) {
            this.p = givenP;
            this.orientation = orientation;
        }
    }


    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }


    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p == n.p) {
            return n;
        }
        double comparison = comparePoints(p, n, orientation);
        if (comparison < 0) {
            n.left = add(p, n.left, !orientation);
        } else if (comparison >= 0) {
            n.right = add(p, n.right, !orientation);
        }

        return n;
    }


    private double comparePoints(Point p, Node n, boolean orientation) {
        if (orientation) {
            return p.getX() - n.p.getX();
        } else {
            return p.getY() - n.p.getY();
        }
    }



    private double dist(double a, double b) {
        return Math.abs(a - b);
    }


    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        return nearest(root, p, root.p, root.orientation);
    }

    private Point nearest(Node n, Point p, Point champ,
                          boolean orientation) {

        if (n == null) {
            return champ;
        }

        if (n.p.equals(p)) {
            return p;
        }

        if (Math.sqrt(Point.distance(n.p, champ)) < Math.sqrt(Point.distance(p, champ))) {
            champ = n.p;
        }

        double toPartitionLine = comparePoints(p, n, orientation);

        if (toPartitionLine < 0) {
            champ = nearest(n.left, p, champ, !orientation);

            if (Math.sqrt(Point.distance(p, champ)) >= toPartitionLine * toPartitionLine) {
                champ = nearest(n.right, p, champ, !orientation);
            }
        } else {
            champ = nearest(n.right, p, champ, !orientation);

            if (Math.sqrt(Point.distance(p, champ)) >= toPartitionLine * toPartitionLine) {
                champ = nearest(n.left, p, champ, !orientation);
            }
        }
        return champ;
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }
}
