package domain.models;

import domain.enums.TransmissionWay;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private int health = 100;
    private int money = 10000;
    private double time = 0;
    private int cost = 0;
    private Station station;
    private Bus previousBus = null;
    private TransmissionWay previousTransmissionWay = null;
    private Player parent = null;

    public Player(Station station) {
        this.station = station;
    }

    private Player(Station station,
                   int health,
                   int money,
                   double time,
                   int cost,
                   Bus previousBus,
                   TransmissionWay previousTransmissionWay,
                   Player parent
    ) {
        this.station = station;
        this.health = health;
        this.money = money;
        this.time = time;
        this.cost = cost;
        this.previousBus = previousBus;
        this.previousTransmissionWay = previousTransmissionWay;
        this.parent = parent;
    }

    public Station getStation() {
        return this.station;
    }

    public double getTime() {
        return this.time;
    }

    public int getCost() {
        return this.cost;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMoney() {
        return this.money;
    }

    public ArrayList<Player> getNextStates() {
        ArrayList<Player> players = new ArrayList<>();
        for (Road road : this.station.getRoads()) {
            if (road.getTaxi() != null) {
                Player player = this.copy();
                player.takeTaxi(road);
                players.add(player);
            }
            if (road.getBuses() != null && road.getBuses().length != 0) {
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
        return players;
    }

    public void walk(Road road) {
        previousTransmissionWay = TransmissionWay.Walk;
        health -= 10 * road.getDistance();
        time += road.getDistance() / 5.5;
        cost += 0;
        station = road.getDestination();
    }

    public void takeTaxi(Road road) {
        previousTransmissionWay = TransmissionWay.Taxi;
        cost += road.getTaxi().getMoneyCost(road.getDistance());
        health += road.getTaxi().getEffortCost(road.getDistance());
        time += road.getTaxi().getTimeCost(road.getDistance());
        time += station.getTaxiWaitingTime();
        station = road.getDestination();
    }

    public void takeBus(Road road, Bus bus) {
        if (previousTransmissionWay != TransmissionWay.Bus || !previousBus.getName().equals(bus.getName())) {
            cost += bus.getMoneyCost();
        }
        previousTransmissionWay = TransmissionWay.Bus;
        previousBus = bus;
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
        return health == player.health && money == player.money && Double.compare(player.time, time) == 0 && cost == player.cost && station.equals(player.station) && Objects.equals(previousBus, player.previousBus) && previousTransmissionWay == player.previousTransmissionWay && Objects.equals(parent, player.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(health, money, time, cost, station, previousBus, previousTransmissionWay, parent);
    }

    public Player copy() {
        return new Player(
                this.station,
                this.health,
                this.money,
                this.time,
                this.cost,
                this.previousBus,
                this.previousTransmissionWay,
                this.parent
        );
    }
}
