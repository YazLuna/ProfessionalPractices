
package gui.coordinator.controller;

import domain.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logic.ValidateProject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class FXMLUpdateProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateProjectController implements Initializable {
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;
    @FXML private Button btnBehind;
    @FXML private TextField tfMethodology;
    @FXML private TextField tfDuration;
    @FXML private TextField tfName;
    @FXML private TextField tfQuiantityPractitioners;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivities;
    @FXML private TextArea taResponsabilities;
    @FXML private ComboBox cbLapse;
    private static Project project;
    private List<String> allLapse = new ArrayList<>();
    private ValidateProject validateProject = new ValidateProject();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancel();
        behind();
        update();
        startProject();
    }

    public void cancel (){
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert cancel = new Alert(Alert.AlertType.NONE);
                cancel.setAlertType(Alert.AlertType.CONFIRMATION);
                cancel.setHeaderText("Do you want to cancel?");
                cancel.setTitle("Cancel");
                Optional<ButtonType> action = cancel.showAndWait();
                if (action.get() == ButtonType.OK) {
                    Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseSection.fxml"));
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
    }

    public void update () {
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String message;
                if(!validateDataProject()) {
                    Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                    alertDataProject.setAlertType(Alert.AlertType.WARNING);
                    alertDataProject.setHeaderText("Enter correct data in the red fields");
                    alertDataProject.setTitle("Warning");
                    alertDataProject.showAndWait();
                }else{
                    getDataProject();
                    //message = project.modifyProject();
                    Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                    alertDataProject.setAlertType(Alert.AlertType.INFORMATION);
                    //alertDataProject.setHeaderText(message);
                    alertDataProject.setTitle("Information");
                    alertDataProject.showAndWait();
                }
            }
        });
    }

    public void getDataProject () {
        project.setNameProject(validateProject.deleteSpace(tfName.getText()));
        project.setDescription(validateProject.deleteSpace(taDescription.getText()));
        project.setObjectiveGeneral(validateProject.deleteSpace(taObjectiveGeneral.getText()));
        project.setObjectiveInmediate(validateProject.deleteSpace(taObjectiveInmediate.getText()));
        project.setObjectiveMediate(validateProject.deleteSpace(taObjectiveMediate.getText()));
        project.setMethodology(validateProject.deleteSpace(tfMethodology.getText()));
        project.setResources(validateProject.deleteSpace(taResource.getText()));
        project.setActivitiesAndFunctions(validateProject.deleteSpace(taActivities.getText()));
        project.setResponsabilities(validateProject.deleteSpace(taResponsabilities.getText()));
        project.setLapse(validateProject.deleteSpace(cbLapse.getEditor().getText()));
        project.setStaffNumberCoordinator(8);
        int duration = Integer.parseInt(tfDuration.getText());
        project.setDuration(duration);
        int quiantityPractitioner = Integer.parseInt(tfQuiantityPractitioners.getText());
        project.setQuantityPractitioner(quiantityPractitioner);
    }

    public void behind (){
        btnBehind.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnBehind.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseSection.fxml"));
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

    public void startProject(){
        tfMethodology.setText(project.getMethodology());
        tfDuration.setText(String.valueOf(project.getDuration()));
        tfName.setText(project.getNameProject());
        tfQuiantityPractitioners.setText(String.valueOf(project.getQuantityPractitioner()));
        taDescription.setText(project.getDescription());
        taObjectiveGeneral.setText(project.getObjectiveGeneral());
        taObjectiveInmediate.setText(project.getObjectiveInmediate());
        taObjectiveMediate.setText(project.getObjectiveMediate());
        taResource.setText(project.getResources());
        taActivities.setText(project.getActivitiesAndFunctions());
        taResponsabilities.setText(project.getResponsabilities());
        cbLapse.getEditor().setText(project.getLapse());
    }


    public void setProject (Project project){
        this.project = project;
    }

    public boolean validateDataProject () {
        boolean result = true;
        if (!validateProject.validateName(tfName.getText())) {
            tfName.getStyleClass().add("error");
            result = false;
        } else {
            tfName.getStyleClass().remove("error");
        }
        if (!validateProject.validateTextArea(taDescription.getText())) {
            taDescription.getStyleClass().add("error");
            result = false;
        } else {
            taDescription.getStyleClass().remove("error");
        }
        if (!validateProject.validateTextArea(taObjectiveGeneral.getText())) {
            taObjectiveGeneral.getStyleClass().add("error");
            result = false;
        } else {
            taObjectiveGeneral.getStyleClass().remove("error");
        }
        if (!validateProject.validateTextArea(taObjectiveInmediate.getText())) {
            taObjectiveInmediate.getStyleClass().add("error");
            result = false;
        } else {
            taObjectiveInmediate.getStyleClass().remove("error");
        }
        if (!validateProject.validateNotEmpty(taObjectiveMediate.getText()) ||
                !validateProject.validateTextArea(taObjectiveMediate.getText())) {
            taObjectiveMediate.getStyleClass().add("error");
            result = false;
        } else {
            taObjectiveMediate.getStyleClass().remove("error");
        }
        if (!validateProject.validateNotEmpty(tfMethodology.getText()) ||
                !validateProject.validateMethology(tfMethodology.getText())) {
            tfMethodology.getStyleClass().add("error");
            result = false;
        } else {
            tfMethodology.getStyleClass().remove("error");
        }
        if (!validateProject.validateTextArea(taResource.getText())) {
            taResource.getStyleClass().add("error");
            result = false;
        } else {
            taResource.getStyleClass().remove("error");
        }
        if (!validateProject.validateText(taActivities.getText())) {
            taActivities.getStyleClass().add("error");
            result = false;
        } else {
            taActivities.getStyleClass().remove("error");
        }
        if (!validateProject.validateText(taResponsabilities.getText())) {
            taResponsabilities.getStyleClass().add("error");
            result = false;
        } else {
            taResponsabilities.getStyleClass().remove("error");
        }
        if (!validateProject.validateLapse(cbLapse.getEditor().getText())) {
            cbLapse.getStyleClass().add("error");
            result = false;
        } else {
            cbLapse.getStyleClass().remove("error");
        }
        if (!validateProject.validateNotEmpty(tfDuration.getText())) {
            tfDuration.getStyleClass().add("error");
            result = false;
        } else {
            tfDuration.getStyleClass().remove("error");
        }
        if (!validateProject.validateNotEmpty(tfQuiantityPractitioners.getText())) {
            tfQuiantityPractitioners.getStyleClass().add("error");
            result = false;
        } else {
            tfQuiantityPractitioners.getStyleClass().remove("error");
        }
        return result;
    }
}
