package data.utils;

import domain.models.Player;

import java.util.Comparator;

public class AStarUsage {
    public Double cost;
    public Double heuristic;
    public Player player;

    public AStarUsage(Double cost, Double heuristic, Player player) {
        this.cost = cost;
        this.heuristic = heuristic;
        this.player = player;
    }

    public static final Comparator<AStarUsage> sortByCostThenHeuristic = (u1, u2) -> {
        double totalCost1 = u1.cost + u1.heuristic;
        double totalCost2 = u2.cost + u2.heuristic;
        if (totalCost1 < totalCost2) return -1;
        else if (totalCost1 > totalCost2) return 1;
        else return u1.heuristic.compareTo(u2.heuristic);
    };
}
