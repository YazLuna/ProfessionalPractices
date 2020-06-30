package gui.coordinator.controller;

import domain.ResponsibleProject;
import gui.FXMLGeneralController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Project;
import logic.ValidateDataPerson;
import logic.ValidateProject;

/**
 * class FXMLUpdateResponsibleProjectController
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
    private List<String> allCharge;
    private ResponsibleProject responsible;
    private ValidateDataPerson validateDataPerson;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startComponent();
        startComboBox();
        startDataComponent();
    }

    public void behind() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml", btnBehind);
    }

    public void cancel() {
        generateConfirmationCancel("¿Seguro desea cancelar?", btnCancel, "/gui/coordinator/fxml/FXMLListResponsibleProject.fxml");
    }

    public void assignEmailResponsible (String emailResponsibleProject){
        this.emailResponsibleProject = emailResponsibleProject;
    }

    public void startDataComponent () {
        responsible = new ResponsibleProject();
        responsible.setEmail(emailResponsibleProject);
        responsible = responsible.getResponsible();
        tfNameResponsible.setText(responsible.getName());
        tfLastNameResponsible.setText(responsible.getLastName());
        tfEmailResponsible.setText(responsible.getEmail());
        cbCharge.getEditor().setText(responsible.getCharge());
    }

    public void startComponent() {
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
        responsible = new ResponsibleProject();
        allCharge = new ArrayList<>();
        allCharge = responsible.listCharge();
        cbCharge.getItems().addAll(allCharge);
    }

    public void modify() {

    }
}


