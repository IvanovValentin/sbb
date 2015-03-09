package com.javaschool.ivanov.DAO;

import com.javaschool.ivanov.Domain.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;


public class UserDao extends GenericDaoJpaImpl<User, Integer> {

    public UserDao() {
        super(User.class);
    }


    /**
     * get user entity by login
     * @param login - login name
     * @return user entity
     */
    public User find(String login)
    {
        try {
            Query query = getEm().createQuery("select u from User u where u.login =:login");
            query.setParameter("login", login);
            User user = (User) query.getSingleResult();
            return user;
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

}
