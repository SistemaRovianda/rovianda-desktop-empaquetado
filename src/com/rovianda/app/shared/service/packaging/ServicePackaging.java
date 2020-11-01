package com.rovianda.app.shared.service.packaging;

import com.rovianda.app.shared.models.*;
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

    public static List<PresentationReturn> getPresentationsReturnById(int productId) throws Exception {
        Response response = HttpClient.get("products-rovianda/catalog/"+productId);
        List<PresentationReturn> presentations = new ArrayList<>();
        if(response.getStatus() == 200){
            presentations = response.readEntity(new GenericType<List<PresentationReturn>>(){});
        }else if (response.getStatus() == 404){
            throw new Exception("Error al obtener presentaciones del producto");
        }
        response.close();
        return presentations;
    }

    public static List<Order> getOrderVendedor(boolean urgent) throws Exception{
        Response response = HttpClient.get("/packaging/sellers/"+urgent);
        List<Order> orders = new ArrayList<>();
        response.readEntity(String.class);
        if(response.getStatus() == 200)
            orders = response.readEntity(new GenericType<List<Order>>(){});
        else if (response.getStatus() == 404){
            throw new Exception("Servicio no disponible");
        }
        response.close();
        return orders;
    }
    public static List<ProductsRequest> getOrderProducts(int orderId) throws Exception{
        Response response = HttpClient.get("/seller/orders-products/"+orderId);
        System.out.println(response.readEntity(String.class));
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

    public static  List<Presentation> getPresentations(int orderId, int productId) throws Exception {
        Response response = HttpClient.get("/seller/order/"+orderId+"/product/"+productId);
        System.out.println(response.readEntity(String.class));
        List<Presentation> presentations = new ArrayList<>();
        if(response.getStatus() == 200)
            presentations = response.readEntity(new GenericType<List<Presentation>>(){});
        else if (response.getStatus() >= 400){
            response.close();
            throw new Exception("Servicio no disponible ");
        }
        response.close();
        return presentations;
    }

    public static void getGuard(String sellerUid){
        Response response = HttpClient.get("/seller/guard/"+sellerUid);
        System.out.println(response.readEntity(String.class));
    }

    public static List<PackagingLots> getLotsByProduct(int product_id) throws Exception {
        Response response = HttpClient.get("/packaging-lots/inventory/product/"+product_id);
        System.out.println(response.readEntity(String.class));
        List<PackagingLots> lots = new ArrayList<>();
        if(response.getStatus() == 200){
            lots = response.readEntity(new GenericType<List<PackagingLots>>(){});
        }else if (response.getStatus() == 404){
            throw new Exception("Error al obtener lotes del producto");
        }
        response.close();
        return lots;
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

    public static boolean registerOutputsLots(List<OutputsProduct> outputsProduct) throws Exception {
        Response response = HttpClient.post("",outputsProduct);
        boolean result = false;
        if (response.getStatus() == 201){
            result= true;
        }else {
            throw new Exception("Error al registrar salidas");
        }
        return  result;
    }
}
