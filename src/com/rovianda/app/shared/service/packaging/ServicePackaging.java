package com.rovianda.app.shared.service.packaging;

import com.rovianda.app.shared.models.MessageError;
import com.rovianda.app.shared.models.Order;
import com.rovianda.app.shared.models.ProductPresentation;
import com.rovianda.app.shared.models.ProductsRequest;
import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ServicePackaging {
    public static List<ProductPresentation> getPresentationsById(int productId) throws Exception {
        Response response = HttpClient.get("packaging/products/presentations/"+productId);
        List<ProductPresentation> presentations = new ArrayList<>();
        if(response.getStatus() == 200){
            presentations = response.readEntity(new GenericType<List<ProductPresentation>>(){});
        }else if (response.getStatus() == 404){
            throw new Exception("Error al obtener presentaciones del producto");
        }
        response.close();
        return presentations;
    }

    public static List<Order> getOrderVendedor() throws Exception{
        Response response = HttpClient.get("/packaging");
        List<Order> orders = new ArrayList<>();
        System.out.println(response.readEntity(String.class));
        if(response.getStatus() == 200)
            orders = response.readEntity(new GenericType<List<Order>>(){});
        else if (response.getStatus() == 404){
            throw new Exception("Servicio no disponible");
        }
        response.close();
        return orders;
    }
    public static List<ProductsRequest> getOrderProducts(String userId) throws Exception{
        Response response = HttpClient.get("/packaging/"+userId);
        List<ProductsRequest> products = new ArrayList<>();
        if(response.getStatus() == 200)
            products = response.readEntity(new GenericType<List<ProductsRequest>>(){});
        else if (response.getStatus() >= 400){
            response.close();
            throw new Exception("Servicio no disponible ");
        }
        response.close();
        return products;
    }

    public static boolean closedOrder(Order order) throws Exception {
        Response response = HttpClient.patch("/packaging/"+order.getUserId(), order);
        boolean result = false;
        if (response.getStatus() == 204){
            result = true;
        }else {
            throw new Exception("Error al atender orden");
        }
        response.close();
        return result;
    }
}
