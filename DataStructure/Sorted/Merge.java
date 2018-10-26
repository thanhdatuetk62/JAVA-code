public class Merge {
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for(int k = lo; k<=hi; k++) {
            if (i>mid)
                aux[k] = a[j++];
            else if (j>hi) 
                aux[k] = a[i++];
            else if (a[j].compareTo(a[i])<0)
                aux[k] = a[j++];
            else
                aux[k] = a[i++];
        }
    }
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if(hi<=lo)
            return;
        int mid = (lo + hi)/2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid+1, hi);
        merge(a, aux, lo, mid, hi); 
    }
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        for(int i=0;i<a.length;i++) {
            aux[i] = a[i];
        }
        sort(aux, a, 0, a.length-1);
    }
    public static void main(String[] args) {
        Integer[] a = new Integer[14];
        a[0] = 1;
        a[1] = 5;
        a[2] = 9;
        a[3] = 7;
        a[4] = 11;
        a[5] = 91;
        a[6] = 45;
        a[7] = 33;
        a[8] = 66;
        a[9] = 124;
        a[10] = 91;
        a[11] = 5;
        a[12] = 4;
        a[13] = -11;
        Merge.sort(a);
        for(Integer i:a) {
            System.out.println(i);
        }
    }
}