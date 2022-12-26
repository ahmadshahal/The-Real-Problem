package domain.models;


import java.util.ArrayList;
import java.util.Objects;

public class Station {
    private final String name;
    private final boolean isFinal;
    private final double taxiWaitingTime;
    private final double busWaitingTime;
    private final ArrayList<Road> outRoads = new ArrayList<>();
    private final ArrayList<Road> inRoads = new ArrayList<>();

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

    public ArrayList<Road> getOutRoads() {
        return outRoads;
    }

    public ArrayList<Road> getInRoads() {
        return inRoads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return isFinal == station.isFinal && Double.compare(station.taxiWaitingTime, taxiWaitingTime) == 0 && Double.compare(station.busWaitingTime, busWaitingTime) == 0 && name.equals(station.name) && outRoads.equals(station.outRoads) && inRoads.equals(station.inRoads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isFinal, taxiWaitingTime, busWaitingTime, outRoads, inRoads);
    }
}
