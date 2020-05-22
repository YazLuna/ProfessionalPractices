package gui.teacher.controller;

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
    @FXML private Button btnGenerateActivity;
    @FXML private Button btnListActivity;
    @FXML private Button btnUpdateActivity;
    @FXML private Button btnDeleteActivity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
