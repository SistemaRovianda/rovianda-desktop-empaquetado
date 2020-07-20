package com.rovianda.app.features.menuPacking;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.rovianda.app.shared.models.OutputLots;
import com.rovianda.app.shared.models.ProductCatalog;
import com.rovianda.app.shared.models.ProductPresentation;
import com.rovianda.app.shared.provider.DataComboBox;
import com.rovianda.app.shared.provider.ResponsiveBorderPane;
import com.rovianda.app.shared.service.auth.AuthService;
import com.rovianda.app.shared.validator.DataValidator;
import com.rovianda.utility.animation.Fade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterProductCtrl implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    Pane register,request,addUser,reprocessing;

    @FXML
    private JFXComboBox <ProductCatalog>  productId;

    @FXML JFXComboBox <OutputLots> lotId;

   @FXML JFXComboBox <ProductPresentation> presentation;

    @FXML
    BorderPane containerRegister,containerRequest,containerAddUser,containerReprocessing;

    @FXML
    private JFXButton buttonRegister, buttonRequest;

    @FXML
    private DatePicker currentDate,expirationDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResponsiveBorderPane.addSizeBorderPane(containerRegister);
        ResponsiveBorderPane.addSizeBorderPane(containerRequest);
        ResponsiveBorderPane.addSizeBorderPane(containerAddUser);
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
    void onUser(){
        addUser.toFront();
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
        System.out.println("Agredando registro");
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
    }

    @FXML
    void selectProduct(){
        DataComboBox.fillLotsById(lotId,productId.getValue().getProductId());
        DataComboBox.fillPresentationsById(presentation,productId.getValue().getProductId());
    }
}
