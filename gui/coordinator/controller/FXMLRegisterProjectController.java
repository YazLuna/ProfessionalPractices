package gui.coordinator.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.LinkedOrganization;
import domain.ResponsibleProject;
import domain.Project;
import logic.ValidateDataPerson;
import logic.ValidateLinkedOrganizarion;
import logic.ValidateProject;

/**
 * class FXMLRegisterProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLRegisterProjectController implements Initializable {
    @FXML private Button btnRegisterProject;
    @FXML private Button btnCancelProject;
    @FXML private TextField tfNameOrganization;
    @FXML private TextField tfDirectUsers;
    @FXML private TextField tfIndirectUsers;
    @FXML private TextField tfEmailOrganization;
    @FXML private TextField tfPhoneNumber;
    @FXML private TextField tfAdress;
    @FXML private TextField tfNameResponsible;
    @FXML private TextField tfLastNameResponsible;
    @FXML private TextField tfEmailResponsible;
    @FXML private TextField tfNameProject;
    @FXML private TextField tfMethodology;
    @FXML private TextField tfDuration;
    @FXML private TextField tfQuiantityPractitioners;
    @FXML private ComboBox cbState;
    @FXML private ComboBox cbCity;
    @FXML private ComboBox cbSector;
    @FXML private ComboBox cbCharge;
    @FXML private ComboBox cbLapse;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivities;
    @FXML private TextArea taResponsabilities;
    private List<String> allCity = new ArrayList<>();
    private List<String> allState = new ArrayList<>();
    private List<String> allSector = new ArrayList<>();
    private List<String> allCharge = new ArrayList<>();
    private List<String> allLapse = new ArrayList<>();
    private LinkedOrganization organization = new LinkedOrganization();
    private Project project = new Project();
    private ResponsibleProject responsible = new ResponsibleProject();
    private ValidateProject validateProject = new ValidateProject();
    private ValidateLinkedOrganizarion validateOrganizarion = new ValidateLinkedOrganizarion();
    private ValidateDataPerson validateDataPerson = new ValidateDataPerson();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startTextField();
        startComboBox();
        registerProject();
        cancelProject();
    }

    public void cancelProject () {
        btnCancelProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert cancel = new Alert(Alert.AlertType.NONE);
                cancel.setAlertType(Alert.AlertType.CONFIRMATION);
                cancel.setHeaderText("Do you want to cancel?");
                cancel.setTitle("Cancel");
                Optional<ButtonType> action = cancel.showAndWait();
                if (action.get() == ButtonType.OK) {

                }
            }
        });
    }
    public void registerProject() {
        btnRegisterProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String message;
                if(!validateDataProject()) {
                    Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                    alertDataProject.setAlertType(Alert.AlertType.WARNING);
                    alertDataProject.setHeaderText("Enter correct data in the red fields");
                    alertDataProject.setTitle("Warning");
                    alertDataProject.show();
                }else{
                    getDataProject();
                    message = project.registerProject();
                    Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                    alertDataProject.setAlertType(Alert.AlertType.INFORMATION);
                    alertDataProject.setHeaderText(message);
                    alertDataProject.setTitle("Information");
                    alertDataProject.show();
                }
            }
        });
    }
    public void startTextField (){
        limitTextField(tfNameOrganization,50);
        deleteNumberTextField(tfNameOrganization);
        limitTextField(tfDirectUsers,100);
        deleteNumberTextField(tfDirectUsers);
        limitTextField(tfIndirectUsers,100);
        deleteNumberTextField(tfIndirectUsers);
        limitTextField(tfEmailOrganization,30);
        deleteSpacesTextField(tfEmailOrganization);
        limitTextField(tfEmailResponsible,50);
        deleteSpacesTextField(tfEmailResponsible);
        limitTextField(tfAdress,50);
        deleteSpacesTextField(tfAdress);
        limitTextField(tfPhoneNumber,10);
        deleteWorkTextField(tfPhoneNumber);
        limitTextField(tfNameResponsible,50);
        deleteNumberTextField(tfNameResponsible);
        limitTextField(tfLastNameResponsible,50);
        deleteNumberTextField(tfLastNameResponsible);
        limitTextField(tfNameProject,25);
        deleteNumberTextField(tfNameProject);
        limitTextField(tfMethodology,15);
        deleteNumberTextField(tfMethodology);
        limitTextField(tfDuration,2);
        deleteWorkTextField(tfDuration);
        limitTextField(tfQuiantityPractitioners,1);
        deleteWorkTextField(tfQuiantityPractitioners);
        limitTextField(cbState.getEditor(),25);
        deleteNumberTextField(cbState.getEditor());
        limitTextField(cbCity.getEditor(),25);
        deleteNumberTextField(cbCity.getEditor());
        limitTextField(cbSector.getEditor(),25);
        deleteNumberTextField(cbSector.getEditor());
        limitTextField(cbCharge.getEditor(),25);
        deleteNumberTextField(cbCharge.getEditor());
        limitTextField(cbLapse.getEditor(),30);
        deleteSpacesTextField(cbLapse.getEditor());
        limitTextArea(taDescription,100);
        deleteSpacesTextArea(taDescription);
        limitTextArea(taObjectiveGeneral,100);
        deleteSpacesTextArea(taObjectiveGeneral);
        limitTextArea(taObjectiveInmediate,100);
        deleteSpacesTextArea(taObjectiveInmediate);
        limitTextArea(taObjectiveMediate,100);
        deleteSpacesTextArea(taObjectiveMediate);
        limitTextArea(taResource,100);
        deleteSpacesTextArea(taResource);
        limitTextArea(taActivities,255);
        deleteSpacesTextArea(taActivities);
        limitTextArea(taResponsabilities,255);
        deleteSpacesTextArea(taResponsabilities);
    }

    public static void limitTextField(TextField textField, int limit) {
        UnaryOperator<Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textField.setTextFormatter(new TextFormatter(textLimitFilter));
    }

    public static void limitTextArea(TextArea textArea, int limit) {
        UnaryOperator<Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textArea.setTextFormatter(new TextFormatter(textLimitFilter));
    }

    public static void deleteWorkTextField (TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public static void deleteNumberTextField (TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                }else{
                    if (!newValue.matches("[A-Za-z_\\s]")) {
                        textField.setText(newValue.replaceAll("[^A-Za-z_\\s]", ""));
                    }
                }
            }
        });
    }
    public static void deleteSpacesTextField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                }
            }
        });
    }

    public static void deleteSpacesTextArea(TextArea textArea){
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textArea.setText(newValue.replaceAll("[\\s]", ""));
                }
            }
        });
    }

    public void startComboBox () {
        allCity = organization.listCity();
        cbCity.getItems().addAll(allCity);
        allState = organization.listState();
        cbState.getItems().addAll(allState);
        allSector = organization.listSector();
        cbSector.getItems().addAll(allSector);
        allLapse = project.listLapse();
        cbLapse.getItems().addAll(allLapse);
        allCharge = responsible.listCharge();
        cbCharge.getItems().addAll(allCharge);
    }

    public void getDataProject () {
        project.setNameProject(validateProject.deleteSpace(tfNameProject.getText()));
        project.setDescription(validateProject.deleteSpace(taDescription.getText()));
        project.setObjectiveGeneral(validateProject.deleteSpace(taObjectiveGeneral.getText()));
        project.setObjectiveInmediate(validateProject.deleteSpace(taObjectiveInmediate.getText()));
        project.setObjectiveMediate(validateProject.deleteSpace(taObjectiveMediate.getText()));
        project.setMethodology(validateProject.deleteSpace(tfMethodology.getText()));
        project.setResources(validateProject.deleteSpace(taResource.getText()));
        project.setActivities(validateProject.deleteSpace(taActivities.getText()));
        project.setResponsabilities(validateProject.deleteSpace(taResponsabilities.getText()));
        project.setLapse(validateProject.deleteSpace(cbLapse.getEditor().getText()));
        project.setStaffNumberCoordinator(8);
        int duration = Integer.parseInt(tfDuration.getText());
        project.setDuration(duration);
        int quiantityPractitioner = Integer.parseInt(tfQuiantityPractitioners.getText());
        project.setQuantityPractitioner(quiantityPractitioner);
        organization.setName(validateProject.deleteSpace(tfNameOrganization.getText()));

        organization.setEmail(tfEmailOrganization.getText());
        organization.setPhoneNumber(tfPhoneNumber.getText());
        organization.setAddress(validateProject.deleteSpace(tfAdress.getText()));
        organization.setCity(validateProject.deleteSpace(cbCity.getEditor().getText()));
        organization.setSector(validateProject.deleteSpace(cbSector.getEditor().getText()));
        organization.setState(validateProject.deleteSpace(cbState.getEditor().getText()));
        project.setOrganization(organization);
        responsible.setName(validateProject.deleteSpace(tfNameResponsible.getText()));
        responsible.setLastName(validateProject.deleteSpace(tfLastNameResponsible.getText()));
        responsible.setEmail(tfEmailResponsible.getText());
        responsible.setCharge(validateProject.deleteSpace(cbCharge.getEditor().getText()));
        project.setResponsible(responsible);
    }

    public boolean validateDataProject (){
        boolean result = true;
        if(!validateProject.validateNameProject(tfNameProject.getText()))  {
            tfNameProject.getStyleClass().add("error");
            result= false;
        }else{
            tfNameProject.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taDescription.getText())){
            taDescription.getStyleClass().add("error");
            result= false;
        }else{
            taDescription.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taObjectiveGeneral.getText())){
            taObjectiveGeneral.getStyleClass().add("error");
            result= false;
        }else{
            taObjectiveGeneral.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taObjectiveInmediate.getText())){
            taObjectiveInmediate.getStyleClass().add("error");
            result= false;
        }else{
            taObjectiveInmediate.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(taObjectiveMediate.getText()) ||
                !validateProject.validateTextArea(taObjectiveMediate.getText())){
            taObjectiveMediate.getStyleClass().add("error");
            result= false;
        }else {
            taObjectiveMediate.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfMethodology.getText()) ||
                !validateProject.validateMethology(tfMethodology.getText())){
            tfMethodology.getStyleClass().add("error");
            result= false;
        }else {
            tfMethodology.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taResource.getText())){
            taResource.getStyleClass().add("error");
            result= false;
        }else {
            taResource.getStyleClass().remove("error");
        }
        if(!validateProject.validateText(taActivities.getText())){
            taActivities.getStyleClass().add("error");
            result= false;
        }else {
            taActivities.getStyleClass().remove("error");
        }
        if(!validateProject.validateText(taResponsabilities.getText())){
            taResponsabilities.getStyleClass().add("error");
            result= false;
        }else {
            taResponsabilities.getStyleClass().remove("error");
        }
        if(!validateProject.validateLapse(cbLapse.getEditor().getText())){
            cbLapse.getStyleClass().add("error");
            result= false;
        }else {
            cbLapse.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfDuration.getText())){
            tfDuration.getStyleClass().add("error");
            result= false;
        }else {
            tfDuration.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfQuiantityPractitioners.getText())){
            tfQuiantityPractitioners.getStyleClass().add("error");
            result= false;
        }else{
            tfQuiantityPractitioners.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateNameLinked(tfNameOrganization.getText())){
            tfNameOrganization.getStyleClass().add("error");
            result= false;
        }else {
            tfNameOrganization.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfDirectUsers.getText())){
            tfDirectUsers.getStyleClass().add("error");
            result= false;
        }else {
            tfDirectUsers.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfIndirectUsers.getText())){
            tfIndirectUsers.getStyleClass().add("error");
            result= false;
        }else {
            tfIndirectUsers.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validatePhoneNumber(tfPhoneNumber.getText())){
            tfPhoneNumber.getStyleClass().add("error");
            result= false;
        }else {
            tfPhoneNumber.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateAddress(tfAdress.getText())){
            tfAdress.getStyleClass().add("error");
            result= false;
        }else {
            tfAdress.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateComboBox(cbCity.getEditor().getText())){
            cbCity.getStyleClass().add("error");
            result= false;
        }else {
            cbCity.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateComboBox(cbSector.getEditor().getText())){
            cbSector.getStyleClass().add("error");
            result= false;
        }else {
            cbSector.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateComboBox(cbState.getEditor().getText())){
            cbState.getStyleClass().add("error");
            result= false;
        }else {
            cbState.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateEmail(tfEmailOrganization.getText())){
            tfEmailOrganization.getStyleClass().add("error");
            result= false;
        }else {
            tfEmailOrganization.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateName(tfNameResponsible.getText())){
            tfNameResponsible.getStyleClass().add("error");
            result= false;
        }else {
            tfNameResponsible.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateLastName(tfLastNameResponsible.getText())){
            tfLastNameResponsible.getStyleClass().add("error");
            result= false;
        }else {
            tfLastNameResponsible.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateEmail(tfEmailResponsible.getText())){
            tfEmailResponsible.getStyleClass().add("error");
            result= false;
        }else {
            tfEmailResponsible.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateCharge(cbCharge.getEditor().getText())){
            cbCharge.getStyleClass().add("error");
            result= false;
        }else {
            cbCharge.getStyleClass().remove("error");
        }
        return result;
    }
}

