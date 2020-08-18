package com.rovianda.app.shared.provider;

import javafx.scene.layout.BorderPane;
import java.awt.*;

public class ResponsiveBorderPane {
    private static int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    private static int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    public static void addSizeBorderPane(BorderPane tap){

        tap.setMinWidth(width-220);
        tap.setMinHeight(height);
    }
}
