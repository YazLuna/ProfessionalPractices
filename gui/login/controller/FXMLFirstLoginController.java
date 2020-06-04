package gui.login.controller;

import dataaccess.LoginAccountImpl;
import domain.User;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.ValidateAddUser;

import java.net.URL;
import java.util.ResourceBundle;


public class FXMLFirstLoginController extends FXMLGeneralController implements Initializable {
    @FXML  private Button btnSaveChanges;
    @FXML  private TextField tfPassword;
    @FXML  private TextField tfUser;
    public static String password;
    public static String userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitTextField(tfUser,50);
        limitTextField(tfPassword,20);
        tfUser.setText(userName);
    }

    public void saveChanges() {
        boolean validate= validate();
        if(validate){
            String password = encryptPassword(tfPassword.getText());
            String userName= tfUser.getText();
            LoginAccountImpl login = new LoginAccountImpl();
            //login.updateLoginAccount()
        }

    }

    public boolean validate() {
        boolean validation = true;
        ValidateAddUser validateAddUser = new ValidateAddUser();
        if ((validateAddUser.validateEmpty(tfUser.getText()))) {
            tfUser.getStyleClass().add("ok");
        } else {
            tfUser.getStyleClass().add("error");
            validation = false;
        }

        if ((validateAddUser.validateEmpty(tfPassword.getText()))) {
            tfPassword.getStyleClass().add("ok");
        } else {
            tfPassword.getStyleClass().add("error");
            validation = false;
        }
        return  validation;
    }
    public void returnLogin() {
        logOutGeneral();
    }
}
