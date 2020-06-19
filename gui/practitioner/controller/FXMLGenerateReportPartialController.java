package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Creation of the controller FXMLGenerateReportPartial
 * @author Ivana Correa
 * @version 16/05/2020
 */

public class FXMLGenerateReportPartialController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnGenerate;
    @FXML private Button btnCancel;
    @FXML private TextField tfNumberReport;
    @FXML private TextField tfHoursCovered;
    @FXML private TextField tfMethodology;
    @FXML private TextArea tfObjective;
    @FXML private TextArea tfResultsObtained;
    @FXML private TextArea tfObservations;

    @Override
    public void initialize(URL url, ResourceBundle rb) { configureTextFields(); }

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

    public void removeStyle(){
        tfNumberReport.getStyleClass().remove("ok");
        tfHoursCovered.getStyleClass().remove("ok");
        tfMethodology.getStyleClass().remove("ok");
        tfObjective.getStyleClass().remove("ok");
        tfObservations.getStyleClass().remove("ok");
        tfResultsObtained.getStyleClass().remove("ok");
    }

    public void configureTextFields(){
        prohibitWordTextField(tfNumberReport);
        prohibitSpacesTextField(tfNumberReport);
        prohibitWordTextField(tfHoursCovered);
        prohibitSpacesTextField(tfHoursCovered);
        limitTextField(tfMethodology,50);
        prohibitNumberTextField(tfMethodology);
        prohibitSpacesTextField(tfMethodology);
        limitTextArea(tfObjective,150);
        prohibitSpacesTextArea(tfObjective);
        limitTextArea(tfResultsObtained,150);
        prohibitSpacesTextArea(tfResultsObtained);
        limitTextArea(tfObservations,150);
        prohibitSpacesTextArea(tfObservations);
    }

    public void generate() {
        removeStyle();
        openWindowGeneral("/gui/practitioner/fxml/FXMLPreviewReportPartial.fxml",btnGenerate);
    }

}
