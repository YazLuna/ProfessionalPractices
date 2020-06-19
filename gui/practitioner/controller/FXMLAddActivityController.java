package gui.practitioner.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Creation of the controller FXMLAddActivityController
 * @author Ivana Correa
 * @version 14/06/2020
 */

public class FXMLAddActivityController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogOut;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private Button btnSelect;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {

    }

    public void select() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoge un archivo pdf");
        ExtensionFilter pdfExtension = new FileChooser.ExtensionFilter("PDF","*.PDF");
        fileChooser.getExtensionFilters().addAll(pdfExtension);

    }

}
