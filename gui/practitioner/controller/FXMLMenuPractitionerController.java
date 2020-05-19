package gui.practitioner.controller;

import javafx.application.Application;
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
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public class FXMLMenuPractitionerController implements Initializable {
    @FXML private Button btnProject;
    @FXML private Button btnGenerateDocumentation;
    @FXML private Button btnUploadDocumentation;
    @FXML private Button btnActivities;
    @FXML private Button btnLogOut;

    @Override
    public void initialize(URL url, ResourceBundle rb){ }

    public void logOut(ActionEvent actionEvent) {
        btnLogOut.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnLogOut.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login/FXMLLogin.fxml"));
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

    public void sectionProject(ActionEvent actionEvent) {
        btnProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnProject.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/practitioner/fxml/FXMLSectionProjectPractitioner.fxml"));
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

    public void sectionGenerateDocumentation(ActionEvent actionEvent) {
        btnGenerateDocumentation.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try {
                    Stage stagePrincipal = (Stage) btnGenerateDocumentation.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/practitioner/fxml/FXMLMenuReports.fxml"));
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

    public void sectionUploadDocumentation(ActionEvent actionEvent) {
    }

    public void sectionActivities(ActionEvent actionEvent) {
    }
}
