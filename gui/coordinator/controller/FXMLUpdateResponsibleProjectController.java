package gui.coordinator.controller;

import dataaccess.Number;
import domain.ResponsibleProject;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import logic.ValidateDataPerson;

/**
 * Class FXMLUpdateResponsibleProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateResponsibleProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCancel;
    @FXML private Button btnModify;
    @FXML private Button btnBehind;
    @FXML private TextField tfNameResponsible;
    @FXML private TextField tfLastNameResponsible;
    @FXML private TextField tfEmailResponsible;
    @FXML private ComboBox cbCharge;
    private static String emailResponsibleProject;
    private boolean isValidDataResponsible;
    private boolean isEmailValidated = false;
    private List<String> allCharge;
    private List<String> datesUpdate;
    private ResponsibleProject responsibleProject;
    private ResponsibleProject responsibleProjectEdit;
    private ValidateDataPerson validateDataPerson;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limitComponentsResponsibleProject();
        startComboBox();
        startComponentResponsibleProject();
    }

    public void behindListResponsibleProject() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml", btnBehind);
    }

    public void backListResponsibleProject() {
        generateCancel("¿Seguro desea cancelar?", btnCancel, "/gui/coordinator/fxml/FXMLListResponsibleProject.fxml");
    }

    public void assignEmailResponsible (String emailResponsibleProject){
        this.emailResponsibleProject = emailResponsibleProject;
    }

    public void startComponentResponsibleProject() {
        responsibleProject = new ResponsibleProject();
        responsibleProject = responsibleProject.getResponsibleProject(emailResponsibleProject);
        tfNameResponsible.setText(responsibleProject.getName());
        tfLastNameResponsible.setText(responsibleProject.getLastName());
        tfEmailResponsible.setText(responsibleProject.getEmail());
        cbCharge.getEditor().setText(responsibleProject.getCharge());
        responsibleProjectEdit = new ResponsibleProject();
        responsibleProjectEdit.setIdResponsible(responsibleProject.getIdResponsible());
    }

    public void limitComponentsResponsibleProject() {
        limitTextField(tfEmailResponsible, 50);
        prohibitSpacesTextField(tfEmailResponsible);
        limitTextField(tfNameResponsible, 50);
        prohibitNumberTextField(tfNameResponsible);

        limitTextField(tfLastNameResponsible, 50);
        prohibitNumberTextField(tfLastNameResponsible);

        limitTextField(cbCharge.getEditor(), 70);
        prohibitNumberTextField(cbCharge.getEditor());
    }

    public void startComboBox() {
        responsibleProject = new ResponsibleProject();
        allCharge = new ArrayList<>();
        allCharge = responsibleProject.getListCharge();
        cbCharge.getItems().addAll(allCharge);
    }

    public void modifyResponsibleProject() {
        datesUpdate = new ArrayList<>();
        validateDataResponsible();
        if(datesUpdate.size()!= Number.ZERO.getNumber()) {
            if (isValidDataResponsible) {
                boolean isValidateRepeatResponsibleProject = false;
                if (isEmailValidated) {
                    isValidateRepeatResponsibleProject = responsibleProjectEdit.validateRepeatResponsibleProject(responsibleProjectEdit.getEmail());
                } else {
                    isEmailValidated = false;
                }
                if (!isValidateRepeatResponsibleProject) {
                    boolean isModifyResponsibleProject;
                    isModifyResponsibleProject = responsibleProjectEdit.modifyResponsibleProject(responsibleProjectEdit,datesUpdate);
                    if (isModifyResponsibleProject) {
                        generateInformation("El responsable del proyecto se modificó con éxito");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml", btnModify);
                    } else {
                        generateError("El responsable del proyecto no pudo modificarse");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml", btnModify);
                    }
                } else {
                    generateError("Existe un responsable del proyecto con el mismo correo electrónico registrado");
                }
            } else {
                generateAlert("Ingresar datos válidos");
                datesUpdate.clear();
            }
        }else{
            removeStyleClass();
            generateAlert("Modifique datos");
        }
    }

    public void removeStyleClass (){
        tfNameResponsible.getStyleClass().remove("error");
        tfNameResponsible.getStyleClass().remove("ok");
        tfLastNameResponsible.getStyleClass().remove("error");
        tfLastNameResponsible.getStyleClass().remove("ok");
        tfEmailResponsible.getStyleClass().remove("error");
        tfEmailResponsible.getStyleClass().remove("ok");
        cbCharge.getStyleClass().remove("error");
        cbCharge.getStyleClass().remove("ok");
    }

    public void validateName(){
        tfNameResponsible.getStyleClass().remove("error");
        tfNameResponsible.getStyleClass().remove("ok");
        boolean isValidName = validateDataPerson.validateName(tfNameResponsible.getText());
        if (isValidName) {
            tfNameResponsible.getStyleClass().add("ok");
            responsibleProjectEdit.setName(validateDataPerson.deleteSpace(tfNameResponsible.getText()));
        } else {
            tfNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }
        datesUpdate.add("Name");
    }

    public void validateLastName(){
        boolean isValidLastName = validateDataPerson.validateLastName(tfLastNameResponsible.getText());
        tfLastNameResponsible.getStyleClass().remove("error");
        tfLastNameResponsible.getStyleClass().remove("ok");
        if(isValidLastName){
            tfLastNameResponsible.getStyleClass().add("ok");
            responsibleProjectEdit.setLastName(validateDataPerson.deleteSpace(tfLastNameResponsible.getText()));
        }else {
            tfLastNameResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }
        datesUpdate.add("LastName");
    }

    public void validateEmail(){
        boolean isValidEmailResponsible = validateDataPerson.validateEmail(tfEmailResponsible.getText());
        tfEmailResponsible.getStyleClass().remove("error");
        tfEmailResponsible.getStyleClass().remove("ok");
        if(isValidEmailResponsible){
            tfEmailResponsible.getStyleClass().add("ok");
            responsibleProjectEdit.setEmail(tfEmailResponsible.getText());
            isEmailValidated= true;
        }else {
            tfEmailResponsible.getStyleClass().add("error");
            isValidDataResponsible = false;
        }
        datesUpdate.add("Email");
    }

    public void validateCharge (){
        boolean isValidCharge = validateDataPerson.validateCharge(cbCharge.getEditor().getText());
        cbCharge.getStyleClass().remove("error");
        cbCharge.getStyleClass().remove("ok");
        if(isValidCharge){
            cbCharge.getStyleClass().add("ok");
            responsibleProjectEdit.setCharge(validateDataPerson.deleteSpace(cbCharge.getEditor().getText()));
        }else {
            cbCharge.getStyleClass().add("error");
            isValidDataResponsible = false;
        }
        datesUpdate.add("Charge");
    }

    public void validateDataResponsible (){
        validateDataPerson = new ValidateDataPerson();
        isValidDataResponsible = true;
        if(!tfNameResponsible.getText().equals(responsibleProject.getName())) {
            validateName();
        }
        if (!tfLastNameResponsible.getText().equals(responsibleProject.getLastName())){
            validateLastName();
        }
        if (!tfEmailResponsible.getText().equals(responsibleProject.getEmail())){
            validateEmail();
        }
        if (!cbCharge.getEditor().getText().equals(responsibleProject.getCharge())){
            validateCharge();
        }
    }
}


