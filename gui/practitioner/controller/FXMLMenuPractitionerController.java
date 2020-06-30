package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * FXML MenuPractitionerController
 * @author Yazmin
 * @version 29/06/2020
 */

public class FXMLMenuPractitionerController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnMyProject;
    @FXML private Button btnRequestProject;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void myProject() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLMyProject.fxml",btnMyProject);
    }

    public void requestProject() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLChooseRequestProject.fxml",btnRequestProject);
    }

    public void logOut() {
        logOutGeneral();
    }
}
