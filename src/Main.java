import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        LinkedList<Car> cars = new LinkedList<>();
        Car lambo = new Car("Lamborghini Aventador");
        Car ford = new Car("Ford Mustang");
        Car reno = new Car("Reno Arkana");
        Car kia = new Car("KIA Stinger");
        Car mazda = new Car("Mazda rx7");
        Car toyota = new Car("Toyota Supra");
        Car lexus = new Car("Lexus rc");

        cars.add(lambo);
        cars.add(ford);
        cars.add(reno);
        cars.add(kia);
        cars.add(mazda);
        cars.add(toyota);
        cars.add(lexus);

        cars.remove(2);

        System.out.println(cars);


        ArrayList<Cat> cats = new ArrayList<>();
        Cat pushok = new Cat("Пушок");
        Cat pirat = new Cat("Пират");
        Cat fedor = new Cat("Федор");
        Cat lucifer = new Cat("Люцифер");

        cats.add(pushok);
        cats.add(pirat);
        cats.add(fedor);
        cats.add(lucifer);

        cats.remove(2);

        System.out.println(cats);
        }
    }