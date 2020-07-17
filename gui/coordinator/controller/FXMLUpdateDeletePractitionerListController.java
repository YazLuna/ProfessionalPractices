package gui.coordinator.controller;

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
import domain.Practitioner;
import gui.FXMLGeneralController;

/**
 * Update Delete Practitioner List Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLUpdateDeletePractitionerListController extends FXMLGeneralController implements Initializable {
    public TableView<Practitioner> tvPractitioner;
    public TableColumn<Practitioner, String> tcEnrollment;
    public TableColumn<Practitioner, String> tcName;
    public TableColumn<Practitioner, String> tcLastName;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    public static String action;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(action.equals("Update")){
            colocatePractitionerUpdateList();
            btnUpdate.setVisible(true);
        } else{
            colocatePractitionerDeleteList();
            btnDelete.setVisible(true);
        }
    }

    /**
     * Method to cancel and return to the menu
     */
    public void backMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnCancel);
    }

    /**
     *  Method to exit the system
     */
    public void logOutCoordinator() {
        logOutGeneral();
    }

    /**
     * Method that places the list of Practitioners that can be updated
     */
    public void updatePractitioner() {
        Practitioner practitionerSelected = tvPractitioner.getSelectionModel().getSelectedItem();
        if(practitionerSelected == null){
            generateAlert("Por favor seleccione algún practicante");
        }else{
            FXMLUpdatePractitionerController.enrollment =practitionerSelected.getEnrollment();
            openWindowGeneral("/gui/coordinator/fxml/FXMLUpdatePractitioner.fxml",btnUpdate);
        }
    }

    /**
     * Method that places the list of Practitioners that can be delete
     */
    public void deletePractitioner() {
        Practitioner practitionerSelected = tvPractitioner.getSelectionModel().getSelectedItem();
        if(practitionerSelected == null){
            generateAlert("Por favor seleccione algún practicante");
        }else{
            FXMLDeletePractitionerController.enrollment =practitionerSelected.getEnrollment();
            openWindowGeneral("/gui/coordinator/fxml/FXMLDeletePractitioner.fxml",btnDelete);
        }
    }

    private void colocatePractitionerUpdateList() {
        List<Practitioner> practitionerList = Practitioner.getPractitioners();
        if (practitionerList.size() == Search.NOTFOUND.getValue()) {
            generateError("No hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnUpdate);
        } else {
            tcEnrollment.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tvPractitioner.getItems().setAll(practitionerList);
        }
    }

    private void colocatePractitionerDeleteList() {
        List<Practitioner> practitionerList = Practitioner.getPractitionersActive();
        if (practitionerList.size() == Search.NOTFOUND.getValue()) {
            generateError("No hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnDelete);
        } else {
            tcEnrollment.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tvPractitioner.getItems().setAll(practitionerList);
        }
    }

}
