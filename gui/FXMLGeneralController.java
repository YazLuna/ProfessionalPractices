package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;

public class FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    FileChooser fileChooser = new FileChooser();
    @FXML public ImageView imgProfilePicture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logOutGeneral() {
        try {
            Stage stagePrincipal = (Stage) btnLogOut.getScene().getWindow();
            stagePrincipal.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login/fxml/FXMLLogin.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
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
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void openWindowGeneral(String fxml, TableView tableView) {
        try {
            Stage stagePrincipal = (Stage) tableView.getScene().getWindow();
            stagePrincipal.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }


    public void generateAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        alert.setTitle("Advertencia");
        alert.show();
    }

    public void generateError(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    public boolean generateConfirmation(String message) {
        boolean option = false;
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION, message, YES, NO);
        cancel.setTitle("Confirmar");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.orElse(NO) == YES) {
            option = true;
        }
        return option;
    }

    public void generateInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.setTitle("Información");
        alert.showAndWait();
    }

    public void generateConfirmationCancel(String message, Button btnCancel, String fxml) {
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION, message, YES, NO);
        cancel.setTitle("Cancelar");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.orElse(NO) == YES) {
            openWindowGeneral(fxml, btnCancel);
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

    public static void prohibitWordTextField(TextField textField) {
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

    public static void prohibitWordTextFieldAllowSpecialChar(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^,")) {
                    textField.setText(newValue.replaceAll("[,]", ""));
                }else{
                    if (!newValue.matches("\\d*_,_\\s")) {
                        textField.setText(newValue.replaceAll("[^\\d_,_\\s]", ""));
                    }
                }
            }
        });
    }

    public static void prohibitNumberTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                } else {
                    if (!newValue.matches("[A-Za-z_\\s_ñ_á_é_í_ú_ó]")) {
                        textField.setText(newValue.replaceAll("[^A-Za-z_\\s_ñ_á_é_í_ú_ó]", ""));
                    }
                }
            }
        });
    }

    public static void prohibitNumberAllowSpecialCharTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                } else {
                    if (!newValue.matches("[A-Za-z_\\s_,_ñ_á_é_í_ú_ó]")) {
                        textField.setText(newValue.replaceAll("[^A-Za-z_\\s_,_ñ_á_é_í_ú_ó]", ""));
                    }
                }
            }
        });
    }

    public String createTerm () {
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);
        String term;
        if(Integer.parseInt(month) > Number.ONE.getNumber() && Integer.parseInt(month) <= Number.SEVEN.getNumber()){
            term = "FEBRERO-JULIO "+year;
        } else{
            term = "AGOSTO-ENERO "+year+ " " +(Integer.parseInt(year)+1);
        }
        return term;
    }

    public static void prohibitSpacesTextField(TextField textField) {
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

    public static void prohibitSpacesTextArea(TextArea textArea) {
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

  public String encryptPassword(String password) {
      String passwordEncrypt = null;
      try {
          MessageDigest md;
          md = MessageDigest.getInstance("SHA-512");
          md.update(password.getBytes());
          byte[] mb = md.digest();
          passwordEncrypt = String.valueOf(Hex.encodeHex(mb));
      } catch (NoSuchAlgorithmException e) {
          Logger logger = Logger.getLogger(getClass().getName());
          logger.log(Level.SEVERE, "Failed to create an encrypt Password", e);
      }
      return passwordEncrypt;
  }

}