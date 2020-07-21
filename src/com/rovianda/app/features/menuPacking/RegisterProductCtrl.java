package com.rovianda.app.features.menuPacking;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.shared.models.*;
import com.rovianda.app.shared.provider.DataComboBox;
import com.rovianda.app.shared.provider.ResponsiveBorderPane;
import com.rovianda.app.shared.provider.TableViewRegister;
import com.rovianda.app.shared.service.auth.AuthService;
import com.rovianda.app.shared.validator.DataValidator;
import com.rovianda.utility.animation.Fade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegisterProductCtrl implements Initializable {

    @FXML
    private TableView<TableRegisterProduct> tableRegister;

    @FXML
    private TableColumn <TableRegisterProduct,String>
            columnProduct,
            columnLot,
            columnExpiration,
            columnPresentation,
            columnUnity,
            columnWeight,
            columnUser,
            columnObservations;

    @FXML
    private AnchorPane container;
    @FXML
    Pane register,request,
            reprocessing;

    @FXML
    private JFXComboBox <ProductCatalog>  productId;

    @FXML
    JFXTextField lotId,weight;

   @FXML JFXComboBox <ProductPresentation> presentation;

   @FXML JFXComboBox <Unity>  units;

    @FXML
    BorderPane containerRegister,containerRequest,containerReprocessing;

    @FXML
    private JFXButton buttonRegister, buttonRequest;

    @FXML
    private DatePicker currentDate,expirationDate;

    @FXML
    private JFXTextArea observation;

    private TableRegisterProduct item;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResponsiveBorderPane.addSizeBorderPane(containerRegister);
        ResponsiveBorderPane.addSizeBorderPane(containerRequest);
        ResponsiveBorderPane.addSizeBorderPane(containerReprocessing);
        buttonRegister.getStyleClass().add("tap-selected");
        register.toFront();
        Fade.visibleElement(container,1000);
        initializePaneRegister();
    }

    @FXML
    private void onRegister(){
        buttonRegister.getStyleClass().add("tap-selected");
        buttonRequest.getStyleClass().remove("tap-selected");
        register.toFront();
    }
    @FXML
    private void onRequest(){
        buttonRequest.getStyleClass().add("tap-selected");
        buttonRegister.getStyleClass().remove("tap-selected");
        request.toFront();
    }

    @FXML
    private void onExit(){
        AuthService.SignOutSession();
        Fade.invisibleElement(container,500,()->{
            try {
                Parent loginScreen = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/rovianda/app/features/login/Login.fxml"));
                Scene newScene = new Scene(loginScreen);
                Stage currentStage = (Stage) container.getScene().getWindow();
                currentStage.setScene(newScene);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @FXML
    void onSave(){
        System.out.println("Guardando registro");
    }

    @FXML
    void onReprocessing(){
        reprocessing.toFront();
    }

    @FXML
    void onPrintReport(){
        System.out.println("Impriminedo reporte");
    }

    @FXML
    void addRegister(){
        productId.setDisable(true);
        expirationDate.setDisable(true);
        item = new TableRegisterProduct();
        item.setProduct(productId.getValue().getName());
        item.setLot(lotId.getText());
        item.setExpiration(expirationDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        item.setPresentation(/*presentation.getValue().getTypePresentation()*/"Example");
        item.setUnits(units.getValue().getTag());
        item.setWeight(weight.getText());
        item.setUser(User.getInstance().getFullName());
        item.setObservations(observation.getText());
        TableViewRegister.addItem(item);

    }

    @FXML
    void onCancelAddUser(){
        register.toFront();
    }
    @FXML
    void onSaveUser(){
        System.out.println("agregando usuario");
    }
    @FXML
    void onCancelReprocessing(){
        register.toFront();
    }
    @FXML
    void  onSaveReprocessing (){

    }

    private void initializePaneRegister(){
        currentDate.setValue(LocalDate.now());
        expirationDate.setValue(LocalDate.now().plusMonths(2));
        DataValidator.minDate(expirationDate,LocalDate.now());
        DataComboBox.FillProductCatalog(productId);
        DataComboBox.fillUnits(units);
        buildTableRegister();

    }

    @FXML
    void selectProduct(){
        DataComboBox.fillPresentationsById(presentation,productId.getValue().getProductId());
        lotId.setText(productId.getValue().getLot());
    }

    private void  buildTableRegister(){

        TableViewRegister.currentTable = tableRegister;
        TableViewRegister.assignColumnProduct(columnProduct);
        TableViewRegister.assignColumnLot(columnLot);
        TableViewRegister.assignColumnExpiration(columnExpiration);
        TableViewRegister.assignColumnPresentation(columnPresentation);
        TableViewRegister.assignColumnUnity(columnUnity);
        TableViewRegister.assignColumnWeight(columnWeight);
        TableViewRegister.assignColumnUser(columnUser);
        TableViewRegister.assignColumnObservations(columnObservations);
    }
}
