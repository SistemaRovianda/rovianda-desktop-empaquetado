package com.rovianda.utility.animation;

import com.rovianda.app.shared.models.Method;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class Fade {
    public static void visibleElement(Node node ,int millis){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(millis));
        fade.setNode(node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public static void invisibleElement(Node node, int millis, Method method){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(millis));
        fade.setNode(node);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                method.method();
            }
        });
        fade.play();
    }
}
