package gui.coordinator.controller;

import javafx.fxml.FXML;
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

public class FXMLRegisterLinkedOrganizationController extends FXMLGeneralController {
    @FXML private Button btnBehind;
    @FXML private Button btnCancel;
    @FXML private Button btnRegister;
    @FXML private ComboBox cbSector;
    @FXML private ComboBox cbState;
    @FXML private ComboBox cbCity;
    @FXML private TextField tfNameOrganization;
    @FXML private TextField tfDirectUsers;
    @FXML private TextField tfIndirectUsers;
    @FXML private TextField tfEmailOrganization;
    @FXML private TextField tfAddress;
    @FXML private TextField tfPhoneNumber;
    private List<String> allSector;
    private List<String> allCity;
    private List<String> allState;
    private LinkedOrganization organization;
    private ValidateLinkedOrganization validateDataOrganization;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startComponent();
        startComboBox();
    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }
    public void cancel () {
        generateCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLMenuCoordinator.fxml");
    }
    public void startComponent (){
        limitTextField(tfNameOrganization,100);
        deleteNumberTextField(tfNameOrganization);

        limitTextField(tfIndirectUsers,100);
        deleteNumberTextField(tfIndirectUsers);

        limitTextField(tfDirectUsers,100);
        deleteNumberTextField(tfDirectUsers);

        limitTextField(cbSector.getEditor(),30);
        deleteNumberTextField(cbSector.getEditor());

        limitTextField(cbState.getEditor(),30);
        deleteNumberTextField(cbState.getEditor());

        limitTextField(cbCity.getEditor(),30);
        deleteNumberTextField(cbCity.getEditor());

        limitTextField(tfEmailOrganization,50);
        deleteSpacesTextField(tfEmailOrganization);

        limitTextField(tfAddress,50);
        deleteSpacesTextField(tfAddress);

        limitTextField(tfPhoneNumber,10);
        deleteWorkTextField(tfPhoneNumber);
    }

    public void startComboBox () {
        organization = new LinkedOrganization();
        allCity = new ArrayList<>();
        allCity = organization.listCity();
        cbCity.getItems().addAll(allCity);

        allSector = new ArrayList<>();
        allSector = organization.listSector();
        cbSector.getItems().addAll(allSector);

        allState = new ArrayList<>();
        allState = organization.listState();
        cbState.getItems().addAll(allState);
    }

    public void register () {
        organization = new LinkedOrganization();
        String message;
        if(!validateDataResponsible()) {
            generateAlert("Ingresar datos válidos");
        }else{
            organization = getDataOrganization();
            message = organization.addLinkedOrganization();
            if(message.equals("La organización vinculada se registro exitosamente")){
                generateInformation(message);
                openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
            } else {
                generateError(message);
            }
        }
    }

    public LinkedOrganization getDataOrganization () {
        validateDataOrganization = new ValidateLinkedOrganization();
        organization = new LinkedOrganization();
        organization.setName(validateDataOrganization.deleteSpace(tfNameOrganization.getText()));
        organization.setDirectUsers(validateDataOrganization.deleteSpace(tfDirectUsers.getText()));
        organization.setIndirectUsers(validateDataOrganization.deleteSpace(tfIndirectUsers.getText()));
        organization.setEmail(tfEmailOrganization.getText());
        organization.setEmail(tfPhoneNumber.getText());
        organization.setCity(validateDataOrganization.deleteSpace(cbCity.getEditor().getText()));
        return organization;
    }

    public boolean validateDataResponsible (){
        validateDataOrganization = new ValidateLinkedOrganization();
        boolean isValidDataOrganization = true;

        tfNameOrganization.getStyleClass().remove("error");
        tfNameOrganization.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateName(tfNameOrganization.getText())){
            tfNameOrganization.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            tfNameOrganization.getStyleClass().add("ok");
        }

        tfDirectUsers.getStyleClass().remove("error");
        tfDirectUsers.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateNotEmpty(tfDirectUsers.getText())){
            tfDirectUsers.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            tfDirectUsers.getStyleClass().add("ok");
        }

        tfIndirectUsers.getStyleClass().remove("error");
        tfIndirectUsers.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateNotEmpty(tfIndirectUsers.getText())){
            tfIndirectUsers.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            tfIndirectUsers.getStyleClass().add("ok");
        }

        tfPhoneNumber.getStyleClass().remove("error");
        tfPhoneNumber.getStyleClass().remove("ok");
        if(!validateDataOrganization.validatePhoneNumber(tfPhoneNumber.getText())){
            tfPhoneNumber.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            tfPhoneNumber.getStyleClass().add("ok");
        }

        tfAddress.getStyleClass().remove("error");
        tfAddress.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateAddress(tfAddress.getText())){
            tfAddress.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            tfAddress.getStyleClass().add("ok");
        }

        cbCity.getStyleClass().remove("error");
        cbCity.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateComboBox(cbCity.getEditor().getText())){
            cbCity.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            cbCity.getStyleClass().add("ok");
        }

        cbSector.getStyleClass().remove("error");
        cbSector.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateComboBox(cbSector.getEditor().getText())){
            cbSector.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            cbSector.getStyleClass().add("ok");
        }

        cbState.getStyleClass().remove("error");
        cbState.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateComboBox(cbState.getEditor().getText())){
            cbState.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            cbState.getStyleClass().add("ok");
        }

        tfEmailOrganization.getStyleClass().remove("error");
        tfEmailOrganization.getStyleClass().remove("ok");
        if(!validateDataOrganization.validateEmail(tfEmailOrganization.getText())){
            tfEmailOrganization.getStyleClass().add("error");
            isValidDataOrganization = false;
        }else {
            tfEmailOrganization.getStyleClass().add("ok");
        }
        return isValidDataOrganization;
    }
}
