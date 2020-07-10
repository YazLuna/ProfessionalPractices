package gui.coordinator.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import domain.Practitioner;
import gui.FXMLGeneralController;

/**
 * List Practitioner Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLListPractitionerController extends FXMLGeneralController {
    public TableView<Practitioner> tvPractitioners;
    public TableColumn<Practitioner, Integer> tcEnrollment;
    public TableColumn<Practitioner, String> tcName;
    public TableColumn<Practitioner, String> tcLastName;
    public TableColumn<Practitioner, String> tcEmail;
    public TableColumn<Practitioner, String> tcAlternateEmail;
    public TableColumn<Practitioner, String> tcPhone;
    public TableColumn<Practitioner, String> tcStatus;
    public TableColumn<Practitioner, String> tcTerm;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListPractitioners();
    }

    /**
     * Method to return to the menu
     */
    public void backMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnCancel);
    }

    /**
     * Method to exit the system
     */
    public void logOutCoordinator() {
        logOutGeneral();
    }

    private void colocateListPractitioners() {
        List<Practitioner> PractitionerList = Practitioner.getInformationPractitioner();
        tcEnrollment.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcAlternateEmail.setCellValueFactory(new PropertyValueFactory<>("alternateEmail"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tcTerm.setCellValueFactory(new PropertyValueFactory<>("term"));
        tvPractitioners.getItems().setAll(PractitionerList);
    }
}
