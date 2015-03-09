package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Security.MD5;
import com.javaschool.ivanov.Service.UserService;
import org.hibernate.Session;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ViewScoped
@ManagedBean(name = "authorizationBean")
public class AuthorizationBean implements Serializable {

    private String login = null;
    private String password = null;
    private int accessLevel = 0;
    private String message;
    private static final int EMPLOYEE_STATUS = 2;

    @ManagedProperty( value = "#{userBean}")
    private UserBean userBean;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5.getHash(password);
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String authorization()
    {

        UserService userService = new UserService();
        try
        {
            accessLevel = userService.authorization(login, password);
            userBean.setAcceessLevel(accessLevel);

        }
        catch (IncorrectDataException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Неверный логин и, или пароль.", ""));
            return "";
        }

        if(accessLevel == EMPLOYEE_STATUS) return  "employee_page.xhtml?faces-redirect=true";
        else return  "customer_page.xhtml?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index_page.xhtml?faces-redirect=true";
    }
}
