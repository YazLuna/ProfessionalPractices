package gui.coordinator.controller;

import domain.PhoneNumber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import domain.LinkedOrganization;
import gui.FXMLGeneralController;
import logic.ValidateLinkedOrganization;

/**
 * Class FXMLRegisterLinkedOrganizationController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLRegisterLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnCancel;
    @FXML private Button btnRegister;
    @FXML private ComboBox cbSector;
    @FXML private ComboBox cbState;
    @FXML private ComboBox cbCity;
    @FXML private TextField tfNameOrganization;
    @FXML private TextField tfUsersDirect;
    @FXML private TextField tfUsersIndirect;
    @FXML private TextField tfEmailOrganization;
    @FXML private TextField tfAddress;
    @FXML private TextField tfPhoneNumberOne;
    @FXML private TextField tfPhoneNumberTwo;
    @FXML private TextField tfExtensionsOne;
    @FXML private TextField tfExtensionsTwo;
    private String message;
    private String messageRepeat;
    private PhoneNumber phoneNumberOne;
    private PhoneNumber phoneNumberTwo;
    private boolean isValidDataOrganization;
    private List<String> allSector;
    private List<String> allCity;
    private List<String> allState;
    private LinkedOrganization organization;
    private ValidateLinkedOrganization validateDataOrganization;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitComponentOrganization();
        startComboBox();
    }

    public void behindMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }
    public void backMenu() {
        generateConfirmationCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLMenuCoordinator.fxml");
    }
    public void limitComponentOrganization(){
        limitTextField(tfNameOrganization,100);
        prohibitNumberTextField(tfNameOrganization);

        limitTextField(tfUsersIndirect,100);
        prohibitNumberAllowSpecialCharTextField(tfUsersIndirect);

        limitTextField(tfUsersDirect,100);
        prohibitNumberAllowSpecialCharTextField(tfUsersDirect);

        limitTextField(cbSector.getEditor(),30);
        prohibitNumberTextField(cbSector.getEditor());

        limitTextField(cbState.getEditor(),30);
        prohibitNumberTextField(cbState.getEditor());

        limitTextField(cbCity.getEditor(),30);
        prohibitNumberTextField(cbCity.getEditor());

        limitTextField(tfEmailOrganization,50);
        prohibitSpacesTextField(tfEmailOrganization);

        limitTextField(tfAddress,100);
        prohibitSpacesTextField(tfAddress);

        limitTextField(tfPhoneNumberOne,10);
        prohibitWordTextField(tfPhoneNumberTwo);

        limitTextField(tfPhoneNumberTwo,10);
        prohibitWordTextField(tfPhoneNumberTwo);

        limitTextField(tfExtensionsOne,30);
        prohibitWordTextFieldAllowSpecialChar(tfExtensionsOne);

        limitTextField(tfExtensionsTwo,30);
        prohibitWordTextFieldAllowSpecialChar(tfExtensionsTwo);
    }

    public boolean validateRepeatPhoneNumber (){
        boolean isValidNumbers = false;
        boolean isRepeatPhoneNumberOne = false;
        boolean isRepeatPhoneNumberTwo = false;
        phoneNumberOne = new PhoneNumber();
        phoneNumberTwo = new PhoneNumber();
        if(!tfPhoneNumberOne.getText().trim().isEmpty() && !tfPhoneNumberTwo.getText().trim().isEmpty()) {
            if(!tfPhoneNumberOne.getText().equals(tfPhoneNumberTwo.getText())){
                phoneNumberTwo.setPhoneNumber(tfPhoneNumberTwo.getText());
                phoneNumberTwo.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsTwo.getText()));
                phoneNumberOne.setPhoneNumber(tfPhoneNumberOne.getText());
                phoneNumberOne.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsOne.getText()));
                isRepeatPhoneNumberOne = phoneNumberOne.validateRepeatPhoneNumber(phoneNumberOne.getPhoneNumber());
                isRepeatPhoneNumberTwo = phoneNumberTwo.validateRepeatPhoneNumber(phoneNumberTwo.getPhoneNumber());
                if(isRepeatPhoneNumberOne) {
                    tfPhoneNumberOne.getStyleClass().remove("ok");
                    tfPhoneNumberOne.getStyleClass().add("error");
                }
                if(isRepeatPhoneNumberTwo) {
                    tfPhoneNumberTwo.getStyleClass().remove("ok");
                    tfPhoneNumberTwo.getStyleClass().add("error");
                }
                if(isRepeatPhoneNumberOne || isRepeatPhoneNumberTwo){
                    messageRepeat = messageRepeat +". El numero de telefono ya esta registrado";
                }
            }else {
                tfPhoneNumberTwo.getStyleClass().remove("ok");
                tfPhoneNumberOne.getStyleClass().remove("ok");
                messageRepeat = messageRepeat +". Los numeros de teléfono son identicos";
                tfPhoneNumberOne.getStyleClass().add("error");
                tfPhoneNumberTwo.getStyleClass().add("error");
            }
        }else {
            if(!tfPhoneNumberOne.getText().trim().isEmpty()){
                phoneNumberOne.setPhoneNumber(tfPhoneNumberOne.getText());
                phoneNumberOne.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsOne.getText()));
                isRepeatPhoneNumberOne = phoneNumberOne.validateRepeatPhoneNumber(phoneNumberOne.getPhoneNumber());
                if(isRepeatPhoneNumberOne) {
                    tfPhoneNumberOne.getStyleClass().remove("ok");
                    tfPhoneNumberOne.getStyleClass().add("error");
                    messageRepeat = messageRepeat +". El numero de telefono ya esta registrado";
                }
            }else {
                phoneNumberTwo.setPhoneNumber(tfPhoneNumberTwo.getText());
                phoneNumberTwo.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsTwo.getText()));
                isRepeatPhoneNumberTwo = phoneNumberTwo.validateRepeatPhoneNumber(phoneNumberTwo.getPhoneNumber());
                if(isRepeatPhoneNumberTwo) {
                    tfPhoneNumberTwo.getStyleClass().remove("ok");
                    tfPhoneNumberTwo.getStyleClass().add("error");
                    messageRepeat = messageRepeat +". El numero de telefono ya esta registrado";
                }
            }
        }
        if(isRepeatPhoneNumberOne || isRepeatPhoneNumberTwo) {
            isValidNumbers = true;
        }
        return isValidNumbers;
    }


    public void startComboBox () {
        allCity = new ArrayList<>();
        allCity = organization.getListCity();
        cbCity.getItems().addAll(allCity);

        allSector = new ArrayList<>();
        allSector = organization.getListSector();
        cbSector.getItems().addAll(allSector);

        allState = new ArrayList<>();
        allState = organization.getListState();
        cbState.getItems().addAll(allState);
    }

    public void registerProject() {
        messageRepeat = "Existe una organizacion vinculada con el mismo nombre o correo";
        organization = new LinkedOrganization();
        validateDataLinkedOrganization();
        if(!isValidDataOrganization) {
            generateAlert(message);
            message= "Ingresar datos válidos";
        }else{
            organization = getDataOrganization();
            boolean isRepeatLinkedOrganization;
            isRepeatLinkedOrganization = organization.validateRepeatLinkedOrganization(organization.getName(),organization.getEmail());
            boolean isValidPhoneNumber = validateRepeatPhoneNumber();
            if(!isRepeatLinkedOrganization && !isValidPhoneNumber){
                boolean isRegisterLinkedOrganization;
                isRegisterLinkedOrganization = organization.addLinkedOrganization(organization);
                int idLinkedOrganization = organization.searchIdLinkedOrganization(organization.getName());
                boolean isRegisterPhoneNumber=false;
                if(!tfPhoneNumberTwo.getText().trim().isEmpty()){
                    isRegisterPhoneNumber = phoneNumberTwo.addPhoneNumber(phoneNumberTwo,idLinkedOrganization);
                }
                if(!tfPhoneNumberOne.getText().trim().isEmpty()){
                    isRegisterPhoneNumber = phoneNumberOne.addPhoneNumber(phoneNumberOne,idLinkedOrganization);
                }
                if(isRegisterLinkedOrganization){
                    if(isRegisterPhoneNumber) {
                        generateInformation("La organizacion vinculada se registro exitosamente");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml", btnRegister);
                    }else{
                        generateInformation("La organizacion vinculada se registro exitosamente. Pero su numero de telefono no.");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml", btnRegister);
                    }

                } else {
                    generateError("La organizacion vinculada no pudo registrarse");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                }
            }else {
                generateError(messageRepeat);
            }
        }
    }

    public LinkedOrganization getDataOrganization () {
        validateDataOrganization = new ValidateLinkedOrganization();
        organization = new LinkedOrganization();
        organization.setName(validateDataOrganization.deleteSpace(tfNameOrganization.getText()));
        organization.setDirectUsers(validateDataOrganization.deleteSpace(tfUsersDirect.getText()));
        organization.setIndirectUsers(validateDataOrganization.deleteSpace(tfUsersIndirect.getText()));
        organization.setEmail(tfEmailOrganization.getText());
        organization.setAddress(validateDataOrganization.deleteSpace(tfAddress.getText()));
        organization.setCity(validateDataOrganization.deleteSpace(cbCity.getEditor().getText()));
        organization.setSector(validateDataOrganization.deleteSpace(cbSector.getEditor().getText()));
        organization.setState(validateDataOrganization.deleteSpace(cbState.getEditor().getText()));
        return organization;
    }

    public void validateName () {
        boolean isValidNameOrganization = validateDataOrganization.validateName(tfNameOrganization.getText());
        tfNameOrganization.getStyleClass().remove("error");
        tfNameOrganization.getStyleClass().remove("ok");
        if(isValidNameOrganization){
            tfNameOrganization.getStyleClass().add("ok");
        }else {
            tfNameOrganization.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }

    public  void validateDirectUsers (){
        boolean isValidDirectUsers = validateDataOrganization.validateUsersOrganization(tfUsersDirect.getText());
        tfUsersDirect.getStyleClass().remove("error");
        tfUsersDirect.getStyleClass().remove("ok");
        if(isValidDirectUsers){
            tfUsersDirect.getStyleClass().add("ok");
        }else {
            tfUsersDirect.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }

    public  void validateIndirectUsers () {
        boolean isValidIndirectUser = validateDataOrganization.validateUsersOrganization(tfUsersIndirect.getText());
        tfUsersIndirect.getStyleClass().remove("error");
        tfUsersIndirect.getStyleClass().remove("ok");
        if(isValidIndirectUser){
            tfUsersIndirect.getStyleClass().add("ok");
        }else {
            tfUsersIndirect.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }

    public void validatePhoneNumberOne (){
        boolean isValidPhoneNumberOne = validateDataOrganization.validatePhoneNumber(tfPhoneNumberOne.getText());
        tfPhoneNumberOne.getStyleClass().remove("error");
        tfPhoneNumberOne.getStyleClass().remove("ok");
        tfExtensionsOne.getStyleClass().remove("error");
        tfExtensionsOne.getStyleClass().remove("ok");
        if (isValidPhoneNumberOne) {
            tfPhoneNumberOne.getStyleClass().add("ok");
        } else {
            tfPhoneNumberOne.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        if(!tfExtensionsOne.getText().trim().isEmpty()){
            boolean isValidExtension;
            isValidExtension = validateDataOrganization.validateExtensions(tfExtensionsOne.getText());
            if(isValidExtension) {
                tfExtensionsOne.getStyleClass().add("ok");
            }else {
                tfExtensionsOne.getStyleClass().add("error");
                isValidDataOrganization = false;
            }
        }
    }

    public void validatePhoneNumberTwo () {
            boolean isValidPhoneNumberTwo = validateDataOrganization.validatePhoneNumber(tfPhoneNumberTwo.getText());
            tfPhoneNumberTwo.getStyleClass().remove("error");
            tfPhoneNumberTwo.getStyleClass().remove("ok");
            tfExtensionsTwo.getStyleClass().remove("error");
            tfExtensionsTwo.getStyleClass().remove("ok");
            if (isValidPhoneNumberTwo) {
                tfPhoneNumberTwo.getStyleClass().add("ok");
            } else {
                tfPhoneNumberTwo.getStyleClass().add("error");
                isValidDataOrganization = false;
            }
            if(!tfExtensionsTwo.getText().trim().isEmpty()){
                boolean isValidExtension;
                isValidExtension = validateDataOrganization.validateExtensions(tfExtensionsTwo.getText());
                if(isValidExtension) {
                    tfExtensionsTwo.getStyleClass().add("ok");
                }else {
                    tfExtensionsTwo.getStyleClass().add("error");
                    isValidDataOrganization = false;
                }
            }
    }

    public void validateAddress (){
        boolean isValidAdress = validateDataOrganization.validateAddress(tfAddress.getText());
        tfAddress.getStyleClass().remove("error");
        tfAddress.getStyleClass().remove("ok");
        if(isValidAdress){
            tfAddress.getStyleClass().add("ok");
        }else {
            tfAddress.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }

    public void validateCity () {
        boolean isValidCity = validateDataOrganization.validateComboBox(cbCity.getEditor().getText());
        cbCity.getStyleClass().remove("error");
        cbCity.getStyleClass().remove("ok");
        if(isValidCity){
            cbCity.getStyleClass().add("ok");
        }else {
            cbCity.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }

    public void validateSector (){
        boolean isValidSector = validateDataOrganization.validateComboBox(cbSector.getEditor().getText());
        cbSector.getStyleClass().remove("error");
        cbSector.getStyleClass().remove("ok");
        if(isValidSector){
            cbSector.getStyleClass().add("ok");
        }else {
            cbSector.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }

    public void validateState (){
        boolean isValidState = validateDataOrganization.validateComboBox(cbState.getEditor().getText());
        cbState.getStyleClass().remove("error");
        cbState.getStyleClass().remove("ok");
        if(isValidState){
            cbState.getStyleClass().add("ok");
        }else {
            cbState.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }

    public void validateEmail () {
        boolean isValidEmailOrganization = validateDataOrganization.validateEmail(tfEmailOrganization.getText());
        tfEmailOrganization.getStyleClass().remove("error");
        tfEmailOrganization.getStyleClass().remove("ok");
        if(isValidEmailOrganization){
            tfEmailOrganization.getStyleClass().add("ok");
        }else {
            tfEmailOrganization.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
    }
    public void validateDataLinkedOrganization(){
        message = "Ingresar datos válidos";
        isValidDataOrganization=true;
        validateDataOrganization = new ValidateLinkedOrganization();
        validateName();
        validateDirectUsers();
        validateIndirectUsers();
        validateEmail();
        validateAddress();
        validateSector();
        validateCity();
        validateState();
        tfPhoneNumberTwo.getStyleClass().remove("error");
        tfPhoneNumberTwo.getStyleClass().remove("ok");
        tfPhoneNumberOne.getStyleClass().remove("error");
        tfPhoneNumberOne.getStyleClass().remove("ok");
        tfExtensionsOne.getStyleClass().remove("error");
        tfExtensionsOne.getStyleClass().remove("ok");
        tfExtensionsTwo.getStyleClass().remove("error");
        tfExtensionsTwo.getStyleClass().remove("ok");
        if(!tfPhoneNumberOne.getText().trim().isEmpty()) {
            validatePhoneNumberOne();
        }else {
            if(!tfExtensionsOne.getText().trim().isEmpty()){
                isValidDataOrganization=false;
                tfPhoneNumberOne.getStyleClass().add("error");
                message = "Ingresar datos válidos. Ingrese un número de teléfono para la extensión";
            }
        }
        if(!tfPhoneNumberTwo.getText().trim().isEmpty()) {
            validatePhoneNumberTwo();
        } else {
            if(!tfExtensionsTwo.getText().trim().isEmpty()){
                isValidDataOrganization=false;
                tfPhoneNumberTwo.getStyleClass().add("error");
                message = "Ingresar datos válidos. Ingrese un número de teléfono para la extensión";
            }
        }
        if(tfExtensionsOne.getText().trim().isEmpty() && tfExtensionsTwo.getText().trim().isEmpty() &&
                tfPhoneNumberOne.getText().trim().isEmpty() && tfPhoneNumberTwo.getText().trim().isEmpty()){
            isValidDataOrganization=false;
            message = message+". Ingrese por lo menos un número de telefono";
            tfPhoneNumberOne.getStyleClass().add("error");
        }
    }
}
