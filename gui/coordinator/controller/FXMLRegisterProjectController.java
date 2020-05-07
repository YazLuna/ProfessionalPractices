package gui.coordinator.controller;

import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.function.UnaryOperator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Project;

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
    @FXML private ComboBox cbPeriod;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivities;
    @FXML private TextArea taResponsabilities;
    @FXML private ScrollBar sbSpacer;
    @FXML private VBox vData;
    private List<String> allCity;
    private List<String> allState;
    private List<String> allSector;
    private Project project;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startScrollBar();
        startTextField();
        startComboBox();

        btnRegisterProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnRegisterProject.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLSectionProject.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });

        btnCancelProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnCancelProject.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLSectionProject.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
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
        limitTextField(tfDirectUsers,2);
        limitTextField(tfIndirectUsers,2);
        limitTextField(tfEmailOrganization,30);
        limitTextField(tfPhoneNumber,10);
        limitTextField(tfAdress,50);
        limitTextField(tfNameResponsible,50);
        limitTextField(tfLastNameResponsible,50);
        limitTextField(tfEmailResponsible,50);
        limitTextField(tfNameProject,25);
        limitTextField(tfMethodology,15);
        limitTextField(tfDuration,2);
        limitTextField(tfQuiantityPractitioners,1);
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

    public void startComboBox () {
        /*allCity = project.getOrganization().listCity();
        cbCity.setItems((ObservableList<String>) allCity);
        allSector = project.getOrganization().listSector();
        cbSector.setItems((ObservableList<String>) allSector);
        allState = project.getOrganization().listState();
        cbState.setItems((ObservableList<String>) allState);*/
        //cbCharge;
        //cbPeriod;
    }


}

