

package gui.coordinator.controller;

import dataaccess.Number;
import domain.LinkedOrganization;
import domain.PhoneNumber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.ValidateLinkedOrganization;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Class FXMLUpdateLinkedOrganizationController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnCancel;
    @FXML private Button btnModify;
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
    private static String nameLinkedOrganization;
    private String message;
    private String messageRepeat;
    private List<String> allSector;
    private List<String> allCity;
    private List<String> allState;
    private List<String> datesUpdate;
    private List<String> datesUpdatePhoneNumberTwo;
    private List<String> datesUpdatePhoneNumberOne;
    private List<PhoneNumber> phoneNumberList;
    private LinkedOrganization linkedOrganization;
    private LinkedOrganization linkedOrganizationEdit;
    private ValidateLinkedOrganization validateDataOrganization;
    private boolean isValidDataOrganization;
    private boolean isNewPhoneNumber;
    private PhoneNumber phoneNumberOne;
    private PhoneNumber phoneNumberTwo;
    private PhoneNumber phoneNumberOneEdit;
    private PhoneNumber phoneNumberTwoEdit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startDataComponentOrganization();
        startComboBox();
        startComponent();
    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml",btnBehind);
    }

    public void cancel () {
        generateCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml");
    }

    public void assignLinkedOrganization (String nameLinkedOrganization){
        this.nameLinkedOrganization = nameLinkedOrganization;
    }

    public void startDataComponentOrganization () {
        linkedOrganization = new LinkedOrganization();
        linkedOrganization = linkedOrganization.getOrganization(nameLinkedOrganization);
        tfNameOrganization.setText(linkedOrganization.getName());
        tfUsersDirect.setText(linkedOrganization.getDirectUsers());
        tfUsersIndirect.setText(linkedOrganization.getIndirectUsers());
        tfEmailOrganization.setText(linkedOrganization.getEmail());
        tfAddress.setText(linkedOrganization.getAddress());
        cbState.getEditor().setText(linkedOrganization.getState());
        cbCity.getEditor().setText(linkedOrganization.getCity());
        cbSector.getEditor().setText(linkedOrganization.getSector());
        phoneNumberOne = new PhoneNumber();
        phoneNumberTwo = new PhoneNumber();
        phoneNumberList = phoneNumberOne.getPhoneNumberList(linkedOrganization.getIdLinkedOrganization());
        phoneNumberOne = phoneNumberList.get(0);
        tfPhoneNumberOne.setText(phoneNumberList.get(0).getPhoneNumber());
        tfExtensionsOne.setText(phoneNumberList.get(0).getExtensions());
        if(phoneNumberList.size()== Number.TWO.getNumber()){
            tfPhoneNumberTwo.setText(phoneNumberList.get(1).getPhoneNumber());
            tfExtensionsTwo.setText(phoneNumberList.get(1).getExtensions());
            phoneNumberTwo = phoneNumberList.get(1);
        }
    }

    public void startComponent (){
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

    public void startComboBox () {
        allCity = new ArrayList<>();
        allCity = linkedOrganization.getListCity();
        cbCity.getItems().addAll(allCity);

        allSector = new ArrayList<>();
        allSector = linkedOrganization.getListSector();
        cbSector.getItems().addAll(allSector);

        allState = new ArrayList<>();
        allState = linkedOrganization.getListState();
        cbState.getItems().addAll(allState);
    }

    public void modify () {
        phoneNumberTwoEdit = new PhoneNumber();
        phoneNumberOneEdit = new PhoneNumber();
        datesUpdate = new ArrayList<>();
        datesUpdatePhoneNumberOne = new ArrayList<>();
        datesUpdatePhoneNumberTwo = new ArrayList<>();
        messageRepeat = "Existe una organizacion vinculada con el mismo nombre o correo";
        validateDataLinkedOrganization();
        if(datesUpdate.size()!= Number.ZERO.getNumber() || isNewPhoneNumber ||
                datesUpdatePhoneNumberOne.size()!= Number.ZERO.getNumber() ||
                datesUpdatePhoneNumberTwo.size()!= Number.ZERO.getNumber()) {
            if (!isValidDataOrganization) {
                generateAlert(message);
                message = "Ingresar datos válidos";
            } else {
            /*organization = getDataOrganization();
            boolean isRepeatLinkedOrganization;
            isRepeatLinkedOrganization = organization.validateRepeatLinkedOrganization();
            boolean isValidPhoneNumber = validateRepeatPhoneNumber();
            if(!isRepeatLinkedOrganization && !isValidPhoneNumber){
                boolean isRegisterLinkedOrganization;
                isRegisterLinkedOrganization = organization.addLinkedOrganization();
                int idLinkedOrganization = organization.searchIdLinkedOrganization();
                boolean isRegisterPhoneNumber=false;
                if(!tfPhoneNumberTwo.getText().trim().isEmpty()){
                    isRegisterPhoneNumber = phoneNumberTwo.addPhoneNumber(idLinkedOrganization);
                }
                if(!tfPhoneNumberOne.getText().trim().isEmpty()){
                    isRegisterPhoneNumber = phoneNumberOne.addPhoneNumber(idLinkedOrganization);
                }
                if(isRegisterLinkedOrganization){
                    if(isRegisterPhoneNumber) {
                        generateInformation("La organizacion vinculada se registro exitosamente");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml", btnModify);
                    }else{
                        generateInformation("La organizacion vinculada se registro exitosamente. Pero su numero de telefono no.");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml", btnModify);
                    }

                } else {
                    generateError("La organizacion vinculada no pudo registrarse");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnModify);
                }
            }else {
                generateError(messageRepeat);
            }*/
            }
        }else{
            removeStyleClass();
            generateAlert("Modifique datos");
        }
    }

    public void removeStyleClass (){
        tfNameOrganization.getStyleClass().remove("error");
        tfNameOrganization.getStyleClass().remove("ok");
        tfEmailOrganization.getStyleClass().remove("error");
        tfEmailOrganization.getStyleClass().remove("ok");
        tfAddress.getStyleClass().remove("error");
        tfAddress.getStyleClass().remove("ok");
        tfPhoneNumberOne.getStyleClass().remove("ok");
        tfPhoneNumberOne.getStyleClass().remove("error");
        tfExtensionsOne.getStyleClass().remove("error");
        tfExtensionsOne.getStyleClass().remove("ok");
        tfPhoneNumberTwo.getStyleClass().remove("error");
        tfPhoneNumberTwo.getStyleClass().remove("ok");
        tfExtensionsTwo.getStyleClass().remove("error");
        tfExtensionsTwo.getStyleClass().remove("ok");
        tfUsersDirect.getStyleClass().remove("error");
        tfUsersDirect.getStyleClass().remove("ok");
        tfUsersIndirect.getStyleClass().remove("error");
        tfUsersIndirect.getStyleClass().remove("ok");
        cbState.getStyleClass().remove("error");
        cbState.getStyleClass().remove("ok");
        cbSector.getStyleClass().remove("error");
        cbSector.getStyleClass().remove("ok");
        cbCity.getStyleClass().remove("error");
        cbCity.getStyleClass().remove("ok");
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

    public void validateName () {
        boolean isValidNameOrganization = validateDataOrganization.validateName(tfNameOrganization.getText());
        tfNameOrganization.getStyleClass().remove("error");
        tfNameOrganization.getStyleClass().remove("ok");
        if(isValidNameOrganization){
            tfNameOrganization.getStyleClass().add("ok");
            linkedOrganizationEdit.setName(validateDataOrganization.deleteSpace(tfNameOrganization.getText()));
        }else {
            tfNameOrganization.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("Name");
    }

    public  void validateDirectUsers (){
        boolean isValidDirectUsers = validateDataOrganization.validateUsersOrganization(tfUsersDirect.getText());
        tfUsersDirect.getStyleClass().remove("error");
        tfUsersDirect.getStyleClass().remove("ok");
        if(isValidDirectUsers){
            tfUsersDirect.getStyleClass().add("ok");
            linkedOrganizationEdit.setDirectUsers(validateDataOrganization.deleteSpace(tfUsersDirect.getText()));
        }else {
            tfUsersDirect.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("DirectUsers");
    }

    public  void validateIndirectUsers () {
        boolean isValidIndirectUser = validateDataOrganization.validateUsersOrganization(tfUsersIndirect.getText());
        tfUsersIndirect.getStyleClass().remove("error");
        tfUsersIndirect.getStyleClass().remove("ok");
        if(isValidIndirectUser){
            tfUsersIndirect.getStyleClass().add("ok");
            linkedOrganizationEdit.setIndirectUsers(validateDataOrganization.deleteSpace(tfUsersIndirect.getText()));
        }else {
            tfUsersIndirect.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("IndirectUsers");
    }


    public void validateExtensionsOne (){
        if(!tfExtensionsOne.getText().trim().isEmpty()){
            boolean isValidExtension;
            isValidExtension = validateDataOrganization.validateExtensions(tfExtensionsOne.getText());
            if(isValidExtension) {
                tfExtensionsOne.getStyleClass().add("ok");
                phoneNumberOneEdit.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsOne.getText()));
            }else {
                tfExtensionsOne.getStyleClass().add("error");
                isValidDataOrganization = false;
            }
        }
        datesUpdatePhoneNumberOne.add("Extensions");
    }

    public void validateExtensionsTwo (){
        if(!tfExtensionsTwo.getText().trim().isEmpty()) {
            boolean isValidExtension;
            isValidExtension = validateDataOrganization.validateExtensions(tfExtensionsTwo.getText());
            if (isValidExtension) {
                tfExtensionsTwo.getStyleClass().add("ok");
                phoneNumberTwoEdit.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsTwo.getText()));
            } else {
                tfExtensionsTwo.getStyleClass().add("error");
                isValidDataOrganization = false;
            }
        }
        datesUpdatePhoneNumberTwo.add("Extensions");
    }

    public void validatePhoneNumberOne () {
        if(!tfPhoneNumberOne.getText().trim().isEmpty()) {
            boolean isValidPhoneNumberOne = validateDataOrganization.validatePhoneNumber(tfPhoneNumberOne.getText());
            if (isValidPhoneNumberOne) {
                tfPhoneNumberOne.getStyleClass().add("ok");
                phoneNumberOneEdit.setPhoneNumber(tfPhoneNumberOne.getText());
            } else {
                tfPhoneNumberOne.getStyleClass().add("error");
                isValidDataOrganization = false;
            }
            if(!tfExtensionsOne.getText().equals(phoneNumberOne.getExtensions())){
                if (!tfExtensionsOne.getText().trim().isEmpty()) {
                    boolean isValidExtension;
                    isValidExtension = validateDataOrganization.validateExtensions(tfExtensionsOne.getText());
                    if (isValidExtension) {
                        tfExtensionsOne.getStyleClass().add("ok");
                        phoneNumberOneEdit.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsOne.getText()));
                    } else {
                        tfExtensionsOne.getStyleClass().add("error");
                        isValidDataOrganization = false;
                    }
                }
                datesUpdatePhoneNumberOne.add("Extensions");
            }
        } else {
            if(!tfExtensionsOne.getText().trim().isEmpty()){
                isValidDataOrganization=false;
                tfPhoneNumberOne.getStyleClass().add("error");
                message = "Ingresar datos válidos. Ingrese un numero para la extensión";
            }
        }
        datesUpdatePhoneNumberOne.add("PhoneNumber");
    }

    public void validateNewRegisterPhoneNumberTwo () {
        boolean isValidPhoneNumberTwo = validateDataOrganization.validatePhoneNumber(tfPhoneNumberTwo.getText());
        tfPhoneNumberTwo.getStyleClass().remove("error");
        tfPhoneNumberTwo.getStyleClass().remove("ok");
        tfExtensionsTwo.getStyleClass().remove("error");
        tfExtensionsTwo.getStyleClass().remove("ok");
        if (isValidPhoneNumberTwo) {
            tfPhoneNumberTwo.getStyleClass().add("ok");
            phoneNumberTwoEdit.setPhoneNumber(tfPhoneNumberTwo.getText());
        } else {
            tfPhoneNumberTwo.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        if(!tfExtensionsTwo.getText().trim().isEmpty()){
            boolean isValidExtension;
            isValidExtension = validateDataOrganization.validateExtensions(tfExtensionsTwo.getText());
            if(isValidExtension) {
                tfExtensionsTwo.getStyleClass().add("ok");
                phoneNumberTwoEdit.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsTwo.getText()));
            }else {
                tfExtensionsTwo.getStyleClass().add("error");
                isValidDataOrganization = false;
            }
        }
    }

    public void validatePhoneNumberTwo () {
        if(!tfPhoneNumberTwo.getText().trim().isEmpty()) {
            boolean isValidPhoneNumberTwo = validateDataOrganization.validatePhoneNumber(tfPhoneNumberTwo.getText());
            if (isValidPhoneNumberTwo) {
                tfPhoneNumberTwo.getStyleClass().add("ok");
                phoneNumberTwoEdit.setPhoneNumber(tfPhoneNumberTwo.getText());
            } else {
                tfPhoneNumberTwo.getStyleClass().add("error");
                isValidDataOrganization = false;
            }
            if(!tfExtensionsTwo.equals(phoneNumberTwo.getExtensions())){
                if (!tfExtensionsTwo.getText().trim().isEmpty()) {
                    boolean isValidExtension;
                    isValidExtension = validateDataOrganization.validateExtensions(tfExtensionsTwo.getText());
                    if (isValidExtension) {
                        tfExtensionsTwo.getStyleClass().add("ok");
                        phoneNumberTwoEdit.setExtensions(validateDataOrganization.deleteSpace(tfExtensionsTwo.getText()));
                    } else {
                        tfExtensionsTwo.getStyleClass().add("error");
                        isValidDataOrganization = false;
                    }
                }
                datesUpdatePhoneNumberTwo.add("Extensions");
            }
        } else {
            if(!tfExtensionsTwo.getText().trim().isEmpty()){
                isValidDataOrganization=false;
                tfPhoneNumberTwo.getStyleClass().add("error");
                message = "Ingresar datos válidos. Ingrese un numero para la extensión";
            }
        }
        datesUpdatePhoneNumberTwo.add("PhoneNumber");
    }

    public void validateAddress (){
        boolean isValidAdress = validateDataOrganization.validateAddress(tfAddress.getText());
        tfAddress.getStyleClass().remove("error");
        tfAddress.getStyleClass().remove("ok");
        if(isValidAdress){
            tfAddress.getStyleClass().add("ok");
            linkedOrganizationEdit.setAddress(validateDataOrganization.deleteSpace(tfAddress.getText()));
        }else {
            tfAddress.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("Address");
    }

    public void validateCity () {
        boolean isValidCity = validateDataOrganization.validateComboBox(cbCity.getEditor().getText());
        cbCity.getStyleClass().remove("error");
        cbCity.getStyleClass().remove("ok");
        if(isValidCity){
            cbCity.getStyleClass().add("ok");
            linkedOrganizationEdit.setCity(validateDataOrganization.deleteSpace(cbCity.getEditor().getText()));
        }else {
            cbCity.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("City");
    }

    public void validateSector (){
        boolean isValidSector = validateDataOrganization.validateComboBox(cbSector.getEditor().getText());
        cbSector.getStyleClass().remove("error");
        cbSector.getStyleClass().remove("ok");
        if(isValidSector){
            cbSector.getStyleClass().add("ok");
            linkedOrganizationEdit.setSector(validateDataOrganization.deleteSpace(cbSector.getEditor().getText()));
        }else {
            cbSector.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("Sector");
    }

    public void validateState (){
        boolean isValidState = validateDataOrganization.validateComboBox(cbState.getEditor().getText());
        cbState.getStyleClass().remove("error");
        cbState.getStyleClass().remove("ok");
        if(isValidState){
            cbState.getStyleClass().add("ok");
            linkedOrganizationEdit.setState(validateDataOrganization.deleteSpace(cbState.getEditor().getText()));
        }else {
            cbState.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("State");
    }

    public void validateEmail () {
        boolean isValidEmailOrganization = validateDataOrganization.validateEmail(tfEmailOrganization.getText());
        tfEmailOrganization.getStyleClass().remove("error");
        tfEmailOrganization.getStyleClass().remove("ok");
        if(isValidEmailOrganization){
            tfEmailOrganization.getStyleClass().add("ok");
            linkedOrganizationEdit.setEmail(validateDataOrganization.deleteSpace(tfEmailOrganization.getText()));
        }else {
            tfEmailOrganization.getStyleClass().add("error");
            isValidDataOrganization = false;
        }
        datesUpdate.add("Email");
    }

    public void validateDataLinkedOrganization(){
        isNewPhoneNumber=false;
        linkedOrganizationEdit = new LinkedOrganization();
        message = "Ingresar datos válidos";
        isValidDataOrganization=true;
        validateDataOrganization = new ValidateLinkedOrganization();
        if(!linkedOrganization.getName().equals(tfNameOrganization.getText())) {
            validateName();
        }
        if(!linkedOrganization.getDirectUsers().equals(tfUsersDirect.getText())) {
            validateDirectUsers();
        }
        if(!linkedOrganization.getIndirectUsers().equals(tfUsersIndirect.getText())) {
            validateIndirectUsers();
        }
        if(!linkedOrganization.getEmail().equals(tfEmailOrganization.getText())) {
            validateEmail();
        }
        if(!linkedOrganization.getAddress().equals(tfAddress.getText())) {
            validateAddress();
        }
        if(!linkedOrganization.getSector().equals(cbSector.getEditor().getText())) {
            validateSector();
        }
        if(!linkedOrganization.getCity().equals(cbCity.getEditor().getText())) {
            validateCity();
        }
        if(!linkedOrganization.getState().equals(cbState.getEditor().getText())) {
            validateState();
        }
        tfPhoneNumberTwo.getStyleClass().remove("error");
        tfPhoneNumberTwo.getStyleClass().remove("ok");
        tfPhoneNumberOne.getStyleClass().remove("error");
        tfPhoneNumberOne.getStyleClass().remove("ok");
        tfExtensionsOne.getStyleClass().remove("error");
        tfExtensionsOne.getStyleClass().remove("ok");
        tfExtensionsTwo.getStyleClass().remove("error");
        tfExtensionsTwo.getStyleClass().remove("ok");

        if(tfExtensionsOne.getText().trim().isEmpty() && tfExtensionsTwo.getText().trim().isEmpty() &&
                tfPhoneNumberOne.getText().trim().isEmpty() && tfPhoneNumberTwo.getText().trim().isEmpty()){
            isValidDataOrganization=false;
            message = message+". Ingrese por lo menos un número de telefono";
            tfPhoneNumberOne.getStyleClass().add("error");
        }else {
            if(!phoneNumberOne.getPhoneNumber().equals(tfPhoneNumberOne.getText())){
                validatePhoneNumberTwo();
            }else{
                if(!phoneNumberOne.getExtensions().equals(tfExtensionsOne.getText())){
                    validateExtensionsOne();
                }
            }
            if(phoneNumberList.size()>Number.ONE.getNumber()){
                if(!phoneNumberTwo.getPhoneNumber().equals(tfPhoneNumberTwo.getText())){
                    validatePhoneNumberTwo();
                }else{
                    if(!phoneNumberTwo.getExtensions().equals(tfExtensionsTwo.getText())){
                        validateExtensionsTwo();
                    }
                }
            }else {
                if(!tfPhoneNumberTwo.getText().trim().isEmpty()) {
                    validateNewRegisterPhoneNumberTwo();
                    isNewPhoneNumber=true;
                } else {
                    if(!tfExtensionsTwo.getText().trim().isEmpty()){
                        isValidDataOrganization=false;
                        tfPhoneNumberTwo.getStyleClass().add("error");
                        message = "Ingresar datos válidos. Ingrese un número de teléfono para la extensión";
                    }
                }
            }
        }
    }
}

