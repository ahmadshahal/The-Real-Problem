package data.utils;

import domain.models.Station;

import java.util.Comparator;

public class DijkstraUsage {
    public Station station;
    public Double distance;

    public DijkstraUsage(Station station, Double distance) {
        this.distance = distance;
        this.station = station;
    }

    public static final Comparator<DijkstraUsage> sortByDistance = Comparator.comparing(t -> t.distance);
}
