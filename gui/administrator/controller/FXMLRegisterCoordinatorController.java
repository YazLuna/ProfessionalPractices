package gui.administrator.controller;

import java.io.File;
import java.net.URL;
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
    public static File imgFile;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTextFields();
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        generateConfirmationCancel("¿Deseas cancelar?",btnCancel,"/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }

    public void loadProfilePicture() {
        loadImage();
    }

    public void register() {
        removeStyle();
        boolean validate = validate();
        if(validate){
            Coordinator coordinator = new Coordinator();
            createObjectCoordinator(coordinator);
            boolean registerComplete = coordinator.addCoordinator(coordinator);
            if(registerComplete){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRegister);
                generateInformation("Registro Exitoso");
            }else{
                generateError("Este coordinador ya esta registrado");
            }
        }
    }

    private void configureTextFields() {
        limitTextField(tfName,30);
        prohibitNumberTextField(tfName);
        limitTextField(tfLastName,30);
        prohibitNumberTextField(tfLastName);
        limitTextField(tfEmail,50);
        limitTextField(tfAlternateEmail,50);
        limitTextField(tfPhone,10);
        prohibitWordTextField(tfPhone);
        limitTextField(tfUserName,50);
        limitTextField(tfPassword,20);
        limitTextField(tfStaffNumber,10);
        prohibitWordTextField(tfStaffNumber);
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
        prohibitSpacesTextField(tfName);
        prohibitSpacesTextField(tfLastName);
        prohibitSpacesTextField(tfEmail);
        prohibitSpacesTextField(tfAlternateEmail);
        prohibitSpacesTextField(tfPhone);
        prohibitSpacesTextField(tfUserName);
        prohibitSpacesTextField(tfPassword);
        boolean validation= true;
        if((validateAddUser.validateEmpty(tfStaffNumber.getText()))){
            tfStaffNumber.getStyleClass().add("ok");
        }else{
            tfStaffNumber.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfName.getText()))) {
            String nameWithOutSpaces = validateAddUser.deleteSpace(tfName.getText());
            tfName.setText(nameWithOutSpaces);
            if(validateAddUser.validateName(tfName.getText())){
                tfName.getStyleClass().add("ok");
            }else{
                tfName.getStyleClass().add("error");
            }

        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfLastName.getText())) && (validateAddUser.validateNameUser(tfLastName.getText()))) {
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

        if((validateAddUser.validateEmpty(tfPhone.getText())) && (validateAddUser.validatePhoneNumber(tfPhone.getText()))) {
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
            generateAlert("Selecciona un género");
            rbMale.fire();
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
                rbMale.getStyleClass().add("error");
                rbFemale.getStyleClass().add("error");
                generateAlert("Selecciona solo un género");
            }
        }

        return validation;
    }

    private void createObjectCoordinator(Coordinator coordinator) {
        coordinator.setStaffNumber(Integer.parseInt(validateAddUser.deleteAllSpace(tfStaffNumber.getText())));
        coordinator.setName(validateAddUser.deleteSpace(tfName.getText()));
        coordinator.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
        coordinator.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
        coordinator.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
        coordinator.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
        coordinator.setUserName(tfUserName.getText());
        String passwordEncryption = encryptPassword(tfPassword.getText());
        coordinator.setPassword(passwordEncryption);
        if(rbMale.isSelected()){
            coordinator.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                coordinator.setGender(Gender.FEMALE.getGender());
            }
        }
        Date registerDate = new Date();
        coordinator.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(registerDate));
        coordinator.setProfilePicture(imgFile);
    }

}
