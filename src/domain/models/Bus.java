package domain.models;

public class Bus {
    private final String name;
    private final double speed;

    public Bus(String name, double speed) {
        this.name = name;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public double getTimeCost(int distance) {
        return distance / speed;
    }

    public int getEffortCost(int distance) {
        return -5 * distance;
    }

    public int getMoneyCost() {
        return 400;
    }
}
