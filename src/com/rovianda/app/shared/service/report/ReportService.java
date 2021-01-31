package com.rovianda.app.shared.service.report;

import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.Response;
import java.io.InputStream;

public class ReportService {

    public static InputStream getReport(int order) throws Exception {
        Response response = HttpClient.get("/report/packaging/"+order);
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

    public static InputStream getReportDelivery(int deliveredId) throws Exception {
        Response response = HttpClient.get("/report/packaging-delivered/"+deliveredId);
        if(response.getStatus() == 200){
            return response.readEntity(InputStream.class);
        }else if(response.getStatus()==400){
            throw new Exception("Reporte no encontrado con el id");
        }else if(response.getStatus()==404 || response.getStatus()==500){
            throw  new Exception("Servicio no disponible");
        }
        return null;
    }

    public static InputStream getReportReprocessing(String reprocessingId) throws Exception {
        Response response = HttpClient.get("/packaging/reprocessing-report/"+reprocessingId);
        if(response.getStatus() == 200){
            return response.readEntity(InputStream.class);
        }else if(response.getStatus()==400){
            throw new Exception("Reporte no encontrado con el id");
        }else if(response.getStatus()==404 || response.getStatus()==500){
            throw  new Exception("Servicio no disponible");
        }
        return null;
    }

    public static InputStream getReportGeneral(boolean urgent, String date) throws Exception {
        System.out.println("Urgent: "+urgent);
        System.out.println("Date: "+date);
        Response response = HttpClient.get("/sellers/orders-list?urgent="+urgent+"&date="+date);
        if(response.getStatus() == 200){
            return response.readEntity(InputStream.class);
        }else if(response.getStatus()==400){
            throw new Exception("Error al descargar reporte");
        }else if(response.getStatus()==404 || response.getStatus()==500){
            throw  new Exception("Servicio no disponible");
        }
        return null;
    }
}
