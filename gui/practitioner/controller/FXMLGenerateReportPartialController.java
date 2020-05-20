package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Creation of the controller FXMLGenerateReportPartial
 * @author Ivana Correa
 * @version 16/05/2020
 */

public class FXMLGenerateReportPartialController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private TextField tfNumberReport;
    @FXML private TextField tfHoursCovered;
    @FXML private TextField tfMethodology;
    @FXML private TextArea tfObjective;
    @FXML private TextArea tfResultsObtained;
    @FXML private TextArea tfObservations;

    @Override
    public void initialize(URL url, ResourceBundle rb){ }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml",btnCancel);
    }

}
