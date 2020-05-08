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

public class FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnCancel;
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

    public void returnGeneral(String fxmlBack) {
        try {
            Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
            stagePrincipal.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlBack));
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
        Alert alertStaffNumber = new Alert(Alert.AlertType.NONE);
        alertStaffNumber.setAlertType(Alert.AlertType.WARNING);
        alertStaffNumber.setHeaderText(message);
        alertStaffNumber.setTitle("Warning");
        alertStaffNumber.show();
    }

    public void generateError(String message){
        Alert alertStaffNumber = new Alert(Alert.AlertType.NONE);
        alertStaffNumber.setAlertType(Alert.AlertType.ERROR);
        alertStaffNumber.setHeaderText(message);
        alertStaffNumber.setTitle("Error");
        alertStaffNumber.show();
    }

    public void generateConfirmation(String message){
        Alert alertStaffNumber = new Alert(Alert.AlertType.NONE);
        alertStaffNumber.setAlertType(Alert.AlertType.CONFIRMATION);
        alertStaffNumber.setHeaderText(message);
        alertStaffNumber.setTitle("Confirmation");
        alertStaffNumber.show();
    }
}
