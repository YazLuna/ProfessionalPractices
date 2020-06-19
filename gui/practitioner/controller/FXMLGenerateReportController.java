package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Creation of the controller FXMLGenerateReport
 * @author Ivana Correa
 * @version 16/05/2020
 */

public class FXMLGenerateReportController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnGenerate;
    @FXML private Button btnCancel;
    @FXML private TextField tftName;
    @FXML private DatePicker dpCompletionDate;
    @FXML private DatePicker dpDeliverDate;
    @FXML private TextArea tftActivities;

    @Override
    public void initialize(URL url, ResourceBundle rb) { configureTextFields();}

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        Alert cancel = new Alert(Alert.AlertType.NONE);
        cancel.setAlertType(Alert.AlertType.CONFIRMATION);
        cancel.setHeaderText("¿Desea cancelar?");
        cancel.setTitle("Cancel");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.get() == ButtonType.OK) {
            openWindowGeneral("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml",btnCancel);
        }
    }

    public void configureTextFields(){
        prohibitNumberTextField(tftName);
        prohibitSpacesTextField(tftName);
        limitTextField(tftName,30);
        prohibitSpacesTextArea(tftActivities);
        limitTextArea(tftActivities,200);
    }

    public void removeStyle(){
        tftName.getStyleClass().remove("ok");
        tftActivities.getStyleClass().remove("ok");
    }

    public void generate() {
        removeStyle();
        openWindowGeneral("/gui/practitioner/fxml/FXMLPreviewReport.fxml",btnGenerate);
    }

}
