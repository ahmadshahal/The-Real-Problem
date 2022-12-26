package domain.models;


import java.util.ArrayList;

public class Station {
    private final String name;
    private final boolean isFinal;
    private final int taxiWaitingTime;
    private final int busWaitingTime;
    private final ArrayList<Road> roads = new ArrayList<>();

    public Station(String name, boolean isFinal, int taxiWaitingTime, int busWaitingTime) {
        this.name = name;
        this.isFinal = isFinal;
        this.taxiWaitingTime = taxiWaitingTime;
        this.busWaitingTime = busWaitingTime;
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

    public ArrayList<Road> getRoads() {
        return roads;
    }
}
