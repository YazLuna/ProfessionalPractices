package gui.coordinator.controller;

import domain.Practitioner;
import domain.Gender;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import logic.ValidateAddUser;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Update Practitioner Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLUpdatePractitionerController extends FXMLGeneralController implements Initializable {
    @FXML private Label lbTerm;
    @FXML private TextField tfCredits;
    @FXML private Button btnRecoverPractitioner;
    @FXML private TextField tfEnrollment;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;
    public static String enrollment;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();
    private Practitioner practitioner = new Practitioner();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        practitioner.setEnrollment(enrollment);
        setLimitsTextFields();
        colocatePractitioner();
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
    public void backList() {
        FXMLUpdateDeletePractitionerListController.action = "Update";
        openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateDeletePractitionerList.fxml", btnCancel);
    }

    public void update(){
        boolean validate;
        boolean registerComplete;
        removeStyle();
        validate = validate();
        if(validate){
            boolean confirm = generateConfirmation("¿Seguro que desea modificar el Practicante?");
            if(confirm){
                createObjectPractitioner(practitioner);
            /*registerComplete = practitioner.addpractitioner();
            if(registerComplete){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                generateInformation("Registro Exitoso");
            }else{
                generateError("Este practicante ya esta registrado");
            }*/
                System.out.println("Ya es válido");
            }
        }
    }

    private void setLimitsTextFields() {
        limitTextField(tfName,30);
        limitTextField(tfLastName,30);
        limitTextField(tfEmail,50);
        limitTextField(tfAlternateEmail,50);
        limitTextField(tfPhone,10);
        limitTextField(tfEnrollment,9);
    }

    private void colocatePractitioner() {
        //practitioner = practitioner.getPractitioner();
        tfName.setText(practitioner.getName());
        tfLastName.setText(practitioner.getLastName());
        tfEmail.setText(practitioner.getEmail());
        tfAlternateEmail.setText(practitioner.getAlternateEmail());
        if(practitioner.getGender()== Gender.MALE.getGender()){
            rbMale.isSelected();
        }else{
            rbFemale.isSelected();
        }
        tfPhone.setText(practitioner.getPhone());
        tfEnrollment.setText(practitioner.getEnrollment());
        /*if(practitioner.getProfilePicture()==null){
            //imgpractitioner.setImage("/images/Add.png");
        }else{
            //imgpractitioner.setImage(practitioner.getProfilePicture());
        }
        if(practitioner.getStatus().equalsIgnoreCase("Inactive")){
            btnRecoverPractitioner.setVisible(true);
        }*/
    }

    public void removeStyle(){
        tfEnrollment.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
    }

    public boolean validate(){
        boolean validation= true;
        if(validateAddUser.validateEmpty(tfEnrollment.getText()) && validateAddUser.validateEnrollment(tfEnrollment.getText())){
            tfEnrollment.getStyleClass().add("ok");
        }else{
            tfEnrollment.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfName.getText())) && (validateAddUser.validateNameUser(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
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

    private void createObjectPractitioner(Practitioner practitioner) {
        practitioner.setEnrollment(validateAddUser.deleteAllSpace(tfEnrollment.getText()));
        practitioner.setName(validateAddUser.deleteSpace(tfName.getText()));
        practitioner.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
        practitioner.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
        practitioner.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
        practitioner.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
        if(rbMale.isSelected()){
            practitioner.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                practitioner.setGender(Gender.FEMALE.getGender());
            }
        }
    }

    public void recoverPractitioner() {
        boolean recoverOk = generateConfirmation("¿Seguro que desea reactivar este practicante?");
        if(recoverOk){
            //boolean recoverSuccessful = practitioner.recoverPractitioner();
            if(true){
                openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRecoverPractitioner);
                generateInformation("Practicante reactivado exitosamente");
            }else{
                generateError("No se puedo reactivar el practicante");
            }
        }
    }
    
}
