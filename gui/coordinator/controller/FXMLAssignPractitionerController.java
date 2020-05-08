package gui.coordinator.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class FXMLAssignPractitionerController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLAssignPractitionerController implements Initializable {
    @FXML private TextField tfNamePractitioner;
    @FXML private TextField tfEnrollment;
    @FXML private TextField tfEmailPractitioner;
    @FXML private TextField tfGender;
    @FXML private TextField tfPeriod;
    @FXML private TextField tfPhone;
    @FXML private TextField tfNameProject;
    @FXML private TextField tfDuration;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private Button btnAssing;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAssing.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnAssing.getScene().getWindow();
                stagePrincipal.close();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setHeight(500);
                stage.setWidth(610);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/assignPractitioner/FXMLListOfPractitioners.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLSectionPractitioner.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setScene(new Scene(root1));
                    stage.setHeight(500);
                    stage.setWidth(610);
                    stage.show();
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });
    }    
    
}
