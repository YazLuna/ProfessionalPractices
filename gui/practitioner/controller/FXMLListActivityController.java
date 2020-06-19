package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

public class FXMLListActivityController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnCancel;
    @FXML private Button btnAccept;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {

    }

    public void accept() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLAddActivity.fxml",btnAccept);
    }
}
