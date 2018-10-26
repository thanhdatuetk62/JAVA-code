import java.util.*;

import sun.misc.Queue;
public class test {
    public static void main(String[] args) {
        Deque<String> al = new Deque<String>();
        al.push("a");
        al.push("b");
        al.push("c");
        al.push("Nguyen thanh dat");
        al.push("Hello my world");
        for(String s : al) {
            System.out.println(s);
        }
    }
}