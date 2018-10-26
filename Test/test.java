import java.util.ArrayList;

class Animal {
    private String color;
    public void setColor(String c) {
        color = c;
    }
    public String getColor() {
        return color;
    }
}
class Cat extends Animal {
    public Cat() {
        setColor(c.getColor());
    }
    public String getType() {
        return "Lovely Cat";
    }
}
class CatAsian extends Animal {
    public CatAsian(Animal c) {
        setColor(c.getColor());
    }
    Cat c = new Animal(super.getColor());


}
public class test {
    
    public static void main(String[] args) {
       Animal a = new Animal();
       a.setColor("Blue");
       Cat cat = new CatAsian(a);
       cat.c.getType();
    }
} 