package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.rovianda.app.shared.models.Order;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public class ActionButtonColumn<S> extends TableCell<S, JFXButton> {

    private final JFXButton actionButton = new JFXButton("Orden atendida");

    public ActionButtonColumn(Function <S,S> function){
        this.actionButton.getStyleClass().add( "gold");
        this.actionButton.setOnAction(e ->{
            function.apply(getCurrentItem());
        });
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
    }

    public S getCurrentItem(){
        return (S) getTableView().getItems().get(getIndex());
    }

    public static <S> Callback<TableColumn<S,JFXButton>,TableCell<S,JFXButton>> forTableColumn(Function<S,S> function){
        return param ->  new ActionButtonColumn<S>(function);
    }

    @Override
    protected void updateItem(JFXButton item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}
