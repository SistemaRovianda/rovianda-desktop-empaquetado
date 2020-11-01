package com.rovianda.app.shared.service.weight;

import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.shared.provider.ToastProvider;
import com.rovianda.utility.portserial.PortSerial;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

import javax.print.attribute.standard.Finishings;

public class WeightService {

    public  static JFXTextField localInput;

    private static Thread t;

    private static Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            while(true){
                updateField();
            }
        }
    };




    public static void start(JFXTextField input) {
        localInput = input;
        
        try {
            PortSerial.openPort();
            t = new Thread(task);
            t.setDaemon(true);
            t.start();
        }catch (Exception e){
            ToastProvider.showToastError("Error al abrir el puerto",1500);
        }
    }

    public static void stop(){
        try{
            task.cancel();
            PortSerial.closePort();
            t = null;
        }catch (Exception e){
            ToastProvider.showToastError("Error al cerrar el puerto",1500);
        }

    }

    static void updateField() throws Exception{
        localInput.setText( ""+PortSerial.getWeight());
    }
}
