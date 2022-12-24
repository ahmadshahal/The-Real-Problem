package domain.models;

public class Player {
    private int health = 100;
    private int cost = 0;

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
}
