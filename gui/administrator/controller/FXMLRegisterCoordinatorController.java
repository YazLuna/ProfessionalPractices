package gui.administrator.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Coordinator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class FXMLRegisterCoordinatorController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnCancel;
    @FXML private Button btnRegister;
    @FXML private TextField tfStaffNumber;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
                    stagePrincipal.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/administrator/fxml/FXMLSectionCoordinator.fxml"));
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
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if((tfStaffNumber.getLength() != 0) || (tfName.getLength() != 0) ||  (tfLastName.getLength() != 0) || (tfEmail.getLength() != 0) || (tfAlternateEmail.getLength() != 0) || (tfPhone.getLength() != 0)){
                    Coordinator coordinator = new Coordinator();
                    coordinator.setStaffNumber(Integer.parseInt(tfStaffNumber.getText()));
                    coordinator.setName(tfName.getText());
                    coordinator.setLastName(tfLastName.getText());
                    coordinator.setEmail(tfEmail.getText());
                    coordinator.setAlternateEmail(tfAlternateEmail.getText());
                    coordinator.setPhone(tfPhone.getText());

                    ToggleGroup radioGroup = new ToggleGroup();
                    rbFemale.setToggleGroup(radioGroup);
                    rbFemale.setToggleGroup(radioGroup);
                    if(rbMale.isSelected()){
                        coordinator.setGender(1);
                    }
                    if(rbFemale.isSelected()){
                        coordinator.setGender(0);
                    }

                    Date myDate = new Date();
                    coordinator.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));

                }else{
                    Alert alertStaffNumber = new Alert(Alert.AlertType.NONE);
                    alertStaffNumber.setAlertType(Alert.AlertType.WARNING);
                    alertStaffNumber.setHeaderText("Enter all the fields ");
                    alertStaffNumber.setTitle("Warning");
                    alertStaffNumber.show();
                }



            }
        });
    }

}
