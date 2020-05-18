package gui.coordinator.controller;

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
 * class FXMLSectionProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLSectionProjectController implements Initializable {
    @FXML private Button btnHome;
    @FXML private Button btnRegisterProject;
    @FXML private Button btnUpdateProject;
    @FXML private Button btnListProject;
    @FXML private Button btnLogout;
    @FXML private Button btnDeleteProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegisterProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnRegisterProject.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLRegisterProject.fxml"));
                Stage stage = new Stage();
                try {
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage.setScene(new Scene(root1));
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
                stage.setResizable(false);
                stage.setHeight(500);
                stage.setWidth(610);
                stage.show();
            }
        });

        btnUpdateProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FXMLChooseProjectController chooseProject = new FXMLChooseProjectController();
                chooseProject.controllerSection("update");
                Stage stagePrincipal = (Stage) btnUpdateProject.getScene().getWindow();
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

        btnDeleteProject.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FXMLChooseProjectController chooseProject = new FXMLChooseProjectController();
                chooseProject.controllerSection("delete");
                Stage stagePrincipal = (Stage) btnDeleteProject.getScene().getWindow();
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
        btnHome.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnHome.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml"));
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
        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnLogout.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login/FXMLLogin.fxml"));
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
