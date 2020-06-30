package gui.coordinator.controller;

import domain.Practitioner;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLListPractitionerController extends FXMLGeneralController {
    public TableView<Practitioner> tablePractitioners;
    public TableColumn<Practitioner, Integer> enrollment;
    public TableColumn<Practitioner, String> name;
    public TableColumn<Practitioner, String> lastName;
    public TableColumn<Practitioner, String> email;
    public TableColumn<Practitioner, String> alternateEmail;
    public TableColumn<Practitioner, String> phone;
    public TableColumn<Practitioner, String> status;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListPractitioners();
    }

    private void colocateListPractitioners() {
        Practitioner Practitioner = new Practitioner();
        List<Practitioner> PractitionerList = Practitioner.getInformationPractitioner();
        enrollment.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        alternateEmail.setCellValueFactory(new PropertyValueFactory<>("alternateEmail"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tablePractitioners.getItems().setAll(PractitionerList);
    }

    public void cancel() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }
}
