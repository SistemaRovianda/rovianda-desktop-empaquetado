package com.rovianda.app.shared.service.weight;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.shared.provider.ToastProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class WeightService {

    private  static JFXTextField localInput;

    private  static Label localLabel;

    private static SerialPort weightPort;

    static {
        weightPort = SerialPort.getCommPort("Prolific USB-to-Serial Comm Port");
        if(weightPort!= null){
                ToastProvider.showToastSuccess("Conexión exitosa a la bascula",2000);
            weightPort.addDataListener(new SerialPortDataListener() {
                String message;
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;
                    byte[] newData = new byte[weightPort.bytesAvailable()];
                    int numRead = weightPort.readBytes(newData, newData.length);
                    message = new String(newData,0, numRead);
                    localInput.setText(""+Double.parseDouble(message.replaceAll("KG","")));
                }
            });
        }
        else
            ToastProvider.showToastError("Error al conectar con la bascula",1500);
    }


    public static void start(JFXTextField input, Label label) {
        localLabel = label;
        localInput = input;
        Tooltip tooltip = new Tooltip("Click para obtener el peso de la bascula");
        localInput.setTooltip(tooltip);
        localInput.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    if(weightPort!= null){
                        weightPort.openPort();
                        if(weightPort.isOpen()){
                            localLabel.setText("Para detener la captura de peso presionar el boton");
                        }else{
                            localLabel.setText("ERROR: puerto no abierto o no encontrado");
                        }
                        localLabel.setVisible(true);

                    }else
                        ToastProvider.showToastError("Error de comunicacion con la bascula",1500);
                }

            }
        });
    }

    public static void stop(){
        if(weightPort!= null){
            weightPort.closePort();
        }else
            ToastProvider.showToastError("Error de comunicacion con la bascula",1500);
    }

}
