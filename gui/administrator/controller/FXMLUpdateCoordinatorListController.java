package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import domain.Coordinator;
import gui.FXMLGeneralController;

public class FXMLUpdateCoordinatorListController extends FXMLGeneralController implements Initializable {
    public TableView<Coordinator> tableCoordinators;
    public TableColumn<Coordinator, Integer> staffNumber;
    public TableColumn<Coordinator, String> name;
    public TableColumn<Coordinator, String> lastName;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListCoordinators();
    }

    private void colocateListCoordinators() {
        Coordinator coordinator = new Coordinator();
        List<Coordinator> coordinatorList=coordinator.getCoordinators();
        staffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableCoordinators.getItems().setAll(coordinatorList);
    }

    public void cancel() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }

    public void updateCoordinator() {
        Coordinator coordinatorSelected = tableCoordinators.getSelectionModel().getSelectedItem();
        if(coordinatorSelected == null){
            generateAlert("Por favor seleccione algún coordinador");
        }else{
            FXMLUpdateCoordinatorController.staffNumber =coordinatorSelected.getStaffNumber();
            openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinator.fxml",btnUpdate);
        }
    }
}
