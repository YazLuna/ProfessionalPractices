package gui.coordinator.controller;

import domain.Search;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import logic.ValidateUser;
import domain.Gender;
import domain.Practitioner;
import gui.FXMLGeneralController;

/**
 * Register Practitioner Controller
 * @author Yazmin
 * @version 16/07/2020
 */
public class FXMLRegisterPractitionerController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCancel;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfCredits;
    @FXML private TextField tfEnrollment;
    @FXML private TextField tfPassword;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private Button btnRegister;
    @FXML private Label lbTerm;
    public static int staffNumberCoordinator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbTerm.setText(createTerm());
        setLimitsTextFields();
    }

    /**
     * Method to exit the system
     */
    public void logOutCoordinator() {
        logOutGeneral();
    }

    /**
     * Method to cancel registration and return to the menu
     */
    public void backMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnCancel);
    }

    /**
     * Method to register a practitioner
     */
    public void registerPractitioner() {
        removeStyle();
        boolean validate = validateFields();
        if(validate){
            Practitioner practitioner = createObjectPractitioner();
            int validUserPractitioner = Practitioner.validPractitionerAdd(practitioner);
            if (validUserPractitioner == Search.NOTFOUND.getValue()) {
                boolean addPractitioner = Practitioner.addPractitioner(practitioner);
                if(addPractitioner){
                    generateInformation("Practicante registrado exitosamente");
                }else{
                    generateError("No hay conexión con la base de datos. Intente más tarde");
                }
                openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
            } else {
                if (validUserPractitioner == Search.FOUND.getValue()) {
                    generateInformation("Este practicante ya esta registrado");
                } else {
                    if (validUserPractitioner == Search.EXCEPTION.getValue()) {
                        generateError("No hay conexión con la base de datos. Intente más tarde");
                    }
                }
            }
        }
    }

    private void setLimitsTextFields() {
        limitTextField(tfName,30);
        prohibitNumberTextField(tfName);
        limitTextField(tfLastName,30);
        prohibitNumberTextField(tfLastName);
        limitTextField(tfEmail,50);
        limitTextField(tfAlternateEmail,50);
        limitTextField(tfPhone,10);
        prohibitWordTextField(tfPhone);
        limitTextField(tfEnrollment,9);
        limitTextField(tfPassword,20);
        limitTextField(tfCredits, 3);
        prohibitWordTextField(tfCredits);
    }

    private void removeStyle(){
        tfEnrollment.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
        tfCredits.getStyleClass().remove("ok");
        rbMale.getStyleClass().remove("ok");
        rbFemale.getStyleClass().remove("ok");
        tfEnrollment.getStyleClass().remove("error");
        tfName.getStyleClass().remove("error");
        tfLastName.getStyleClass().remove("error");
        tfEmail.getStyleClass().remove("error");
        tfAlternateEmail.getStyleClass().remove("error");
        tfPassword.getStyleClass().remove("error");
        tfPhone.getStyleClass().remove("error");
        tfCredits.getStyleClass().remove("error");
        rbMale.getStyleClass().remove("error");
        rbFemale.getStyleClass().remove("error");
    }

    private boolean validateFields(){
        ValidateUser validateUser = new ValidateUser();
        boolean validation = true;
        if(validateUser.validateEmpty(tfCredits.getText()) && validateUser.validateCreditsPractitioner(tfCredits.getText())){
            tfCredits.getStyleClass().add("ok");
        }else{
            tfCredits.getStyleClass().add("error");
            validation = false;
        }

        if(validateUser.validateEmpty(tfEnrollment.getText()) && validateUser.validateEnrollment(tfEnrollment.getText())){
            tfEnrollment.getStyleClass().add("ok");
        }else{
            tfEnrollment.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfName.getText())) && (validateUser.validateNameUser(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
            tfName.setText(validateUser.deleteSpace(tfName.getText()));
            tfName.setText(validateUser.createCorrectProperName(tfName.getText()));
        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfLastName.getText())) && (validateUser.validateNameUser(tfLastName.getText()))) {
            tfLastName.getStyleClass().add("ok");
            tfLastName.setText(validateUser.deleteSpace(tfLastName.getText()));
            tfLastName.setText(validateUser.createCorrectProperName(tfLastName.getText()));
        }else{
            tfLastName.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfEmail.getText())) && (validateUser.validateEmail(tfEmail.getText())) &&
                (!tfEmail.getText().equalsIgnoreCase(tfAlternateEmail.getText()))) {
            tfEmail.getStyleClass().add("ok");
        }else{
            tfEmail.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfAlternateEmail.getText())) && (validateUser.validateEmail(tfAlternateEmail.getText())) &&
                (!tfEmail.getText().equalsIgnoreCase(tfAlternateEmail.getText()))) {
            tfAlternateEmail.getStyleClass().add("ok");
        }else{
            tfAlternateEmail.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfPhone.getText())) && (validateUser.validatePhoneNumber(tfPhone.getText()))) {
            tfPhone.getStyleClass().add("ok");
        }else{
            tfPhone.getStyleClass().add("error");
            validation = false;
        }

        if(validateUser.validateEmpty(tfPassword.getText()) && validateUser.validatePassword(tfPassword.getText())) {
            tfPassword.getStyleClass().add("ok");
        }else{
            tfPassword.getStyleClass().add("error");
            validation = false;
        }

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
            rbMale.getStyleClass().add("error");
            rbFemale.getStyleClass().add("error");
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
                rbMale.getStyleClass().add("error");
                rbFemale.getStyleClass().add("error");
            } else {
                rbMale.getStyleClass().add("ok");
                rbFemale.getStyleClass().add("ok");
            }
        }
        return validation;
    }

    private Practitioner createObjectPractitioner() {
        Practitioner practitioner = new Practitioner();
        practitioner.setEnrollment(tfEnrollment.getText());
        practitioner.setName(tfName.getText());
        practitioner.setLastName(tfLastName.getText());
        practitioner.setEmail(tfEmail.getText());
        practitioner.setAlternateEmail(tfAlternateEmail.getText());
        practitioner.setPhone(tfPhone.getText());
        practitioner.setUserName(tfEnrollment.getText());
        String passwordEncryption = encryptPassword(tfPassword.getText());
        practitioner.setPassword(passwordEncryption);
        practitioner.setCredits(Integer.parseInt(tfCredits.getText()));
        if(rbMale.isSelected()){
            practitioner.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                practitioner.setGender(Gender.FEMALE.getGender());
            }
        }
        practitioner.setTerm(lbTerm.getText());
        practitioner.setStaffNumberCoordinator(staffNumberCoordinator);
        return practitioner;
    }
}
