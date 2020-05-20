package gui.administrator.controller;

import gui.FXMLGeneralController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import java.io.IOException;

public class FXMLUpdateTeacherListController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cancel() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }
}
