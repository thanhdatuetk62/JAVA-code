import java.util.Collections;

public class quicksort {
    public static void exchange(Comparable[] a, int i, int j) {
        Comparable s = a[i];
        a[i] = a[j];
        a[j] = s;
    }
    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        while(true) {
            while(a[++i].compareTo(a[lo])<0)
                if(i==hi) break;
            while(a[--j].compareTo(a[lo])>0)
                if(j==lo) break;
            if(i>=j) break;
            exchange(a,i,j);
        }
        exchange(a, lo, i);
        return i;
    }
    public static void sort(Comparable[] a ){
        

    }
    private static void sort(Comparable[] a, int lo, int hi) {
        if(hi<=lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
}