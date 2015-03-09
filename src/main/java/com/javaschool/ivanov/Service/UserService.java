package com.javaschool.ivanov.Service;


import com.javaschool.ivanov.DAO.UserDao;
import com.javaschool.ivanov.Domain.User;
import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Exception.ServerError;
import org.apache.log4j.Logger;

import javax.persistence.RollbackException;

public class UserService {

    private final static Logger log = Logger.getLogger(UserService.class);
    private static final int CUSTOMER_STATUS = 1;
    private static final int EMPLOYEE_STATUS = 2;

    UserDao userDao;


    /**
     * create user in data base
     * @param login - user login
     * @param password - user password
     * @throws ObjectExistException - if this user already exist in data base
     * @throws ServerError
     */
    public void registration(String login, String password) throws ObjectExistException, ServerError
    {
        log.info(login+" Start 'registration' method.");
        userDao = new UserDao();
        try {
            if (userDao.find(login) == null) {
                userDao.getEt().begin();
                userDao.create(new User(login, password, CUSTOMER_STATUS));
                userDao.getEt().commit();
            }
            else throw new ObjectExistException();
        }
        catch (RollbackException e)
        {
            if(userDao.getEt().isActive()) userDao.getEt().rollback();
            throw new ServerError();
        }

    }

    /**
     *
     * @param login - user login
     * @param password - user password
     * @return - access level
     * @throws IncorrectDataException
     */
    public int authorization(String login, String password) throws IncorrectDataException
    {
        userDao = new UserDao();
        log.info(login+" Start 'authorization' method.");
            User user = userDao.find(login);
        if(user != null) {
            if (password.equals(user.getPassword())) {
                if (user.getAccessLevel() == EMPLOYEE_STATUS) {
                    return EMPLOYEE_STATUS;
                } else return CUSTOMER_STATUS;
            } else throw new IncorrectDataException();

        }
        else throw new IncorrectDataException();
    }
}
