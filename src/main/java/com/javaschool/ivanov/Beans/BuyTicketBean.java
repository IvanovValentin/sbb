package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.DTO.BuyTicketInfo;
import com.javaschool.ivanov.Exception.InValidTimeException;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Exception.TrainFullException;
import com.javaschool.ivanov.Service.CustomerService;
import com.javaschool.ivanov.Service.SelectOneMenu;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ViewScoped
@ManagedBean
public class BuyTicketBean implements Serializable{

    private String train;
    private String route;
    private String date;
    private String stationFrom;
    private String stationTo;
    private String firstName;
    private String lastName;
    private Date birthday;
    private List<Object> allTrains;
    private List<Object> allDates;
    private List<Object> allStationsFrom;
    private List<Object> allStationsTo;

    public String getTrain() {
        return train;
    }
    public void setTrain(String train) {
        this.train = train;
    }
    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStationFrom() {
        return stationFrom;
    }
    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }
    public String getStationTo() {
        return stationTo;
    }
    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public List<Object> getAllTrains() {
        return allTrains;
    }
    public void setAllTrains(List<Object> allTrains) {
        this.allTrains = allTrains;
    }
    public List<Object> getAllDates() {
        return allDates;
    }
    public void setAllDates(List<Object> allDates) {
        this.allDates = allDates;
    }
    public List<Object> getAllStationsFrom() {
        return allStationsFrom;
    }
    public void setAllStationsFrom(List<Object> allStationsFrom) {
        this.allStationsFrom = allStationsFrom;
    }
    public List<Object> getAllStationsTo() {
        return allStationsTo;
    }
    public void setAllStationsTo(List<Object> allStationsTo) {
        this.allStationsTo = allStationsTo;
    }

    public void trainsByRoute()
    {
        allTrains = SelectOneMenu.findTrainsByRoute(route);
    }

    public void datesByTrainAndRoute()
    {
        allDates = SelectOneMenu.findDatesByTrainAndRoute(train, route);
    }
    public void stationsFromByRoute()
    {
        allStationsFrom = SelectOneMenu.findStationsFromByRoute(route);
    }
    public void stationsToByRoute()
    {
        allStationsTo = SelectOneMenu.findStationsToByRoute(route);
    }

    public void buyTicket()
    {
        try {
            BuyTicketInfo info = new BuyTicketInfo();
            info.setFirstname(firstName);
            info.setLastname(lastName);
            info.setBirthday(birthday);
            info.setStationFrom(stationFrom);
            info.setStationTo(stationTo);
            info.setTrain(train);
            info.setRoute(route);
            info.setDate(date);
            CustomerService customerService = new CustomerService();
            customerService.buyTicket(info);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Покупка совершена успешно!", ""));
        }
        catch (TrainFullException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Нет свободных мест!", ""));
        }
        catch (InValidTimeException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Продажа билетов уже завершена!", ""));
        }
        catch (ObjectExistException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Такой человек уже зарегистрирован на этом поезде!", ""));
        }
    }
}
