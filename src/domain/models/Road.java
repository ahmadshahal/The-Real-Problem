package domain.models;

public class Road {
    private final int distance;
    private final Bus bus; // Defaults to null.
    private final Taxi taxi; // Defaults to null.
    private final Station destination;

    public Road(int distance, Bus bus, Taxi taxi, Station destination) {
        this.distance = distance;
        this.bus = bus;
        this.taxi = taxi;
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public Station getDestination() {
        return destination;
    }

    public Bus getBus() {
        return bus;
    }

    public Taxi getTaxi() {
        return taxi;
    }
}
