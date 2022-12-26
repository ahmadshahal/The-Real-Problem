package domain.models;


import java.util.ArrayList;

public class Station {
    private final String name;
    private final boolean isFinal;
    private final double taxiWaitingTime;
    private final double busWaitingTime;
    private final ArrayList<Road> roads = new ArrayList<>();

    public Station(String name, boolean isFinal, double taxiWaitingTime, double busWaitingTime) {
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

    public double getTaxiWaitingTime() {
        return taxiWaitingTime;
    }

    public double getBusWaitingTime() {
        return busWaitingTime;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }
}
