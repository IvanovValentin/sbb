package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Service.EmployeeService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ViewScoped
@ManagedBean
public class ViewAllPassengersBean {

    private String train;

    private List<Object[]> listPassenges;

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public List<Object[]> getListPassenges() {
        return listPassenges;
    }

    public void setListPassenges(List<Object[]> listPassenges) {
        this.listPassenges = listPassenges;
    }

    public void loadPassengers()
    {
        EmployeeService employeeService = new EmployeeService();
        listPassenges = employeeService.loadAllPassengers(train);
    }
}
