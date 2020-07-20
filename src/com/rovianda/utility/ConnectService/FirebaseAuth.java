package com.rovianda.utility.ConnectService;

import com.rovianda.utility.ConnectService.Models.AuthFirebase;
import com.rovianda.utility.ConnectService.Models.AuthToken;
import com.rovianda.utility.ConnectService.Models.ResponseError;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class FirebaseAuth {

    private static String URL_BASE = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    private String firebaseKey;
    private static Client client = ClientBuilder.newClient();

    private static FirebaseAuth instance = null;
    private FirebaseAuth () {
       firebaseKey = "AIzaSyDaoKnC-MSM0b069pawJ5KI1eWlbmng99o";
    }

    public static FirebaseAuth getInstance(){
        if(instance == null){
            instance = new FirebaseAuth();
        }
        return  instance;
    }

    public String  authWithEmailAndPassword (AuthFirebase authFirebase) throws Exception {
        String result ="";
        Response response = client.target(URL_BASE+"?key="+firebaseKey).request().post(Entity.json(authFirebase));
        if (response.getStatus() == 200){
            AuthToken authToken = response.readEntity(AuthToken.class);
            response.close();
            HttpClient.authorization = authToken.getIdToken();
            result = authToken.getLocalId();
        }else {
            throw  new Exception(response.readEntity(ResponseError.class).error.getMessage());
        }
        return  result;
    }

    public void SigOutFirebase(){
        HttpClient.authorization= null;
    }

}
