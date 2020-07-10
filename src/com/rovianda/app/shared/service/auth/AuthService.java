package com.rovianda.app.shared.service.auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService {

    private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    //https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword
    private static final String OPERATION_AUTH = "verifyPassword";
    private static final String OPERATION_REFRESH_TOKEN = "token";
    private static final String OPERATION_ACCOUNT_INFO = "getAccountInfo";

    private String firebaseKey;

    private static AuthService instance = null;

    protected AuthService(){
        firebaseKey = "AIzaSyDaoKnC-MSM0b069pawJ5KI1eWlbmng99o";
    }

    public static AuthService getInstance() {
        if(instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public void  authWithEmailAndPassword(String email, String password){
        HttpURLConnection urlRequest = null;
        String token = null;
        try {
            URL url = new URL(BASE_URL+"?key="+firebaseKey);
            urlRequest = (HttpURLConnection) url.openConnection();
            urlRequest.setDoOutput(true);
            urlRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStream os = urlRequest.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write("{\"email\":\""+email+"\",\"password\":\""+password+"\",\"returnSecureToken\":true}");
            osw.flush();
            osw.close();
            urlRequest.connect();
            System.out.println(urlRequest.getInputStream());
            JsonParser jp = new JsonParser();
            System.out.println(urlRequest.getResponseMessage());
            JsonElement root = jp.parse(new InputStreamReader((InputStream) urlRequest.getContent()));
            JsonObject rootobj = root.getAsJsonObject();

            token = rootobj.get("idToken").getAsString();
            System.out.println(token);

        }catch (Exception e ){
           e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        AuthService oAuth = AuthService.getInstance();
        oAuth.authWithEmailAndPassword("proceso@yopmail.co","proceso");
    }

}
