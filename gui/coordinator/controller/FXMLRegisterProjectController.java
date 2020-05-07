package gui.coordinator.controller;

import domain.LinkedOrganization;
import domain.ResponsibleProject;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Project;
import logic.ValidateDataPerson;
import logic.ValidateLinkedOrganizarion;
import logic.ValidateProject;

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
    @FXML private ScrollBar sbSpacer;
    @FXML private VBox vData;
    private List<String> allCity = new ArrayList<>();
    private List<String> allState = new ArrayList<>();
    private List<String> allSector = new ArrayList<>();
    private List<String> allCharge = new ArrayList<>();
    private List<String> allLapse = new ArrayList<>();
    private Project project = new Project();
    private LinkedOrganization organization = new LinkedOrganization();
    private ResponsibleProject responsible = new ResponsibleProject();
    private ValidateProject validateProject = new ValidateProject();
    private ValidateLinkedOrganizarion validateOrganizarion = new ValidateLinkedOrganizarion();
    private ValidateDataPerson validateDataPerson = new ValidateDataPerson();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startScrollBar();
        startTextField();
        startComboBox();
        buttonRegister();
        btnCancelProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnCancelProject.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLSectionProject.fxml"));
                Stage stage = new Stage();
                try {
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage.setScene(new Scene(root1));
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
                stage.setResizable(false);
                stage.show();
            }
        });
    }
    public void startScrollBar(){
        sbSpacer.setMin(0);
        sbSpacer.setPrefHeight(455);
        sbSpacer.setMax(1050);
        sbSpacer.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                vData.setLayoutY((-new_val.doubleValue()));
            }
        });

    }

    public void startTextField (){
        limitTextField(tfNameOrganization,50);
        deleteNumberTextField(tfNameOrganization);

        limitTextField(tfDirectUsers,2);
        deleteWorkTextField(tfDirectUsers);

        limitTextField(tfIndirectUsers,2);
        deleteWorkTextField(tfIndirectUsers);

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

    public void buttonRegister() {
        btnRegisterProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                project.setNameProject(tfNameProject.getText());
                project.setDescription(taDescription.getText());
                project.setObjectiveGeneral(taObjectiveGeneral.getText());
                project.setObjectiveInmediate(taObjectiveInmediate.getText());
                project.setObjectiveMediate(taObjectiveMediate.getText());
                project.setMethodology(tfMethodology.getText());
                project.setResources(taResource.getText());
                project.setActivities(taActivities.getText());
                project.setResponsabilities(taResponsabilities.getText());


                Stage stagePrincipal = (Stage) btnRegisterProject.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLSectionProject.fxml"));
                Stage stage = new Stage();
                try {
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage.setScene(new Scene(root1));
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
                stage.setResizable(false);
                stage.show();
            }
        });
    }
}

