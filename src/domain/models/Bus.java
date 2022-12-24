package domain.models;

public class Bus {
    private final String name;
    private final int speed;

    public Bus(String name, int speed) {
        this.name = name;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getEffortCost(int distance) {
        return -5 * distance;
    }

    public int getMoneyCost() {
        // TODO: Discuss.
        return 400;
    }
}
