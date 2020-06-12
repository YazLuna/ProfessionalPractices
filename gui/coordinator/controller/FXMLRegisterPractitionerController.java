package gui.coordinator.controller;

import domain.Coordinator;
import domain.Gender;
import domain.Practitioner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import logic.ValidateAddUser;

public class FXMLRegisterPractitionerController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCancel;
    public ImageView imgProfilePicture;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfScore;
    @FXML private TextField tfEnrollment;
    @FXML private TextField tfPassword;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private Button btnRegister;
    public static File imgFile;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTextFields();
    }

    public void loadProfilePicture() {
        loadImage();
    }

    public void register() {
        removeStyle();
        boolean validate = validate();
        if(validate){
            Practitioner practitioner = new Practitioner();
            createObjectPractitioner(practitioner);
            boolean registerComplete = practitioner.addPractitioner();
            if(registerComplete){
                openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                generateInformation("Registro Exitoso");
            }else{
                generateError("Este practicante ya esta registrado");
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
        limitTextField(tfEnrollment,50);
        limitTextField(tfPassword,20);
    }

    public void removeStyle(){
        tfEnrollment.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
        tfScore.getStyleClass().remove("ok");
    }

    public boolean validate(){
        prohibitSpacesTextField(tfName);
        prohibitSpacesTextField(tfLastName);
        prohibitSpacesTextField(tfEmail);
        prohibitSpacesTextField(tfAlternateEmail);
        prohibitSpacesTextField(tfPhone);
        prohibitSpacesTextField(tfPassword);
        boolean validation= true;
        if(validateAddUser.validateEmpty(tfScore.getText())){
            if(Integer.parseInt(tfScore.getText()) < 250){
                generateError("Tiene que tener un mínimo del 70% de los creditos para cursar esta EE");
            }else{
                tfScore.getStyleClass().add("ok");
            }
        }else{
            tfScore.getStyleClass().add("error");
            validation = false;
        }
        if(validateAddUser.validateEmpty(tfEnrollment.getText()) && validateAddUser.validateEnrollment(tfEnrollment.getText())){
            tfEnrollment.getStyleClass().add("ok");
        }else{
            tfEnrollment.getStyleClass().add("error");
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

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
            rbMale.getStyleClass().add("error");
            rbFemale.getStyleClass().add("error");
            generateAlert("Selecciona un género");
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

    private void createObjectPractitioner(Practitioner practitioner) {
        practitioner.setEnrollment(validateAddUser.deleteAllSpace(tfEnrollment.getText()));
        practitioner.setName(validateAddUser.deleteSpace(tfName.getText()));
        practitioner.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
        practitioner.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
        practitioner.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
        practitioner.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
        practitioner.setUserName(tfEnrollment.getText());
        String passwordEncryption = encryptPassword(tfPassword.getText());
        practitioner.setPassword(passwordEncryption);
        if(rbMale.isSelected()){
            practitioner.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                practitioner.setGender(Gender.FEMALE.getGender());
            }
        }
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);
        String lapse;
        if(Integer.parseInt(month) > 1 && Integer.parseInt(month) <= 7){
            lapse = "FEBRERO-JULIO "+year;
        } else{
            lapse = "AGOSTO-ENERO "+year+ " " +(Integer.parseInt(year)+1);
        }
        practitioner.setPeriod(lapse);
        practitioner.setProfilePicture(imgFile);
    }
    public void cancel() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }
}
