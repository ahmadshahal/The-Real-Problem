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
                player.takeTaxi(road);
                players.add(player);
            }
            if(road.getBuses() != null && road.getBuses().length != 0) {
                for (Bus bus : road.getBuses()) {
                    Player player = this.copy();
                    player.takeBus(road, bus);
                    players.add(player);
                }
            }
            Player player = this.copy();
            player.walk(road);
            players.add(player);
        }
        return (Player[]) players.toArray();
    }

    public void walk(Road road) {
        health -= 10 * road.getDistance();
        time += road.getDistance() / 5.5;
        cost += 0;
        station = road.getDestination();
    }

    public void takeTaxi(Road road) {
        cost += road.getTaxi().getMoneyCost(road.getDistance());
        health += road.getTaxi().getEffortCost(road.getDistance());
        time += road.getTaxi().getTimeCost(road.getDistance());
        time += station.getTaxiWaitingTime();
        station = road.getDestination();
    }

    public void takeBus(Road road, Bus bus) {
        cost += bus.getMoneyCost();
        health += bus.getEffortCost(road.getDistance());
        time += bus.getTimeCost(road.getDistance());
        time += station.getBusWaitingTime();
        station = road.getDestination();
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
