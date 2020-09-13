package com.rovianda.app.shared.provider;

import javafx.scene.layout.BorderPane;
import java.awt.*;

public class ResponsiveBorderPane {
    private static int height = ResponsiveDimensions.getHeight();
    private static int width = ResponsiveDimensions.getWidth();
    public static void addSizeBorderPane(BorderPane tap){

        tap.setMinWidth(width-220);
        tap.setMinHeight(height);
    }
}
