package presentation;

import data.LogicDataSource;
import domain.models.Player;
import domain.models.Station;

public class Main {
    private static final LogicDataSource logicDataSource = new LogicDataSource();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + " ms");
    }
}

/*
        // Q1:
        logicDataSource.aStar(
                new Player(new Station(0, false, 2, 2, null)),
                Player::getTime,
                player -> player.getCost() <= player.getMoney() && player.getHealth() >= 0
        );

        // Q3 - 1:
        logicDataSource.aStar(
                new Player(new Station(0, false, 2, 2, null)),
                Player::getCost,
                player -> player.getHealth() >= 0
        );

        // Q3 - 2:
        logicDataSource.aStar(
                new Player(new Station(0, false, 2, 2, null)),
                player -> player.getHealth() * -1,
                player -> player.getCost() <= player.getMoney()
        );
 */
