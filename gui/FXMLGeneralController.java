package gui;

import dataaccess.Number;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextArea;
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

/**
 * FXMLGeneralController
 * @author Yazmin
 * @version 17/07/2020
 */
public class FXMLGeneralController implements Initializable {
	@FXML private Button btnLogOut;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	/**
	 * Method to exit the system
	 */
	public void logOutGeneral() {
		try {
			Stage stagePrincipal = (Stage) btnLogOut.getScene().getWindow();
			stagePrincipal.close();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login/fxml/FXMLLogin.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.log(Level.SEVERE, "Failed to create new Window.", e);
		}
	}

	/**
	 * method to open a window and close the current one
	 * @param fxml origin
	 * @param buttonOrigin button
	 */
	public void openWindowGeneral(String fxml, Button buttonOrigin) {
		try {
			Stage stagePrincipal = (Stage) buttonOrigin.getScene().getWindow();
			stagePrincipal.close();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.log(Level.SEVERE, "Failed to create new Window.", e);
		}
	}

	/**
	 * method to open a window and close the current one
	 * @param fxml origin
	 * @param tableView where they click
	 */
	public void openWindowGeneral(String fxml, TableView tableView) {
		try {
			Stage stagePrincipal = (Stage) tableView.getScene().getWindow();
			stagePrincipal.close();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.log(Level.SEVERE, "Failed to create new Window.", e);
		}
	}

	/**
	 * Method to generate an Alert warning
	 * @param message will appear in the alert
	 */
	public void generateAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setAlertType(Alert.AlertType.WARNING);
		alert.setHeaderText(message);
		alert.setTitle("Advertencia");
		alert.show();
	}

	/**
	 * Method to generate an Alert error
	 * @param message will appear in the alert
	 */
	public void generateError(String message) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setAlertType(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.setTitle("Error");
		alert.showAndWait();
	}

	/**
	 * Method to generate an Alert confirmation
	 * @param message will appear in the alert
	 */
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

	/**
	 * Method to generate an Alert information
	 * @param message will appear in the alert
	 */
	public void generateInformation(String message) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setAlertType(Alert.AlertType.INFORMATION);
		alert.setHeaderText(message);
		alert.setTitle("Información");
		alert.showAndWait();
	}

	/**
	 * Method to generate an Alert confirmation to cancel
	 * @param message will appear in the alert
	 * @param btnCancel button origin
	 * @param fxml origin
	 */
	public void generateCancel(String message, Button btnCancel, String fxml) {
		ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
		ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
		Alert cancel = new Alert(Alert.AlertType.CONFIRMATION, message, YES, NO);
		cancel.setTitle("Cancelar");
		Optional<ButtonType> action = cancel.showAndWait();
		if (action.orElse(NO) == YES) {
			openWindowGeneral(fxml, btnCancel);
		}
	}

	/**
	 * Method to limit the number of characters in a text field
	 * @param textField to limit
	 * @param limit length
	 */
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

	/**
	 * Method to limit the number of characters in a text area
	 * @param textArea to limit
	 * @param limit length
	 */
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

	/**
	 * Method to prohibit words in a text field
	 * @param textField to which the prohibition applies
	 */
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

	/**
	 * Method to prohibit words in a text field
	 * @param textField to which the prohibition applies
	 */
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

	/**
	 * Method to prohibit number in a text field
	 * @param textField to which the prohibition applies
	 */
	public static void prohibitNumberTextField(TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
				if (newValue.matches("^\\s")) {
					textField.setText(newValue.replaceAll("[\\s]", ""));
				} else {
					if (!newValue.matches("[A-Za-z_\\s_ñ_á_é_í_ú_ó_Ñ_Á,É,Í,Ó,Ú]")) {
						textField.setText(newValue.replaceAll("[^A-Za-z_\\s_ñ_á_é_í_ú_ó_Ñ_Á,É,Í,Ó,Ú]", ""));
					}
				}
			}
		});
	}

	/**
	 * Method to prohibit words in a text field and allow specialChar
	 * @param textField to which the prohibition applies
	 */
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

	/**
	 * method to create current period
	 * @return actual term
	 */
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

	/**
	 * Method to prohibit spaces
	 * @param textField where it will be banned
	 */
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

	/**
	 * Method to prohibit spaces
	 * @param textArea where it will be banned
	 */
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

	/**
	 *  method to encrypt a password
	 * @param password to encrypt
	 * @return encryption
	 */
	public String encryptPassword(String password){
		String passwordEncrypt= null;
		try{
			MessageDigest messageDigest;
			messageDigest= MessageDigest.getInstance("SHA-512");
			messageDigest.update(password.getBytes());
			byte[] digest = messageDigest.digest();
			passwordEncrypt= String.valueOf(Hex.encodeHex(digest));
		}catch (NoSuchAlgorithmException e){
			Logger logger = Logger.getLogger(getClass().getName());
			logger.log(Level.SEVERE, "Failed to create an encrypt Password", e);
		}
		return passwordEncrypt;
	}

}