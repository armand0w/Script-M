package com.arm.controller;

import com.arm.model.Museo;
import com.arm.util.HSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.DataException;

/**
 * Created by ACsatillo on 15/02/2016.
 */
public class MuseoControl implements Controller{
    private Museo museo = null;
    private Session session = null;

    public Museo getMuseo() { return museo; }
    public void setMuseo(Museo museo) { this.museo = museo; }

    public MuseoControl(){
        session = HSession.getSessionFactory().openSession();
        session.beginTransaction();
    }

    public MuseoControl(Museo m){
        this.museo = m;
        session = HSession.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @Override
    public boolean exist() {
        boolean exist = false;
        Query q = session.createQuery("FROM Museo WHERE mus_id = :mus_id");
        q.setParameter("mus_id", this.museo.getMus_id());
        if( q.list().size() > 0 ) exist = true;
        return exist;
    }

    @Override
    public String save() {
        String ret = "";
        if( !exist() ) {
            try {
            session.save(this.museo);
            session.getTransaction().commit();
            ret = "{ \"mensaje\" : \""+this.museo.getMus_nombre()+" guardado con exito\"}";
            DirecControl dir = new DirecControl(this.museo.getDireccion());
            System.out.println( dir.save() );
            dir.close();
            } catch (DataException de){
                System.err.println("HSQL : " + de.getLocalizedMessage());
                System.err.println("Museo : " + this.museo.toString());
            }
        } else ret = "{ \"mensaje\" : \""+this.museo.getMus_nombre()+" ya existe\"}";
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
