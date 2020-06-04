package gui.administrator.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import logic.ValidateAddUser;
import domain.Coordinator;
import gui.FXMLGeneralController;
import domain.Gender;

/**
 * DAO User
 * @author Yazmin
 * @version 03/06/2020
 */

public class FXMLRegisterCoordinatorController extends FXMLGeneralController implements Initializable  {
    public ImageView imgProfilePicture;
    @FXML private TextField tfStaffNumber;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfUserName;
    @FXML private TextField tfPassword;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private Button btnCancel;
    @FXML private Button btnRegister;
    private File imgFile;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        generateCancel("¿Deseas cancelar?",btnCancel,"/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }

    public void loadProfilePicture() {
        loadImage();
    }

    public void register() throws SQLException {
        boolean validate;
        boolean registerComplete;
        removeStyle();
        validate = validate();
        if(validate){
            Coordinator coordinator = new Coordinator();
            createObjectCoordinator(coordinator);
            registerComplete = coordinator.addCoordinator();
            if(registerComplete){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRegister);
                generateInformation("Registro Exitoso");
            }else{
                generateError("Este coordinador ya esta registrado");
            }
        }
    }

    private void setLimitsTextFields() {
        limitTextField(tfName,30);
        limitTextField(tfLastName,30);
        limitTextField(tfEmail,50);
        limitTextField(tfAlternateEmail,50);
        limitTextField(tfPhone,10);
        limitTextField(tfUserName,50);
        limitTextField(tfPassword,20);
        limitTextField(tfStaffNumber,10);
    }

    public void removeStyle(){
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfUserName.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
    }

    public boolean validate(){
        boolean validation= true;
        if((validateAddUser.validateEmpty(tfStaffNumber.getText())) && (validateAddUser.validateStaffNumber(tfStaffNumber.getText()))){
            tfStaffNumber.getStyleClass().add("ok");
        }else{
            tfStaffNumber.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfName.getText())) && (validateAddUser.validateName(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfLastName.getText())) && (validateAddUser.validateLastName(tfLastName.getText()))) {
            tfLastName.getStyleClass().add("ok");
        }else{
            tfLastName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfEmail.getText())) && (validateAddUser.validateEmail(tfEmail.getText()))) {
            tfEmail.getStyleClass().add("ok");
        }else{
            tfEmail.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfAlternateEmail.getText())) && (validateAddUser.validateEmail(tfAlternateEmail.getText()))) {
            tfAlternateEmail.getStyleClass().add("ok");
        }else{
            tfAlternateEmail.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfPhone.getText())) && (validateAddUser.validatePhone(tfPhone.getText()))) {
            tfPhone.getStyleClass().add("ok");
        }else{
            tfPhone.getStyleClass().add("error");
            validation = false;
        }

        if(validateAddUser.validateEmpty(tfPassword.getText())) {
            tfPassword.getStyleClass().add("ok");
        }else{
            tfPassword.getStyleClass().add("error");
            validation = false;
        }

        if(validateAddUser.validateEmpty(tfUserName.getText())) {
            tfUserName.getStyleClass().add("ok");
        }else{
            tfUserName.getStyleClass().add("error");
            validation = false;
        }

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
            rbMale.getStyleClass().add("error");
            rbFemale.getStyleClass().add("error");
            generateAlert("Select the gender");
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
                rbMale.getStyleClass().add("error");
                rbFemale.getStyleClass().add("error");
                generateAlert("Select only one gender");
            }
        }

        return validation;
    }

    private void createObjectCoordinator(Coordinator coordinator) {
        Date registerDate = new Date();
        String passwordEncryption;
        coordinator.setStaffNumber(Integer.parseInt(validateAddUser.deleteAllSpace(tfStaffNumber.getText())));
        coordinator.setName(validateAddUser.deleteSpace(tfName.getText()));
        coordinator.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
        coordinator.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
        coordinator.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
        coordinator.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
        coordinator.setUserName(tfUserName.getText());
        passwordEncryption = encryptPassword(tfPassword.getText());
        coordinator.setPassword(passwordEncryption);
        if(rbMale.isSelected()){
            coordinator.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                coordinator.setGender(Gender.FEMALE.getGender());
            }
        }
        coordinator.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(registerDate));
        coordinator.setProfilePicture(imgFile);
    }

}
