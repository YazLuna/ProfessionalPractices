package gui.coordinator.controller;

import domain.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class FXMLChooseSectionController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLChooseSectionController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnSectionOrganization;
    @FXML private Button btnSectionResponsible;
    @FXML private Button btnSectionProject;
    @FXML private Button btnLogout;
    private static Project project;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        behind();
        updateProject();
        updateOrganization();
        updateResponsible();
    }
    public void setProject (Project project){
        this.project = project;
    }

    public void behind (){
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
    public void updateOrganization (){
        btnSectionOrganization.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FXMLUpdateLinkedOrganizationController updateOrganization = new FXMLUpdateLinkedOrganizationController();
                updateOrganization.setProject(project);
                Stage stagePrincipal = (Stage) btnSectionOrganization.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLUpdateLinkedOrganization.fxml"));
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

    public void updateProject () {
        btnSectionProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FXMLUpdateProjectController updateProject = new FXMLUpdateProjectController();
                updateProject.setProject(project);
                Stage stagePrincipal = (Stage) btnSectionProject.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLUpdateProject.fxml"));
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

    public void updateResponsible () {
        btnSectionResponsible.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FXMLUpdateResponsibleProjectController updateResponsible = new FXMLUpdateResponsibleProjectController();
                updateResponsible.setProject(project);
                Stage stagePrincipal = (Stage) btnSectionResponsible.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLUpdateResponsibleProject.fxml"));
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
