public class Car {

    String model;

    public Car(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Модель машины " + model;
    }
}