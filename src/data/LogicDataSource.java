package data;

import domain.models.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class LogicDataSource {

    private final Comparator<Triple<Double, Double, Player>> sortByCostThenHeuristic = (t1, t2) -> {
        double totalCost1 = t1.getFirst() + t1.getSecond();
        double totalCost2 = t2.getFirst() + t2.getSecond();
        if (totalCost1 < totalCost2) return -1;
        else if (totalCost1 > totalCost2) return 1;
        else return t1.getSecond().compareTo(t2.getSecond());
    };

    public void aStar(Player player) {

        // Key: Player's HashCode.
        // Value: First: G (Time), Second: H, Third: Player.
        final HashMap<Integer, Triple<Double, Double, Player>> visited = new HashMap<>();
        PriorityQueue<Triple<Double, Double, Player>> queue = new PriorityQueue<>(sortByCostThenHeuristic);

        Triple<Double, Double, Player> initial = new Triple<>(0.0, calcHeuristic(player), player);
        queue.add(initial);
        visited.put(player.hashCode(), initial);

        while (!queue.isEmpty()) {
            Triple<Double, Double, Player> current = queue.poll();
            if (visited.containsKey(current.getThird().hashCode())) {
                double previousPossibleCost = visited.get(current.hashCode()).getFirst();
                if (previousPossibleCost < current.getFirst()) continue;
            }
            if (current.getThird().getStation().isFinal()) {
                // TODO: Print Results.
                return;
            }
            ArrayList<Player> children = current.getThird().getNextStates();
            for (Player child : children) {
                Triple<Double, Double, Player> newValue = new Triple<>(child.getTime(), calcHeuristic(child), child);
                if (visited.containsKey(child.hashCode())) {
                    double previousPossibleCost = visited.get(child.hashCode()).getFirst();
                    if (child.getTime() < previousPossibleCost) {
                        visited.put(child.hashCode(), newValue);
                        queue.add(newValue);
                    }
                } else {
                    visited.put(child.hashCode(), newValue);
                    queue.add(newValue);
                }
            }
        }
    }

    private double calcHeuristic(Player player) {
        return 0;
    }
}
