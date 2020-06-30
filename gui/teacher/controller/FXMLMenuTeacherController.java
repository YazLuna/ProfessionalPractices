package gui.teacher.controller;

import gui.coordinator.controller.FXMLMenuCoordinatorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * DAO User
 * @author Yazmin
 * @version 19/05/2020
 */

public class FXMLMenuTeacherController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnChangeRole;
    @FXML private Button btnGenerateActivity;
    @FXML private Button btnListActivity;
    @FXML private Button btnUpdateActivity;
    @FXML private Button btnDeleteActivity;
    public static boolean isCoordinator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(isCoordinator){
            btnChangeRole.setVisible(true);
        }
    }

    public void deleteActivity() {
    }

    public void updateActivity() {
    }

    public void generateActivity() {
        openWindowGeneral("/gui/teacher/fxml/FXMLGenerateActivity.fxml",btnGenerateActivity);
    }

    public void listActivity() {
    }

    public void logOut() {
        logOutGeneral();
    }

    public void changeRole() {
        FXMLMenuCoordinatorController.isTeacher = true;
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnChangeRole);
    }
}
