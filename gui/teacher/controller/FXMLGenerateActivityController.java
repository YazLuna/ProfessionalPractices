package gui.teacher.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Creation of the controller FXMLGenerateActivity
 * @author Ivana Correa
 * @version 21/05/2020
 */

public class FXMLGenerateActivityController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnGenerate;
    @FXML private Button btnCancel;
    @FXML private TextField tftName;
    @FXML private TextField tftValue;
    @FXML private TextField tftDescription;
    @FXML private DatePicker dpDeliverDate;

    @Override
    public void initialize(URL url, ResourceBundle rb){ configureTextFields(); }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        Alert cancel = new Alert(Alert.AlertType.NONE);
        cancel.setAlertType(Alert.AlertType.CONFIRMATION);
        cancel.setHeaderText("¿Seguro que desea cancelar?");
        cancel.setTitle("Cancel");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.get() == ButtonType.OK) {
            openWindowGeneral("/gui/teacher/fxml/FXMLMenuTeacher.fxml",btnCancel);
        }
    }

    public void configureTextFields(){
        prohibitSpacesTextField(tftName);
        limitTextField(tftName,20);
        prohibitSpacesTextField(tftDescription);
        limitTextField(tftDescription,100);
        prohibitWordTextField(tftValue);
        prohibitSpacesTextField(tftValue);
        limitTextField(tftValue,5);
    }

    public void removeStyle(){
        tftName.getStyleClass().remove("ok");
        tftDescription.getStyleClass().remove("ok");
        tftValue.getStyleClass().remove("ok");
    }

    public void generate() {
        removeStyle();
        openWindowGeneral("/gui/teacher/fxml/FXMLPreviewActivity.fxml",btnGenerate);
    }
}
