package gui.administrator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

public class FXMLDeleteTeacherListController extends FXMLGeneralController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void logOut(ActionEvent actionEvent) {
        logOutGeneral();
    }

    public void cancel(ActionEvent actionEvent) {
        cancelGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }
}
