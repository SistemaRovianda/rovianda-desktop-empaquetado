package com.rovianda.app.shared.service.packaging;

import com.rovianda.app.shared.models.ProductPresentation;
import com.rovianda.utility.ConnectService.HttpClient;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ServicePackaging {
    public static List<ProductPresentation> getPresentationsById(int productId){
        Response response = HttpClient.get("packaging/products/presentations/"+productId);
        List<ProductPresentation> presentations = new ArrayList<>();
        if(response.getStatus() == 200){
            presentations = response.readEntity(new GenericType<List<ProductPresentation>>(){});
        }
        response.close();
        return presentations;
    }
}
