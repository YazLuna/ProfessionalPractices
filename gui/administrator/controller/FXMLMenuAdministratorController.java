package gui.administrator.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import domain.Coordinator;
import gui.FXMLGeneralController;

public class FXMLMenuAdministratorController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnListCoordinator;
    @FXML private Button btnListTeacher;
    @FXML private Button btnRegisterCoordinator;
    @FXML private Button btnRegisterTeacher;
    @FXML private Button btnDeleteCoordinator;
    @FXML private Button btnDeleteTeacher;
    @FXML private Button btnUpdateCoordinator;
    @FXML private Button btnUpdateTeacher;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void logOut() {
        logOutGeneral();
    }

    public void registerCoordinator() throws SQLException {
        Coordinator coordinator = new Coordinator();
        coordinator = coordinator.getCoordinator();
        if(coordinator.getName() == null){
            openWindowGeneral("/gui/administrator/fxml/FXMLRegisterCoordinator.fxml",btnRegisterCoordinator);
        }else{
            generateError("An active coordinator already exists");
        }
    }

    public void deleteCoordinator() throws SQLException {
        Coordinator coordinator = new Coordinator();
        coordinator = coordinator.getCoordinator();
        if(coordinator.getName() != null){
            openWindowGeneral("/gui/administrator/fxml/FXMLDeleteCoordinator.fxml",btnDeleteCoordinator);
        }else{
            generateError("There is no active coordinator");
        }
    }

    public void updateCoordinator() {
        openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinatorList.fxml",btnUpdateCoordinator);
    }

    public void registerTeacher() {
        openWindowGeneral("/gui/administrator/fxml/FXMLRegisterTeacher.fxml", btnRegisterTeacher);
    }

    public void deleteTeacher() {
        openWindowGeneral("/gui/administrator/fxml/FXMLDeleteTeacherList.fxml", btnDeleteTeacher);
    }

    public void updateTeacher() {
        openWindowGeneral("/gui/administrator/fxml/FXMLUpdateTeacherList.fxml", btnUpdateTeacher);
    }

    public void listCoordinator() {
    }

    public void listTeacher() {
    }

}
