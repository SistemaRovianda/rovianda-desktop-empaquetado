package com.rovianda.app.shared.validator;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.shared.models.Method;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class DataValidator {

    public static void emailValidator(JFXTextField input, Label inputLabel, Method method){
        input.focusedProperty().addListener((arg,oldValue,newValue)->{
            if(!newValue){
                if(input.getText().isEmpty()){
                    inputLabel.setText("Dato requerido");
                    inputLabel.setVisible(true);
                }else
                if(!input.getText().matches("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")){
                    inputLabel.setText("Formato invalido");
                    inputLabel.setVisible(true);
                }else{
                    inputLabel.setVisible(false);
                }
                method.method();
            }if(newValue)
                method.method();
        });
    }
    public static void passwordValidator(JFXPasswordField input, Label inputLabel, Method method){
        input.focusedProperty().addListener((arg,oldValue,newValue)->{
            if(!newValue){
                if(input.getText().isEmpty()){
                    inputLabel.setText("Dato requerido");
                    inputLabel.setVisible(true);
                }else {
                    inputLabel.setVisible(false);
                }
                method.method();
            }
        });
    }

    public static void  minDate(DatePicker picker , LocalDate minDate){
        picker.setDayCellFactory(d -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(minDate));
            }
        });
    }

    public static void decimalValidate(JFXTextField input,Label inputLabel){
        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("^\\d+(\\.\\d{1,2})?$")) {
                    input.setText(newValue.replaceAll("[^\\d+(\\.\\d{1,2})?$/g]", ""));

                    inputLabel.setText("Formato Inválido");
                    inputLabel.setVisible(true);

                }else {
                    inputLabel.setVisible(false);
                }
            }
        });
    }

    public static void numberValidate(JFXTextField input,Label inputLabel){
        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    input.setText(newValue.replaceAll("[^\\d]", ""));

                    inputLabel.setText("Solo se aceptan números");
                    inputLabel.setVisible(true);

                }else {
                    inputLabel.setVisible(false);
                }
            }
        });
    }

}
