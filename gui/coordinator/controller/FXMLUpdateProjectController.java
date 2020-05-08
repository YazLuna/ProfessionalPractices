
package gui.coordinator.controller;

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
 * class FXMLUpdateProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateProjectController implements Initializable {
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;
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
    @FXML private ScrollBar sbSpacer;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseSection.fxml"));
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
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnUpdate.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseSection.fxml"));
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
