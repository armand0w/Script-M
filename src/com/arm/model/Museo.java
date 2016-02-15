package com.arm.model;

/**
 * Created by ACsatillo on 11/02/2016.
 */
public class Museo {
    private String mus_id = "";
    private String mus_place_id = "";
    private String mus_nombre = "";
    private String mus_rating = "";
    private String mus_scope = "";
    private String mus_phone_number = "";
    private String mus_icon = "";
    private String mus_weekday_text = "";
    private String mus_website = "";
    private Direccion mus_direccion = null;

    public Museo(){}

    public Museo(String mus_id, String mus_place_id, String mus_nombre, String mus_rating, String mus_scope, String mus_phone_number, String mus_icon, String mus_weekday_text, String mus_website, Direccion direccion) {
        this.mus_id = mus_id;
        this.mus_place_id = mus_place_id;
        this.mus_nombre = mus_nombre;
        this.mus_rating = mus_rating;
        this.mus_scope = mus_scope;
        this.mus_phone_number = mus_phone_number;
        this.mus_icon = mus_icon;
        this.mus_weekday_text = mus_weekday_text;
        this.mus_website = mus_website;
        this.mus_direccion = direccion;
    }

    public String getMus_id() { return mus_id; }

    public void setMus_id(String mus_id) { this.mus_id = mus_id; }

    public String getMus_place_id() { return mus_place_id; }

    public void setMus_place_id(String mus_place_id) { this.mus_place_id = mus_place_id; }

    public String getMus_nombre() { return mus_nombre; }

    public void setMus_nombre(String mus_nombre) { this.mus_nombre = mus_nombre; }

    public String getMus_rating() { return mus_rating; }

    public void setMus_rating(String mus_rating) { this.mus_rating = mus_rating; }

    public String getMus_scope() { return mus_scope; }

    public void setMus_scope(String mus_scope) { this.mus_scope = mus_scope; }

    public String getMus_phone_number() { return mus_phone_number; }

    public void setMus_phone_number(String mus_phone_number) { this.mus_phone_number = mus_phone_number; }

    public String getMus_icon() { return mus_icon; }

    public void setMus_icon(String mus_icon) { this.mus_icon = mus_icon; }

    public String getMus_weekday_text() { return mus_weekday_text; }

    public void setMus_weekday_text(String mus_weekday_text) { this.mus_weekday_text = mus_weekday_text; }

    public String getMus_website() { return mus_website; }

    public void setMus_website(String mus_website) { this.mus_website = mus_website; }

    public Direccion getDireccion() { return mus_direccion; }

    public void setDireccion(Direccion direccion) { this.mus_direccion = direccion; }

    @Override
    public String toString() {
        return "Museo{" +
                "mus_id='" + mus_id + '\'' +
                ", mus_place_id='" + mus_place_id + '\'' +
                ", mus_nombre='" + mus_nombre + '\'' +
                ", mus_rating='" + mus_rating + '\'' +
                ", mus_scope='" + mus_scope + '\'' +
                ", mus_phone_number='" + mus_phone_number + '\'' +
                ", mus_icon='" + mus_icon + '\'' +
                ", mus_weekday_text='" + mus_weekday_text + '\'' +
                ", mus_website='" + mus_website + '\'' +
                ", direccion=" + mus_direccion.toString() +
                '}';
    }
}
