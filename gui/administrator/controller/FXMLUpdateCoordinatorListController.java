package gui.administrator.controller;

import domain.Search;
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

/**
 * Update Coordinator List Controller
 * @author Yazmin
 * @version 08/07/2020
 */
public class FXMLUpdateCoordinatorListController extends FXMLGeneralController implements Initializable {
    @FXML private TableView<Coordinator> tvCoordinators;
    @FXML private TableColumn<Coordinator, Integer> tcStaffNumber;
    @FXML private TableColumn<Coordinator, String> tcName;
    @FXML private TableColumn<Coordinator, String> tcLastName;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListCoordinators();
    }

    private void colocateListCoordinators() {
        List<Coordinator> coordinatorList = Coordinator.getCoordinators();
        if (coordinatorList.size() == Search.NOTFOUND.getValue()) {
            generateError("no hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
        } else {
            tcStaffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tvCoordinators.getItems().setAll(coordinatorList);
        }
    }

    /**
     * Method to cancel the selection and return to the menu
     */
    public void backMenu() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
    }

    /**
     * Method to exit to the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method to choose the coordinator to modify
     */
    public void updateCoordinator() {
        Coordinator coordinatorSelected = tvCoordinators.getSelectionModel().getSelectedItem();
        if(coordinatorSelected == null){
            generateAlert("Por favor seleccione algún coordinador");
        }else{
            FXMLUpdateCoordinatorController.staffNumber =coordinatorSelected.getStaffNumber();
            openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinator.fxml", btnUpdate);
        }
    }
}
