
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
import logic.ValidateDataPerson;
import logic.ValidateLinkedOrganizarion;
import logic.ValidateProject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class FXMLUpdateLinkedOrganizationController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateLinkedOrganizationController implements Initializable {
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;
    @FXML private Button btnBehind;
    @FXML private TextField tfNameOrganization;
    @FXML private TextField tfDirectUsers;
    @FXML private TextField tfIndirectUsers;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhoneNumber;
    @FXML private TextField tfAdress;
    @FXML private ComboBox cbState;
    @FXML private ComboBox cbCity;
    @FXML private ComboBox cbSector;
    private List<String> allCity = new ArrayList<>();
    private List<String> allState = new ArrayList<>();
    private List<String> allSector = new ArrayList<>();
    private ValidateProject validateProject = new ValidateProject();
    private ValidateLinkedOrganizarion validateOrganizarion = new ValidateLinkedOrganizarion();
    private ValidateDataPerson validateDataPerson = new ValidateDataPerson();
    private static Project project;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancel();
        behind();
        update();
        startOrganization();
        startComboBox();
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
                if(!validateDataOrganization()) {
                    Alert alertDataProject = new Alert(Alert.AlertType.NONE);
                    alertDataProject.setAlertType(Alert.AlertType.WARNING);
                    alertDataProject.setHeaderText("Enter correct data in the red fields");
                    alertDataProject.setTitle("Warning");
                    alertDataProject.showAndWait();
                }else{
                    getDataOrganization();
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

    public void getDataOrganization () {
        project.getOrganization().setName(validateProject.deleteSpace(tfNameOrganization.getText()));
        int directUsers = Integer.parseInt(tfDirectUsers.getText());
        project.getOrganization().setDirectUsers(directUsers);
        int indirectUsers = Integer.parseInt(tfIndirectUsers.getText());
        project.getOrganization().setIndirectUsers(indirectUsers);
        project.getOrganization().setEmail(tfEmail.getText());
        project.getOrganization().setPhoneNumber(tfPhoneNumber.getText());
        project.getOrganization().setAddress(validateProject.deleteSpace(tfAdress.getText()));
        project.getOrganization().setCity(validateProject.deleteSpace(cbCity.getEditor().getText()));
        project.getOrganization().setSector(validateProject.deleteSpace(cbSector.getEditor().getText()));
        project.getOrganization().setState(validateProject.deleteSpace(cbState.getEditor().getText()));
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

    public void startOrganization (){
        tfNameOrganization.setText(project.getOrganization().getName());
        tfDirectUsers.setText(String.valueOf(project.getOrganization().getDirectUsers()));
        tfIndirectUsers.setText(String.valueOf(project.getOrganization().getIndirectUsers()));
        tfEmail.setText(project.getOrganization().getEmail());
        tfPhoneNumber.setText(project.getOrganization().getPhoneNumber());
        tfAdress.setText(project.getOrganization().getAddress());
        cbState.getEditor().setText(project.getOrganization().getState());
        cbCity.getEditor().setText(project.getOrganization().getCity());
        cbSector.getEditor().setText(project.getOrganization().getSector());
    }

    public void startComboBox () {
        allCity = project.getOrganization().listCity();
        cbCity.getItems().addAll(allCity);
        allSector = project.getOrganization().listSector();
        cbSector.getItems().addAll(allSector);
        allState = project.getOrganization().listState();
        cbState.getItems().addAll(allState);
    }

    public void setProject (Project project){
        this.project = project;
    }

    public boolean validateDataOrganization (){
        boolean result = true;
        if(!validateOrganizarion.validateNameLinked(tfNameOrganization.getText())){
            tfNameOrganization.getStyleClass().add("error");
            result= false;
        }else {
            tfNameOrganization.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfDirectUsers.getText())){
            tfDirectUsers.getStyleClass().add("error");
            result= false;
        }else {
            tfDirectUsers.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfIndirectUsers.getText())){
            tfIndirectUsers.getStyleClass().add("error");
            result= false;
        }else {
            tfIndirectUsers.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validatePhoneNumber(tfPhoneNumber.getText())){
            tfPhoneNumber.getStyleClass().add("error");
            result= false;
        }else {
            tfPhoneNumber.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateAddress(tfAdress.getText())){
            tfAdress.getStyleClass().add("error");
            result= false;
        }else {
            tfAdress.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateComboBox(cbCity.getEditor().getText())){
            cbCity.getStyleClass().add("error");
            result= false;
        }else {
            cbCity.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateComboBox(cbSector.getEditor().getText())){
            cbSector.getStyleClass().add("error");
            result= false;
        }else {
            cbSector.getStyleClass().remove("error");
        }
        if(!validateOrganizarion.validateComboBox(cbState.getEditor().getText())){
            cbState.getStyleClass().add("error");
            result= false;
        }else {
            cbState.getStyleClass().remove("error");
        }
        if(!validateDataPerson.validateEmail(tfEmail.getText())){
            tfEmail.getStyleClass().add("error");
            result= false;
        }else {
            tfEmail.getStyleClass().remove("error");
        }
        return  result;
    }
}
