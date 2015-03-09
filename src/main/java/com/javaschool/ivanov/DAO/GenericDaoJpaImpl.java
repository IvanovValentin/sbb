package com.javaschool.ivanov.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;


public class GenericDaoJpaImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected EntityManager em = EmfInit.getEm();
    protected EntityTransaction et = EmfInit.getEt();
    private final Class<T> type;

    public GenericDaoJpaImpl(Class<T> type){
        this.type = type;
    }

    public EntityManager getEm() {
        return em;
    }

    public EntityTransaction getEt() {
        return et;
    }

    /**
     *
     * @return type entity
     */
    public Class<T> getType() {
        return type;
    }

    /**
     *
     * @param t - entity
     * @return entity
     */
    @Override
    public T create(final T t){
        getEm().persist(t);
        return t;
    }

    /**
     *
     * @param key - Primary key
     * @return entity
     */
    @Override
    public T find(final PK key){
        T t = getEm().find(getType(), key);
        return t;
    }

    /**
     *
     * @return list entity
     */
    @Override
    public List<T> findAll(){
        List<T> list = getEm().createQuery("select x from " + getType().getSimpleName() + " x").getResultList();
        return list;
    }

    /**
     * method for route, train, station entity
     * @param name - name field entity
     * @return entity
     */
    @Override
    public T find(String name) {
        try {
            Query query = getEm().createQuery("select x from " + getType().getSimpleName() + " x where x.name =:name");
            query.setParameter("name", name);
            T t = (T) query.getSingleResult();
            return t;
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}