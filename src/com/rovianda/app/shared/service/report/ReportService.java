package com.rovianda.app.shared.service.report;

import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.Response;
import java.io.InputStream;

public class ReportService {

    public static InputStream getReport(int order) throws Exception {
        Response response = HttpClient.get("/report/packagin/"+order);
        if(response.getStatus() == 200){
            return response.readEntity(InputStream.class);
        }else if(response.getStatus()==400){
            throw new Exception("Reporte no encontrado con el id");
        }else if(response.getStatus()==404 || response.getStatus()==500){
            throw  new Exception("Servicio no disponible");
        }
        return null;
    }

    public static InputStream getReportReturns(Long productId) throws Exception {
        Response response = HttpClient.get("/packaging/devolution-report/"+productId);
        if(response.getStatus() == 200){
            return response.readEntity(InputStream.class);
        }else if(response.getStatus()==400){
            throw new Exception("Reporte no encontrado con el id");
        }else if(response.getStatus()==404 || response.getStatus()==500){
            throw  new Exception("Servicio no disponible");
        }
        return null;
    }
}
