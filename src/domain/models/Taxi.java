package domain.models;

public class Taxi {
    private final int speed;

    public Taxi(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getEffortCost(int distance) {
        return 5 * distance;
    }

    public int getMoneyCost(int distance) {
        return 1000 * distance;
    }
}
