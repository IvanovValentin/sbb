package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Service.EmployeeService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean
public class ViewAllTrainsBean implements Serializable{

    private List<Object[]> listTrains;

    @PostConstruct
    public void init(){
        EmployeeService employeeService = new EmployeeService();
        listTrains = employeeService.loadAllTrains();
    }

    public List<Object[]> getListTrains() {
        return listTrains;
    }

    public void setListTrains(List<Object[]> listTrains) {
        this.listTrains = listTrains;
    }
}
