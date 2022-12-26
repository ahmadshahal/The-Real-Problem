package presentation;

import data.LogicDataSource;
import domain.models.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {

    private static final LogicDataSource logicDataSource = new LogicDataSource();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        out.println("Enter the amount of money you have:");
        int money = scanner.nextInt();

        out.println("Enter the number of stations:");
        int stationsCount = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Station> stations = new ArrayList<>();
        for (int i = 0; i < stationsCount; i++) {
            out.println("Station " + (i + 1));

            out.println("Enter station's name:");
            String stationName = scanner.nextLine();

            out.println("Enter bus's expected waiting time in hours:");
            double busWaitingTime = scanner.nextDouble();

            out.println("Enter taxi's expected waiting time in hours:");
            double taxiWaitingTime = scanner.nextDouble();
            scanner.nextLine();

            out.println("Is this the station you want to reach eventually? (Y/N)");
            boolean finalStation = Objects.equals(scanner.nextLine(), "Y");

            Station station = new Station(stationName, finalStation, taxiWaitingTime, busWaitingTime);
            stations.add(station);
        }

        out.println("Enter the number of roads:");
        int roadsCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < roadsCount; i++) {
            out.println("Road " + (i + 1));

            out.println("Enter the source station's name of this Road:");
            String sourceStationName = scanner.nextLine();

            out.println("Enter the destination station's name of this Road:");
            String destinationStationName = scanner.nextLine();

            out.println("Enter Road's distance in KM:");
            int roadDistance = scanner.nextInt();
            scanner.nextLine();

            out.println("Does this road has taxis? (Y/N)");
            boolean hasTaxis = Objects.equals(scanner.nextLine(), "Y");

            Taxi taxi = null;
            if(hasTaxis) {
                out.println("Enter taxi's speed (KM/H):");
                double taxiSpeed = scanner.nextDouble();
                scanner.nextLine();

                taxi = new Taxi(taxiSpeed);
            }

            out.println("Does this road has busses? (Y/N)");
            boolean hasBusses = Objects.equals(scanner.nextLine(), "Y");

            ArrayList<Bus> buss = null;
            if(hasBusses) {
                out.println("Enter bus's speed (KM/H):");
                double busSpeed = scanner.nextDouble();
                scanner.nextLine();

                out.println("Enter the number of buses in this road:");
                int bussCount = scanner.nextInt();
                scanner.nextLine();

                ArrayList<Bus> bussTemp = new ArrayList<>();
                for (int j = 0; j < bussCount; j++) {
                    out.println("Enter Bus's name:");
                    String busName = scanner.nextLine();
                    bussTemp.add(new Bus(busName, busSpeed));
                }
                buss = bussTemp;
            }

            Station sourceStation = null;
            Station destinationStation = null;

            for (Station station : stations) {
                if(Objects.equals(station.getStationName(), sourceStationName)) {
                    sourceStation = station;
                }
                if(Objects.equals(station.getStationName(), destinationStationName)) {
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
            if(station.isFinal()) {
                finalStation = station;
                break;
            }
        }

        out.println("Enter initial station's name:");
        String initStationName = scanner.nextLine();

        Station initStation = null;
        for (Station station : stations) {
            if(Objects.equals(station.getStationName(), initStationName)) {
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
                distance -> distance / 5.5 // TODO: Consider 160
        );
         */

        /*
        logicDataSource.aStar(
                initPlayer,
                Player::getTakenMoney,
                player -> player.getTakenHealth() <= player.getMaxHealth(),
                distance -> distance * 1000 // TODO: Consider 0
        );
         */

        /*
        logicDataSource.aStar(
                initPlayer,
                Player::getTakenHealth,
                player -> player.getTakenMoney() <= player.getMaxMoney(),
                distance -> distance * 10 // TODO: Consider -10
        );
         */

        /*
        logicDataSource.aStar(
                initPlayer,
                player -> player.getTime() + player.getTakenHealth() + player.getTakenMoney(),
                player -> true,
                distance -> (distance / 5.5) + (distance * 1000) + (distance * 10)
        );
         */

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Elapsed Time: " + elapsedTime + " ms");
    }
}