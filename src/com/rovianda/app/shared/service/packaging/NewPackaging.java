package com.rovianda.app.shared.service.packaging;

import com.rovianda.app.shared.models.*;
import com.rovianda.app.shared.provider.ToastProvider;
import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.Response;
public class NewPackaging {

    public static int registerNewProductPackaging(ProductPackaging productPackaging) throws Exception {

        Response response = HttpClient.post("/packaging", productPackaging);
        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));
        PackagingId packagingId = null;
        if(response.getStatus()==201){
            packagingId = response.readEntity(PackagingId.class);
            assignUser(packagingId.getPackaging());
        }else  if(response.getStatus()==404){
            throw new Exception("Servicio no disponible");
        }else if (response.getStatus()==400) {
            throw new Exception("Error al registrar el productos");
        }else if (response.getStatus()==500){
            throw new Exception("Servicio no disponible");
        }
        //response.close();
        return (packagingId != null)?packagingId.getPackaging():0 ;
    }

    public static boolean assignUser(int order) throws Exception {
        System.out.println("registrando usuario ");
        UserId userId = new UserId();
        userId.setUserId(User.getInstance().getUuid());
        Response response = HttpClient.post("/packaging/users/"+order, userId);
        if(response.getStatus()==404){
            throw new Exception("Servicio no disponible");
        }else if (response.getStatus()==400) {
            throw new Exception("Error al asignar usuario a la orden de productos");
        }
        return true;
    }
}
