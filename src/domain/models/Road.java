package domain.models;

public class Road {
    private final int distance;
    private final Bus[] buses; // Defaults to null.
    private final Taxi taxi; // Defaults to null.
    private final Station destination;

    public Road(int distance, Bus[] buses, Taxi taxi, Station destination) {
        this.distance = distance;
        this.buses = buses;
        this.taxi = taxi;
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public Station getDestination() {
        return destination;
    }

    public Bus[] getBuses() {
        return buses;
    }

    public Taxi getTaxi() {
        return taxi;
    }
}
