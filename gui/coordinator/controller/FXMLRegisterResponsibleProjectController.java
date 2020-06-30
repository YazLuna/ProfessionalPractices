package gui.coordinator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;
import domain.ResponsibleProject;
import logic.ValidateDataPerson;

public class FXMLRegisterResponsibleProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnCancel;
    @FXML private Button btnRegister;
    @FXML private ComboBox cbCharge;
    @FXML private TextField tfNameResponsible;
    @FXML private TextField tfLastNameResponsible;
    @FXML private TextField tfEmailResponsible;
    private List<String> allCharge;
    private ResponsibleProject responsible;
    private ValidateDataPerson validateDataPerson;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startComponent();
        startComboBox();
    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }

    public void cancel () {
        generateConfirmationCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLMenuCoordinator.fxml");
    }

    public void startComponent (){
        limitTextField(tfEmailResponsible,50);
        prohibitSpacesTextField(tfEmailResponsible);

        limitTextField(tfNameResponsible,50);
        prohibitNumberTextField(tfNameResponsible);

        limitTextField(tfLastNameResponsible,50);
        prohibitNumberTextField(tfLastNameResponsible);

        limitTextField(cbCharge.getEditor(),70);
        prohibitNumberTextField(cbCharge.getEditor());
    }

    public void startComboBox () {
        responsible = new ResponsibleProject();
        allCharge = new ArrayList<>();
        allCharge = responsible.listCharge();
        cbCharge.getItems().addAll(allCharge);
    }

    public void register () {
        responsible = new ResponsibleProject();
        String message;
        boolean isValidDataResponsible = validateDataResponsible();
        if(!isValidDataResponsible) {
            generateAlert("Ingresar datos válidos");
        }else{
            responsible = getDataResponsible();
            boolean isFoundLinkedOrganization;
            isFoundLinkedOrganization = responsible.searchResponsibleProject();
            if(!isFoundLinkedOrganization){
                boolean isRegisterResponsibleProject;
                isRegisterResponsibleProject = responsible.addResponsibleProject();
                if(!isRegisterResponsibleProject){
                    generateError("El responsable del proyecto no pudo registrarse");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                } else {
                    generateInformation("El responsable del proyecto se registro exitosamente");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                }
            }else  {
                generateError("Existe un responsable del proyecto con el mismo correo electrónico registrado");
            }
        }
    }

    public ResponsibleProject getDataResponsible () {
        validateDataPerson = new ValidateDataPerson();
        responsible = new ResponsibleProject();
        responsible.setName(validateDataPerson.deleteSpace(tfNameResponsible.getText()));
        responsible.setLastName(validateDataPerson.deleteSpace(tfLastNameResponsible.getText()));
        responsible.setEmail(tfEmailResponsible.getText());
        responsible.setCharge(validateDataPerson.deleteSpace(cbCharge.getEditor().getText()));
        return responsible;
    }

    public boolean validateDataResponsible (){
        validateDataPerson = new ValidateDataPerson();
        boolean isValidDataResponsible = true;
        tfNameResponsible.getStyleClass().remove("error");
        tfNameResponsible.getStyleClass().remove("ok");
        boolean isValidName = validateDataPerson.validateName(tfNameResponsible.getText());
        if(!isValidName){
            tfNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            tfNameResponsible.getStyleClass().add("ok");
        }

        boolean isValidLastName = validateDataPerson.validateLastName(tfLastNameResponsible.getText());
        tfLastNameResponsible.getStyleClass().remove("error");
        tfLastNameResponsible.getStyleClass().remove("ok");
        if(!isValidLastName){
            tfLastNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            tfLastNameResponsible.getStyleClass().add("ok");
        }

        boolean isValidEmailResponsible = validateDataPerson.validateEmail(tfEmailResponsible.getText());
        tfEmailResponsible.getStyleClass().remove("error");
        tfEmailResponsible.getStyleClass().remove("ok");
        if(!isValidEmailResponsible){
            tfEmailResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            tfEmailResponsible.getStyleClass().add("ok");
        }

        boolean isValidCharge = validateDataPerson.validateCharge(cbCharge.getEditor().getText());
        cbCharge.getStyleClass().remove("error");
        cbCharge.getStyleClass().remove("ok");
        if(!isValidCharge){
            cbCharge.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            cbCharge.getStyleClass().add("ok");
        }
        return isValidDataResponsible;
    }
}

