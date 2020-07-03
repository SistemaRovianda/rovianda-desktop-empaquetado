package com.rovianda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RoviandaAppRun extends Application {
    Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        try {
            window();
        }catch (Exception error){
            error.printStackTrace();
        }
    }

    public void window() throws IOException {
        String urlFXML = "app/features/login/Login.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(urlFXML));
        AnchorPane root  = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Rovianda Empaquetados");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(RoviandaAppRun.class.getResourceAsStream("assets/icons/icon.png")));
        stage.setHeight(Screen.getPrimary().getBounds().getHeight());
        stage.setWidth(Screen.getPrimary().getBounds().getWidth());
        stage.show();

    }
}
