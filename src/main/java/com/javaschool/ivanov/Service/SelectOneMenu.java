package com.javaschool.ivanov.Service;


import com.javaschool.ivanov.DAO.RouteDao;
import com.javaschool.ivanov.DAO.StationDao;
import com.javaschool.ivanov.DAO.TrainDao;
import com.javaschool.ivanov.DAO.TripDao;
import com.javaschool.ivanov.Domain.Route;
import com.javaschool.ivanov.Domain.Station;
import com.javaschool.ivanov.Domain.Train;

import java.util.ArrayList;
import java.util.List;

public class SelectOneMenu {

    public static List<String> findAllStations()
    {
        StationDao stationDao = new StationDao();
        List<Station> allStations = stationDao.findAll();
        List<String> stations = new ArrayList<String>();
        for (Station station : allStations) {
            stations.add(station.getName());
        }
        return stations;
    }
    public static List<String> findAllRoutes()
    {
        RouteDao routeDao = new RouteDao();
        List<Route> allRoutes = routeDao.findAll();
        List<String> routes = new ArrayList<String>();
        for (Route route : allRoutes) {
            routes.add(route.getName());
        }
        return routes;
    }
    public static List<String> findAllTrains()
    {
        TrainDao trainDao = new TrainDao();
        List<Train> allTrains = trainDao.findAll();
        List<String> trains = new ArrayList<String>();
        for (Train train : allTrains) {
            trains.add(train.getName());
        }
        return trains;
    }
    public static List<Object> findTrainsByRoute(String route)
    {
        TrainDao trainDao = new TrainDao();
        List<Object> allTrains = trainDao.findTrainByRoute(route);
        return allTrains;
    }
    public static List<Object> findDatesByTrainAndRoute(String train, String route)
    {
        TripDao tripDao = new TripDao();
        List<Object> allDates = tripDao.findDateByTrainAndRoute(train, route);
        return allDates;
    }
    public static List<Object> findStationsFromByRoute(String route)
    {
        StationDao stationDao = new StationDao();
        List<Object> allStationsFrom = stationDao.findStationsFromByRoute(route);
        return allStationsFrom;
    }
    public static List<Object> findStationsToByRoute(String route)
    {
        StationDao stationDao = new StationDao();
        List<Object> allStationsFrom = stationDao.findStationsToByRoute(route);
        return allStationsFrom;
    }

}
