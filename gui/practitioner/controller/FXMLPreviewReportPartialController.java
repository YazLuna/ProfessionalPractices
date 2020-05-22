package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Creation of the controller PreviewReportPartial
 * @author Ivana Correa
 * @version 20/05/2020
 */

public class FXMLPreviewReportPartialController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnCancel;
    @FXML private Button btnPost;

    @Override
    public void initialize(URL url, ResourceBundle rb){ }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLGenerateReportPartial.fxml",btnCancel);
    }
}
