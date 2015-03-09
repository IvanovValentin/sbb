package com.javaschool.ivanov.Beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean implements Serializable{

    private String login;
    private int acceessLevel=0;

    public int getAcceessLevel() {
        return acceessLevel;
    }

    public void setAcceessLevel(int acceessLevel) {
        this.acceessLevel = acceessLevel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
