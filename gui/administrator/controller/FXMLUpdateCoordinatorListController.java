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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLUpdateCoordinatorListController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnReturn;
    @FXML private Button btnLogOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void cancel(ActionEvent actionEvent) {
        cancelGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }

    public void logOut(ActionEvent actionEvent) {
        logOutGeneral();
    }
}
