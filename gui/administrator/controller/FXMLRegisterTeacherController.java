package gui.administrator.controller;

import gui.FXMLGeneralController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

public class FXMLRegisterTeacherController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void registerTeacher(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
        Alert cancel = new Alert(Alert.AlertType.NONE);
        cancel.setAlertType(Alert.AlertType.CONFIRMATION);
        cancel.setHeaderText("Do you want to cancel?");
        cancel.setTitle("Cancel");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.get() == ButtonType.OK) {
            cancelGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
        }
    }

    public void logOut(ActionEvent actionEvent) {
        logOutGeneral();
    }

    public void loadProfilePicture(ActionEvent actionEvent) {
    }
}
