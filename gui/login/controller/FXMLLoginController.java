package gui.login.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dataaccess.LoginAccountImpl;
import dataaccess.UserMethodDAOImpl;
import gui.FXMLGeneralController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import logic.ValidateAddUser;

/**
 * DAO User
 * @author Yazmin
 * @version 18/05/2020
 */

public class FXMLLoginController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogin;
    @FXML private TextField tfUser;
    @FXML private PasswordField pfPassword;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limitTextField(tfUser,50);
        limitTextField(pfPassword,20);
    }

    public void login() throws IOException, SQLException {
        LoginAccountImpl loginA = new LoginAccountImpl();
        boolean login;
        String password;
        removeStyle();
        boolean validate= validate();
        if(validate){
            password= encryptPassword(pfPassword.getText());
            login = loginA.firstLogin(tfUser.getText(),password);
            if(login){
                FXMLFirstLoginController.password= password;
                FXMLFirstLoginController.userName= tfUser.getText();

                openWindowGeneral("/gui/login/fxml/FXMLFirstLogin.fxml",btnLogin);
            }else{
                login = loginA.searchLoginAccount(tfUser.getText(),password);
                if(login){
                    openMenu(tfUser.getText(),password);
                }else{
                    tfUser.getStyleClass().add("error");
                    pfPassword.getStyleClass().add("error");
                }
            }
        }
    }

    private void removeStyle() {
        tfUser.getStyleClass().remove("error");
        pfPassword.getStyleClass().remove("error");
        tfUser.getStyleClass().remove("ok");
        pfPassword.getStyleClass().remove("ok");
    }

    private boolean validate() {
        boolean validation = true;
        ValidateAddUser validateAddUser = new ValidateAddUser();
        if ((!validateAddUser.validateEmpty(tfUser.getText()))) {
            tfUser.getStyleClass().add("error");
            validation = false;
        }

        if ((!validateAddUser.validateEmpty(pfPassword.getText()))) {
            pfPassword.getStyleClass().add("error");
            validation = false;
        }
        return  validation;
    }

    private void openMenu(String user, String password) throws SQLException {
        LoginAccountImpl login = new LoginAccountImpl();
        String type= login.searchUserTypeWithLoginAccount(user,password);
        switch (type){
            case "Coordinator":
                openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnLogin);
                break;
            case "Administrator":
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnLogin);
                break;
            case "Teacher":
                openWindowGeneral("/gui/teacher/fxml/FXMLMenuTeacher.fxml",btnLogin);
                break;
            case "Practitioner":
                openWindowGeneral("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml",btnLogin);
                break;
        }
    }

}
