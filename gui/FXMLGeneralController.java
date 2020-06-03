package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
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
    public void generateCancel(String message,Button btnCancel) {
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION,message,YES,NO);
        cancel.setTitle("Cancelar");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.orElse(NO) == YES) {
            openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnCancel);
        }
    }

    public static void limitTextField(TextField textField, int limit) {
        UnaryOperator<TextFormatter.Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textField.setTextFormatter(new TextFormatter(textLimitFilter));
    }

    public static void limitTextArea(TextArea textArea, int limit) {
        UnaryOperator<TextFormatter.Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textArea.setTextFormatter(new TextFormatter(textLimitFilter));
    }

    public static void deleteWorkTextField (TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public static void deleteNumberTextField (TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                }else{
                    if (!newValue.matches("[A-Za-z_\\s]")) {
                        textField.setText(newValue.replaceAll("[^A-Za-z_\\s]", ""));
                    }
                }
            }
        });
    }

    public static void deleteSpacesTextField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                }
            }
        });
    }

    public static void deleteSpacesTextArea(TextArea textArea){
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textArea.setText(newValue.replaceAll("[\\s]", ""));
                }
            }
        });
    }

}

    public void generateConfirmationCancel(String header,String title,String fxml,Button btnCancel){
        Alert cancel = new Alert(Alert.AlertType.NONE);
        cancel.setAlertType(Alert.AlertType.CONFIRMATION);
        cancel.setHeaderText(header);
        cancel.setTitle(title);
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.get() == ButtonType.OK) {
            openWindowGeneral(fxml,btnCancel);
        }
    }
