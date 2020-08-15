package com.rovianda.app.shared.provider;

import com.rovianda.app.shared.models.*;
import com.rovianda.app.shared.service.packaging.NewPackaging;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class TableViewRegister {

    public static List <TableRegisterProduct> items = new ArrayList<TableRegisterProduct>();

    public static TableView<TableRegisterProduct> currentTable;

    public  static ProductPackaging currentProductPackaging;

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
    public static  void clearTable(){
        items = new ArrayList<TableRegisterProduct>();
        currentTable.getItems().setAll(items);;
        currentTable.refresh();
    }

    public static void registerItems(Method callBack){
        System.out.println(currentProductPackaging.toString());
        ModalProvider.showModal("Una vez guardada la información no se podrá editar, ¿Estás seguro que deseas guardar el proceso?", ()->{
            ToastProvider.showToastInfo("Registrando productos ", 1500);
            Task<Integer> taskRegister = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    return NewPackaging.registerNewProductPackaging(currentProductPackaging);
                }
            };
            Thread thread = new Thread(taskRegister);
            thread.setDaemon(true);
            thread.start();
            taskRegister.setOnSucceeded(event ->{
                items = new ArrayList<>();
                currentTable.getItems().setAll(items);
                callBack.method();
                ToastProvider.showToastSuccess("Registro de proceso exitoso", 1500);
            });
            taskRegister.setOnFailed(e->{
                System.out.println("Fallo aqui xD");
                ToastProvider.showToastError(e.getSource().getException().getMessage(), 1500);
            });
        });

    }



}
