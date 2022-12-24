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

    /*
    public Player[] getNextStates() {
        ArrayList<Player> players = new ArrayList<Player>();
        for (Road road : this.station.getRoads()) {

        }
        return (Player[]) players.toArray();
    }
     */

    public void move(int health, int cost, int time, Station station) {
        this.health = health;
        this.cost = cost;
        this.time = time;
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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
