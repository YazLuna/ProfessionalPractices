package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * DAO User
 * @author Yazmin
 * @version 19/05/2020
 */

public class FXMLMenuPractitionerController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnMyProject;
    @FXML private Button btnRequestProject;
    @FXML private Button btnUpdatePartialReport;
    @FXML private Button btnUpdateMonthlyReport;
    @FXML private Button btnGeneratePartialReport;
    @FXML private Button btnGenerateMonthlyReport;
    @FXML private Button btnListPartialReport;
    @FXML private Button btnListMonthlyReport;
    @FXML private Button btnUploadActivity;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void myProject() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLMyProject.fxml",btnMyProject);
    }

    public void requestProject() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLChooseRequestProject.fxml",btnRequestProject);
    }

    public void uploadActivity() {
    }

    public void generateMonthlyReport() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLGenerateReport.fxml",btnGenerateMonthlyReport);
    }

    public void updateMonthlyReport() {
    }

    public void listMonthlyReport() {
    }

    public void listPartialReport() {
    }

    public void updatePartialReport() {
    }

    public void generatePartialReport() {
        openWindowGeneral("/gui/practitioner/fxml/FXMLGenerateReportPartial.fxml",btnGeneratePartialReport);
    }

    public void logOut() {
        logOutGeneral();
    }
}
