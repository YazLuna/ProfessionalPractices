package gui.login.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import dataaccess.LoginAccountDAOImpl;
import gui.FXMLGeneralController;
import logic.ValidateAddUser;

public class FXMLFirstLoginController extends FXMLGeneralController implements Initializable {
    @FXML  private TextField tfPassword;
    @FXML  private TextField tfUser;
    @FXML  private TextField tfPasswordConfirm;
    @FXML  private Label lbUser;
    public static String password;
    public static String userName;
    private boolean isPractitioner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValidateAddUser validateAddUser = new ValidateAddUser();
        isPractitioner = validateAddUser.validateEnrollment(userName.toUpperCase());
        if(isPractitioner){
            tfUser.setVisible(false);
            lbUser.setText("Nombre de usuario: "+ userName);
            tfUser.setText(userName);
        }
        limitTextField(tfUser,50);
        limitTextField(tfPassword,20);
        limitTextField(tfPasswordConfirm,20);
    }

    public void saveChanges() {
        removeStyle();
        boolean passwordEquals = validatePasswords();
        boolean validate= validateFormat();
        boolean loginAccountNew = validateLoginAccountNew();
        if(passwordEquals && validate && loginAccountNew){
            String passwordNew = encryptPassword(tfPassword.getText());
            String userNameNew= tfUser.getText();
            LoginAccountDAOImpl login = new LoginAccountDAOImpl();
            boolean updateSuccessful;
            if(isPractitioner){
                updateSuccessful = login.updateLoginAccountPractitioner(userName,password,passwordNew);
            } else {
                updateSuccessful= login.updateLoginAccount(userName,password,passwordNew,userNameNew);
            }
            if(updateSuccessful){
                logOutGeneral();
                generateInformation("Cambios guardados correctamente");
            }else{
                removeStyle();
                generateError("Nombre de usuario no disponible");
            }
        }
    }

    private boolean validateLoginAccountNew() {
        boolean validate = true;
        if(password.equals(encryptPassword(tfPassword.getText()))){
            validate = false;
            tfPassword.getStyleClass().remove("ok");
            tfPasswordConfirm.getStyleClass().remove("ok");
            tfPassword.getStyleClass().add("error");
            tfPasswordConfirm.getStyleClass().add("error");
            generateError("Tienes que ingresar una contraseña diferente a la anterior ");
        }
        if(!isPractitioner){
            if(userName.equalsIgnoreCase(tfUser.getText())){
                validate = false;
                tfUser.getStyleClass().remove("ok");
                tfUser.getStyleClass().add("error");
                generateError("Tienes que ingresar un nombre de usuario diferente al anterior ");
            }
        }
        return validate;
    }

    private void removeStyle() {
        tfPassword.getStyleClass().remove("ok");
        tfPasswordConfirm.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("error");
        tfPasswordConfirm.getStyleClass().remove("error");
        tfUser.getStyleClass().remove("ok");
        tfUser.getStyleClass().remove("error");
    }

    public boolean validateFormat() {
        boolean validation = true;
        ValidateAddUser validateAddUser = new ValidateAddUser();
        if (validateAddUser.validateEmpty(tfUser.getText())) {
            tfUser.getStyleClass().add("ok");
        } else {
            tfUser.getStyleClass().add("error");
            validation = false;
        }
        if (!validateAddUser.validateEmpty(tfPassword.getText())) {
            tfPassword.getStyleClass().add("error");
            validation = false;
        }

        if (!validateAddUser.validateEmpty(tfPasswordConfirm.getText())) {
            tfPasswordConfirm.getStyleClass().add("error");
            validation = false;
        }
        return validation;
    }

    private boolean validatePasswords() {
        boolean validation = false;
        ValidateAddUser validateAddUser = new ValidateAddUser();
        removeStyle();
        if(tfPassword.getText().equals(tfPasswordConfirm.getText()) && (validateAddUser.validateEmpty(tfPassword.getText())) &&
                (validateAddUser.validateEmpty(tfPasswordConfirm.getText()))){
            tfPassword.getStyleClass().add("ok");
            tfPasswordConfirm.getStyleClass().add("ok");
            validation = true;
        }else{
            tfPassword.getStyleClass().add("error");
            tfPasswordConfirm.getStyleClass().add("error");
            if(validateAddUser.validateEmpty(tfPassword.getText()) || validateAddUser.validateEmpty(tfPasswordConfirm.getText())){
                generateError("Las contraseñas no son iguales");
            }
        }
        return validation;
    }

    public void returnLogin() {
        logOutGeneral();
    }
}
