package com.javaschool.ivanov.DAO;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmfInit {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sbb");

    private static EntityManager em = emf.createEntityManager();

    private static EntityTransaction et = em.getTransaction();

    public static EntityManager getEm() {
        return em;
    }

    public static EntityTransaction getEt() {
        return et;
    }
}