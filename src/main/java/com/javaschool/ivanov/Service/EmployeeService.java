package com.javaschool.ivanov.Service;


import com.javaschool.ivanov.DAO.*;
import com.javaschool.ivanov.DTO.CreateRouteInfo;
import com.javaschool.ivanov.Domain.*;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Exception.ServerError;
import org.apache.log4j.Logger;

import javax.persistence.RollbackException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeService {

    private final static Logger log = Logger.getLogger(EmployeeService.class);
    private StationDao stationDao;
    private RouteDao routeDao;
    private TrainDao trainDao;
    private ScheduleDao scheduleDao;
    private Route route;

    /**
     * @param name - station name
     * @throws ObjectExistException - if station already exist in data base
     * @throws ServerError
     */
    public void createStation(String name) throws ObjectExistException, ServerError
    {

        stationDao = new StationDao();
        try {
            if (stationDao.find(name) == null) {
                stationDao.getEt().begin();
                stationDao.create(new Station(name));
                stationDao.getEt().commit();
                log.info("Station '" + name + "' (Created)");
            } else throw new ObjectExistException();
        }
        catch (RollbackException e)
        {
            if(stationDao.getEt().isActive()) stationDao.getEt().rollback();
            throw new ServerError();

        }
    }

    /**
     * @param info - employee request params
     * @throws ObjectExistException - if route already exist in data base
     * @throws ServerError
     */
    public void createRoute(CreateRouteInfo info) throws ObjectExistException, ServerError
    {
        routeDao = new RouteDao();
        try {
            route = routeDao.find(info.getName());
            if( route == null)
            {
                routeDao.getEt().begin();
                route = routeDao.create(new Route(info.getName()));
                routeDao.getEt().commit();
            }

            DirectionDao directionDao = new DirectionDao();
            Integer directionId = directionDao.find(info.getStationFrom(), info.getStationTo(), info.getDuration());

            Direction direction;
            if(directionId == null)
            {
                stationDao = new StationDao();
                Station stationFrom = stationDao.find(info.getStationFrom());
                Station stationTo = stationDao.find(info.getStationTo());
                Timestamp timestamp = new Timestamp(info.getDuration().getTime());
                direction = new Direction(timestamp, stationFrom, stationTo);

                directionDao.getEt().begin();
                direction = directionDao.create(direction);
                directionDao.getEt().commit();
            }
            else direction = directionDao.find(directionId);

            int sequenceNumber = Integer.parseInt(info.getSequenceNumber());
            scheduleDao = new ScheduleDao();
            Integer scheduleId = scheduleDao.find(sequenceNumber, route.getId(), direction.getId());
            if(scheduleId == null)
            {
                Schedule schedule = new Schedule(sequenceNumber, route, direction);
                scheduleDao.getEt().begin();
                scheduleDao.create(schedule);
                scheduleDao.getEt().commit();
                log.info("Route '" + info.getName() + "' (Created)");
            }
            else throw new ObjectExistException();
        }
        catch (RollbackException e)
        {
            if(routeDao.getEt().isActive()) routeDao.getEt().rollback();
            throw new ServerError();

        }

    }

    /**
     * @param name - train name
     * @param capacity - train capacity
     * @throws ObjectExistException - if train already exist in data base
     * @throws ServerError
     */
    public void createTrain(String name, Integer capacity) throws ObjectExistException, ServerError
    {
        trainDao = new TrainDao();
        try {
            if(trainDao.find(name) == null)
            {
                trainDao.getEt().begin();
                trainDao.create(new Train(name, capacity));
                trainDao.getEt().commit();
                log.info("Train '" + name + "' (Created)");
            }
            else throw new ObjectExistException();
        }
        catch (RollbackException e)
        {
            if(trainDao.getEt().isActive()) trainDao.getEt().rollback();
            throw new ServerError();

        }
    }

    /**
     *
     * @param trainName
     * @param routeName
     * @param date - departure date
     * @throws ObjectExistException - if trip is already exist
     * @throws ServerError
     */
    public void createTrip(String trainName, String routeName, Date date) throws ObjectExistException, ServerError
    {
        TripDao tripDao = new TripDao();
        trainDao = new TrainDao();
        try
        {
            if(tripDao.findTripPK(trainName, routeName, date.toString()) == null) {
                Train train = trainDao.find(trainName);
                route = routeDao.find(routeName);
                tripDao.getEt().begin();
                tripDao.create(date, train.getId(), route.getId());
                tripDao.getEt().commit();
                log.info("Trip '" + trainName + "' (Created)");
            }
            else throw  new ObjectExistException();
        }
        catch (RollbackException e)
        {
            if(tripDao.getEt().isActive()) tripDao.getEt().rollback();
            throw new ServerError();
        }
    }

    /**
     * get all trains
     * @return - list of schedule
     */
    public List<Object[]> loadAllTrains()
    {
        scheduleDao = new ScheduleDao();
        try {
            log.info("Send list with all trains to employee.");
            return scheduleDao.loadAllTrains();


        } catch (IndexOutOfBoundsException e) {
            List<Object[]> list = new ArrayList<Object[]>();
            Object[] objects = {null, null, null};
            list.add(objects);
            return list;
        }
    }

    /**
     * get all passengers in this train
     * @param train - train name
     * @return list of passengers
     */
    public List<Object[]> loadAllPassengers(String train)
    {
        PersonDao personDao = new PersonDao();
        try {
            log.info("Send list with all passengers to employee.");
            return personDao.loadAllPassengers(train);

        } catch (IndexOutOfBoundsException e) {
            List<Object[]> list = new ArrayList<Object[]>();
            Object[] objects = {null, null, null, null};
            list.add(objects);
            return list;
        }
    }
}
