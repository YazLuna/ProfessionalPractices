package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * class FXMLMyProjectController
 * @author MARTHA
 * @version 08/05/2020
 */

public class FXMLMyProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private TextField tfNameOrganization;
    @FXML private TextField tfDirectUsers;
    @FXML private TextField tfIndirectUsers;
    @FXML private TextField tfEmailOrganization;
    @FXML private TextField tfPhoneOrganization;
    @FXML private TextField tfAdressOrganization;
    @FXML private TextField tfNameResponsible;
    @FXML private TextField tfLastNameResponsible;
    @FXML private TextField tfEmailResponsible;
    @FXML private TextField tfNameProject;
    @FXML private TextField tfMethodology;
    @FXML private TextField tfDuration;
    @FXML private TextField tfQuantityPractitioners;
    @FXML private TextField tfState;
    @FXML private TextField tfCity;
    @FXML private TextField tfSector;
    @FXML private TextField tfCharge;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivities;
    @FXML private TextArea taResponsabilities;
    @FXML private ScrollBar sbSpacer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cancel() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml",btnBehind);
    }
}
