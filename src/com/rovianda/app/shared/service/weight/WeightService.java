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

    private static Timeline timeline;

    private static JFXTextField localInput;

    private static Thread t;

    public static void assignWeight (JFXTextField input){

        localInput = input;
        localInput.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        while(true){
                            updateField();
                        }
                    }
                };
                if(newValue){
                    PortSerial.openPort();
                    t= new Thread(task);
                    t.setDaemon(true);
                    t.start();
                }
                if(oldValue){
                    task.cancel();
                    PortSerial.closePort();
                    t = null;
                }

            }
        });

    }

    static void updateField() throws Exception{
        localInput.setText( ""+PortSerial.getWeight());
    }
}
