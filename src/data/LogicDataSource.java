package data;

import data.callbacks.CalculateCost;
import data.callbacks.CheckConstraints;
import domain.models.Player;
import domain.models.Road;
import domain.models.Station;
import utils.Pair;
import utils.Triple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class LogicDataSource {

    private final HashMap<Station, Double> heuristicMap = new HashMap<>();

    private final Comparator<Triple<Double, Double, Player>> sortByCostThenHeuristic = (t1, t2) -> {
        double totalCost1 = t1.getFirst() + t1.getSecond();
        double totalCost2 = t2.getFirst() + t2.getSecond();
        if (totalCost1 < totalCost2) return -1;
        else if (totalCost1 > totalCost2) return 1;
        else return t1.getSecond().compareTo(t2.getSecond());
    };

    public void aStar(Player player, CalculateCost calculateCost, CheckConstraints checkConstraints) {

        // Key: Player's HashCode.
        // Value: First: G (Time), Second: H, Third: Player.
        final HashMap<Integer, Triple<Double, Double, Player>> visited = new HashMap<>();
        PriorityQueue<Triple<Double, Double, Player>> queue = new PriorityQueue<>(sortByCostThenHeuristic);

        Triple<Double, Double, Player> initial = new Triple<>(0.0, heuristic(player), player);
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
                if (!checkConstraints.fun(child)) continue;
                Triple<Double, Double, Player> newValue = new Triple<>(calculateCost.fun(child), heuristic(child), child);
                if (visited.containsKey(child.hashCode())) {
                    double previousPossibleCost = visited.get(child.hashCode()).getFirst();
                    if (calculateCost.fun(child) < previousPossibleCost) {
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


    public void dijkstra(Station station) {

        // Sort by second.
        PriorityQueue<Pair<Station, Double>> queue = new PriorityQueue<>(Comparator.comparingDouble(Pair::getSecond));
        queue.add(new Pair<>(station, 0.0));
        heuristicMap.put(station, 0.0);

        while (!queue.isEmpty()) {
            Pair<Station, Double> current = queue.poll();

            Double previousPossibleDistance = heuristicMap.get(current.getFirst());
            if (previousPossibleDistance != null && previousPossibleDistance < current.getSecond()) continue;

            for (Road road : station.getRoads()) {
                Double childStationDistance = Double.MAX_VALUE;
                Station destination = road.getDestination();

                if (heuristicMap.get(destination) != null)
                    childStationDistance = heuristicMap.get(destination);

                if (childStationDistance > current.getSecond() + road.getDistance()) {
                    heuristicMap.put(destination, current.getSecond() + road.getDistance());
                    queue.add(new Pair<>(destination, heuristicMap.get(destination)));
                }
            }
        }

    }

    private double heuristic(Player player) {
        return heuristicMap.get(player.getStation());
    }
}
