package com.arm.controller;

import com.arm.model.Direccion;
import com.arm.util.HSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.DataException;

/**
 * Created by ACsatillo on 15/02/2016.
 */
public class DirecControl implements Controller{
    private Direccion direccion = null;
    private Session session = null;

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public DirecControl(){
        session = HSession.getSessionFactory().openSession();
        session.beginTransaction();
    }

    public  DirecControl(Direccion d){
        this.direccion = d;
        session = HSession.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @Override
    public boolean exist() {
        boolean exist = false;
        Query q = session.createQuery("FROM Direccion WHERE ubn_mus_place_id = :ubn_mus_id");
        q.setParameter("ubn_mus_id", this.direccion.getUbn_mus_place_id());
        if( q.list().size() > 0 ) exist = true;
        return exist;
    }

    @Override
    public String save() {
        String ret = "";
        if( !exist() ) {
            try {
                session.save(this.direccion);
                session.getTransaction().commit();
                ret = "{ \"mensaje\" : \"" + this.direccion.getUbn_mus_place_id() + " guardado con exito\"}";
            } catch (DataException de){
                System.err.println("HSQL : " + de.getLocalizedMessage());
                System.err.println("Direccion : " + this.direccion.toString());
            }
        } else ret = "{ \"mensaje\" : \""+this.direccion.getUbn_mus_place_id()+" ya existe\"}";
        return ret;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public String getAll() {
        return null;
    }

    @Override
    public String getById(String id) {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public void close() {
        session.close();
    }
}
