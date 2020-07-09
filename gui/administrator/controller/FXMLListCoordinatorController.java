package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import domain.Coordinator;
import gui.FXMLGeneralController;

public class FXMLListCoordinatorController extends FXMLGeneralController {
    public TableView<Coordinator> tableCoordinators;
    public TableColumn<Coordinator, Integer> staffNumber;
    public TableColumn<Coordinator, String> name;
    public TableColumn<Coordinator, String> lastName;
    public TableColumn<Coordinator, String> email;
    public TableColumn<Coordinator, String> alternateEmail;
    public TableColumn<Coordinator, String> phone;
    public TableColumn<Coordinator, String> status;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListCoordinators();
    }

    private void colocateListCoordinators() {
        Coordinator coordinator = new Coordinator();
        List<Coordinator> coordinatorList=coordinator.getInformationAllCoordinator();
        staffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        alternateEmail.setCellValueFactory(new PropertyValueFactory<>("alternateEmail"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableCoordinators.getItems().setAll(coordinatorList);
    }

    public void cancel() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }
}