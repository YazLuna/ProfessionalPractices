package gui.coordinator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import logic.ValidateAddUser;
import domain.Gender;
import domain.Practitioner;
import gui.FXMLGeneralController;
import domain.User;

/**
 * Register Practitioner Controller
 * @author Yazmin
 * @version 09/07/2020
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
            boolean validUserPractitioner = Practitioner.validPractitionerAdd(practitioner);
            if (validUserPractitioner) {
                validUserPractitioner = Practitioner.addPractitioner(practitioner);
                if(validUserPractitioner){
                    generateInformation("Practicante registrado exitosamente");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                }else{
                    generateError("No hay conexión con la base de datos. Intente más tarde");
                }
            } else {
                generateInformation("Este practicante ya esta registrado");
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
        tfEnrollment.getStyleClass().remove("error");
        tfName.getStyleClass().remove("error");
        tfLastName.getStyleClass().remove("error");
        tfEmail.getStyleClass().remove("error");
        tfAlternateEmail.getStyleClass().remove("error");
        tfPassword.getStyleClass().remove("error");
        tfPhone.getStyleClass().remove("error");
        tfCredits.getStyleClass().remove("error");
    }

    private boolean validateFields(){
        ValidateAddUser validateAddUser = new ValidateAddUser();
        boolean validation = true;
        if(validateAddUser.validateEmpty(tfCredits.getText()) && validateAddUser.validateCreditsPractitioner(tfCredits.getText())){
            tfCredits.getStyleClass().add("ok");
        }else{
            tfCredits.getStyleClass().add("error");
            validation = false;
        }

        if(validateAddUser.validateEmpty(tfEnrollment.getText()) && validateAddUser.validateEnrollment(tfEnrollment.getText())){
            tfEnrollment.getStyleClass().add("ok");
        }else{
            tfEnrollment.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfName.getText())) && (validateAddUser.validateNameUser(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
            tfName.setText(validateAddUser.deleteSpace(tfName.getText()));
            tfName.setText(validateAddUser.createCorrectProperName(tfName.getText()));
        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfLastName.getText())) && (validateAddUser.validateNameUser(tfLastName.getText()))) {
            tfLastName.getStyleClass().add("ok");
            tfLastName.setText(validateAddUser.deleteSpace(tfLastName.getText()));
            tfLastName.setText(validateAddUser.createCorrectProperName(tfLastName.getText()));
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

        if(validateAddUser.validateEmpty(tfPassword.getText()) && validateAddUser.validatePassword(tfPassword.getText())) {
            tfPassword.getStyleClass().add("ok");
        }else{
            tfPassword.getStyleClass().add("error");
            validation = false;
        }

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
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
        return practitioner;
    }
}
