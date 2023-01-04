package domain.models;

import domain.enums.TransmissionWay;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private int takenHealth = 0;
    private final int maxMoney;
    private double time = 0;
    private int takenMoney = 0;
    private Station station;
    private Bus previousBus = null;
    private TransmissionWay previousTransmissionWay = null;
    private Player parent = null;

    public Player(Station station, int maxMoney) {
        this.station = station;
        this.maxMoney = maxMoney;
    }

    private Player(Station station,
                   int takenHealth,
                   int maxMoney,
                   double time,
                   int takenMoney,
                   Bus previousBus,
                   TransmissionWay previousTransmissionWay,
                   Player parent
    ) {
        this.station = station;
        this.takenHealth = takenHealth;
        this.maxMoney = maxMoney;
        this.time = time;
        this.takenMoney = takenMoney;
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

    public int getTakenMoney() {
        return this.takenMoney;
    }

    public int getMaxHealth() {
        return 1000;
    }

    public int getTakenHealth() {
        return this.takenHealth;
    }

    public int getMaxMoney() {
        return this.maxMoney;
    }

    public TransmissionWay getPreviousTransmissionWay() {
        return previousTransmissionWay;
    }

    public Player getParent() {
        return this.parent;
    }

    public ArrayList<Player> getNextStates() {
        ArrayList<Player> players = new ArrayList<>();
        for (Road road : this.station.getOutRoads()) {
            if (road.getTaxi() != null) {
                Player player = this.copy();
                player.parent = this;
                player.takeTaxi(road);
                players.add(player);
            }
            if (road.getBuses() != null && road.getBuses().size() != 0) {
                for (Bus bus : road.getBuses()) {
                    Player player = this.copy();
                    player.parent = this;
                    player.takeBus(road, bus);
                    players.add(player);
                }
            }
            Player player = this.copy();
            player.parent = this;
            player.walk(road);
            players.add(player);
        }
        return players;
    }

    public void walk(Road road) {
        previousTransmissionWay = TransmissionWay.Walk;
        takenHealth += 10 * road.getDistance();
        time += road.getDistance() / 5.5;
        takenMoney += 0;
        station = road.getDestination();
    }

    public void takeTaxi(Road road) {
        if(previousTransmissionWay == null || previousTransmissionWay != TransmissionWay.Taxi) {
            time += station.getTaxiWaitingTime();
        }
        previousTransmissionWay = TransmissionWay.Taxi;
        takenMoney += road.getTaxi().getMoneyCost(road.getDistance());
        takenHealth += road.getTaxi().getEffortCost(road.getDistance());
        time += road.getTaxi().getTimeCost(road.getDistance());
        station = road.getDestination();
    }

    public void takeBus(Road road, Bus bus) {
        if (previousTransmissionWay != TransmissionWay.Bus || !previousBus.getName().equals(bus.getName())) {
            takenMoney += bus.getMoneyCost();
            time += station.getBusWaitingTime();
        }
        previousTransmissionWay = TransmissionWay.Bus;
        previousBus = bus;
        takenHealth += bus.getEffortCost(road.getDistance());
        time += bus.getTimeCost(road.getDistance());
        station = road.getDestination();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return takenHealth == player.takenHealth && maxMoney == player.maxMoney && Double.compare(player.time, time) == 0 && takenMoney == player.takenMoney && station.equals(player.station) && Objects.equals(previousBus, player.previousBus) && previousTransmissionWay == player.previousTransmissionWay && Objects.equals(parent, player.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(takenHealth, maxMoney, time, takenMoney, station, previousBus, previousTransmissionWay, parent);
    }

    public Player copy() {
        return new Player(
                this.station,
                this.takenHealth,
                this.maxMoney,
                this.time,
                this.takenMoney,
                this.previousBus,
                this.previousTransmissionWay,
                this.parent
        );
    }
}
