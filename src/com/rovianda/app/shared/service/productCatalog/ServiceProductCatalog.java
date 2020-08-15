package com.rovianda.app.shared.service.productCatalog;

import com.rovianda.app.shared.models.OutputLots;
import com.rovianda.app.shared.models.ProductCatalog;
import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ServiceProductCatalog {
    public static List<ProductCatalog>getProductsCatalog () throws Exception {
        Response response = HttpClient.get("packaging/products/oven");
        List <ProductCatalog> products = new ArrayList<>();
        if(response.getStatus() == 200){
            products = response.readEntity(new GenericType<List<ProductCatalog>>(){});
        }else if (response.getStatus() == 404)
            throw  new Exception ("Error al obtener productos");
        response.close();
        return products;
    }

    public static List<OutputLots> getOutputLotsById(int idProduct){
        Response response = HttpClient.get("/meat/lots/output?status=NOTUSED&rawMaterialId="+idProduct);
        List<OutputLots> lots = new ArrayList<>();
        if(response.getStatus() == 200){
            lots = response.readEntity(new GenericType<List<OutputLots>>(){});
        }
        response.close();
        return  lots;
    }
}
