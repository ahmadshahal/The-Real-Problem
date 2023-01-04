package presentation;

import data.LogicDataSource;
import domain.models.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.*;

public class MainTemp {

    private static final LogicDataSource logicDataSource = new LogicDataSource();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        out.println("Enter the amount of money you have:");
        int money = scanner.nextInt();

        out.println("Enter the number of stations:");
        int stationsCount = scanner.nextInt();

        ArrayList<Station> stations = new ArrayList<>();
        for (int i = 0; i < stationsCount; i++) {
            out.println("Station " + (i + 1));

            out.println("Enter station's name:");
            String stationName = scanner.next();

            out.println("Enter bus's expected waiting time in hours:");
            double busWaitingTime = scanner.nextDouble();

            out.println("Enter taxi's expected waiting time in hours:");
            double taxiWaitingTime = scanner.nextDouble();

            out.println("Is this the station you want to reach eventually? (Y/N)");
            boolean finalStation = Objects.equals(scanner.next(), "Y");

            Station station = new Station(stationName, finalStation, taxiWaitingTime, busWaitingTime);
            stations.add(station);
        }

        out.println("Enter the number of roads:");
        int roadsCount = scanner.nextInt();

        for (int i = 0; i < roadsCount; i++) {
            out.println("Road " + (i + 1));

            out.println("Enter the source station's name of this Road:");
            String sourceStationName = scanner.next();

            out.println("Enter the destination station's name of this Road:");
            String destinationStationName = scanner.next();

            out.println("Enter Road's distance in KM:");
            int roadDistance = scanner.nextInt();

            out.println("Does this road has taxis? (Y/N)");
            boolean hasTaxis = Objects.equals(scanner.next(), "Y");

            Taxi taxi = null;
            if (hasTaxis) {
                out.println("Enter taxi's speed (KM/H):");
                double taxiSpeed = scanner.nextDouble();

                taxi = new Taxi(taxiSpeed);
            }

            out.println("Does this road has busses? (Y/N)");
            boolean hasBusses = Objects.equals(scanner.next(), "Y");

            ArrayList<Bus> buss = null;
            if (hasBusses) {
                out.println("Enter bus's speed (KM/H):");
                double busSpeed = scanner.nextDouble();

                out.println("Enter the number of buses in this road:");
                int bussCount = scanner.nextInt();

                ArrayList<Bus> bussTemp = new ArrayList<>();
                for (int j = 0; j < bussCount; j++) {
                    out.println("Enter Bus's name:");
                    String busName = scanner.next();
                    bussTemp.add(new Bus(busName, busSpeed));
                }
                buss = bussTemp;
            }

            Station sourceStation = null;
            Station destinationStation = null;

            for (Station station : stations) {
                if (Objects.equals(station.getStationName(), sourceStationName)) {
                    sourceStation = station;
                }
                if (Objects.equals(station.getStationName(), destinationStationName)) {
                    destinationStation = station;
                }
            }
            assert destinationStation != null;
            assert sourceStation != null;

            Road outRoad = new Road(roadDistance, buss, taxi, destinationStation);
            Road inRoad = new Road(roadDistance, buss, taxi, sourceStation);

            destinationStation.getInRoads().add(inRoad);
            sourceStation.getOutRoads().add(outRoad);
        }

        Station finalStation = null;
        for (Station station : stations) {
            if (station.isFinal()) {
                finalStation = station;
                break;
            }
        }

        out.println("Enter initial station's name:");
        String initStationName = scanner.next();

        Station initStation = null;
        for (Station station : stations) {
            if (Objects.equals(station.getStationName(), initStationName)) {
                initStation = station;
                break;
            }
        }

        assert initStation != null;
        Player initPlayer = new Player(initStation, money);

        long startTime = System.currentTimeMillis();

        assert finalStation != null;
        logicDataSource.initHeuristicByDijkstra(finalStation);

        /*
        logicDataSource.aStar(
                initPlayer,
                Player::getTime,
                player -> player.getTakenMoney() <= player.getMaxMoney() && player.getTakenHealth() <= player.getMaxHealth(),
                distance -> distance / 160 // Always taking the fastest taxi.
        );
         */

        logicDataSource.aStar(
                initPlayer,
                Player::getTakenMoney,
                player -> player.getTakenHealth() <= player.getMaxHealth(),
                distance -> distance * -0.5 // Taking a Transmission Way that gives 0.5 for each KM.
        );

        /*
        logicDataSource.aStar(
                initPlayer,
                Player::getTakenHealth,
                player -> player.getTakenMoney() <= player.getMaxMoney(),
                distance -> distance * -5 // Always taking a taxi.
        );
         */

        /*
        logicDataSource.aStar(
                initPlayer,
                player -> (player.getTime() / 3) + // Three Hours as a Max hours.
                        (player.getTakenHealth() / player.getMaxHealth()) +
                        (player.getTakenMoney() / player.getMaxMoney()),
                player -> player.getTakenMoney() <= player.getMaxMoney() && player.getTakenHealth() <= player.getMaxHealth(),
                distance -> ((distance / 160) / 3) +
                        ((distance * -0.5) / initPlayer.getMaxHealth()) +
                        ((distance * -5) / initPlayer.getMaxMoney())
        );
         */

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Elapsed Time: " + elapsedTime + " ms");
    }
}