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
    private static String urlPlaces = "https://maps.googleapis.com/maps/api/place/radarsearch/json?location=19.447880,-99.152853&radius=25000&types=museum&key="+key;
    /** Place Detail **/
    //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJScjIILQB0oURJMVub-MaI4Q&key=
    private static String urlPlaceById = "https://maps.googleapis.com/maps/api/place/details/json?key="+key+"&language=es&placeid=";
    //private static String urlPlaceById = "http://localhost/museums/docs/ChIJw_w-OTP50YURNf4xcyDBE3M_-_MuseoTutankam%C3%B3n-Mexico.json";

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
                                                getAddressComponent(jsid, "route"), //calle
                                                getAddressComponent(jsid, "neighborhood"), //colonia
                                                getAddressComponent(jsid, "administrative_area_level_3"), //delegacion
                                                getAddressComponent(jsid, "locality"), //ciudad
                                                getAddressComponent(jsid, "administrative_area_level_1"), //estado
                                                getAddressComponent(jsid, "country"), //pais
                                                getAddressComponent(jsid, "postal_code"), //cp
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

                        } else {
                            String status = getString(jsid, "$.status");
                            if( status.equals("OVER_QUERY_LIMIT") ){
                                System.err.println("Llego al limite de querys en WS");
                                System.exit(0);
                            } else System.err.println("No se encontro lugar con Url : " + urlPlaceById + id);
                        }
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
            if(!path.contains("opening_hours") && !path.contains("address_components"))
                System.err.println("Error : " + path);
        } catch (NullPointerException nul){
            ret = "";
        }
        return ret;
    }

    /**
     * calle		route
     * colonia		neighborhood
     * delegacion	sublocality_level_1/administrative_area_level_3
     * ciudad		locality
     * estado		administrative_area_level_1
     * pais	        country
     * cp			postal_code
     */
    protected static String getAddressComponent(String json, String component){
        String comp = "";
        JSONArray ja = null;
        try{
            ja = JsonPath.read(json, "$result.address_components");
            for( Object j: ja ){
                if( JsonPath.read(j, "$.types")!=null && JsonPath.read(j, "$.types").toString().contains(component) ){
                    comp = JsonPath.read(j, "$.long_name");
                }
            }
        } catch (PathNotFoundException | NullPointerException e){
            comp = "";
        } catch (Exception e){
            comp = "";
            System.err.println("========= No existe adress component ========");
        }
        return comp;
    }

}
