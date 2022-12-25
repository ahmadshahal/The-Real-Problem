package data;

import data.callbacks.CalculateCost;
import data.callbacks.CheckConstraints;
import domain.models.Player;
import domain.models.Road;
import domain.models.Station;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class LogicDataSource {

    private final HashMap <Station,Double> Heuristic  = new HashMap<>();

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
                if(!checkConstraints.fun(child)) continue;
                Triple<Double, Double, Player> newValue = new Triple<>(calculateCost.fun(child), calcHeuristic(child), child);
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



    private void Dijkstra (Station station) {
        // sort on the second value
        PriorityQueue <Pair<Station,Double>> q = new PriorityQueue<>((a, b) -> Double.compare(a.getValue(),b.getValue()));
        q.add(new Pair<Station,Double>(station,0.0));
        Heuristic.put(station,0.0);
        while(q.size() > 0) {
            Pair <Station,Double> curStation = q.peek();
            Double curDistance = Heuristic.get(curStation.getKey());
            q.poll();
            if(curDistance != null  &&  curDistance < curStation.getValue())   continue;
            for (Road road : station.getRoads()) {
                Double childStationDistance = Double.MAX_VALUE;
                if(Heuristic.get(road.getDestination()) != null)    childStationDistance = Heuristic.get(road.getDestination());
                if(childStationDistance > curStation.getValue() + road.getDistance()){
                    Heuristic.put(road.getDestination(),curStation.getValue() + road.getDistance());
                    q.add(new Pair<Station,Double>(road.getDestination(),Heuristic.get(road.getDestination())));
                }
            }
        }

    }

    private double calcHeuristic (Player player) {
        return Heuristic.get(player.getStation());
    }
}
