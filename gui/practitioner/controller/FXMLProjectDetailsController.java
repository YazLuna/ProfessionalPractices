package gui.practitioner.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class FXMLProjectDetailsController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLProjectDetailsController implements Initializable {
    @FXML private Button btnBehind;
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
    @FXML private TextField tfState;
    @FXML private TextField tfCity;
    @FXML private TextField tfSector;
    @FXML private TextField tfCharge;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivities;
    @FXML private TextArea taResponsabilities;
    @FXML private ScrollBar sbSpacer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnBehind.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnBehind.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/practitioner/fxml/FXMLChooseRequestProject.fxml"));
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
    
}
