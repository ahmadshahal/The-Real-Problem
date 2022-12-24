package domain.models;

import java.util.Objects;

public class Player {
    private int health = 100;
    private int cost = 0;
    private int time = 0;
    private Station station;

    public Player(Station station) {
        this.station = station;
    }

    private Player(Station station, int health, int cost, int time) {
        this.station = station;
        this.health = health;
        this.cost = cost;
        this.time = time;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void walk() {
        health -= 10;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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
