package domain.models;

public class Road {
    private final int distance;
    private final Bus[] buses;
    private final Taxi[] taxis;
    private final Station destination;
    private final Station source;

    public Road(int distance, Bus[] buses, Taxi[] taxis, Station destination, Station source) {
        this.distance = distance;
        this.buses = buses;
        this.taxis = taxis;
        this.destination = destination;
        this.source = source;
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

    public Station getDestination() {
        return destination;
    }

    public Station getSource() {
        return source;
    }
}
