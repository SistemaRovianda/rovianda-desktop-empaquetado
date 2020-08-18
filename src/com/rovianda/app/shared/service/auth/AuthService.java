package com.rovianda.app.shared.service.auth;

import com.rovianda.app.shared.models.User;
import com.rovianda.utility.ConnectService.FirebaseAuth;
import com.rovianda.utility.ConnectService.HttpClient;
import com.rovianda.utility.ConnectService.Models.AuthFirebase;

import javax.ws.rs.core.Response;

public class AuthService {
    private static String uiid;
    public static  boolean singIn(String email,String password) throws Exception{
        AuthFirebase auth = new AuthFirebase();
        auth.setEmail(email);
        auth.setPassword(password);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uiid = firebaseAuth.authWithEmailAndPassword(auth);
        return uiid != null || uiid != "";
    }

    public static boolean getInfoUser () throws Exception {
        Response response = HttpClient.get("user/"+uiid);
       if(response.getStatus() == 200){
            User.initialInstance(response.readEntity(User.class));
            if(!User.getInstance().getRol().equals("PACKAGING"))
                throw new Exception("Usuario no válido");
        }else{
            throw  new Exception("Error al iniciar sesión intente mas tarde");
        }
        response.close();
     return true;
    }

    public static void SignOutSession(){
        uiid = null;
        User.initialInstance(null);
        FirebaseAuth.getInstance().SigOutFirebase();
    }

}
