package com.rovianda.utility.ConnectService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class HttpClient {
    static String authorization = "";

    private static String URL = "https://us-central1-rovianda-88249.cloudfunctions.net/app/rovianda/";

    private static Client client = ClientBuilder.newClient();


    public static Response get(String path){
        Response response = client.target(URL+path).request().
                header(HttpHeaders.AUTHORIZATION,authorization).get();
        return  response;
    }

    public static Response put(String path, Object body){
        Response response = client.target(URL+path).request().
                header(HttpHeaders.AUTHORIZATION,authorization).put(Entity.json(body));
        response.close();
        return response;
    }

    public static Response post (String path, Object body){
        Response response = client.target(URL+path).request().
                header(HttpHeaders.AUTHORIZATION,authorization).post(Entity.json(body));
        response.close();
        return response;
    }

}
