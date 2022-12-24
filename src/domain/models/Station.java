package domain.models;


public class Station {
    private final int id; // Can be converted to name.
    private final boolean isFinal;
    private final int taxiWaitingTime;
    private final int busWaitingTime;

    public Station(int id, boolean isFinal, int taxiWaitingTime, int busWaitingTime) {
        this.id = id;
        this.isFinal = isFinal;
        this.taxiWaitingTime = taxiWaitingTime;
        this.busWaitingTime = busWaitingTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id == station.id;
    }
}
