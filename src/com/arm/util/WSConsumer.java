package com.arm.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by ACsatillo on 16/02/2016.
 */
public class WSConsumer {
    public static String consumeURL(String url){
        System.out.println( url );
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
                System.err.println("Error WS : " + response.toString());
                ret = "";
            }
        }

        return ret;
    }
}
