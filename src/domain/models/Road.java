package domain.models;

import java.util.ArrayList;

public class Road {
    private final int distance;
    private final ArrayList<Bus> buses; // Defaults to null.
    private final Taxi taxi; // Defaults to null.
    private final Station destination;

    public Road(int distance, ArrayList<Bus> buses, Taxi taxi, Station destination) {
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

    public ArrayList<Bus> getBuses() {
        return buses;
    }

    public Taxi getTaxi() {
        return taxi;
    }
}
