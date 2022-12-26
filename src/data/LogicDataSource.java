package data;

import data.callbacks.CalculateCost;
import data.callbacks.CheckConstraints;
import data.utils.AStarUsage;
import data.utils.DijkstraUsage;
import domain.models.Player;
import domain.models.Road;
import domain.models.Station;

import java.util.*;


public class LogicDataSource {

    private final HashMap<Station, Double> heuristicMap = new HashMap<>();

    public void aStar(Player player, CalculateCost calculateCost, CheckConstraints checkConstraints) {

        // Key: Player's HashCode.
        // Value: First: G (Time), Second: H, Third: Player.
        final HashMap<Integer, AStarUsage> visited = new HashMap<>();
        PriorityQueue<AStarUsage> queue = new PriorityQueue<>(AStarUsage.sortByCostThenHeuristic);

        AStarUsage initial = new AStarUsage(0.0, heuristic(player), player);
        queue.add(initial);
        visited.put(player.hashCode(), initial);

        while (!queue.isEmpty()) {
            AStarUsage current = queue.poll();
            if (visited.containsKey(current.player.hashCode())) {
                double previousPossibleCost = visited.get(current.hashCode()).cost;
                if (previousPossibleCost < current.cost) continue;
            }
            if (current.player.getStation().isFinal()) {
                return;
            }
            ArrayList<Player> children = current.player.getNextStates();
            for (Player child : children) {
                if (!checkConstraints.fun(child)) continue;
                AStarUsage newValue = new AStarUsage(calculateCost.fun(child), heuristic(child), child);
                if (visited.containsKey(child.hashCode())) {
                    double previousPossibleCost = visited.get(child.hashCode()).cost;
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


    /**
     * Calculates Heuristic for each Station as the Min Distance from it to the final station.
     *
     * @param station: The Final station.
     */
    public void initHeuristicByDijkstra(Station station) {

        // Sort by cost.
        PriorityQueue<DijkstraUsage> queue = new PriorityQueue<>(DijkstraUsage.sortByDistance);
        queue.add(new DijkstraUsage(station, 0.0));
        heuristicMap.put(station, 0.0);

        while (!queue.isEmpty()) {
            DijkstraUsage current = queue.poll();

            Double previousPossibleDistance = heuristicMap.get(current.station);
            if (previousPossibleDistance != null && previousPossibleDistance < current.distance) continue;

            for (Road road : station.getRoads()) {
                Double childStationDistance = Double.MAX_VALUE;
                Station destination = road.getDestination();

                if (heuristicMap.get(destination) != null)
                    childStationDistance = heuristicMap.get(destination);

                if (childStationDistance > current.distance + road.getDistance()) {
                    heuristicMap.put(destination, current.distance + road.getDistance());
                    queue.add(new DijkstraUsage(destination, heuristicMap.get(destination)));
                }
            }
        }
    }

    private double heuristic(Player player) {
        return heuristicMap.get(player.getStation());
    }
}
