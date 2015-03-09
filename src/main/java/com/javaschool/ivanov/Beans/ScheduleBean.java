package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Service.CustomerService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean
public class ScheduleBean implements Serializable {


    private String station;
    private List<Object[]> schedule;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<Object[]> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Object[]> schedule) {
        this.schedule = schedule;
    }

    public void loadSchedule(){
        CustomerService customerService = new CustomerService();
        schedule = customerService.loadSchedule(station);
    }
}