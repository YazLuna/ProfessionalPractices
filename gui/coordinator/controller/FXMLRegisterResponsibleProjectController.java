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
import logic.ValidateDataResponsible;

/**
 * Class FXMLRegisterResponsibleProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
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
    private ValidateDataResponsible validateDataResponsible;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitComponentResponsibleProject();
        startComboBox();
    }

    public void behindMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }

    public void backMenu() {
        generateCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLMenuCoordinator.fxml");
    }

    public void limitComponentResponsibleProject(){
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
        allCharge = new ArrayList<>();
        allCharge = responsible.getListCharge();
        cbCharge.getItems().addAll(allCharge);
    }

    public void registerResponsibleProject() {
        responsible = new ResponsibleProject();
        boolean isValidDataResponsible = validateDataResponsible();
        if(!isValidDataResponsible) {
            generateAlert("Ingresar datos válidos");
        }else{
            responsible = getDataResponsible();
            boolean isValidateRepeatResponsibleProject;
            isValidateRepeatResponsibleProject = responsible.validateRepeatResponsibleProject(responsible.getEmail());
            if(!isValidateRepeatResponsibleProject){
                boolean isRegisterResponsibleProject;
                isRegisterResponsibleProject = responsible.registerResponsibleProject(responsible);
                if(isRegisterResponsibleProject){
                    generateInformation("El responsable del proyecto se registro exitosamente");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                } else {
                    generateError("El responsable del proyecto no pudo registrarse");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                }
            }else  {
                generateError("Existe un responsable del proyecto con el mismo correo electrónico registrado");
            }
        }
    }

    public ResponsibleProject getDataResponsible () {
        validateDataResponsible = new ValidateDataResponsible();
        responsible = new ResponsibleProject();
        responsible.setName(validateDataResponsible.deleteSpace(tfNameResponsible.getText()));
        responsible.setLastName(validateDataResponsible.deleteSpace(tfLastNameResponsible.getText()));
        responsible.setEmail(tfEmailResponsible.getText());
        responsible.setCharge(validateDataResponsible.deleteSpace(cbCharge.getEditor().getText()));
        return responsible;
    }

    public boolean validateDataResponsible (){
        validateDataResponsible = new ValidateDataResponsible();
        boolean isValidDataResponsible = true;
        tfNameResponsible.getStyleClass().remove("error");
        tfNameResponsible.getStyleClass().remove("ok");
        boolean isValidName = validateDataResponsible.validateName(tfNameResponsible.getText());
        if(isValidName){
            tfNameResponsible.getStyleClass().add("ok");
        }else {
            tfNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }

        boolean isValidLastName = validateDataResponsible.validateLastName(tfLastNameResponsible.getText());
        tfLastNameResponsible.getStyleClass().remove("error");
        tfLastNameResponsible.getStyleClass().remove("ok");
        if(isValidLastName){
            tfLastNameResponsible.getStyleClass().add("ok");
        }else {
            tfLastNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }

        boolean isValidEmailResponsible = validateDataResponsible.validateEmail(tfEmailResponsible.getText());
        tfEmailResponsible.getStyleClass().remove("error");
        tfEmailResponsible.getStyleClass().remove("ok");
        if(isValidEmailResponsible){
            tfEmailResponsible.getStyleClass().add("ok");
        }else {
            tfEmailResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }

        boolean isValidCharge = validateDataResponsible.validateCharge(cbCharge.getEditor().getText());
        cbCharge.getStyleClass().remove("error");
        cbCharge.getStyleClass().remove("ok");
        if(isValidCharge){
            cbCharge.getStyleClass().add("ok");
        }else {
            cbCharge.getStyleClass().add("error");
            isValidDataResponsible = false;
        }
        return isValidDataResponsible;
    }
}

