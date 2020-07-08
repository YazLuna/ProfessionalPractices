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

/**
 * FXMLGetCoordinatorListController
 * @author Yazmin
 * @version 05/07/2020
 */
public class FXMLGetCoordinatorListController extends FXMLGeneralController {
    public TableView<Coordinator> tvCoordinators;
    public TableColumn<Coordinator, Integer> tcStaffNumber;
    public TableColumn<Coordinator, String> tcName;
    public TableColumn<Coordinator, String> tcLastName;
    public TableColumn<Coordinator, String> tcEmail;
    public TableColumn<Coordinator, String> tcAlternateEmail;
    public TableColumn<Coordinator, String> tcPhone;
    public TableColumn<Coordinator, String> tcStatus;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListCoordinators();
    }

    private void colocateListCoordinators() {
        List<Coordinator> coordinatorList = Coordinator.getCoordinatorsInformation();
        tcStaffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcAlternateEmail.setCellValueFactory(new PropertyValueFactory<>("alternateEmail"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tvCoordinators.getItems().setAll(coordinatorList);
    }

    /**
     * Method to cancel the list display
     */
    public void backMenu() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
    }

    /**
     * Method to exit the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }
}
