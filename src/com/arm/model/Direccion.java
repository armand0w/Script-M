package com.arm.model;

/**
 * Created by ACsatillo on 11/02/2016.
 */
public class Direccion {
    private String ubn_mus_place_id = "";
    private String ubn_latitud = "";
    private String ubn_longitud = "";
    private String ubn_direccion = "";
    private String ubn_vicinity = "";
    private String ubn_calle = "";
    private String ubn_colonia = "";
    private String ubn_delegacion = "";
    private String ubn_ciudad = "";
    private String ubn_estado = "";
    private String ubn_pais = "";
    private String ubn_cp = "";
    private String ubn_maps_url = "";

    public Direccion(){}

    public Direccion(String ubn_mus_place_id, String ubn_latitud, String ubn_longitud, String ubn_direccion, String ubn_vicinity, String ubn_calle, String ubn_colonia, String ubn_delegacion, String ubn_ciudad, String ubn_estado, String ubn_pais, String ubn_cp, String ubn_maps_url) {
        this.ubn_mus_place_id = ubn_mus_place_id;
        this.ubn_latitud = ubn_latitud;
        this.ubn_longitud = ubn_longitud;
        this.ubn_direccion = ubn_direccion;
        this.ubn_vicinity = ubn_vicinity;
        this.ubn_calle = ubn_calle;
        this.ubn_colonia = ubn_colonia;
        this.ubn_delegacion = ubn_delegacion;
        this.ubn_ciudad = ubn_ciudad;
        this.ubn_estado = ubn_estado;
        this.ubn_pais = ubn_pais;
        this.ubn_cp = ubn_cp;
        this.ubn_maps_url = ubn_maps_url;
    }

    public String getUbn_mus_place_id() {
        return ubn_mus_place_id;
    }

    public void setUbn_mus_place_id(String ubn_mus_place_id) {
        this.ubn_mus_place_id = ubn_mus_place_id;
    }

    public String getUbn_latitud() {
        return ubn_latitud;
    }

    public void setUbn_latitud(String ubn_latitud) {
        this.ubn_latitud = ubn_latitud;
    }

    public String getUbn_longitud() {
        return ubn_longitud;
    }

    public void setUbn_longitud(String ubn_longitud) {
        this.ubn_longitud = ubn_longitud;
    }

    public String getUbn_direccion() {
        return ubn_direccion;
    }

    public void setUbn_direccion(String ubn_direccion) {
        this.ubn_direccion = ubn_direccion;
    }

    public String getUbn_vicinity() {
        return ubn_vicinity;
    }

    public void setUbn_vicinity(String ubn_vicinity) {
        this.ubn_vicinity = ubn_vicinity;
    }

    public String getUbn_calle() {
        return ubn_calle;
    }

    public void setUbn_calle(String ubn_calle) {
        this.ubn_calle = ubn_calle;
    }

    public String getUbn_colonia() {
        return ubn_colonia;
    }

    public void setUbn_colonia(String ubn_colonia) {
        this.ubn_colonia = ubn_colonia;
    }

    public String getUbn_delegacion() {
        return ubn_delegacion;
    }

    public void setUbn_delegacion(String ubn_delegacion) {
        this.ubn_delegacion = ubn_delegacion;
    }

    public String getUbn_ciudad() {
        return ubn_ciudad;
    }

    public void setUbn_ciudad(String ubn_ciudad) {
        this.ubn_ciudad = ubn_ciudad;
    }

    public String getUbn_estado() {
        return ubn_estado;
    }

    public void setUbn_estado(String ubn_estado) {
        this.ubn_estado = ubn_estado;
    }

    public String getUbn_pais() {
        return ubn_pais;
    }

    public void setUbn_pais(String ubn_pais) {
        this.ubn_pais = ubn_pais;
    }

    public String getUbn_cp() {
        return ubn_cp;
    }

    public void setUbn_cp(String ubn_cp) {
        this.ubn_cp = ubn_cp;
    }

    public String getUbn_maps_url() {
        return ubn_maps_url;
    }

    public void setUbn_maps_url(String ubn_maps_url) {
        this.ubn_maps_url = ubn_maps_url;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "ubn_mus_place_id='" + ubn_mus_place_id + '\'' +
                ", ubn_latitud='" + ubn_latitud + '\'' +
                ", ubn_longitud='" + ubn_longitud + '\'' +
                ", ubn_direccion='" + ubn_direccion + '\'' +
                ", ubn_vicinity='" + ubn_vicinity + '\'' +
                ", ubn_calle='" + ubn_calle + '\'' +
                ", ubn_colonia='" + ubn_colonia + '\'' +
                ", ubn_delegacion='" + ubn_delegacion + '\'' +
                ", ubn_ciudad='" + ubn_ciudad + '\'' +
                ", ubn_estado='" + ubn_estado + '\'' +
                ", ubn_pais='" + ubn_pais + '\'' +
                ", ubn_cp='" + ubn_cp + '\'' +
                ", ubn_maps_url='" + ubn_maps_url + '\'' +
                '}';
    }
}
