package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.rovianda.app.shared.models.Method;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ModalProvider {
    public static Label messageLabel;
    public static JFXButton cancel, accept;
    public static Pane container, currentContainer;

    public static  void showModal(String message, Method method){
        cancel.setOnAction(event -> onBack());
        messageLabel.setText(message);
        container.toFront();
        accept.setOnAction( event -> {
            method.method();
            onBack();
        });
    }

    private static void onBack(){
        currentContainer.toFront();
    }


}
