package com.rovianda.app.shared.service.weight;

import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.shared.provider.ToastProvider;
import com.rovianda.utility.portserial.PortSerial;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class WeightService {

    private  static JFXTextField localInput;

    private  static Label localLabel;

    private static Thread t;

    private static Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            while(true){
                updateField();
            }
        }
    };




    public static void start(JFXTextField input, Label label) {
        localLabel = label;
        localInput = input;
        Tooltip tooltip = new Tooltip("Click para obtener el peso de la bascula");
        localInput.setTooltip(tooltip);
        localInput.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    try {

                        if(PortSerial.searchPort()){
                            localLabel.setVisible(true);
                            localLabel.setText("Click en el botón si desea detener la captura del peso ");
                            PortSerial.openPort();
                            t = new Thread(task);
                            t.setDaemon(true);
                            t.start();
                        }
                    }catch (Exception e){
                        ToastProvider.showToastError(e.getMessage(),500);
                    }
                }

            }
        });
    }

    public static void stop(){
        try{
            if(!localLabel.getText().equals("")) {
                ToastProvider.showToastSuccess("Se detuvo la captura del peso",2000);
                localLabel.setVisible(false);
                localLabel.setText("");
                task.cancel();
                PortSerial.closePort();
                t = null;
            }

        }catch (Exception e){
            ToastProvider.showToastError("Error al cerrar el puerto",1500);
        }

    }

    static void updateField() throws Exception{
        localInput.setText( ""+PortSerial.getWeight());
    }
}
