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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Project;
import logic.ValidateDataPerson;
import logic.ValidateProject;

/**
 * class FXMLUpdateResponsibleProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateResponsibleProjectController implements Initializable {
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;
    @FXML private Button btnBehind;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private ComboBox cbCharge;
    private List<String> allCharge = new ArrayList<>();
    private ValidateDataPerson validateDataPerson = new ValidateDataPerson();
    private ValidateProject validateProject = new ValidateProject();
    private static Project project;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancel();
        behind();
        update();
        startResponsible();
        startComboBox();
    }

    public void setProject (Project project){
        this.project = project;
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
                if(!validateResponsible()) {
                    Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                    alertDataProject.setAlertType(Alert.AlertType.WARNING);
                    alertDataProject.setHeaderText("Enter correct data in the red fields");
                    alertDataProject.setTitle("Warning");
                    alertDataProject.showAndWait();
                }else{
                    getDataResponsible();
                    message = project.actualizationProject();
                    Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                    alertDataProject.setAlertType(Alert.AlertType.INFORMATION);
                    alertDataProject.setHeaderText(message);
                    alertDataProject.setTitle("Information");
                    alertDataProject.showAndWait();
                }
            }
        });
    }

    public void getDataResponsible () {
        project.getResponsible().setName(validateProject.deleteSpace(tfName.getText()));
        project.getResponsible().setLastName(validateProject.deleteSpace(tfLastName.getText()));
        project.getResponsible().setEmail(tfEmail.getText());
        project.getResponsible().setCharge(validateProject.deleteSpace(cbCharge.getEditor().getText()));
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

    public void startResponsible (){
        tfName.setText(project.getResponsible().getName());
        tfLastName.setText(project.getResponsible().getLastName());
        tfEmail.setText(project.getResponsible().getEmail());
        cbCharge.getEditor().setText(project.getResponsible().getCharge());
    }

    public void startComboBox () {
        allCharge = project.getResponsible().listCharge();
        cbCharge.getItems().addAll(allCharge);
    }

    public boolean validateResponsible (){
        boolean result = true;
        if(!validateDataPerson.validateName(tfName.getText())){
            tfName.getStyleClass().add("error");
            result= false;
        }else {
            tfName.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateLastName(tfLastName.getText())){
            tfLastName.getStyleClass().add("error");
            result= false;
        }else {
            tfLastName.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateEmail(tfEmail.getText())){
            tfEmail.getStyleClass().add("error");
            result= false;
        }else {
            tfEmail.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateCharge(cbCharge.getEditor().getText())){
            cbCharge.getStyleClass().add("error");
            result= false;
        }else {
            cbCharge.getStyleClass().remove("error");
        }
        return result;
    }
}
