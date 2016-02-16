package com.arm.museos;

import com.arm.model.Direccion;
import com.arm.model.Museo;
import com.arm.util.Utilities;
import com.arm.util.WSConsumer;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Created by ACsatillo on 11/02/2016.
 */
public class MuseosCDMX {

    private static String key = "AIzaSyBXkD485Luv8zOVWRVTGXFv0eLGsBNtQhQ";
    private static String urlPlaces = "http://localhost/museums/places.json";
    private static String urlPlaceById = "http://localhost/museums/place_id.json";

    public static void main(String[] args){
        String json = WSConsumer.consumeURL(urlPlaces);
        if( JsonPath.read(json, "$.status").equals("OK") ) {
            JSONArray ja = JsonPath.read(json, "$.results");
            if( ja.size()>0 ){
                Museo museo = null;
                for(int i=0; i<ja.size(); i++) {
                    String id = JsonPath.read(ja.get(i), "$.place_id");
                    if( id.length()>0 && id.length()<28 ){
                        String jsid = WSConsumer.consumeURL(urlPlaceById);
                        if( JsonPath.read(jsid, "$.status").equals("OK") ){
                            JSONObject place = JsonPath.read(jsid, "$.result");
                            try {
                                museo = new Museo(
                                        JsonPath.read(place, "$.id"), //id
                                        JsonPath.read(place, "$.place_id"), //place_id
                                        JsonPath.read(place, "$.name"), //nombre
                                        JsonPath.read(place, "$.rating").toString(), //rating
                                        JsonPath.read(place, "$.scope"), //scope
                                        JsonPath.read(place, "$.international_phone_number"), //phone
                                        JsonPath.read(place, "$.icon"), //icon
                                        JsonPath.read(place, "$.opening_hours.weekday_text").toString(), //weekday
                                        JsonPath.read(place, "$.website"), //website
                                        new Direccion(
                                                JsonPath.read(place, "$.place_id"), //place_id
                                                JsonPath.read(place, "$.geometry.location.lat").toString(), //latitud
                                                JsonPath.read(place, "$.geometry.location.lng").toString(), //longitud
                                                JsonPath.read(place, "$.formatted_address"), //direccion
                                                JsonPath.read(place, "$.vicinity"), //vicinity
                                                JsonPath.read(place, "$.address_components.[0].long_name"), //calle
                                                JsonPath.read(place, "$.address_components.[1].long_name"), //colonia
                                                JsonPath.read(place, "$.address_components.[2].long_name"), //delegacion
                                                JsonPath.read(place, "$.address_components.[3].long_name"), //ciudad
                                                JsonPath.read(place, "$.address_components.[4].long_name"), //estado
                                                JsonPath.read(place, "$.address_components.[5].long_name"), //pais
                                                JsonPath.read(place, "$.address_components.[6].long_name"), //cp
                                                JsonPath.read(place, "$.url")  //maps_url
                                        )
                                );
                                //arreglar Mus_weekday_text
                                museo.setMus_weekday_text( museo.getMus_weekday_text().substring(1, museo.getMus_weekday_text().length()-1).replaceAll("\"", "").replaceAll("\u2013", "-") );
                                //Guardar json
                                Utilities.saveDocument( jsid, museo.getMus_place_id() + "_-_" + museo.getMus_nombre() + ".json"  );
                            } catch (Exception e){
                                System.err.println("Ocurrio algo mal al generar Museo : " + e.getLocalizedMessage());
                            }

                            //MuseoControl mc = new MuseoControl(museo);
                            //System.out.println( mc.save() );

                        } else System.err.println("No se encontro lugar con id : " + id + "\nUrl : " + urlPlaceById);
                    } else System.err.println("Algo raro en id : " + id);
                }
            } else System.err.println("No se encontro resultados con : " + urlPlaces);
        } else System.err.println("Ocurrio error al consumir WS : " + urlPlaces);
    }

}
