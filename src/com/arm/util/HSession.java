package com.arm.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.File;

/**
 * Created by ACsatillo on 15/02/2016.
 */
public class HSession {
    private static final SessionFactory sessionFactory;
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure(new File("hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Falla en creacion de SessionFactory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
