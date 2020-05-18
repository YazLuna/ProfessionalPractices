package gui.administrator.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import domain.Coordinator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import gui.FXMLGeneralController;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FXMLMenuAdministratorController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnRegisterCoordinator;
    @FXML private Button btnRegisterTeacher;
    @FXML private Button btnDeleteCoordinator;
    @FXML private Button btnDeleteTeacher;
    @FXML private Button btnUpdateCoordinator;
    @FXML private Button btnUpdateTeacher;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void logOut(ActionEvent actionEvent) {
        logOutGeneral();
    }

    public void registerCoordinator(ActionEvent actionEvent) throws SQLException {
        Coordinator coordinator = new Coordinator();
        coordinator = coordinator.getCoordinator();
        if(coordinator.getName() == null){
            openWindowGeneral("/gui/administrator/fxml/FXMLRegisterCoordinator.fxml");
            Stage stagePrincipal = (Stage) btnRegisterCoordinator.getScene().getWindow();
            stagePrincipal.close();
        }else{
            generateError("An active coordinator already exists");
        }
    }

    public void deleteCoordinator(ActionEvent actionEvent) throws SQLException {
        Coordinator coordinator = new Coordinator();
        coordinator = coordinator.getCoordinator();
        if(coordinator.getName() != null){
            openWindowGeneral("/gui/administrator/fxml/FXMLDeleteCoordinator.fxml");
            Stage stagePrincipal = (Stage) btnDeleteCoordinator.getScene().getWindow();
            stagePrincipal.close();
        }else{
            generateError("There is no active coordinator");
        }
    }

    public void updateCoordinator(ActionEvent actionEvent) {
        Stage stagePrincipal = (Stage) btnUpdateCoordinator.getScene().getWindow();
        stagePrincipal.close();
        openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinatorList.fxml");
    }

    public void registerTeacher(ActionEvent actionEvent) {
        Stage stagePrincipal = (Stage) btnRegisterTeacher.getScene().getWindow();
        stagePrincipal.close();
        openWindowGeneral("/gui/administrator/fxml/FXMLRegisterTeacher.fxml");
    }

    public void deleteTeacher(ActionEvent actionEvent) {
        Stage stagePrincipal = (Stage) btnDeleteTeacher.getScene().getWindow();
        stagePrincipal.close();
        openWindowGeneral("/gui/administrator/fxml/FXMLDeleteTeacherList.fxml");
    }

    public void updateTeacher(ActionEvent actionEvent) {
        Stage stagePrincipal = (Stage) btnUpdateTeacher.getScene().getWindow();
        stagePrincipal.close();
        openWindowGeneral("/gui/administrator/fxml/FXMLUpdateTeacherList.fxml");
    }
}
