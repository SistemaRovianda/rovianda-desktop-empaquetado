package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.features.menuPacking.RegisterProductCtrl;
import com.rovianda.app.shared.models.*;
import com.rovianda.app.shared.service.packaging.ServicePackaging;
import com.rovianda.app.shared.service.weight.WeightService;
import com.rovianda.app.shared.validator.DataValidator;
import com.rovianda.app.shared.validator.ItemFormValidator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableViewOrders {

    static ObservableList<OutputsProduct> itemsOutputs = FXCollections.observableArrayList();
    public static TableView<Order> currentTable;
    public static TableView<ProductsRequest> currentTableProducts;
    public static TableView<Presentation> currentTablePresentation;
    public static Order currentOrder;
    public static ProductsRequest currentProduct;
    public static Presentation currentPresentation;
    public static TableColumn<ProductsRequest, Integer> columnId;
    public static TableColumn<Presentation, Integer> columnIdPresentation;
    public static Pane presentations;
    public static JFXComboBox<PackagingLots> lots;
    public static List<PackagingLots> lotsTemp=new ArrayList<>();
    public static List<Boolean> registering=new ArrayList<>();
    public static JFXTextField unitsToTake, weightPresentations ;
    public static JFXButton buttonAddUnitsToTake, buttonSaveOutput;
    public static Label tagName, tagUnits, errorQuantity, errorLots,errorPresentationWeight;
    static TableView<OutputsProduct> currentTableOutput;

    private static void initLots(List<PackagingLots> lots){
        lotsTemp.clear();
        lotsTemp.addAll(lots);
        for(PackagingLots item : lots){
            System.out.println("Item: "+item.getTypePresentation());
        }
    }

    public static void assignColumnNoOrder(TableColumn<Order, Integer> column) {
        column.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
    }

    public static void assignColumnVendedor(TableColumn<Order, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<Order, String>("vendedor"));
    }

    public static void assignColumnDate(TableColumn<Order, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
    }

    public static void assignColumnOptions(TableColumn<Order, JFXButton> column,RegisterProductCtrl reg) {
        column.setCellFactory(ActionButtonColumn.<Order>forTableColumn((Order o) -> {
            ModalProvider.currentContainer = presentations;
            currentOrder = o;
            RegisterProductCtrl.activePresentations = true;
            fillTablePresentations(o.getOrderId());
            presentations.toFront();
            fillLots(o.getOrderId());
            DataValidator.decimalValidate(weightPresentations,errorPresentationWeight);
            weightPresentations.setText("");
            WeightService.start(weightPresentations,errorPresentationWeight);
                /*ToastProvider.showToastInfo("Atendiendo pedido", 3000);
                Task<Boolean> closeOrderTask = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return ServicePackaging.closedOrder(o);
                    }
                };
                Thread thread = new Thread(closeOrderTask);
                thread.setDaemon(true);
                thread.start();
                closeOrderTask.setOnSucceeded(event -> {
                    if (closeOrderTask.getValue())
                        currentTable.getItems().remove(o);
                    ReportProvider.buildReportDelivered(o.getOrderId());
                });

                closeOrderTask.setOnFailed(e -> {
                    ToastProvider.showToastError(e.getSource().getException().getMessage(), 3000);
                });
            */
                return  o;
            }));

    }


    public static void assignColumnProductsId() {
        columnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductsRequest, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ProductsRequest, Integer> p) {
                return new ReadOnlyObjectWrapper(currentTableProducts.getItems().indexOf(p.getValue()) + 1 + "");
            }
        });
    }

    public static void assignColumnProductsProduct(TableColumn<ProductsRequest, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<ProductsRequest, String>("name"));
    }

    public static void assignTableOrder(TableView<Order> table) {
        currentTable = table;
        currentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            /*if (newValue != null) {
                fillTableOrdersProducts(newValue);
            }*/
        });
    }

   /* public static void assignTableProducts(TableView<ProductsRequest> table) {
        currentTableProducts = table;
        currentTableProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ModalProvider.currentContainer = presentations;
                currentProduct = newValue;
                RegisterProductCtrl.activePresentations = true;
                fillTablePresentations();
                presentations.toFront();
                fillLots();
                DataValidator.decimalValidate(weightPresentations,errorPresentationWeight);
                weightPresentations.setText("");
                WeightService.start(weightPresentations,errorPresentationWeight);
            }
        });
    }
*/
    public static void assignTablePresentations(TableView<Presentation> table) {
        currentTablePresentation = table;
        currentTablePresentation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentPresentation = newValue;
                System.out.println("Nuevo valor: "+currentPresentation.getPresentationId());
                tagName.setText(currentPresentation.getName() + " " + currentPresentation.getTypePresentation());
                unitsToTake.setText("");

                lots.setValue(null);
                lots.hide();
                lots.getItems().clear();
                List<PackagingLots> lotsSorted= lotsTemp.stream().filter(x->x.getPresentationId()==currentPresentation.getPresentationId()).collect(Collectors.toList());
                lotsSorted.sort((a,b)->(b.getLoteId()+b.getTypePresentation()).length()-(a.getLoteId()+a.getTypePresentation()).length());
                lots.getItems().addAll(lotsSorted);
                lots.show();
            }
        });
    }

    public static void assignColumnPresentationsId() {
        columnIdPresentation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Presentation, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Presentation, Integer> p) {
                return new ReadOnlyObjectWrapper(currentTablePresentation.getItems().indexOf(p.getValue()) + 1 + "");
            }
        });
    }
    //reemplazo de assignColumnPresentationsId
    public static void assignColumnProductName(TableColumn<Presentation, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<Presentation, String>("name"));
    }

    public static void assignColumnPresentations(TableColumn<Presentation, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<Presentation, String>("typePresentation"));
    }

    public static void assignColumnUnits(TableColumn<Presentation, Integer> column) {
        column.setCellValueFactory(new PropertyValueFactory<Presentation, Integer>("units"));
    }

    public static void assignUnitsTextField(JFXTextField units) {
        unitsToTake = units;
        DataValidator.numberValidate(unitsToTake, errorQuantity);
        ItemFormValidator.isValidSelectorFocus(lots, errorLots);
        buttonAddUnitsToTake.setOnAction(e -> {
            addItemOutputTable();
        });
    }

    public static void buildTableOutputProduct(TableView<OutputsProduct> table, TableColumn<OutputsProduct, String> columnLot, TableColumn<OutputsProduct, Integer> columnQuantity,
                                               TableColumn<OutputsProduct, Double> columnWeight) {
        currentTableOutput = table;
        columnLot.setCellValueFactory(new PropertyValueFactory<OutputsProduct, String>("loteId"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<OutputsProduct, Integer>("quantity"));
        columnWeight.setCellValueFactory( new PropertyValueFactory<OutputsProduct,Double>("weight"));
        currentTableOutput.setOnMouseClicked( event->{
            if(event.getButton()==MouseButton.PRIMARY && event.getClickCount()==2) {
                System.out.println("se clico 2 veces");
                weightPresentations.setText("");
                lots.setValue(null);
                OutputsProduct output = table.getSelectionModel().getSelectedItem();
                int index = table.getSelectionModel().getSelectedIndex();

                List<OutputsProduct> outputs= new ArrayList<>();
                for(int i=0;i<table.getItems().size();i++){
                    if(i!=index){
                        outputs.add(table.getItems().get(i));
                    }
                }
                List<Presentation> newP = new ArrayList<>();
                for(Presentation p : currentTablePresentation.getItems()){
                    if(p.getPresentationId()==output.getPresentationId() && p.getSubOrderId()==output.getSubOrderId()){
                        p.setUnits(p.getUnits()+output.getQuantity());
                    }
                    newP.add(p);
                }
                currentTablePresentation.getItems().clear();
                currentTablePresentation.getItems().addAll(newP);
                currentTableOutput.getItems().clear();
                currentTableOutput.getItems().addAll(outputs);
            }
        });
    }

    public static List<OutputsProduct> getLotsUsed(int presentationId){
        return currentTableOutput.getItems().stream().filter(item->item.getPresentationId()==presentationId).collect(Collectors.toList());
    }

    public static void fillTableOrders(boolean urgent) {
        System.out.println("Value of urgent: "+urgent);
        if(currentTableProducts!=null) {
            currentTableProducts.getItems().clear();
        }
        currentTable.getItems().clear();
        currentTableOutput.getItems().clear();
        tagName.setText("");
        tagUnits.setText("0");
        unitsToTake.setText("");
        lots.setValue(null);
        ToastProvider.showToastInfo("Obteniendo Ordenes", 1500);
        Task<List<Order>> ordersTask = new Task<List<Order>>() {
            @Override
            protected List<Order> call() throws Exception {
                return ServicePackaging.getOrderVendedor(urgent);
            }
        };
        Thread thread = new Thread(ordersTask);
        thread.setDaemon(true);
        thread.start();
        ordersTask.setOnSucceeded(e -> {
            if (ordersTask.getValue().size() == 0) {
                ToastProvider.showToastInfo("No existen ordenes por el momento", 3000);


            } else {
                currentTable.getItems().setAll(ordersTask.getValue());
            }
        });

        ordersTask.setOnFailed(e -> {
            ToastProvider.showToastError(e.getSource().getException().getMessage(), 3000);
            System.out.println(e.getSource().getException().getMessage());
        });

    }

    public static void fillTableOrdersProducts(Order selected) {
        currentTableProducts.getItems().clear();
        currentOrder = selected;
        ToastProvider.showToastInfo("Espere por favor: Obteniendo productos", 1000);
        Task<List<ProductsRequest>> productsTask = new Task<List<ProductsRequest>>() {
            @Override
            protected List<ProductsRequest> call() throws Exception {
                return ServicePackaging.getOrderProducts(selected.getOrderId());
            }
        };
        Thread thread = new Thread(productsTask);
        thread.setDaemon(true);
        thread.start();
        productsTask.setOnSucceeded(e -> {

            currentTableProducts.getItems().setAll(productsTask.getValue());
            currentTableProducts.getItems().setAll(productsTask.getValue());


        });

        productsTask.setOnFailed(e -> {
            ToastProvider.showToastError(e.getSource().getException().getMessage(), 1000);
        });
        assignColumnProductsId();
    }

    static void fillTablePresentations(int orderId) {
        currentTablePresentation.getItems().clear();
        ToastProvider.showToastInfo("Espere porfavor: Obteniendo presentaciones del producto", 1000);
        Task<List<Presentation>> presentationsTask = new Task<List<Presentation>>() {
            @Override
            protected List<Presentation> call() throws Exception {

                return ServicePackaging.getPresentations(currentOrder.getOrderId());
            }
        };
        Thread thread = new Thread(presentationsTask);
        thread.setDaemon(true);
        thread.start();
        presentationsTask.setOnSucceeded(e -> {
            currentTablePresentation.getItems().setAll(presentationsTask.getValue());
            for(Presentation p : presentationsTask.getValue()){
                if(p.getUnits()>0){
                    registering.add(true);
                }else if(p.getUnits()==0){
                    registering.add(false);
                }
            }
        });

        presentationsTask.setOnFailed(e -> {
            ToastProvider.showToastError(e.getSource().getException().getMessage(), 3000);
        });
        //assignColumnPresentationsId();
    }

    static void fillLots(int orderId) {

        ObservableList<PackagingLots> lotsProducts = FXCollections.observableArrayList();
        Task<List<PackagingLots>> taskLots = new Task<List<PackagingLots>>() {
            @Override
            protected List<PackagingLots> call() throws Exception {
                return ServicePackaging.getLotsByProduct(orderId);
            }
        };
        Thread thread = new Thread(taskLots);
        thread.setDaemon(true);
        thread.start();
        taskLots.setOnSucceeded(e -> {
            ToastProvider.showToastSuccess("Lotes obtenidos", 1500);
            if (taskLots.getValue().size() > 0) {
                lots.setDisable(false);
                List<OutputsProduct> currentOutputs=currentTableOutput.getItems().stream().collect(Collectors.toList());

                /*List<String> packagingLotsString = new ArrayList<String>();
                List<PackagingLots> packagingLots1 = taskLots.getValue().stream().collect(Collectors.toList());
                List<PackagingLots> packagingLots2 = new ArrayList<>();
                for(PackagingLots item : packagingLots1){
                    if(!packagingLotsString.contains(item.getLoteId())){
                        packagingLotsString.add(item.getLoteId());
                        packagingLots2.add(item);
                    }
                }*/
                 List<PackagingLots> packagingLots = taskLots.getValue().stream().map((packaging)->{
                     int total=packaging.getQuantity();
                     for(OutputsProduct outputProduct : currentOutputs){
                         if(outputProduct.getLoteId().equals(packaging.getLoteId())){
                             total-=outputProduct.getQuantity();
                         }
                     }
                     packaging.setQuantity(total);
                     return packaging;
                }).collect(Collectors.toList());
                initLots(packagingLots);
                lotsProducts.addAll(packagingLots);
            } else {
                ToastProvider.showToastInfo("No existen lotes para el producto", 1500);
                lots.setDisable(false);
            }

        });

        taskLots.setOnFailed(event -> {
            ToastProvider.showToastError(event.getSource().getException().getMessage(), 1500);
            lots.setDisable(true);
        });
        /*lotsProducts.clear();
        List<String> packagingLots = new ArrayList<String>();
        List<PackagingLots> packagingLots2 = lotsProducts.sorted().stream().collect(Collectors.toList());
        for(PackagingLots item : packagingLots2){
            if(!packagingLots.contains(item.getLoteId())){
                packagingLots.add(item.getLoteId());
                lotsProducts.add(item);
            }
        }*/
        lots.setItems(lotsProducts);


        lots.setConverter(new StringConverter<PackagingLots>() {
            @Override
            public String toString(PackagingLots object) {
                return object.getLoteId();
            }

            @Override
            public PackagingLots fromString(String string) {
                return null;
            }
        });
    }



    static void addItemOutputTable() {

        if (ItemFormValidator.isValidSelector(lots, errorLots) && ItemFormValidator.isValidInputNumber(unitsToTake, errorQuantity)&&
                ItemFormValidator.isValidInputDecimal(weightPresentations,errorPresentationWeight) ) {
            if(currentTablePresentation!=null) {
                if(lots.getValue().getPresentationId()==currentPresentation.getPresentationId()) {
                    List presentations = currentTablePresentation.getItems().stream().map(presentation -> {

                        if (presentation.getPresentationId() == currentPresentation.getPresentationId()) {
                            if (presentation.getUnits() > 0) {
                                int units = Integer.parseInt(unitsToTake.getText());
                                if (lots.getValue().getQuantity() >= units) {
                                    if ((presentation.getUnits() - units) >= 0) {

                                        tagUnits.setText("");
                                        presentation.setUnits(presentation.getUnits() - units);
                                        OutputsProduct item = new OutputsProduct(lots.getValue().getLoteId(), currentPresentation.getSubOrderId(), units,
                                                currentPresentation.getPresentationId(),
                                                Double.parseDouble(weightPresentations.getText()));
                                        currentTableOutput.getItems().add(item);
                                    } else {
                                        ModalProvider.showModalInfo("No puedes asignar mas de los que se piden");
                                    }
                                } else {
                                    ModalProvider.showModalInfo("Las unidades a tomar deben ser menores a las dispobibles por lote");
                                }

                            } else {
                                ModalProvider.showModalInfo("El registro de la suborden ya esta completo");
                            }
                        }
                        return presentation;
                    }).collect(Collectors.toList());
                    currentTablePresentation.getItems().clear();
                    currentTablePresentation.getItems().addAll(presentations);
                }else{
                    ModalProvider.showModalInfo("No puedes asignar de una presentacion diferente");
                }
            }else {
                ModalProvider.showModalInfo("Primero selecciona un registro");
            }
            }



            /*if (currentItem == null) {
                OutputsProduct item = new OutputsProduct(lots.getValue().getLoteId(), currentPresentation.getSubOrderId(), units, currentPresentation.getPresentationId());
                currentTableOutput.getItems().add(item);
            } else {
                List<OutputsProduct> newItems = currentTableOutput.getItems().stream().map(output -> {
                    if (output.getPresentationId() == currentPresentation.getPresentationId()) {
                        output.setQuantity(output.getQuantity() + units);
                    }
                    return output;
                }).collect(
                        Collectors.toList()
                );
                currentTableOutput.getItems().clear();
                currentTableOutput.getItems().addAll(newItems);
            }*/
            unitsToTake.setText("");
        }



    public static void saveOutputLots(Method method) {
       if(currentTableOutput.getItems().size() >0){
           List<Presentation> presentations = currentTablePresentation.getItems().stream().filter(e->e.getUnits()>0).collect(Collectors.toList());

               ToastProvider.showToastInfo("Registrando salidas de lotes por favor espere...", 1500);
               Task<Boolean> taskOutputs = new Task<Boolean>() {
                   @Override
                   protected Boolean call() throws Exception {
                       return ServicePackaging.registerOutputsLots(currentTableOutput.getItems());
                   }
               };
               Thread thread = new Thread(taskOutputs);
               thread.setDaemon(true);
               thread.start();

               taskOutputs.setOnSucceeded(e -> {
                   currentTableOutput.getItems().clear();
                   currentTablePresentation.getItems().clear();
                   lots.getItems().clear();
                   registering.clear();
                   fillTablePresentations(currentOrder.getOrderId());
                   fillLots(currentOrder.getOrderId());

                   ToastProvider.showToastInfo("Registro de salidas completado", 1500);
                   method.method();
               });

               taskOutputs.setOnFailed(e -> {
                   ToastProvider.showToastError(e.getSource().getException().getMessage(), 1500);
               });

       }else {
           ModalProvider.showModalInfo("Es necesario agregar lotes para realizar el registro");
       }
    }

    public static void closeOrder(Method method) {

            List<Presentation> presentations = currentTablePresentation.getItems().stream().filter(e->e.getUnits()>0).collect(Collectors.toList());
            System.out.println(presentations.size());
            for(Presentation p : presentations){
                System.out.println(p.getTypePresentation());
            }
            if(registering.indexOf(true)==-1) {
                if( presentations.size()==0 ) {
                    closeOrderTask(method);
                }else{
                    ModalProvider.showModalInfo("Primero guarda las entregas de producto.");
                }
            }else{
                ModalProvider.showModal("¿Seguro que desea cerrar la orden? (La entrega está incompleta)",()-> {
                    closeOrderTask(method);
                }
                );
            }

    }

    public static Method closeOrderTask(Method method){
        ToastProvider.showToastInfo("Cerrando pedido", 3000);
        Task<Boolean> closeOrderTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return ServicePackaging.closedOrder(currentOrder);
            }
        };
        Thread thread = new Thread(closeOrderTask);
        thread.setDaemon(true);
        thread.start();
        closeOrderTask.setOnSucceeded(event -> {
            if (closeOrderTask.getValue())

                ReportProvider.buildReportDelivered(currentOrder.getOrderId(),method);
        });

        closeOrderTask.setOnFailed(e -> {
            ToastProvider.showToastError(e.getSource().getException().getMessage(), 3000);
        });

        return method;
    }
}
