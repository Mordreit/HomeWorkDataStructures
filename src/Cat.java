public class Cat {

    String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Кота зовут " + name;
    }
}