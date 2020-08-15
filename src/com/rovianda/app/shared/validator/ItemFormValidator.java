package com.rovianda.app.shared.validator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.shared.models.ProductCatalog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

public class ItemFormValidator {

    public static boolean  isValidInputDecimal(TextInputControl input, Label inputLabel){
        boolean result = false;
        if (input.getText().isEmpty() || input.getText() == null){
            inputLabel.setVisible(true);
            inputLabel.setText("Valor requerido");

        }else{
            if(input.getText().matches("^\\d+(\\.\\d{1,2})?$")){
                inputLabel.setText("");
                inputLabel.setVisible(false);
                result = true;
            }else{
                inputLabel.setVisible(true);
                inputLabel.setText("Formato inválido");
            }

        }
        return  result;
    }

    public static boolean  isValidInputNumber(TextInputControl input, Label inputLabel){
        boolean result = false;
        if (input.getText().isEmpty() || input.getText() == null){
            inputLabel.setVisible(true);
            inputLabel.setText("Valor requerido");

        }else{
            if(input.getText().matches("\\d*")){
                inputLabel.setText("");
                inputLabel.setVisible(false);
                result = true;
            }else{
                inputLabel.setVisible(true);
                inputLabel.setText("Solo se admiten nùmeros");
            }

        }
        return  result;
    }

    public static boolean  isValidInput(TextInputControl input, Label inputLabel){
        boolean result = false;
        if (input.getText().isEmpty() || input.getText() == null){
            inputLabel.setVisible(true);
            inputLabel.setText("Valor requerido");

        }else{
            inputLabel.setText("");
            inputLabel.setVisible(false);
            result = true;
        }
        return  result;
    }

    public static boolean  isValidSelector(JFXComboBox selector, Label inputLabel){
        boolean result = false;
        if (selector.getValue() == null){
            inputLabel.setVisible(true);
            inputLabel.setText("Valor requerido");

        }else{
            inputLabel.setText("");
            inputLabel.setVisible(false);
            result = true;
        }
        return  result;
    }

    public static void isValidInputFocus(TextInputControl input, Label inputLabel){
        input.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue){
                if(input.getText().isEmpty()){
                    inputLabel.setVisible(true);
                    inputLabel.setText("Valor requerido");
                }else{
                    inputLabel.setText("");
                    inputLabel.setVisible(false);
                }
            }
        } );
    }

    public static void isValidSelectorFocus(JFXComboBox selector, Label inputLabel){
        selector.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                if (selector.getValue() == null){
                    inputLabel.setVisible(true);
                    inputLabel.setText("Valor requerido");
                }else{
                    inputLabel.setText("");
                    inputLabel.setVisible(false);
                }
            }
        });
    }


}
