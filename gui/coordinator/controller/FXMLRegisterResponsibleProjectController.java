package gui.coordinator.controller;

import domain.ResponsibleProject;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.ValidateDataPerson;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
        generateCancel("¿Seguro desea cancelar?",btnCancel);
    }
    public void startComponent (){
        limitTextField(tfEmailResponsible,50);
        deleteSpacesTextField(tfEmailResponsible);
        limitTextField(tfNameResponsible,50);
        deleteNumberTextField(tfNameResponsible);
        limitTextField(tfLastNameResponsible,50);
        deleteNumberTextField(tfLastNameResponsible);
        limitTextField(cbCharge.getEditor(),50);
        deleteNumberTextField(cbCharge.getEditor());
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
        if(!validateDataResponsible()) {
            generateAlert("Ingresar datos válidos");
        }else{
            responsible = getDataResponsible();
            message = responsible.addResponsibleProject();
            generateInformation(message);
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
        if(!validateDataPerson.validateName(tfNameResponsible.getText())){
            tfNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            tfNameResponsible.getStyleClass().add("ok");
        }
        tfLastNameResponsible.getStyleClass().remove("error");
        tfLastNameResponsible.getStyleClass().remove("ok");
        if(!validateDataPerson.validateLastName(tfLastNameResponsible.getText())){
            tfLastNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            tfLastNameResponsible.getStyleClass().add("ok");
        }
        tfEmailResponsible.getStyleClass().remove("error");
        tfEmailResponsible.getStyleClass().remove("ok");
        if(!validateDataPerson.validateEmail(tfEmailResponsible.getText())){
            tfEmailResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            tfEmailResponsible.getStyleClass().add("ok");
        }
        cbCharge.getStyleClass().remove("error");
        cbCharge.getStyleClass().remove("ok");
        if(!validateDataPerson.validateCharge(cbCharge.getEditor().getText())){
            cbCharge.getStyleClass().add("error");
            isValidDataResponsible = false;
        }else {
            cbCharge.getStyleClass().add("ok");
        }
        return isValidDataResponsible;
    }
}

