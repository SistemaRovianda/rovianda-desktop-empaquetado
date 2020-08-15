package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.rovianda.app.shared.models.Order;
import com.rovianda.app.shared.models.ProductsRequest;
import com.rovianda.app.shared.service.packaging.ServicePackaging;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class TableViewOrders {

    static ObservableList<Order> items;

    public static ObservableList<ProductsRequest> itemsProducts;

    public static TableView<Order> currentTable;

    public static  TableView<ProductsRequest> currentTableProducts;

    public static Order currentOrder;

    public static TableColumn<ProductsRequest,Integer> columnId;

    public static void  assignColumnNoOrder(TableColumn<Order,Integer> column){
        column.setCellValueFactory(new PropertyValueFactory<Order,Integer>("noOrder"));
    }

    public static void  assignColumnVendedor(TableColumn<Order,String> column){
        column.setCellValueFactory(new PropertyValueFactory<Order,String>("vendedor"));
    }
    public static void  assignColumnOptions(TableColumn<Order, JFXButton> column){
        column.setCellFactory( ActionButtonColumn.<Order>forTableColumn((Order o)->{
            ModalProvider.showModal("Al atender la orden no se volverá a mostrar, ¿Deseas cerrar la orden?",()->{
                System.out.println(o.toString());
                ToastProvider.showToastInfo("Atendiendo pedido",3000);
                Task <Boolean> closeOrderTask = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return ServicePackaging.closedOrder(o);
                    }
                };
                Thread thread = new Thread(closeOrderTask);
                thread.setDaemon(true);
                thread.start();
                closeOrderTask.setOnSucceeded(event -> {
                    if(closeOrderTask.getValue())
                        currentTable.getItems().remove(o);
                });

                closeOrderTask.setOnFailed(e->{
                    ToastProvider.showToastError(e.getSource().getException().getMessage(),3000);
                });


            });
            return o;
        }));
    }

    public static void assignColumnProductsId(){
        columnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductsRequest, Integer>, ObservableValue<Integer>>() {
            @Override public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ProductsRequest,Integer> p) {
                return new ReadOnlyObjectWrapper(currentTableProducts.getItems().indexOf(p.getValue())+1 + "");
            }
        });
    }

    public static void assignColumnProductsProduct(TableColumn<ProductsRequest,String> column){
        column.setCellValueFactory( new PropertyValueFactory<ProductsRequest,String>("Product"));
    }

    public static  void  assignColumnProductsQuantity(TableColumn<ProductsRequest,Integer> column){
        column.setCellValueFactory( new PropertyValueFactory<ProductsRequest,Integer>("quantity"));
    }

    public static void assignTableOrder(TableView<Order> table){
        currentTable = table;
        currentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fillTableOrdersProducts(newValue);
        });
    }

    public  static void fillTableOrders(){
        ToastProvider.showToastInfo("Obteniendo Ordenes",3000);
        items = FXCollections.observableArrayList();
        Task<List<Order>> ordersTask = new Task<List<Order>>() {
            @Override
            protected List<Order> call() throws Exception{
                return ServicePackaging.getOrderVendedor();
            }
        };
        Thread thread = new Thread(ordersTask);
        thread.setDaemon(true);
        thread.start();
        ordersTask.setOnSucceeded(e->{
            if(ordersTask.getValue().size() ==0)
                ToastProvider.showToastInfo("No existen ordenes por el momento",3000);
            items.addAll(ordersTask.getValue());
        });

        ordersTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),3000);
        });
        items.add(new Order("ejemplo","panchito",1));
        currentTable.getItems().setAll(items);
    }

    public  static void fillTableOrdersProducts(Order selected){
        ToastProvider.showToastInfo("Obteniendo productos",3000);
        System.out.println(selected.toString());
        itemsProducts = FXCollections.observableArrayList();
        Task <List<ProductsRequest>> productsTask = new Task<List<ProductsRequest>>() {
            @Override
            protected List<ProductsRequest> call() throws Exception{
                return ServicePackaging.getOrderProducts(selected.getUserId());
            }
        };
        Thread thread = new Thread(productsTask);
        thread.setDaemon(true);
        thread.start();
        productsTask.setOnSucceeded(e -> {
            itemsProducts.addAll(productsTask.getValue());
        });

        productsTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),3000);
        });
        currentTableProducts.getItems().setAll(itemsProducts);
        assignColumnProductsId();
    }

}
