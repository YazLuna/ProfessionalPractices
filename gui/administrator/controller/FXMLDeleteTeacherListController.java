package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import gui.FXMLGeneralController;

public class FXMLDeleteTeacherListController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
    }
}
