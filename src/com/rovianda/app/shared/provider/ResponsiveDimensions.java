package com.rovianda.app.shared.provider;

import java.awt.*;

public class ResponsiveDimensions {

    static private int defaultHeight = 840;
    static private int defaultWidth = 1300;

    static private int systemHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    static private int systemWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

    public static int getHeight() {
        if (systemHeight > defaultHeight && systemWidth > defaultWidth)
            return defaultHeight;
        else
            return systemHeight;
    }


    public static int getWidth() {
        if (systemHeight > defaultHeight && systemWidth > defaultWidth)
            return defaultWidth;
        else
            return systemWidth;
    }

}
