package gui.administrator.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import domain.Search;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import domain.Coordinator;
import gui.FXMLGeneralController;
import domain.Gender;
import logic.ValidateUser;
import domain.User;

/**
 * Register Coordinator Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLRegisterCoordinatorController extends FXMLGeneralController implements Initializable  {
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
    public static int idAdministrator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
    }

    /**
     * Method to exit the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method to cancel registration and return to the menu
     */
    public void backMenu() {
        generateCancel("¿Seguro que desea cancelar?",btnCancel,"/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }

    /**
     * Method to register a Coordinator
     */
    public void registerCoordinator() {
        removeStyle();
        boolean validate = validateFields();
        if(validate){
            Coordinator coordinator = createObjectCoordinator();
            int validUserCoordinator = userValidateNotExist(coordinator);
            if (validUserCoordinator == Search.NOTFOUND.getValue()) {
                boolean registerComplete = Coordinator.addCoordinator(coordinator);
                if(registerComplete) {
                    generateInformation("Coordinador registrado exitosamente");
                }else{
                    generateError("No hay conexión con la base de datos. Intente más tarde");
                }
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRegister);
            } else {
                if (validUserCoordinator == Search.FOUND.getValue()) {
                    generateInformation("Este coordinador ya está registrado");
                } else {
                    if (validUserCoordinator == Search.EXCEPTION.getValue()) {
                        generateError("No hay conexión con la base de datos. Intente más tarde");
                        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRegister);
                    }
                }
            }

        }
    }

    private int userValidateNotExist(Coordinator coordinator){
        int validUserCoordinator = Coordinator.validateAcademicAdd(coordinator.getStaffNumber(), coordinator.getEmail()
                , coordinator.getAlternateEmail(), coordinator.getPhone(), coordinator.getUserName());
        if (validUserCoordinator == Search.NOTFOUND.getValue()) {
            boolean addUser = Coordinator.addUser((User)coordinator);
            if (!addUser) {
                validUserCoordinator = Search.EXCEPTION.getValue();
            }
        } else {
            boolean isTeacher = Coordinator.isTeacher(coordinator);
            if (isTeacher) {
                validUserCoordinator = Search.NOTFOUND.getValue();
            } else{
                validUserCoordinator = Search.EXCEPTION.getValue();
            }
        }
        return  validUserCoordinator;
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
        prohibitNumberTextField(tfName);
        prohibitNumberTextField(tfLastName);
        prohibitWordTextField(tfStaffNumber);
        prohibitWordTextField(tfPhone);
    }

    private void removeStyle(){
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfUserName.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
        rbMale.getStyleClass().remove("ok");
        rbFemale.getStyleClass().remove("ok");
        tfStaffNumber.getStyleClass().remove("error");
        tfName.getStyleClass().remove("error");
        tfLastName.getStyleClass().remove("error");
        tfEmail.getStyleClass().remove("error");
        tfAlternateEmail.getStyleClass().remove("error");
        tfUserName.getStyleClass().remove("error");
        tfPassword.getStyleClass().remove("error");
        tfPhone.getStyleClass().remove("error");
        rbMale.getStyleClass().remove("error");
        rbFemale.getStyleClass().remove("error");
    }

    private boolean validateFields(){
        ValidateUser validateUser = new ValidateUser();
        boolean validation= true;
        if((validateUser.validateEmpty(tfStaffNumber.getText()))){
            tfStaffNumber.getStyleClass().add("ok");
        }else{
            tfStaffNumber.getStyleClass().add("error");
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

        if(validateUser.validateEmpty(tfPhone.getText()) && validateUser.validatePhoneNumber(tfPhone.getText())) {
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

        if(validateUser.validateEmpty(tfUserName.getText()) && validateUser.validateUserName(tfUserName.getText())) {
            tfUserName.getStyleClass().add("ok");
        }else{
            tfUserName.getStyleClass().add("error");
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

    private Coordinator createObjectCoordinator() {
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(Integer.parseInt(tfStaffNumber.getText()));
        coordinator.setName(tfName.getText());
        coordinator.setLastName(tfLastName.getText());
        coordinator.setEmail(tfEmail.getText());
        coordinator.setAlternateEmail(tfAlternateEmail.getText());
        coordinator.setPhone(tfPhone.getText());
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
        coordinator.setIdAdministrator(idAdministrator);
        return coordinator;
    }
}
