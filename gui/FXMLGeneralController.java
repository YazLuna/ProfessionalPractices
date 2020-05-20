package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO User
 * @author Yazmin
 * @version 19/05/2020
 */

public class FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logOutGeneral() {
        try {
            Stage stagePrincipal = (Stage) btnLogOut.getScene().getWindow();
            stagePrincipal.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login/FXMLLogin.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void openWindowGeneral(String fxml, Button buttonOrigin) {
        try {
            Stage stagePrincipal = (Stage) buttonOrigin.getScene().getWindow();
            stagePrincipal.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }


    public void generateAlert(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        alert.setTitle("Warning");
        alert.show();
    }

    public void generateError(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.setTitle("Error");
        alert.show();
    }

    public void generateConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(message);
        alert.setTitle("Confirmation");
        alert.show();
    }
    public void generateInformation(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.setTitle("Information");
        alert.show();
    }
}
