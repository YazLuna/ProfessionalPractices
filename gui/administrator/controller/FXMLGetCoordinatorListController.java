package gui.administrator.controller;

import domain.Search;
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
    @FXML private TableView<Coordinator> tvCoordinators;
    @FXML private TableColumn<Coordinator, Integer> tcStaffNumber;
    @FXML private TableColumn<Coordinator, String> tcName;
    @FXML private TableColumn<Coordinator, String> tcLastName;
    @FXML private TableColumn<Coordinator, String> tcEmail;
    @FXML private TableColumn<Coordinator, String> tcAlternateEmail;
    @FXML private TableColumn<Coordinator, String> tcPhone;
    @FXML private TableColumn<Coordinator, String> tcStatus;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListCoordinators();
    }

    private void colocateListCoordinators() {
        List<Coordinator> coordinatorList = Coordinator.getCoordinatorsInformation();
        if (coordinatorList.size() == Search.NOTFOUND.getValue()) {
            generateError("No hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
        } else {
            tcStaffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tcAlternateEmail.setCellValueFactory(new PropertyValueFactory<>("alternateEmail"));
            tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            tvCoordinators.getItems().setAll(coordinatorList);
        }
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
