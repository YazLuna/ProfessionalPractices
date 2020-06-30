package gui.teacher.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;
import gui.coordinator.controller.FXMLMenuCoordinatorController;

/**
 * FXMLMenuTeacherController
 * @author Yazmin
 * @version 29/06/2020
 */

public class FXMLMenuTeacherController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnChangeRole;
    public static boolean isCoordinator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(isCoordinator){
            btnChangeRole.setVisible(true);
        }
    }

    public void logOut() {
        logOutGeneral();
    }

    public void changeRole() {
        FXMLMenuCoordinatorController.isTeacher = true;
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnChangeRole);
    }
}
