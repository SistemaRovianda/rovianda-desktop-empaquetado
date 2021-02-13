package com.rovianda.app.shared.contract;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

public interface WeightContract {
    void stop();
    void start(JFXTextField input, Label label);
    void readWeight();
}
