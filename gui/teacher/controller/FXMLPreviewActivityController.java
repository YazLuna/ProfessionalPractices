package gui.teacher.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Creation of the controller PreviewActivity
 * @author Ivana Correa
 * @version 21/05/2020
 */

public class FXMLPreviewActivityController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnCancel;
    @FXML private Button btnPost;

    @Override
    public void initialize(URL url, ResourceBundle rb){ }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        openWindowGeneral("/gui/teacher/fxml/FXMLGenerateActivity.fxml",btnCancel);
    }
}
