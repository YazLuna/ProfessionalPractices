package gui.coordinator.controller;

import domain.Practitioner;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLUpdateDeletePractitionerListController extends FXMLGeneralController implements Initializable {
    public TableView<Practitioner> tablePractitioner;
    public TableColumn<Practitioner, String> enrollment;
    public TableColumn<Practitioner, String> name;
    public TableColumn<Practitioner, String> lastName;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    public static String action;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(action.equals("Update")){
            colocateListPractitionerUpdate();
            btnUpdate.setVisible(true);
        } else{
            colocateListPractitionerDelete();
            btnDelete.setVisible(true);
        }
    }

    private void colocateListPractitionerUpdate() {
        Practitioner practitioner = new Practitioner();
        List<Practitioner> practitionerList = practitioner.getAllPractitioner();
        enrollment.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tablePractitioner.getItems().setAll(practitionerList);
    }

    private void colocateListPractitionerDelete() {
        Practitioner practitioner = new Practitioner();
        List<Practitioner> practitionerList = practitioner.getPractitionersActive();
        enrollment.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tablePractitioner.getItems().setAll(practitionerList);
    }

    public void cancel() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }

    public void updatePractitioner() {
        Practitioner practitionerSelected = tablePractitioner.getSelectionModel().getSelectedItem();
        if(practitionerSelected == null){
            generateAlert("Por favor seleccione algún practicante");
        }else{
            FXMLUpdatePractitionerController.enrollment =practitionerSelected.getEnrollment();
            openWindowGeneral("/gui/coordinator/fxml/FXMLUpdatePractitioner.fxml",btnUpdate);
        }
    }

    public void deletePractitioner() {
        Practitioner practitionerSelected = tablePractitioner.getSelectionModel().getSelectedItem();
        if(practitionerSelected == null){
            generateAlert("Por favor seleccione algún practicante");
        }else{
            FXMLDeletePractitionerController.enrollment =practitionerSelected.getEnrollment();
            openWindowGeneral("/gui/coordinator/fxml/FXMLDeletePractitioner.fxml",btnDelete);
        }
    }
}
