package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * class FXMLChooseRequestProjectController
 * @author MARTHA
 * @version 08/05/2020
 */

public class FXMLChooseRequestProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml", btnBehind);
    }
}
