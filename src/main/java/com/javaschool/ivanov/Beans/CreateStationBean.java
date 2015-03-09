package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Exception.ServerError;
import com.javaschool.ivanov.Service.EmployeeService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class CreateStationBean implements Serializable{

    private String station;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void createStation()
    {
        EmployeeService employeeService = new EmployeeService();
        try
        {
            employeeService.createStation(station);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Станция добавлена!", ""));
        }
        catch (ObjectExistException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Маршрут уже существует.", ""));
        }
        catch (ServerError e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_ERROR, "Проблемы с сервером! Свяжитесь с администратором.", ""));
        }
    }
}
