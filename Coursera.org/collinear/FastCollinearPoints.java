import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lines;
    private int count;
    public FastCollinearPoints(Point[] points) {
        //check EXCEPTION
        if(points==null) {
            throw new IllegalArgumentException();
        }
        for(int i =0; i<points.length; i++) {
            if(points[i]==null)
                throw new IllegalArgumentException();
            else {
                for(int j = i+1; j<points.length; j++) {
                    if(points[j]==null) {
                        throw new IllegalArgumentException();
                    } else {
                        if(points[i].compareTo(points[j])==0)
                            throw new IllegalArgumentException();
                    }
                }
            }
        }
        count = 0;
        lines = new ArrayList<LineSegment>();
        int N = points.length;
        Point[] copy = new Point[N];
        for(int i=0; i<N; i++) {
            copy[i] = points[i];    //copy points
        }
        for(int i=0; i<N; i++) {
            if(N<4) break;
            Arrays.sort(copy, points[i].slopeOrder());
            int start = 1;
            int end = 1;
            Point max = copy[start];
            for(int j=1; j<N; j++) {
                double present = points[i].slopeTo(copy[j]);
                if(points[i].compareTo(copy[j])>0) {
                    if(end-start>=2&&present!=points[i].slopeTo(copy[start])) {
                        count++;
                        lines.add(new LineSegment(points[i], max));
                    }
                    while(j<N&&points[i].slopeTo(copy[j])==present) {
                        j++;
                    }
                    start = j;
                    end = j;
                    if(j<N)
                        max = copy[start];
                    j--;
                    continue;
                }
                if(present==points[i].slopeTo(copy[start])) {
                    if(max.compareTo(copy[j])<0)
                        max = copy[j];
                    end=j;
                } else if(present!=points[i].slopeTo(copy[start])){
                    if(end-start>=2) {
                        lines.add(new LineSegment(points[i], max));
                        count++;
                    }
                    start = j;
                    end = j;
                    max = copy[start];
                }
                if(end-start>=2&&j==N-1) {
                    lines.add(new LineSegment(points[i], max));
                    count++;
                }

            }
        }
    }
    public int numberOfSegments() {
        return count;
    }
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[count];
        for(int i=0; i<count; i++) {
            lineSegments[i] = lines.get(i);
        }
        return lineSegments;
    }

}
