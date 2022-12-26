package domain.models;

public class Taxi {
    private final double speed;

    public Taxi(double speed) {
        this.speed = speed;
    }

    public double getTimeCost(int distance) {
        return distance / speed;
    }

    public int getEffortCost(int distance) {
        return -5 * distance;
    }

    public int getMoneyCost(int distance) {
        return 1000 * distance;
    }
}
