package com.rovianda.app.shared.provider;

import com.rovianda.app.shared.models.TableRegisterProduct;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class TableViewRegister {

    public static List <TableRegisterProduct> items = new ArrayList<TableRegisterProduct>();

    public static TableView<TableRegisterProduct> currentTable;

    public static void  assignColumnProduct(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("product"));
    }

    public static void  assignColumnLot(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("lot"));
    }

    public static void  assignColumnExpiration(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("expiration"));
    }

    public static void  assignColumnPresentation(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("presentation"));
    }

    public static void  assignColumnUnity(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("units"));
    }

    public static void  assignColumnWeight(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("weight"));
    }

    public static void  assignColumnUser(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("user"));
    }

    public static void  assignColumnObservations(TableColumn<TableRegisterProduct,String> column){
        column.setCellValueFactory(new PropertyValueFactory<TableRegisterProduct,String>("observations"));
    }

    public static void addItem(TableRegisterProduct item){
        items.add(item);
        currentTable.getItems().setAll(items);
    }


}
