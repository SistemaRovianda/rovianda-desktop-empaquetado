package com.rovianda.app.shared.service.returnProduct;

import com.rovianda.app.shared.models.MessageError;
import com.rovianda.app.shared.models.ProductReturn;
import com.rovianda.app.shared.models.ResponseDevolution;
import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.Response;

public class ReturnProductService {
    public static Long registerReturnProduct(ProductReturn product) throws Exception {
        Response response = HttpClient.post("packaging/devolution",product);
        ResponseDevolution id ;
        if(response.getStatus()==404){
            MessageError e = response.readEntity(MessageError.class);
            throw new Exception(e.getMsg());
        }else if (response.getStatus()==400) {
            throw new Exception("Error al registrar el reproceso");
        }
        id = response.readEntity(ResponseDevolution.class);
        response.close();
        return  id.getDevolutionId();
    }

    public static boolean closeLot(String lotId) throws Exception {
        Response response = HttpClient.putWithoutBody("oven-close/"+lotId);
        if(response.getStatus()==404){
            throw new Exception("Servicio no disponible");
        }else if (response.getStatus()==400) {
            throw new Exception("Error al cerrar el lote");
        }
        response.close();
        return  true;
    }
}
