package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.features.menuPacking.RegisterProductCtrl;
import com.rovianda.app.shared.models.*;
import com.rovianda.app.shared.service.packaging.ServicePackaging;
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
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;
import java.util.stream.Collectors;

public class TableViewOrders {

    static ObservableList<OutputsProduct> itemsOutputs = FXCollections.observableArrayList();
    ;
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
    static JFXTextField unitsToTake;
    public static JFXButton buttonAddUnitsToTake, buttonSaveOutput;
    public static Label tagName, tagUnits, errorQuantity, errorLots;
    static TableView<OutputsProduct> currentTableOutput;

    public static void assignColumnNoOrder(TableColumn<Order, Integer> column) {
        column.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
    }

    public static void assignColumnVendedor(TableColumn<Order, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<Order, String>("vendedor"));
    }

    public static void assignColumnDate(TableColumn<Order, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
    }

    public static void assignColumnOptions(TableColumn<Order, JFXButton> column) {
        column.setCellFactory(ActionButtonColumn.<Order>forTableColumn((Order o) -> {
            ModalProvider.showModal("Al atender la orden no se volverá a mostrar, ¿Deseas cerrar la orden?", () -> {
                System.out.println(o.toString());
                ToastProvider.showToastInfo("Atendiendo pedido", 3000);
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
                });

                closeOrderTask.setOnFailed(e -> {
                    ToastProvider.showToastError(e.getSource().getException().getMessage(), 3000);
                });


            });
            return o;
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
            if (newValue != null) {
                fillTableOrdersProducts(newValue);
            }
        });
    }

    public static void assignTableProducts(TableView<ProductsRequest> table) {
        currentTableProducts = table;
        currentTableProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ModalProvider.currentContainer = presentations;
                currentProduct = newValue;
                RegisterProductCtrl.activePresentations = true;
                fillTablePresentations();
                presentations.toFront();
                fillLots();
            }
        });
    }

    public static void assignTablePresentations(TableView<Presentation> table) {
        currentTablePresentation = table;
        currentTablePresentation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentPresentation = newValue;
                tagUnits.setText("" + currentPresentation.getUnits());
                tagName.setText(currentProduct.getName() + " "
                        + currentPresentation.getTypePresentation());
                unitsToTake.setText("");
                lots.setValue(null);
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

    public static void buildTableOutputProduct(TableView<OutputsProduct> table, TableColumn<OutputsProduct, String> columnLot, TableColumn<OutputsProduct, Integer> columnQuantity) {
        currentTableOutput = table;
        columnLot.setCellValueFactory(new PropertyValueFactory<OutputsProduct, String>("loteId"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<OutputsProduct, Integer>("quantity"));
    }

    public static void fillTableOrders(boolean urgent) {
        currentTableProducts.getItems().clear();
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
                currentTable.getItems().setAll(new Order(1, "04-09-2020", "256eienusn", "Panchito Fake", true));

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
            currentTableProducts.getItems().setAll(new ProductsRequest(1, "producto fake", 5));
        });

        productsTask.setOnFailed(e -> {
            ToastProvider.showToastError(e.getSource().getException().getMessage(), 1000);
        });
        assignColumnProductsId();
    }

    static void fillTablePresentations() {
        currentTablePresentation.getItems().clear();
        ToastProvider.showToastInfo("Espere porfavor: Obteniendo presentaciones del producto", 1000);
        Task<List<Presentation>> presentationsTask = new Task<List<Presentation>>() {
            @Override
            protected List<Presentation> call() throws Exception {
                ServicePackaging.getLotsByProduct(currentProduct.getProduct_id());
                return ServicePackaging.getPresentations(currentOrder.getOrderId(), currentProduct.getProduct_id());
            }
        };
        Thread thread = new Thread(presentationsTask);
        thread.setDaemon(true);
        thread.start();
        presentationsTask.setOnSucceeded(e -> {
            currentTablePresentation.getItems().setAll(presentationsTask.getValue());
            currentTablePresentation.getItems().setAll(new Presentation(1, 1, 50, 1, "gramos", "3.00"),
                   new Presentation(1, 1, 65, 2, "kilos", "3.00"));
        });

        presentationsTask.setOnFailed(e -> {
            ToastProvider.showToastError(e.getSource().getException().getMessage(), 3000);
        });
        assignColumnPresentationsId();
    }

    static void fillLots() {
        ObservableList<PackagingLots> lotsProducts = FXCollections.observableArrayList();
        Task<List<PackagingLots>> taskLots = new Task<List<PackagingLots>>() {
            @Override
            protected List<PackagingLots> call() throws Exception {
                return ServicePackaging.getLotsByProduct(currentProduct.getProduct_id());
            }
        };
        Thread thread = new Thread(taskLots);
        thread.setDaemon(true);
        thread.start();
        taskLots.setOnSucceeded(e -> {
            ToastProvider.showToastSuccess("Lotes obtenidos", 1500);
            if (taskLots.getValue().size() > 0) {
                lots.setDisable(false);
                lotsProducts.addAll(taskLots.getValue());
            } else {
                ToastProvider.showToastInfo("No existen lotes para el producto", 1500);
                lots.setDisable(false);
               lotsProducts.addAll(new PackagingLots(1, "lote1", 1, 1, "gramos", "super", 2.3),
                       new PackagingLots(1, "lote2", 1, 1, "kilos", "super2", 2.3));
            }

        });

        taskLots.setOnFailed(event -> {
            ToastProvider.showToastError(event.getSource().getException().getMessage(), 1500);
            lots.setDisable(true);
        });

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

        if (ItemFormValidator.isValidSelector(lots, errorLots) && ItemFormValidator.isValidInputNumber(unitsToTake, errorQuantity)) {
            int units = Integer.parseInt(unitsToTake.getText());
            OutputsProduct currentItem = currentTableOutput.getItems().stream().filter(item -> item.getPresentationId() == currentPresentation.getPresentation()).findAny().orElse(null);
            tagUnits.setText("" + (Integer.parseInt(tagUnits.getText()) - units));
            List presentations  = currentTablePresentation.getItems().stream().map(presentation -> {
                if(presentation.getPresentation() == currentPresentation.getPresentation()){
                    presentation.setUnits(presentation.getUnits()- units );
                }
                return  presentation;
            }).collect(Collectors.toList());
            currentTablePresentation.getItems().clear();
            currentTablePresentation.getItems().addAll(presentations);

            if (currentItem == null) {
                OutputsProduct item = new OutputsProduct(lots.getValue().getLoteId(), currentPresentation.getSubOrderId(), units, currentPresentation.getPresentation());
                currentTableOutput.getItems().add(item);
            } else {
                List<OutputsProduct> newItems = currentTableOutput.getItems().stream().map(output -> {
                    if (output.getPresentationId() == currentPresentation.getPresentation()) {
                        output.setQuantity(output.getQuantity() + units);
                    }
                    return output;
                }).collect(
                        Collectors.toList()
                );
                currentTableOutput.getItems().clear();
                currentTableOutput.getItems().addAll(newItems);
            }
            unitsToTake.setText("");
        }

    }

    public static void saveOutputLots(Method method) {
       if(currentTableOutput.getItems().size() >0){
           ToastProvider.showToastInfo("Registrando salidas de lotes", 1500);
           Task<Boolean> taskOutputs = new Task<Boolean>() {
               @Override
               protected Boolean call() throws Exception {
                   return ServicePackaging.registerOutputsLots(currentTableOutput.getItems());
               }
           } ;
           Thread thread = new Thread(taskOutputs);
           thread.setDaemon(true);
           thread.start();

           taskOutputs.setOnSucceeded(e->{
               currentTableOutput.getItems().clear();
               method.method();
           });

           taskOutputs.setOnFailed(e->{
               ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
           });
       }else {
           ModalProvider.showModalInfo("Es necesario agregar lotes para realizar el registro");
       }
    }
}
