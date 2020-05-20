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
 * Creation of the controller FXMLGenerateReport
 * @author Ivana Correa
 * @version 16/05/2020
 */

public class FXMLGenerateReportController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private TextField tftName;
    @FXML private TextField tftScore;
    @FXML private TextField tftCompletionDate;
    @FXML private TextField tftDeliverDate;
    @FXML private TextArea tftActivities;

    @Override
    public void initialize(URL url, ResourceBundle rb){ }

    public void logOut() {
       logOutGeneral();
    }

    public void cancel() {
       openWindowGeneral("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml",btnCancel);
    }

}
