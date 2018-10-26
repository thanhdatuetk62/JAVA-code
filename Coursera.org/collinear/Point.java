import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;
public class Point implements Comparable<Point> {
    private final int x;
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void draw() {
        StdDraw.point(x,y);
    }
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    public double slopeTo(Point that) {
        if(this.y==that.y) {
            if(this.x!=that.x)
                return +0.0;
            else
                return Double.NEGATIVE_INFINITY;
        }
        else {
            if(this.x==that.x)
                return Double.POSITIVE_INFINITY;
            else
                return (double)(that.y-this.y)/(that.x-this.x);
        }
    }
    public int compareTo(Point that) {
        if(that.y!=this.y)
            return this.y-that.y;
        else {
            return this.x-that.x;
        }
    }
    private class bySlope implements Comparator<Point> {
        public int compare(Point u, Point v) {
            if(slopeTo(u)>slopeTo(v))
                return 1;
            else if(slopeTo(u)==slopeTo(v))
                return 0;
            else
                return -1;
        }
    }
    public Comparator<Point> slopeOrder() {
        return new bySlope();
    }
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    public static void main(String[] args) { //checked
        Point[] points = new Point[2];
        points[0] = new Point(10000,0);
        points[1] = new Point(0,10000);

        System.out.println("Before");
        Point check = new Point(0,0);
        for(int i= 0; i<2; i++) {
            System.out.println(points[0].slopeTo(points[i]));
        }
        Arrays.sort(points, points[0].slopeOrder());
        System.out.println("After");
        for(int i= 0; i<6; i++) {
            System.out.println(points[i].toString());
        }
    }
}
