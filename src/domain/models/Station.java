package domain.models;


public class Station {
    private final String name;
    private final boolean isFinal;
    private final int taxiWaitingTime;
    private final int busWaitingTime;
    private final Road[] roads;

    public Station(String name, boolean isFinal, int taxiWaitingTime, int busWaitingTime, Road[] roads) {
        this.name = name;
        this.isFinal = isFinal;
        this.taxiWaitingTime = taxiWaitingTime;
        this.busWaitingTime = busWaitingTime;
        this.roads = roads;
    }

    public String getStationName() {
        return name;
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
