package com.rovianda.utility.ConnectService;

import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HttpClient {
    static String authorization = "";

    private static String URL ="https://us-central1-sistema-rovianda.cloudfunctions.net/app/rovianda";//"http://localhost:5001/sistema-rovianda/us-central1/app/rovianda";//

    private static String URLFake ="https://b60885fe-bfd1-44cb-b324-dda60656ed34.mock.pstmn.io";

    private static Client client = ClientBuilder.newClient();

    private static WebTarget webTarget = client.target(URL);


    public static Response get(String path){
        Response response = client.target(URL+path).request().
                header(HttpHeaders.AUTHORIZATION,authorization).get();
        response.bufferEntity();
        return  response;
    }

    public static Response patch(String path, Object body){
        WebTarget patch = webTarget.path(path);
        Invocation.Builder ib = patch.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,authorization);
        Response response;
        response = ib.put(Entity.entity(body, MediaType.APPLICATION_JSON));
        return response;
    }

    public static Response putWithoutBody(String path){
        Response response = client.target(URL+path).request().put(Entity.json(""));
        return response;
    }

    public static Response putWithBody(String path,Object body){
        WebTarget put = webTarget.path(path);
        Invocation.Builder ib = put.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,authorization);
        Response response = ib.put(Entity.json(body));
        response.bufferEntity();
        return response;
    }

    public static Response post(String path, Object body){
        WebTarget post = webTarget.path(path);
        Invocation.Builder ib = post.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,authorization);
        Response response = ib.post(Entity.json(body));
        response.bufferEntity();
        return response;
    }
}
