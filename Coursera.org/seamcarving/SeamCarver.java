import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.AcyclicSP;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int height;
    private int width;
    public SeamCarver(Picture picture) {
        if(picture == null)
            throw new IllegalArgumentException();
        this.picture = new Picture(picture);
        this.height = picture.height();
        this.width = picture.width();
    }
    public Picture picture() {
        return new Picture(picture);
    }
    public int width() {
        return width;
    }
    public int height() {
        return height;
    }
    public double energy(int x, int y) {
        validException(x, y);
        if(checkCorner(x, y))
            return 1000.0;
        Color left = picture.get(x-1, y);
        Color right = picture.get(x+1, y);
        Color up = picture.get(x, y-1);
        Color down = picture.get(x, y+1);
        return Math.sqrt(delta(left, right) + delta(up, down));
    }
    public int[] findHorizontalSeam() {
        int[] path = new int[width];
        EdgeWeightedDigraph graph = construct(horizontal(), height, width);
        AcyclicSP SP = new AcyclicSP(graph, width*height);
        int id = 0;
        for(DirectedEdge e : SP.pathTo(width*height+1)) {
            if(id==width)
                continue;
            path[id++] = e.to()%height;
        }
        return path;
    }
    public int[] findVerticalSeam() {
        int[] path = new int[height];
        EdgeWeightedDigraph graph = construct(vertical(), width, height);
        AcyclicSP SP = new AcyclicSP(graph, width*height);
        int id = 0;
        for(DirectedEdge e : SP.pathTo(width*height+1)) {
            if(id==height)
                continue;
            path[id++] = e.to()%width;
        }
        return path;
    }
    public void removeHorizontalSeam(int[] seam) {
        seamHorizontalValid(seam);
        Picture p = new Picture(width, height-1);
        int h = 0;
        int w = 0;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height-1; j++) {
                if(h==seam[w]) h++;
                p.set(i, j, picture.get(w, h));
                h++;
            }
            w++; h=0;
        }
        picture = new Picture(p);
        height--;
    }
    public void removeVerticalSeam(int[] seam) {
        seamVerticalValid(seam);
        Picture p = new Picture(width-1, height);
        int h = 0;
        int w = 0;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width-1; j++) {
                if(w==seam[h]) w++;
                p.set(j, i, picture.get(w, h));
                w++;
            }
            h++; w=0;
        }
        picture = new Picture(p);
        width--;
    }
    private boolean checkCorner(int x, int y) {
        if(x==0||x==width-1||y==0||y==height-1)
            return true;
        return false;
    }
    private int delta(Color c1, Color c2) {
        int Rx = c1.getRed() - c2.getRed();
        int Gx = c1.getGreen() - c2.getGreen();
        int Bx = c1.getBlue() - c2.getBlue();
        return Rx*Rx + Gx*Gx + Bx*Bx;
    }
    private int shift(int x, int y, int w) {
        return w*y + x;
    }
    private EdgeWeightedDigraph construct(double[][] weights, int width, int height) {
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(width*height + 2);
        for(int i = 0; i < width; i++) {
            graph.addEdge(new DirectedEdge(width*height, i, weights[i][0]));                       //TOP
            graph.addEdge(new DirectedEdge(shift(i, height-1, width),width*height + 1, 0)); //DOWN
        }
        for(int h = 0; h < height ; h++) {
            for(int w = 0; w < width; w++) {
                if(isValid(w, h+1, width, height))
                    graph.addEdge(new DirectedEdge(shift(w, h, width), shift(w, h+1, width), weights[w][h+1]));
                if(isValid(w-1, h+1, width, height))
                    graph.addEdge(new DirectedEdge(shift(w, h, width), shift(w-1, h+1, width), weights[w-1][h+1]));
                if(isValid(w+1, h+1, width, height))
                    graph.addEdge(new DirectedEdge(shift(w, h, width), shift(w+1, h+1, width), weights[w+1][h+1]));
            }
        }
        return graph;
    }
    private double[][] vertical() {
        double[][] store = new double[width][height];
        for(int h = 0; h < height; h++){
            for(int w = 0; w < width; w++) {
                store[w][h] = energy(w, h);
            }
        }
        return store;
    }
    private double[][] horizontal() {
        double[][] store = new double[height][width];
        for(int h = 0; h < height; h++){
            for(int w = 0; w < width; w++) {
                store[h][w] = energy(w, h);
            }
        }
        return store;
    }
    private boolean isValid(int x, int y, int width, int height) {
        if(x<0||x>=width||y<0||y>=height)
            return false;
        return true;
    }
    private void validException(int x, int y) {
        if(x<0||x>=width||y<0||y>=height)
            throw new IllegalArgumentException();
    }
    private void seamVerticalValid(int[] seam) {
        if(seam==null)
            throw new IllegalArgumentException();
        if(seam.length!=height)
            throw new IllegalArgumentException();
        for(int i = 0; i < seam.length; i++) {
            if(seam[i]<0||seam[i]>=width)
                throw new IllegalArgumentException();
            if(i>=1&&(Math.abs(seam[i]-seam[i-1])>1))
                throw new IllegalArgumentException();
        }
        if(width<=1)
            throw new IllegalArgumentException();
    }
    private void seamHorizontalValid(int[] seam) {
        if(seam==null)
            throw new IllegalArgumentException();
        if(seam.length!=width)
            throw new IllegalArgumentException();
        for(int i = 0; i < seam.length; i++) {
            if(seam[i]<0||seam[i]>=height)
                throw new IllegalArgumentException();
            if(i>=1&&(Math.abs(seam[i]-seam[i-1])>1))
                throw new IllegalArgumentException();
        }
        if(height<=1)
            throw new IllegalArgumentException();
    }
}
