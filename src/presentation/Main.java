package presentation;

import data.LogicDataSource;
import domain.models.*;

import java.util.ArrayList;
import java.util.Objects;

public class Main {

    private static final LogicDataSource logicDataSource = new LogicDataSource();

    public static void main(String[] args) {

        int money = 10000;

        int stationsCount = 3;

        ArrayList<Station> stations = new ArrayList<>();

        String station1Name = "C";
        double station1BusWaitingTime = 0.5;
        double station1TaxiWaitingTime = 0.5;
        boolean isStation1FinalStation = false;
        Station station1 = new Station(station1Name, isStation1FinalStation, station1TaxiWaitingTime, station1BusWaitingTime);
        stations.add(station1);

        String station2Name = "M";
        double station2BusWaitingTime = 0.5;
        double station2TaxiWaitingTime = 0.5;
        boolean isStation2FinalStation = true;
        Station station2 = new Station(station2Name, isStation2FinalStation, station2TaxiWaitingTime, station2BusWaitingTime);
        stations.add(station2);

        String station3Name = "P";
        double station3BusWaitingTime = 0.5;
        double station3TaxiWaitingTime = 0.5;
        boolean isStation3FinalStation = false;
        Station station3 = new Station(station3Name, isStation3FinalStation, station3TaxiWaitingTime, station3BusWaitingTime);
        stations.add(station3);

        int roadsCount = 3;

        // ============================================
        // Road1:
        String road1SourceStationName = "C";
        String road1DestinationStationName = "M";
        int road1Distance = 40;
        boolean road1HasTaxis = true;
        double road1TaxiSpeed = 80;
        Taxi road1Taxi = new Taxi(road1TaxiSpeed);

        boolean road1HasBusses = false;
        ArrayList<Bus> road1Buss = null;

        Station road1SourceStation = null;
        Station road1DestinationStation = null;
        for (Station station : stations) {
            if (Objects.equals(station.getStationName(), road1SourceStationName)) {
                road1SourceStation = station;
            }
            if (Objects.equals(station.getStationName(), road1DestinationStationName)) {
                road1DestinationStation = station;
            }
        }
        Road outRoad1 = new Road(road1Distance, road1Buss, road1Taxi, road1DestinationStation);
        Road inRoad1 = new Road(road1Distance, road1Buss, road1Taxi, road1SourceStation);
        road1DestinationStation.getInRoads().add(inRoad1);
        road1SourceStation.getOutRoads().add(outRoad1);

        // ============================================
        // Road2:
        String road2SourceStationName = "C";
        String road2DestinationStationName = "P";
        int road2Distance = 15;
        boolean road2HasTaxis = true;
        double road2TaxiSpeed = 80;
        Taxi road2Taxi = new Taxi(road2TaxiSpeed);

        boolean road2HasBusses = true;
        ArrayList<Bus> road2Buss = new ArrayList<>();
        double road2BusSpeed = 60;
        int road2BussCount = 1;
        Bus road2Bus1 = new Bus("Muhajreen", road2BusSpeed);
        road2Buss.add(road2Bus1);

        Station road2SourceStation = null;
        Station road2DestinationStation = null;
        for (Station station : stations) {
            if (Objects.equals(station.getStationName(), road2SourceStationName)) {
                road2SourceStation = station;
            }
            if (Objects.equals(station.getStationName(), road2DestinationStationName)) {
                road2DestinationStation = station;
            }
        }
        Road outRoad2 = new Road(road2Distance, road2Buss, road2Taxi, road2DestinationStation);
        Road inRoad2 = new Road(road2Distance, road2Buss, road2Taxi, road2SourceStation);
        road2DestinationStation.getInRoads().add(inRoad2);
        road2SourceStation.getOutRoads().add(outRoad2);

        // ============================================
        // Road3:
        String road3SourceStationName = "P";
        String road3DestinationStationName = "M";
        int road3Distance = 15;
        boolean road3HasTaxis = true;
        double road3TaxiSpeed = 80;
        Taxi road3Taxi = new Taxi(road3TaxiSpeed);

        boolean road3HasBusses = true;
        ArrayList<Bus> road3Buss = new ArrayList<>();
        double road3BusSpeed = 60;
        int road3BussCount = 1;
        Bus road3Bus1 = new Bus("Mashrou", road3BusSpeed);
        road3Buss.add(road3Bus1);

        Station road3SourceStation = null;
        Station road3DestinationStation = null;
        for (Station station : stations) {
            if (Objects.equals(station.getStationName(), road3SourceStationName)) {
                road3SourceStation = station;
            }
            if (Objects.equals(station.getStationName(), road3DestinationStationName)) {
                road3DestinationStation = station;
            }
        }
        Road outRoad3 = new Road(road3Distance, road3Buss, road3Taxi, road3DestinationStation);
        Road inRoad3 = new Road(road3Distance, road3Buss, road3Taxi, road3SourceStation);
        road3DestinationStation.getInRoads().add(inRoad3);
        road3SourceStation.getOutRoads().add(outRoad3);

        Station finalStation = null;
        for (Station station : stations) {
            if (station.isFinal()) {
                finalStation = station;
                break;
            }
        }

        String initStationName = "C";

        Station initStation = null;
        for (Station station : stations) {
            if (Objects.equals(station.getStationName(), initStationName)) {
                initStation = station;
                break;
            }
        }

        Player initPlayer = new Player(initStation, money);

        long startTime = System.currentTimeMillis();

        logicDataSource.initHeuristicByDijkstra(finalStation);

        /*
        logicDataSource.aStar(
                initPlayer,
                Player::getTime,
                player -> player.getTakenMoney() <= player.getMaxMoney() && player.getTakenHealth() <= player.getMaxHealth(),
                distance -> distance / 160 // Always taking the fastest taxi.
        );
         */

        /*
        logicDataSource.aStar(
                initPlayer,
                Player::getTakenMoney,
                player -> player.getTakenHealth() <= player.getMaxHealth(),
                distance -> distance * -0.5 // Taking a Transmission Way that gives 0.5 for each KM.
        );
         */

        /*
        logicDataSource.aStar(
                initPlayer,
                Player::getTakenHealth,
                player -> player.getTakenMoney() <= player.getMaxMoney(),
                distance -> distance * -5 // Always taking a taxi.
        );
         */

        logicDataSource.aStar(
                initPlayer,
                player -> (player.getTime() / 3) + // Three Hours as Max hours.
                        (player.getTakenHealth() / player.getMaxHealth()) +
                        (player.getTakenMoney() / player.getMaxMoney()),
                player -> player.getTakenMoney() <= player.getMaxMoney() && player.getTakenHealth() <= player.getMaxHealth(),
                distance -> ((distance / 160) / 3) +
                        ((distance * -0.5) / initPlayer.getMaxHealth()) +
                        ((distance * -5) / initPlayer.getMaxMoney())
        );

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Elapsed Time: " + elapsedTime + " ms");
    }
}