package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.rovianda.RoviandaAppRun;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class EnvironmentActions {
    public static void exitAction(JFXButton button){
        button.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
    }
    public  static void minimizeAction(JFXButton button){
        button.setOnAction(event -> {
            RoviandaAppRun.stage.setIconified(true);
        });
    }
}
