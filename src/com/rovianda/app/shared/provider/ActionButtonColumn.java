package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public class ActionButtonColumn<S> extends TableCell<S, JFXButton> {

    private final JFXButton actionButton = new JFXButton("Atender Orden");

    public ActionButtonColumn(Function <S,S> function){
        this.actionButton.getStyleClass().add( "gold");
        this.actionButton.getStyleClass().add( "table-button");
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
