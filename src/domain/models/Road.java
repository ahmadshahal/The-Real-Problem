package domain.models;

public class Road {
    private final int distance;
    private final Bus[] buses;
    private final Taxi[] taxis;

    public Road(int distance, Bus[] buses, Taxi[] taxis) {
        this.distance = distance;
        this.buses = buses;
        this.taxis = taxis;
    }

    public int getDistance() {
        return distance;
    }

    public Bus[] getBuses() {
        return buses;
    }

    public Taxi[] getTaxis() {
        return taxis;
    }
}
