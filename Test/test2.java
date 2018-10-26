import java.util.ArrayList;

public class test2 {
    public static void main(String[] args) {
        ArrayList al1 = new ArrayList();
        al1.add("1");
        al1.add("2");
        ArrayList al2 = new ArrayList(al1);
        System.out.println(al2.get(0));
    }
}