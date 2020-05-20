package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

public class FXMLRegisterTeacherController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void registerTeacher() {
    }

    public void cancel() {
        Alert cancel = new Alert(Alert.AlertType.NONE);
        cancel.setAlertType(Alert.AlertType.CONFIRMATION);
        cancel.setHeaderText("Do you want to cancel?");
        cancel.setTitle("Cancel");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.get() == ButtonType.OK) {
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
        }
    }

    public void logOut() {
        logOutGeneral();
    }

    public void loadProfilePicture() {
    }
}
