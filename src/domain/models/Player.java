package domain.models;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private int health = 100;
    private int cost = 0;
    private double time = 0;
    private Station station;

    public Player(Station station) {
        this.station = station;
    }

    private Player(Station station, int health, int cost, double time) {
        this.station = station;
        this.health = health;
        this.cost = cost;
        this.time = time;
    }

    public Player[] getNextStates() {
        ArrayList<Player> players = new ArrayList<>();
        for (Road road : this.station.getRoads()) {
            if(road.getTaxi() != null) {
                Player player = this.copy();
                player.takeTaxi(road.getDistance(), road.getTaxi(), road.getDestination());
                players.add(player);
            }
            if(road.getBus() != null) {
                Player player = this.copy();
                player.takeBus(road.getDistance(), road.getBus(), road.getDestination());
                players.add(player);
            }
            Player player = this.copy();
            player.walk(road.getDistance(), road.getDestination());
            players.add(player);
        }
        return (Player[]) players.toArray();
    }

    public void walk(int distance, Station destination) {
        health -= 10 * distance;
        time += distance * 5.5;
        cost += 0;
        station = destination;
    }

    public void takeTaxi(int distance, Taxi taxi, Station destination) {
        cost += taxi.getMoneyCost(distance);
        health += taxi.getEffortCost(distance);
        time += taxi.getTimeCost(distance);
        station = destination;
    }

    public void takeBus(int distance, Bus bus, Station destination) {
        cost += bus.getMoneyCost();
        health += bus.getEffortCost(distance);
        time += bus.getTimeCost(distance);
        station = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return health == player.health && cost == player.cost && time == player.time && Objects.equals(station, player.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(health, cost, time, station);
    }

    public Player copy() {
        return new Player(
                this.station,
                this.health,
                this.cost,
                this.time
        );
    }
}
