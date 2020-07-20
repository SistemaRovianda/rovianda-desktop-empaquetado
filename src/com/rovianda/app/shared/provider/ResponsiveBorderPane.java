package com.rovianda.app.shared.provider;

import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

public class ResponsiveBorderPane {
    public static void addSizeBorderPane(BorderPane tap){
        tap.setMinWidth(Screen.getPrimary().getBounds().getWidth()-220);
        tap.setMinHeight(Screen.getPrimary().getBounds().getHeight());
    }
}
