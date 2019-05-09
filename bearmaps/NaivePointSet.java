package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> myPoints;

    public NaivePointSet(List<Point> points) {
        myPoints = new ArrayList<>();
        for (Point p : points) {
            myPoints.add(p);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point best = myPoints.get(0);
        Point goal = new Point(x, y);

        for (Point p : myPoints) {
            double currentDistance = Point.distance(p, goal);
            if (currentDistance < Point.distance(best, goal)) {
                best = p;
            }
        }

        return best;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0);
        System.out.println(ret.getX());
        System.out.println(ret.getY());
    }

}
