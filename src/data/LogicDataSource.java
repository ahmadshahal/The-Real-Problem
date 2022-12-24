package data;

import domain.models.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class LogicDataSource {
    // G , H , ref to player object
    private HashMap<Integer , Triple<Double , Double , Player>> visited = new HashMap<Integer, Triple<Double, Double, Player>>();

    public void aStar(Player player) {
        Comparator<Triple<Double , Double , Player>> sortByCostThenHeuristic = new Comparator<Triple<Double, Double, Player>>() {
            @Override
            public int compare(Triple<Double, Double, Player> t1, Triple<Double, Double, Player> t2) {
                double totalCost1 = t1.getFirst() + t1.getSecond();
                double totalCost2 = t2.getFirst() + t2.getSecond();
                if(totalCost1 < totalCost2) return -1;
                else if(totalCost1 > totalCost2) return 1;
                else {
                    if (t1.getSecond() <= t1.getSecond()) return -1;
                    else return 1;
                }
            }
        };
        // first is G , second is H , third is Player node
        PriorityQueue<Triple<Double , Double , Player>> pq = new PriorityQueue<Triple<Double , Double , Player>>(10 , sortByCostThenHeuristic);
        pq.add(new Triple<>(0.0 , this.calcHeuristic(player) , player));
        this.visited.put(player.hashCode() , new Triple<>(0.0 , this.calcHeuristic(player) , player));
        while(!pq.isEmpty()){
             Triple<Double , Double , Player> cur = pq.poll();
             if(cur.getThird().getStation().isFinal()) return;
             ArrayList<Player> children = cur.getThird().getNextStates();
             for (Player child : children) {
                 if(this.visited.containsKey(child.hashCode())){
                    if(this.visited.get(child.hashCode()).getFirst() > child.getTime()){
                        this.visited.put(child.hashCode() , new Triple<>(child.getTime() , this.calcHeuristic(child) , child));
                        pq.add(new Triple<>(child.getTime() , this.calcHeuristic(child) , child));
                    }
                 }
                 else{
                     this.visited.put(child.hashCode() , new Triple<>(child.getTime() , this.calcHeuristic(child) , child));
                     pq.add(new Triple<>(child.getTime() , this.calcHeuristic(child) , child));
                 }
             }
        }
    }

    private double calcHeuristic(Player player){

        return 0;
    }
}
