package domain.models;


public class Station {
    private final int id; // Can be converted to name.
    private final boolean isFinal;
    private final int taxiWaitingTime;
    private final int busWaitingTime;
    private final Road[] roads;

    public Station(int id, boolean isFinal, int taxiWaitingTime, int busWaitingTime, Road[] roads) {
        this.id = id;
        this.isFinal = isFinal;
        this.taxiWaitingTime = taxiWaitingTime;
        this.busWaitingTime = busWaitingTime;
        this.roads = roads;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public int getTaxiWaitingTime() {
        return taxiWaitingTime;
    }

    public int getBusWaitingTime() {
        return busWaitingTime;
    }

    public Road[] getRoads() {
        return roads;
    }
}
