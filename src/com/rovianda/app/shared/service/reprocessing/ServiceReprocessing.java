package com.rovianda.app.shared.service.reprocessing;
import com.rovianda.app.shared.models.Reprocessing;
import com.rovianda.app.shared.models.ResponseReprocessing;
import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.Response;
public class ServiceReprocessing {
    public static String registerReprocessing(Reprocessing reprocessing)throws Exception{
        String idReport = "";
        Response response = HttpClient.post("/packaging/reprocessing",reprocessing);
        if(response.getStatus() ==200)
            idReport = response.readEntity(ResponseReprocessing.class).getReprocesingId();
        if(response.getStatus()==404){
            throw new Exception("Servicio no disponible");
        }else if (response.getStatus()==400) {
            throw new Exception("Error al registrar el reproceso");
        }
        response.close();
        return idReport;
    }
}
