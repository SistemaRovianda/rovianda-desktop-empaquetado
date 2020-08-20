package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.rovianda.app.features.menuPacking.RegisterProductCtrl;
import com.rovianda.app.shared.models.Order;
import com.rovianda.app.shared.models.Presentation;
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
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import java.util.List;

public class TableViewOrders {

    static ObservableList<Order> items;

    public static ObservableList<ProductsRequest> itemsProducts;

    public static TableView<Order> currentTable;

    public static  TableView<ProductsRequest> currentTableProducts;

    public static  TableView<Presentation> currentTablePresentation;

    public static Order currentOrder;

    public  static ProductsRequest currentProduct;

    public static TableColumn<ProductsRequest,Integer> columnId;
    public static TableColumn<Presentation,Integer> columnIdPresentation;

    public static Pane presentations;

    public static void  assignColumnNoOrder(TableColumn<Order,Integer> column){
        column.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
    }

    public static void  assignColumnVendedor(TableColumn<Order,String> column){
        column.setCellValueFactory(new PropertyValueFactory<Order,String>("vendedor"));
    }

    public static void  assignColumnDate(TableColumn<Order,String> column){
        column.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));
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
        column.setCellValueFactory( new PropertyValueFactory<ProductsRequest,String>("name"));
    }

    public static  void  assignColumnProductsQuantity(TableColumn<ProductsRequest,Integer> column){
        column.setCellValueFactory( new PropertyValueFactory<ProductsRequest,Integer>("quantity"));
    }

    public static void assignTableOrder(TableView<Order> table){
        currentTable = table;
        currentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!= null){
                fillTableOrdersProducts(newValue);
            }
        });
    }

    public static void assignTableProducts(TableView<ProductsRequest> table){
        currentTableProducts = table;
        currentTableProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!= null){
                ModalProvider.currentContainer = presentations;
                currentProduct = newValue;
                RegisterProductCtrl.activePresentations = true;
                fillTablePresentations();
                presentations.toFront();
            }
        });
    }

    public static void assignTablePresentations(TableView<Presentation> table){
        currentTablePresentation = table;
        currentTablePresentation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!= null){

            }
        });
    }

    public static void assignColumnPresentationsId(){
        columnIdPresentation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Presentation, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Presentation, Integer> p) {
                return new ReadOnlyObjectWrapper(currentTablePresentation.getItems().indexOf(p.getValue())+1 + "");
            }
        });
    }

    public static void  assignColumnPresentations(TableColumn<Presentation,String> column){
        column.setCellValueFactory(new PropertyValueFactory<Presentation,String>("typePresentation"));
    }

    public static void  assignColumnUnits(TableColumn<Presentation,Integer> column){
        column.setCellValueFactory(new PropertyValueFactory<Presentation,Integer>("units"));
    }




    public  static void fillTableOrders(boolean urgent){
        currentTableProducts.getItems().clear();
        currentTable.getItems().clear();
        ToastProvider.showToastInfo("Obteniendo Ordenes",1500);
        items = FXCollections.observableArrayList();
        Task<List<Order>> ordersTask = new Task<List<Order>>() {
            @Override
            protected List<Order> call() throws Exception{
                return ServicePackaging.getOrderVendedor(urgent);
            }
        };
        Thread thread = new Thread(ordersTask);
        thread.setDaemon(true);
        thread.start();
        ordersTask.setOnSucceeded(e->{
            if(ordersTask.getValue().size() ==0){
                ToastProvider.showToastInfo("No existen ordenes por el momento",3000);
            }else {
                currentTable.getItems().setAll(ordersTask.getValue());
            }
        });

        ordersTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),3000);
        });

    }

    public  static void fillTableOrdersProducts(Order selected){
        currentTableProducts.getItems().clear();
        currentOrder = selected;
        ToastProvider.showToastInfo("Obteniendo productos",1500);
        Task <List<ProductsRequest>> productsTask = new Task<List<ProductsRequest>>() {
            @Override
            protected List<ProductsRequest> call() throws Exception{
                return ServicePackaging.getOrderProducts(selected.getOrderId());
            }
        };
        Thread thread = new Thread(productsTask);
        thread.setDaemon(true);
        thread.start();
        productsTask.setOnSucceeded(e -> {
            currentTableProducts.getItems().setAll(productsTask.getValue());
        });

        productsTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),3000);
        });
        assignColumnProductsId();
    }

    static void fillTablePresentations(){
        currentTablePresentation.getItems().clear();
        ToastProvider.showToastInfo("Obteniendo las presentaciones del producto",1500);
        Task <List<Presentation>> presentationsTask = new Task<List<Presentation>>() {
            @Override
            protected List<Presentation> call() throws Exception {
                        ServicePackaging.getGuard(currentOrder.getUserId());
                return  ServicePackaging.getPresentations(currentOrder.getOrderId(),currentProduct.getProduct_id());
            }
        };
        Thread thread = new Thread(presentationsTask);
        thread.setDaemon(true);
        thread.start();
        presentationsTask.setOnSucceeded(e -> {
            currentTablePresentation.getItems().setAll(presentationsTask.getValue());
        });

        presentationsTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),3000);
        });
        assignColumnPresentationsId();
    }
}
