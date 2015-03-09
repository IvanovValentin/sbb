package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Exception.ServerError;
import com.javaschool.ivanov.Security.MD5;
import com.javaschool.ivanov.Service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class RegistrationBean implements Serializable{
    private String login;
    private String password1;
    private String password2;
    private String message;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = MD5.getHash(password1);
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String registration()
    {
        if(!password1.equals(password2))
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Пароли не совпадают!", ""));
            return "registration_page.xhtml?faces-redirect=true";
        }

        UserService userService = new UserService();
        try {
            userService.registration(login, password1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Спасибо за регистрацию! Авторизируейтусь для доступа на сайт.", ""));
            return "index_page.xhtml?faces-redirect=true";
        }
        catch (ObjectExistException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Данный пользователь уже зарегистрирован.", ""));
            return "registration_page.xhtml?faces-redirect=true";
        }
        catch (ServerError e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_ERROR, "Проблемы с сервером! Свяжитесь с администратором.", ""));
            return "registration_page.xhtml?faces-redirect=true";
        }

    }
}
