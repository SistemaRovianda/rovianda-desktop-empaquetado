package com.rovianda.app.features.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.rovianda.app.shared.provider.EnvironmentActions;
import com.rovianda.app.shared.service.auth.AuthService;
import com.rovianda.app.shared.validator.DataValidator;
import com.rovianda.app.shared.validator.ItemFormValidator;
import com.rovianda.utility.animation.Fade;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginCtrl implements Initializable {

    @FXML
    private AnchorPane loginContainer;

    @FXML
    private JFXTextField inputEmail;

    @FXML
    private JFXPasswordField  inputPassword;

    @FXML
    private Label labelEmail, labelPassword,labelSpinner;

    @FXML
    private JFXButton buttonLogin,
            exit,
            minimize;

    @FXML
    private JFXSpinner spinner;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EnvironmentActions.exitAction(exit);
        loginContainer.setOpacity(0);
        Fade.visibleElement(loginContainer,1000);
        DataValidator.emailValidator(inputEmail,labelEmail, ()-> updateButton() );
        DataValidator.passwordValidator(inputPassword,labelPassword,()-> updateButton());
        inputPassword.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.ENTER){
                if(ItemFormValidator.isValidInput(inputEmail,labelEmail)
                        && ItemFormValidator.isValidInput(inputPassword,labelPassword))
                    this.onClick();
            }
        });
    }

    @FXML
    void minimizeScreen(){
        EnvironmentActions.minimizeAction();
    }

    @FXML
    private void onClick(){

        buttonLogin.setDisable(true);
        labelSpinner.setVisible(false);
        labelSpinner.setText("");
        spinner.setVisible(true);
       Task <String> login = new Task<String>() {
           String message ;
           @Override
           protected String call()  {
              try{
                  if (AuthService.singIn(inputEmail.getText(),inputPassword.getText()))
                      message = "success";
              }catch (Exception e ){
                  message = e.getMessage();
              }
              return message;
           }
       };
       Thread thread = new Thread(login);
       thread.setDaemon(true);
       thread.start();
       login.setOnSucceeded(e-> {
           login.cancel();
           switch (login.getValue()){
               case "success":
                    checkUser();
                   break;
               case "INVALID_PASSWORD":
                   spinner.setVisible(false);
                   labelSpinner.setText("Contraseña inválida");
                   labelSpinner.setVisible(true);
                   break;
              case "EMAIL_NOT_FOUND":
                  spinner.setVisible(false);
                   labelSpinner.setText("Correo eléctronico no válido");
                   labelSpinner.setVisible(true);
                   break;
           }
       });
    }

    @FXML
    private void  updateButton(){
        buttonLogin.setDisable(labelEmail.isVisible() || labelPassword.isVisible()
                || inputPassword.getText().equals("")|| inputEmail.getText().equals(""));
    }

    private void checkUser(){
        Task<String> getUserInfo = new Task<String>() {
            String message;
            @Override
            protected String call() throws Exception {
                try{
                   if(AuthService.getInfoUser())
                       message ="success";
                }catch (Exception e){
                    message = e.getMessage();
                }
                return message;
            }
        };
        Thread thread = new Thread(getUserInfo);
        thread.setDaemon(true);
        thread.start();
        getUserInfo.setOnSucceeded(e ->{
            getUserInfo.cancel();
            spinner.setVisible(false);
            if (getUserInfo.getValue().equals("success"))
                changeScene();
            else {
                labelSpinner.setText(getUserInfo.getValue());
                labelSpinner.setVisible(true);
            }
        });

    }

    private void  changeScene(){
        Fade.invisibleElement(loginContainer, 500, () ->{
            try{
                Parent anotherView   = (AnchorPane) FXMLLoader.
                        load(getClass().getResource("/com/rovianda/app/features/menuPacking/RegisterProduct.fxml"));
                Scene newScene = new Scene(anotherView);
                Stage currentStage = (Stage) loginContainer.getScene().getWindow();
                currentStage.setScene(newScene);
            }catch (Exception e){
                labelSpinner.setText("Error carga pantalla comunicarse con soporte");
                labelSpinner.setVisible(true);
            }
        });
    }

}
