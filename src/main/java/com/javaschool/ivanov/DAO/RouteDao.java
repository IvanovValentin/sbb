package com.javaschool.ivanov.DAO;


import com.javaschool.ivanov.Domain.Route;

public class RouteDao extends GenericDaoJpaImpl<Route, Integer> {
    public RouteDao() {
        super(Route.class);
    }
}
