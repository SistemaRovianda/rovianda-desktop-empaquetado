package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.rovianda.app.shared.models.Method;
import com.rovianda.app.shared.models.ProductReturn;
import com.rovianda.app.shared.service.returnProduct.ReturnProductService;
import javafx.concurrent.Task;

public class ReturnProductProvider {
    public  static Long id;
    public static JFXButton currentButton;

    public static void DoReturnProduct(ProductReturn product, JFXButton button){
        ToastProvider.showToastInfo("Registrando devolucion", 1000);
        currentButton.setDisable(true);
        Task <Long>  returnProductTask = new Task<Long>() {
            @Override
            protected Long call() throws Exception {
                return ReturnProductService.registerReturnProduct(product);
            }
        };
        Thread thread = new Thread(returnProductTask);
        thread.setDaemon(true);
        thread.start();

        returnProductTask.setOnSucceeded(e ->{
            if(returnProductTask.getValue() >0){
                id = returnProductTask.getValue();
                button.setVisible(true);
            }
        });

        returnProductTask.setOnFailed(e->{
            button.setVisible(false);
            currentButton.setDisable(false);
            id = new Long(0);
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
        });

    }

    public static void closeLotProduct(String lotId, Method method){
        ToastProvider.showToastInfo("Cerrando lote del producto", 1000);
        Task <Boolean>  closeLotTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return ReturnProductService.closeLot(lotId);
            }
        };
        Thread thread = new Thread(closeLotTask);
        thread.setDaemon(true);
        thread.start();
        closeLotTask.setOnSucceeded(e ->{
            if(closeLotTask.getValue()){
                method.method();
            }
        });

        closeLotTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
        });
    }

}
