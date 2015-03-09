package com.javaschool.ivanov.DAO;


import com.javaschool.ivanov.DTO.BuyTicketInfo;
import com.javaschool.ivanov.Domain.Ticket;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class TicketDao extends GenericDaoJpaImpl<Ticket, Integer> {

    public TicketDao() {
        super(Ticket.class);
    }

    /**
     * check passenger on trip
     * @param firstName - passenger first name
     * @param lastName - passenger last name
     * @param birthday - passenger day of birth
     * @param tripId - trip entity primary key
     * @return true, if passenger already exist on trip, another false
     */
    public boolean isExistPassenger(String firstName, String lastName, Date birthday, int tripId)
    {
        try {
            Query query = getEm().createNativeQuery("select ticket.id \n" +
                    "from ticket " +
                    "join person on person.id = ticket.person_id " +
                    "where person.firstname = ? " +
                    "and person.lastname = ? " +
                    "and person.birthday = ? " +
                    "and ticket.trip_id = ? ");
            query.setParameter(1, firstName);
            query.setParameter(2, lastName);
            query.setParameter(3, birthday);
            query.setParameter(4, tripId);

            query.getSingleResult();
            return true;

        }
        catch (NoResultException e)
        {
            return false;
        }
    }

    /**
     *  check free places on trip
     * @param info - client request params
     * @return true, if train is full, another false
     */
    public boolean  isTrainFull(BuyTicketInfo info)
    {
        try {
            Query query = getEm().createNativeQuery("select (train.capacity - COUNT(ticket.id)) " +
                    "from ticket " +
                    "join trip on trip.id = ticket.trip_id " +
                    "join train on train.id = trip.train_id " +
                    "join route on route.id = trip.route_id " +
                    "where train.name = ? " +
                    "and route.name = ? " +
                    "and trip.departure = ? ");
            query.setParameter(1, info.getTrain());
            query.setParameter(2, info.getRoute());
            query.setParameter(3, info.getDate());
            BigInteger count = (BigInteger) query.getSingleResult();
            if (count.equals(BigInteger.valueOf(0))) return true;
            else return false;
        }
        catch (NoResultException e)
        {
            return false;
        }
    }

    /**
     * create ticket
     * @param personId - person entity primary key
     * @param tripId - trip entity primary key
     * @param stationFromId - departure station entity primary key
     * @param stationToId - arrival station entity primary key
     */
    public void create(int personId, int tripId, int stationFromId, int stationToId)
    {
        Query query = getEm().createNativeQuery("insert into ticket(person_id, trip_id, " +
                "station_from, station_to) value (?, ?, ?, ?)");
        query.setParameter(1, personId);
        query.setParameter(2, tripId);
        query.setParameter(3, stationFromId);
        query.setParameter(4, stationToId);

        query.executeUpdate();
    }

}
