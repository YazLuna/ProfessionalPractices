package gui.coordinator.controller;

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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Project;

/**
 * class FXMLDeleteProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLDeleteProjectController implements Initializable {
    @FXML private Button btnDeleteProject;
    @FXML private Button btnCancelProject;
    @FXML private Button btnBehind;
    private static Project project;
    @FXML private TextField tfNameOrganization;
    @FXML private TextField tfDirectUsers;
    @FXML private TextField tfIndirectUsers;
    @FXML private TextField tfEmailOrganization;
    @FXML private TextField tfPhoneOrganization;
    @FXML private TextField tfAdressOrganization;
    @FXML private TextField tfNameResponsible;
    @FXML private TextField tfLastNameResponsible;
    @FXML private TextField tfEmailResponsible;
    @FXML private TextField tfNameProject;
    @FXML private TextField tfMethodology;
    @FXML private TextField tfDuration;
    @FXML private TextField tfQuiantityPractitioners;
    @FXML private TextField tfCity;
    @FXML private TextField tfLapse;
    @FXML private TextField tfSector;
    @FXML private TextField tfCharge;
    @FXML private TextField tfState;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivities;
    @FXML private TextArea taResponsabilities;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startProject();
        btnDeleteProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String message;
                message = project.deleteProject();
                Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                alertDataProject.setAlertType(Alert.AlertType.INFORMATION);
                alertDataProject.setHeaderText(message);
                alertDataProject.setTitle("Information");
                alertDataProject.showAndWait();
                Stage stagePrincipal = (Stage) btnDeleteProject.getScene().getWindow();
                stagePrincipal.close();
                Stage stage = new Stage();
                stage.setResizable(false);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseProject.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage.setScene(new Scene(root1));
                } catch (Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
                stage.show();
            }
        });

        btnCancelProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert cancel = new Alert(Alert.AlertType.NONE);
                cancel.setAlertType(Alert.AlertType.CONFIRMATION);
                cancel.setHeaderText("Do you want to cancel?");
                cancel.setTitle("Cancel");
                Optional<ButtonType> action = cancel.showAndWait();
                if (action.get() == ButtonType.OK) {
                    Stage stagePrincipal = (Stage) btnCancelProject.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseProject.fxml"));
                    Stage stage = new Stage();
                    try {
                        Parent root1 = (Parent) fxmlLoader.load();
                        stage.setScene(new Scene(root1));
                    } catch (Exception e) {
                        Logger logger = Logger.getLogger(getClass().getName());
                        logger.log(Level.SEVERE, "Failed to create new Window.", e);
                    }
                    stage.setResizable(false);
                    stage.show();
                }
            }
        });

        btnBehind.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnBehind.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseProject.fxml"));
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

    public void setProject (Project project){
        this.project = project;
    }

    public void startProject () {
        tfNameOrganization.setText(project.getOrganization().getName());
        tfDirectUsers.setText(String.valueOf(project.getOrganization().getDirectUsers()));
        tfIndirectUsers.setText(String.valueOf(project.getOrganization().getIndirectUsers()));
        tfEmailOrganization.setText(project.getOrganization().getEmail());
        tfPhoneOrganization.setText(project.getOrganization().getPhoneNumber());
        tfAdressOrganization.setText(project.getOrganization().getAddress());
        tfNameResponsible.setText(project.getResponsible().getName());
        tfLastNameResponsible.setText(project.getResponsible().getLastName());
        tfEmailResponsible.setText(project.getResponsible().getEmail());
        tfNameProject.setText(project.getNameProject());
        tfMethodology.setText(project.getMethodology());
        tfDuration.setText(String.valueOf(project.getDuration()));
        tfQuiantityPractitioners.setText(String.valueOf(project.getQuantityPractitioner()));
        tfCity.setText(project.getOrganization().getCity());
        tfLapse.setText(project.getLapse());
        tfSector.setText(project.getOrganization().getSector());
        tfCharge.setText(project.getResponsible().getCharge());
        tfState.setText(project.getOrganization().getState());
        taDescription.setText(project.getDescription());
        taObjectiveGeneral.setText(project.getObjectiveGeneral());
        taObjectiveInmediate.setText(project.getObjectiveInmediate());
        taObjectiveMediate.setText(project.getObjectiveMediate());
        taResource.setText(project.getResources());
        taActivities.setText(project.getActivities());
        taResponsabilities.setText(project.getResponsabilities());
    }
}
