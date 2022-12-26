package data;

import data.callbacks.CalculateCost;
import data.callbacks.CalculateHeuristic;
import data.callbacks.CheckConstraints;
import data.utils.AStarUsage;
import data.utils.DijkstraUsage;
import data.utils.OutputUtils;
import domain.models.Player;
import domain.models.Road;
import domain.models.Station;

import java.util.*;


public class LogicDataSource {

    private final HashMap<Integer, Double> distanceMap = new HashMap<>();

    public void aStar(
            Player player,
            CalculateCost calculateCost,
            CheckConstraints checkConstraints,
            CalculateHeuristic calculateHeuristic
    ) {
        int visitedNodes = 0;

        // Key: Player's HashCode.
        final HashMap<Integer, AStarUsage> visited = new HashMap<>();
        PriorityQueue<AStarUsage> queue = new PriorityQueue<>(AStarUsage.sortByCostThenHeuristic);

        double initHeuristic = calculateHeuristic.fun(distanceFromGoal(player));
        AStarUsage initial = new AStarUsage(0.0, initHeuristic, player);
        queue.add(initial);
        visited.put(player.hashCode(), initial);

        while (!queue.isEmpty()) {
            AStarUsage current = queue.poll();

            visitedNodes++;

            if (current.player.getStation().isFinal()) {
                OutputUtils.printPath(current.player);
                System.out.println("Visited Nodes: " + visitedNodes);
                return;
            }
            ArrayList<Player> children = current.player.getNextStates();
            for (Player child : children) {
                if (!checkConstraints.fun(child)) continue;
                double childHeuristic = calculateHeuristic.fun(distanceFromGoal(child));
                AStarUsage newValue = new AStarUsage(calculateCost.fun(child), childHeuristic, child);
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

        System.out.println("Problem can't be solved..");
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
        distanceMap.put(station.hashCode(), 0.0);

        while (!queue.isEmpty()) {
            DijkstraUsage current = queue.poll();

            Double previousPossibleDistance = distanceMap.get(current.station.hashCode());
            if (previousPossibleDistance != null && previousPossibleDistance < current.distance) continue;

            for (Road road : current.station.getInRoads()) {
                Double childStationDistance = Double.MAX_VALUE;
                Station destination = road.getDestination();

                if (distanceMap.get(destination.hashCode()) != null)
                    childStationDistance = distanceMap.get(destination.hashCode());

                if (childStationDistance > current.distance + road.getDistance()) {
                    distanceMap.put(destination.hashCode(), current.distance + road.getDistance());
                    queue.add(new DijkstraUsage(destination, distanceMap.get(destination.hashCode())));
                }
            }
        }
    }

    private double distanceFromGoal(Player player) {
         return distanceMap.get(player.getStation().hashCode());
    }
}
