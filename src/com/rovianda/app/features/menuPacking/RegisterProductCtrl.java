package com.rovianda.app.features.menuPacking;

import com.jfoenix.controls.*;
import com.rovianda.app.shared.models.*;
import com.rovianda.app.shared.provider.*;
import com.rovianda.app.shared.service.auth.AuthService;
import com.rovianda.app.shared.service.weight.WeightService;
import com.rovianda.app.shared.validator.DataValidator;
import com.rovianda.app.shared.validator.ItemFormValidator;
import com.rovianda.utility.animation.Fade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterProductCtrl implements Initializable {

    @FXML
    private TableView<TableRegisterProduct> tableRegister;

    @FXML
    private TableColumn<TableRegisterProduct, String>
            columnProduct,
            columnLot,
            columnExpiration,
            columnPresentation,
            columnUnity,
            columnWeight,
            columnUser,
            columnObservations;

    /*@FXML
    private TableView<ProductsRequest> productsRequest;*/

    /*@FXML
    private TableColumn<ProductsRequest, Integer> columnRequestId;*/

    /*@FXML
    private TableColumn<ProductsRequest, String> columnRequestProduct;*/

    @FXML
    private TableView<Order> tableOrders;

    @FXML
    private TableView<OutputsProduct> OutputsProductTake;

    @FXML
    private TableColumn<OutputsProduct, String> columnOutputProductLot;
    @FXML
    private TableColumn<OutputsProduct, Integer> columnOutputProductQuantity;

    @FXML
    private TableColumn<OutputsProduct,Double> columnOutputProductWeight;

    @FXML
    private TableColumn<Order, String>
            columnVendedor, columnDate;
    @FXML
    private TableColumn<Order, JFXButton>
            columnOptions;
    @FXML
    private TableColumn<Order, Integer>
            columnNoOrder;

    @FXML
    private TableView<Presentation> tablePresentations;

    @FXML
    private TableColumn<Presentation, Integer>
            columnPresentationsNo, columnPresentationUnits;

    @FXML
    private TableColumn<Presentation, String>
            columnPresentationName,columnProductName;

    @FXML
    private AnchorPane container;
    @FXML
    Pane register, request,
            reprocessing, modal,
            presentations, returns;

    @FXML
    private JFXComboBox<ProductCatalog> productId;

    @FXML
    private JFXComboBox<ProductCatalogReturn> productReturn;

    @FXML
    private JFXComboBox<PresentationReturn> presentationReturn;

    @FXML
    private JFXComboBox<LotsPresentationsDevolutions> lotsDevolutions;

    @FXML
    private JFXComboBox<OptionOrder> urgent;

    @FXML
    private JFXComboBox<PackagingLots> lotsPresentations;



    @FXML
    JFXTextField lotId, weight, lotReprocessing, weightReprocessing,
            allergenReprocessing, units, unitsToTake,
            quantityReturn,weightPresentations,quantityAvaiableToReturn;

    @FXML
    JFXComboBox<ProductPresentation> presentation;

    @FXML
    BorderPane containerRegister, containerRequest, containerReprocessing, containerModal,
            containerPresentations, containerReturns;

    @FXML
    private JFXButton buttonRegister,
            buttonRequest,
            buttonReprocessing,
            buttonReturns,
            btnSaveProduct,
            btnSaveReprocessing,
            btnModalCancel,
            btnModalAccept,
            buttonPresentationsToTake,
            btnOutput,btnCloseOrder,
            btnSaveReturn,
            btnReturnProduct,
            btnCloseLotRegister,
            btnObservationsShow,
            btnSaveReportReprocessing;

    @FXML
    private JFXSpinner spinnerReprocessing;

    @FXML
    private DatePicker currentDate, expirationDate, dateReprocessing,dateReturn;

    @FXML
    private JFXTextArea observation,commentsReprocesing,observationsOven;

    private ProductPackaging productPackaging;

    private  List<Product> products = new ArrayList<>();

    @FXML
    private Label weightError, errorReproAllergen, errorReproLot, labelModal,
            errorPresentation, errorUnits, errorWeight, errorObservations, errorProductId,
            presentationProduct,
            presentationsQuantity,
            errorPresentationLot,
            errorPresentationQuantity,
            errorProductReturn,errorPresentationReturn,errorReturnLot,errorQuantityReturn,
            errorPresentationWeight;

    boolean activeProcess = false;
    boolean activeDeliver = false;
    boolean tapRegister = true;
    boolean tapReprocessing = false;
    boolean tapReturns = false;
    public static boolean activePresentations = false;

    boolean start= false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResponsiveBorderPane.addSizeBorderPane(containerRegister);
        ResponsiveBorderPane.addSizeBorderPane(containerRequest);
        ResponsiveBorderPane.addSizeBorderPane(containerReprocessing);
        ResponsiveBorderPane.addSizeBorderPane(containerModal);
        ResponsiveBorderPane.addSizeBorderPane(containerPresentations);
        ResponsiveBorderPane.addSizeBorderPane(containerReturns);
        TableViewOrders.presentations = presentations;
        ReportProvider.currentContainer = register;
        buildTableRegister();
        buildTableRequest();
        buildTablePresentations();
        buildPaneRegister();
        initializePaneRegister();
        buttonRegister.getStyleClass().add("tap-selected");
        onRegister();
        Fade.visibleElement(container, 1000);
        initModal();
    }

    @FXML
    private void onRegister() {
        if(activeDeliver)
                ModalProvider.showModal("No has realizado el registro, ¿Seguro que quieres cambiar de ventana?", this::changeToRegist);
            else
                changeToRegist();
    }

    private void changeToRegist(){
        register.toFront();
        if (!tapRegister) {
            initializePaneRegister();
            buttonRegister.getStyleClass().add("tap-selected");
            buttonRequest.getStyleClass().remove("tap-selected");
            buttonReturns.getStyleClass().remove("tap-selected");
            buttonReprocessing.getStyleClass().remove("tap-selected");
            tapRegister = true;
            tapReprocessing = false;
            tapReturns = false;
            activePresentations = false;
        }
    }

    @FXML
    private void onReturns(){
        if (activeProcess  || activeDeliver)
            ModalProvider.showModal("No has realizado el registro, ¿Seguro que quieres cambiar de ventana?", this::changeToReturns);
        else
            changeToReturns();
    }


    private void changeToReturns() {
        if (!tapReturns) {
            buttonRegister.getStyleClass().remove("tap-selected");
            buttonRequest.getStyleClass().remove("tap-selected");
            buttonReprocessing.getStyleClass().remove("tap-selected");
            buttonReturns.getStyleClass().add("tap-selected");
            tapReturns = true;
            tapReprocessing = false;
            tapRegister = false;
            returns.toFront();
            initializePaneReturns();
        }
    }

    private void initializePaneReturns()  {
        ModalProvider.currentContainer = returns;
        presentationReturn.setDisable(true);
        errorQuantityReturn.setVisible(false);
        errorProductReturn.setVisible(false);
        errorPresentationReturn.setVisible(false);
        errorReturnLot.setVisible(false);
        //lotReturn.setText("");
        quantityReturn.setText("");
        quantityAvaiableToReturn.setText("0");
        quantityAvaiableToReturn.setDisable(true);
        lotsDevolutions.setDisable(true);
        DataValidator.numberValidate(quantityReturn,errorQuantityReturn);
        DataComboBox.fillProductsCatalogReturn(productReturn);
        ItemFormValidator.isValidSelectorFocus(productReturn,errorProductReturn);
        ItemFormValidator.isValidSelectorFocus(presentationReturn,errorPresentationReturn);
        //ItemFormValidator.isValidInputFocus(lotReturn,errorReturnLot);
        ItemFormValidator.isValidInputFocus(quantityReturn,errorQuantityReturn);
        dateReturn.setValue(LocalDate.now());
        btnSaveReturn.setVisible(false);
        presentationReturn.setValue(null);
        btnReturnProduct.setDisable(false);
    }

    @FXML
    private void onRequest() {
        if (activeProcess || activeDeliver) {
            ModalProvider.showModal("No has realizado el registro, ¿Seguro que quieres cambiar de ventana?", this::changeTap);
        } else {
            changeTap();
        }
    }

    private void changeTap() {
        if (tapRegister || activePresentations || tapReprocessing ||tapReturns) {
            if (!activePresentations) {
                buttonRequest.getStyleClass().add("tap-selected");
                buttonRegister.getStyleClass().remove("tap-selected");
                buttonReturns.getStyleClass().remove("tap-selected");
                buttonReprocessing.getStyleClass().remove("tap-selected");
            }
            tapRegister = false;
            tapReprocessing = false;
            tapReturns = false;
            activePresentations = false;
            activeProcess = false;
            activeDeliver=true;
            request.toFront();
            initializePaneRequest();

        }
    }

    @FXML
    private void onExit() {
        if (activeProcess || activeDeliver)
            ModalProvider.showModal("No has realizado el registro, ¿Seguro que quieres salir?", this::exit);
        else
            exit();
    }

    void exit() {
        AuthService.SignOutSession();
        Fade.invisibleElement(container, 500, () -> {
            try {
                Parent loginScreen = FXMLLoader.load(getClass().getResource("/com/rovianda/app/features/login/Login.fxml"));
                Scene newScene = new Scene(loginScreen);
                Stage currentStage = (Stage) container.getScene().getWindow();
                currentStage.setScene(newScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void onSave() {

        productPackaging.setProducts(products);
        for(Product item : products){
            System.out.println("Item: "+item.getWeight());
        }
        TableViewRegister.currentProductPackaging = productPackaging;
        TableViewRegister.registerItems(() -> {
            productId.setDisable(false);
            expirationDate.setDisable(false);
            btnSaveProduct.setDisable(true);
            lotId.setText("");
            products=new ArrayList<>();
            productId.setValue(null);
            activeProcess = false;
        });
    }

    @FXML
    void onReprocessing() {
        if (activeProcess || activeDeliver)
            ModalProvider.showModal("No has realizado el registro, ¿Seguro que quieres cambiar de ventana?", this::changeToReprocessing);
        else
            changeToReprocessing();

    }

    private void changeToReprocessing() {
        if (!tapReprocessing) {
            buttonRegister.getStyleClass().remove("tap-selected");
            buttonRequest.getStyleClass().remove("tap-selected");
            buttonReturns.getStyleClass().remove("tap-selected");
            buttonReprocessing.getStyleClass().add("tap-selected");
            tapReprocessing = true;
            tapRegister = false;
            tapReturns = false;
            reprocessing.toFront();
            initializePaneReprocessing();
        }
    }

    @FXML
    void onPrintReport() {
        if (TableViewRegister.packingId > 0) {
            ReportProvider.buildReport();
        } else {
            ModalProvider.showModalInfo("Es necesario realizar un registro de paquetes para obtener el reporte");
        }
    }

    @FXML
    void addRegister() {
        activeProcess = true;
        activeDeliver=false;
        if (ItemFormValidator.isValidSelector(productId, errorProductId)
                && ItemFormValidator.isValidSelector(presentation, errorPresentation)
                && ItemFormValidator.isValidInputNumber(units, errorUnits)
                && ItemFormValidator.isValidInputDecimal(weight, errorWeight)
            //&& ItemFormValidator.isValidInput(observation, errorObservations)
        ) {
            btnSaveProduct.setDisable(false);
            productId.setDisable(true);
            expirationDate.setDisable(true);
            List<TableRegisterProduct> items = TableViewRegister.items;
            Boolean founded = false;
            TableViewRegister.clearTable();
            for(int i=0;i<items.size();i++){
                TableRegisterProduct item = items.get(i);
                if(item.getLot().equals(lotId.getText()) && item.getExpiration().equals(expirationDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                && item.getPresentation().equals(presentation.getValue().getPresentation()+" "+presentation.getValue().getTypePresentation()) && item.getProduct().equals(productId.getValue().getName())){
                    founded=true;
                    item.setUnits(String.valueOf(Integer.parseInt(item.getUnits())+Integer.parseInt(units.getText())));
                    item.setWeight(String.valueOf(Float.parseFloat(item.getWeight())+Float.parseFloat(weight.getText())));
                    if(!observation.getText().isEmpty()) {
                        item.setObservations(item.getObservations() + "-" + observation.getText());
                    }
                }
                TableViewRegister.addItem(item);
            }


            if(founded==false) {
                TableRegisterProduct item = new TableRegisterProduct();
                item.setProduct(productId.getValue().getName());
                item.setLot(lotId.getText());
                item.setExpiration(expirationDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                item.setPresentation(presentation.getValue().getPresentation() + " " + presentation.getValue().getTypePresentation());
                item.setUnits(units.getText());
                item.setWeight(weight.getText());
                item.setUser(User.getInstance().getFullName());
                item.setObservations(observation.getText());
                TableViewRegister.addItem(item);
            }
            createItemRegister(founded);
        }


    }

    private void createItemRegister(boolean flag) {
        if(flag==false) {
            Product product = new Product();
            if (TableViewRegister.items.size() <= 1) {
                productPackaging.setRegisterDate(currentDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                productPackaging.setProductId(productId.getValue().getProductId());
                productPackaging.setLotId(lotId.getText());
                productPackaging.setExpiration(expirationDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            product.setPresentationId(presentation.getValue().getPresentationId());
            product.setWeight(Double.parseDouble(weight.getText()));
            product.setObservations(observation.getText());
            product.setUnits(Integer.parseInt(units.getText()));
            products.add(product);
            presentation.setValue(null);
            weight.setText("");
            errorWeight.setVisible(false);
            observation.setText("");
            units.setText("");
        }else{
            for(Product product : products){
                if(product.getPresentationId()==presentation.getValue().getPresentationId()){
                    product.setWeight(product.getWeight()+Double.parseDouble(weight.getText()));
                    product.setUnits(product.getUnits()+Integer.parseInt(units.getText()));
                    System.out.println("Modificado");
                }
            }
            presentation.setValue(null);
            weight.setText("");
            errorWeight.setVisible(false);
            observation.setText("");
            units.setText("");
        }
    }


    @FXML
    void onSaveReprocessing() {
        if (
                ItemFormValidator.isValidInputDecimal(weightReprocessing, weightError) &&
                        ItemFormValidator.isValidInput(lotReprocessing,errorReproLot)&&
                        ItemFormValidator.isValidInput(allergenReprocessing, errorReproAllergen))
            createItemRegisterReprocessing();
    }

    private void createItemRegisterReprocessing() {

        Reprocessing itemRegister = new Reprocessing();
        itemRegister.setAllergen(allergenReprocessing.getText());
        itemRegister.setDate(dateReprocessing.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        itemRegister.setLotId(lotReprocessing.getText());
        itemRegister.setComment(commentsReprocesing.getText());
        try {
            itemRegister.setWeight(Double.parseDouble(weightReprocessing.getText()));
        } catch (Exception e) {
            itemRegister.setWeight(0);
        }
        ReprocessingData.registerReprocessing(itemRegister, btnSaveReprocessing, spinnerReprocessing, () -> {
            this.initValueReprocessing();
            btnSaveReportReprocessing.setVisible(true);
            ToastProvider.showToastSuccess("Reproceso registrado exitosamente", 2000);
        });

    }

    private void initValueReprocessing() {
        allergenReprocessing.setText("");
        lotReprocessing.setText("");
        weightReprocessing.setText("");
        weightError.setVisible(false);
        errorReproAllergen.setVisible(false);
        errorReproLot.setVisible(false);
        commentsReprocesing.setText("");
        btnSaveReportReprocessing.setVisible(false);

    }

    private void buildPaneRegister() {
        DataValidator.numberValidate(units, errorUnits);
        DataValidator.decimalValidate(weight, errorWeight);
        ItemFormValidator.isValidSelectorFocus(productId, errorProductId);
        ItemFormValidator.isValidSelectorFocus(presentation, errorPresentation);
        ItemFormValidator.isValidInputFocus(units, errorUnits);
        ItemFormValidator.isValidInputFocus(weight, errorWeight);
        //ItemFormValidator.isValidInputFocus(observation, errorObservations);
    }

    private void initializePaneRegister() {
        ModalProvider.currentContainer = register;
        productPackaging = new ProductPackaging();
        errorPresentation.setVisible(false);
        errorUnits.setVisible(false);
        errorWeight.setVisible(false);
        errorObservations.setVisible(false);
        errorProductId.setVisible(false);
        currentDate.setValue(LocalDate.now());
        expirationDate.setValue(LocalDate.now().plusMonths(2));
        productId.setDisable(false);
        expirationDate.setDisable(false);
        btnSaveProduct.setDisable(true);
        DataValidator.minDate(expirationDate, LocalDate.now());
        DataComboBox.FillProductCatalog(productId);
        TableViewRegister.clearTable();
        presentation.setDisable(true);
        btnCloseLotRegister.setDisable(true);
        lotId.setText("");
        WeightService.start(weight,errorWeight);

    }

    private void initializePaneReprocessing() {
        ModalProvider.currentContainer = reprocessing;
        initValueReprocessing();
        dateReprocessing.setValue(LocalDate.now());
        DataValidator.decimalValidate(weightReprocessing, weightError);
        ItemFormValidator.isValidInputFocus(weightReprocessing, weightError);
        ItemFormValidator.isValidInputFocus(allergenReprocessing, errorReproAllergen);
        ItemFormValidator.isValidInputFocus(lotReprocessing,errorReproLot);
        WeightService.start(weightReprocessing,weightError);
    }

    private void initializePaneRequest() {
        ModalProvider.currentContainer = request;
        DataComboBox.fillOptions(urgent);
        this.urgent.getSelectionModel().selectFirst();
    }

    @FXML
    void selectProduct() {
        if (productId.getValue() != null) {
            DataComboBox.fillPresentationsById(presentation, productId.getValue().getProductId());
            lotId.setText(productId.getValue().getLot());
            //observationsOven.setText(productId.getValue().getObservations());
            btnCloseLotRegister.setDisable(false);
        }

    }

    @FXML
    void selectProductReturn() {
        if (productReturn.getValue() != null) {
            DataComboBox.fillPresentationsReturnsById(presentationReturn, productReturn.getValue().getId());
            lotsDevolutions.setValue(null);
            lotsDevolutions.setDisable(true);
            quantityAvaiableToReturn.setText("0");
        }

    }

    @FXML
    void selectProductPresentationReturn(){
        if(presentationReturn.getValue()!=null){
            lotsDevolutions.setDisable(false);
            DataComboBox.fillLotsPresentationsReturnsById(lotsDevolutions,presentationReturn.getValue().getId());
        }
    }

    @FXML
    void selectLotReturn(){
        if(lotsDevolutions.getValue()!=null){
            quantityAvaiableToReturn.setText(lotsDevolutions.getValue().getQuantity().toString());
        }
    }


    @FXML
    void changeOrder() {
        if (this.urgent.getValue() != null)
            TableViewOrders.fillTableOrders(this.urgent.getValue().isValue());
    }

    private void buildTableRegister() {

        TableViewRegister.currentTable = tableRegister;
        TableViewRegister.assignColumnProduct(columnProduct);
        TableViewRegister.assignColumnLot(columnLot);
        TableViewRegister.assignColumnExpiration(columnExpiration);
        TableViewRegister.assignColumnPresentation(columnPresentation);
        TableViewRegister.assignColumnUnity(columnUnity);
        TableViewRegister.assignColumnWeight(columnWeight);
        TableViewRegister.assignColumnUser(columnUser);
        TableViewRegister.assignColumnObservations(columnObservations);

        tableRegister.setOnMouseClicked(event->{
            if(event.getButton()== MouseButton.PRIMARY && event.getClickCount()==2) {
                int  index = tableRegister.getSelectionModel().getSelectedIndex();
                List<TableRegisterProduct> items = tableRegister.getItems();
                List<TableRegisterProduct> itemsMapped = new ArrayList<>();
                List<Product> productsExistTemp = new ArrayList<>();

                for(int i=0;i<items.size();i++){
                    if(i!=index){
                        itemsMapped.add(items.get(i));
                        productsExistTemp.add(products.get(i));
                    }
                }
                products=productsExistTemp;
                tableRegister.getItems().clear();
                tableRegister.getItems().addAll(itemsMapped);
            }
        });

    }

    private void buildTableRequest() {
        DataComboBox.fillOptions(urgent);
        //TableViewOrders.assignTableProducts(productsRequest);
        TableViewOrders.assignTableOrder(tableOrders);
        TableViewOrders.assignColumnNoOrder(columnNoOrder);
        TableViewOrders.assignColumnVendedor(columnVendedor);
        TableViewOrders.assignColumnOptions(columnOptions,this);
        TableViewOrders.assignColumnDate(columnDate);
        //TableViewOrders.columnId = columnRequestId;
        //TableViewOrders.assignColumnProductsProduct(columnRequestProduct);
    }

    private void buildTablePresentations() {
        TableViewOrders.assignTablePresentations(tablePresentations);
        TableViewOrders.assignColumnProductName(columnProductName);
        TableViewOrders.assignColumnPresentations(columnPresentationName);
        TableViewOrders.assignColumnUnits(columnPresentationUnits);
        TableViewOrders.lots = lotsPresentations;

        lotsPresentations.valueProperty().addListener(new ChangeListener<PackagingLots>() {
            @Override
            public void changed(ObservableValue<? extends PackagingLots> observable, PackagingLots oldValue, PackagingLots newValue) {
                if(newValue!=null) {
                    List<OutputsProduct> outputs=TableViewOrders.getLotsUsed(newValue.getPresentationId());

                    int totalUseds=0;
                    for(OutputsProduct output : outputs){
                        if(output.getLoteId().equals(newValue.getLoteId())) {
                            totalUseds += output.getQuantity();
                        }
                    }
                    presentationsQuantity.setText(String.valueOf(newValue.getQuantity()-totalUseds));
                }
            }
        });
        TableViewOrders.tagName = presentationProduct;
        TableViewOrders.tagUnits = presentationsQuantity;
        TableViewOrders.errorLots = errorPresentationLot;
        TableViewOrders.errorQuantity = errorPresentationQuantity;
        TableViewOrders.buttonAddUnitsToTake = buttonPresentationsToTake;
        TableViewOrders.buttonSaveOutput = btnOutput;
        TableViewOrders.errorPresentationWeight = errorPresentationWeight;
        TableViewOrders.weightPresentations = weightPresentations;
        TableViewOrders.buildTableOutputProduct(OutputsProductTake, columnOutputProductLot, columnOutputProductQuantity,columnOutputProductWeight);
        TableViewOrders.assignUnitsTextField(unitsToTake);
    }

    void initModal() {
        ModalProvider.cancel = btnModalCancel;
        ModalProvider.accept = btnModalAccept;
        ModalProvider.container = modal;
        ModalProvider.messageLabel = labelModal;
    }

    @FXML
    void saveOutputLots() {
        TableViewOrders.saveOutputLots(() -> {

        });
    }

    @FXML
    void closeOrder(){
        TableViewOrders.closeOrder(() -> {
            changeTap();
        });
    }

    @FXML
    void minimizeScreen(){
        EnvironmentActions.minimizeAction();
    }

    @FXML
    void getPesoReproceso() {
       WeightService.stop();
    }
    @FXML
    void getPesoRegistro(){
       WeightService.stop();
    }

    @FXML
    void onSaveReturn(){
        if(Double.parseDouble(quantityReturn.getText())<=lotsDevolutions.getValue().getQuantity()) {


            if (ItemFormValidator.isValidSelector(productReturn, errorProductReturn)
                    && ItemFormValidator.isValidSelector(presentationReturn, errorPresentationReturn)
                    //&& ItemFormValidator.isValidInput(lotReturn,errorReturnLot)
                    && ItemFormValidator.isValidInput(quantityReturn, errorQuantityReturn)
            ) {
                ModalProvider.showModal("¿Seguro que deseas realizar la devolución?", () -> {
                    ProductReturn productTemp = new ProductReturn();
                    productTemp.setUnits(Integer.parseInt(quantityReturn.getText()));
                    productTemp.setProductId(productReturn.getValue().getId());
                    productTemp.setPresentationId(presentationReturn.getValue().getId());
                    productTemp.setLotId(lotsDevolutions.getValue().getLotId());
                    productTemp.setDate(dateReturn.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    ReturnProductProvider.currentButton = btnReturnProduct;
                    ReturnProductProvider.DoReturnProduct(productTemp, btnSaveReturn);
                });
            }
        }else{
            ModalProvider.showModalInfo("No se puede devolver más del producto disponible");
        }
    }

    @FXML
    void onSaveReturnReport(){
        ModalProvider.showModal("Al descargar el reporte, se perdera la informacion,¿Deseas continuar?", () -> {
            ReportProvider.buildReportReturn(() -> {
                initializePaneReturns();
            });
        });
    }

    @FXML
    void onSaveReportReprocessing(){
        ModalProvider.showModal("Al descargar el reporte, se perdera la informacion,¿Deseas continuar?", () -> {
            ReportProvider.buildReportReprocessing(() -> {
                initializePaneReprocessing();
            });
        });
    }

    @FXML
    void getReportOrdens(){
        ReportProvider.buildReportOrderGeneral(urgent.getValue().isValue());
    }

    @FXML
    void closeLotProductRegister(){
        System.out.println(productId.getValue().getProductId());
        ModalProvider.showModal("Al cerrar el lote ya no se mostrará en el sistema, por lo cual asegúrese de que ya lleno los datos antes de cerrar el lote.", () -> {
            ReturnProductProvider.closeLotProduct(productId.getValue().getOvenId(),() -> {
                initializePaneRegister();
            });
        });
    }

    @FXML
    void showObservationsOven(){
        if(productId.getValue()!=null) {
            ModalProvider.showModalInfo("Observaciones: " + productId.getValue().getObservations());
        }
    }
}
