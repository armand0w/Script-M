package com.arm.museos;

import com.arm.controller.MuseoControl;
import com.arm.model.Direccion;
import com.arm.model.Museo;
import com.arm.util.Utilities;
import com.arm.util.WSConsumer;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;

/**
 * Created by ACsatillo on 11/02/2016.
 */

public class MuseosCDMX {

    private static String key = "AIzaSyBXkD485Luv8zOVWRVTGXFv0eLGsBNtQhQ";
    /** Radar URL **/
    //https://maps.googleapis.com/maps/api/place/radarsearch/json?location=19.4417788,-99.2036764&radius=25000&types=museum&key=
    private static String urlPlaces = "http://localhost/museums/places.json";
    /** Place Detail **/
    //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJScjIILQB0oURJMVub-MaI4Q&key=
    private static String urlPlaceById = "https://maps.googleapis.com/maps/api/place/details/json?key="+key+"&language=es&placeid=";

    public static void main(String[] args){
        String json = WSConsumer.consumeURL(urlPlaces);
        if( json.length()>0 && JsonPath.read(json, "$.status").equals("OK") ) {
            JSONArray ja = JsonPath.read(json, "$.results");
            if( ja.size()>0 ){
                Museo museo = null;
                for(int i=0; i<ja.size(); i++) {
                    String id = JsonPath.read(ja.get(i), "$.place_id");
                    if( id.length()>0 && id.length()<28 ){
                        String jsid = WSConsumer.consumeURL(urlPlaceById + id);
                        if( jsid.length()>0 && JsonPath.read(jsid, "$.status").equals("OK") ){
                            try {
                                museo = new Museo(
                                        getString(jsid, "$result.id"), //id
                                        getString(jsid, "$result.place_id"), //place_id
                                        getString(jsid, "$result.name"), //nombre
                                        getString(jsid, "$result.rating"), //rating
                                        getString(jsid, "$result.scope"), //scope
                                        getString(jsid, "$result.international_phone_number"), //phone
                                        getString(jsid, "$result.icon"), //icon
                                        getString(jsid, "$result.opening_hours.weekday_text"), //weekday
                                        getString(jsid, "$result.website"), //website
                                        new Direccion(
                                                getString(jsid, "$result.place_id"), //place_id
                                                getString(jsid, "$result.geometry.location.lat"), //latitud
                                                getString(jsid, "$result.geometry.location.lng"), //longitud
                                                getString(jsid, "$result.formatted_address"), //direccion
                                                getString(jsid, "$result.vicinity"), //vicinity
                                                getString(jsid, "$result.address_components.[0].long_name"), //calle
                                                getString(jsid, "$result.address_components.[1].long_name"), //colonia
                                                getString(jsid, "$result.address_components.[2].long_name"), //delegacion
                                                getString(jsid, "$result.address_components.[3].long_name"), //ciudad
                                                getString(jsid, "$result.address_components.[4].long_name"), //estado
                                                getString(jsid, "$result.address_components.[5].long_name"), //pais
                                                getString(jsid, "$result.address_components.[6].long_name"), //cp
                                                getString(jsid, "$result.url")  //maps_url
                                        )
                                );
                                //arreglar Mus_weekday_text
                                if(museo.getMus_weekday_text().length()>5)
                                    museo.setMus_weekday_text( museo.getMus_weekday_text().substring(1, museo.getMus_weekday_text().length()-1).replaceAll("\"", "").replaceAll("\u2013", "-") );
                                //Guardar json
                                Utilities.saveDocument(jsid, museo.getMus_place_id() + "_-_" + museo.getMus_nombre() + ".json");
                            } catch (Exception e){
                                System.err.println("Ocurrio algo mal al generar Museo : " + e.getLocalizedMessage());
                                e.printStackTrace();
                            }

                            MuseoControl mc = new MuseoControl(museo);
                            System.out.println( mc.save() );
                            mc.close();

                        } else System.err.println("No se encontro lugar con id : " + id + "\nUrl : " + urlPlaceById);
                    } else System.err.println("Algo raro en id : " + id);
                }
            } else System.err.println("No se encontro resultados con : " + urlPlaces);
        } else System.err.println("Ocurrio error al consumir WS : " + urlPlaces);
    }

    protected static String getString(String json, String path){
        String ret  = "";
        try{
            ret = JsonPath.read(json, path).toString();
        } catch (PathNotFoundException e){
            ret = "";
            System.err.println("PathNotFoundException : " + path);
            System.err.println("Error : " + e.getLocalizedMessage());
        }
        return ret;
    }

}
