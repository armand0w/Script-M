package com.arm.museos;

import com.arm.controller.MuseoControl;
import com.arm.model.Direccion;
import com.arm.model.Museo;
/*import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;*/
import com.jayway.jsonpath.JsonPath;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Created by ACsatillo on 11/02/2016.
 */
public class MuseosDB {

    private static String key = "AIzaSyBXkD485Luv8zOVWRVTGXFv0eLGsBNtQhQ";
    private static String urlPlaces = "http://localhost/museums/places.json";
    private static String urlPlaceById = "http://localhost/museums/place_id.json";

    public static void main(String[] args){
        String json = consumeURL( urlPlaces );
        if( JsonPath.read(json, "$.status").equals("OK") ) {
            JSONArray ja = JsonPath.read(json, "$.results");
            if( ja.size()>0 ){
                Museo museo = null;
                for(int i=0; i<ja.size(); i++) {
                    String id = JsonPath.read(ja.get(i), "$.place_id");
                    if( id.length()>0 && id.length()<28 ){
                        String jsid = consumeURL( urlPlaceById );
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
                                museo.setMus_weekday_text( museo.getMus_weekday_text().substring(1, museo.getMus_weekday_text().length()-1).replaceAll("\"", "").replaceAll("\u2013", "-") );
                            } catch (Exception e){
                                System.err.println("Ocurrio algo mal al generar Museo : " + e.getLocalizedMessage());
                            }

                            MuseoControl mc = new MuseoControl(museo);
                            System.out.println( mc.save() );

                        } else System.err.println("No se encontro lugar con id : " + id + "\nUrl : " + urlPlaceById);
                    } else System.err.println("Algo raro en id : " + id);
                }
            } else System.err.println("No se encontro resultados con : " + urlPlaces);
        } else System.err.println("Ocurrio error al consumir WS : " + urlPlaces);
    }

    protected static String consumeURL(String url){
        String ret = "";
        WebResource webResource = null;
        ClientResponse response = null;
        Client client = null;

        try {
            client = Client.create();
            webResource = client.resource(url);
            response = webResource
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .get(ClientResponse.class);
        } catch (Exception ex){
            System.err.println("Error : " + ex.getMessage());
        }

        if(response!=null) {
            if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()) { //200
                ret = response.getEntity(String.class);
            } else {
                System.err.println(response.toString());
                ret = "";
            }
        }

        return ret;
    }

}