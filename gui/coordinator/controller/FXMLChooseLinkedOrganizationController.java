package gui.coordinator.controller;

import domain.LinkedOrganization;
import gui.FXMLGeneralController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.event.ChangeListener;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class FXMLChooseLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnAccept;
    @FXML private Button btnCancel;
    @FXML private TableView<LinkedOrganization> tvLinkedOrganizations;
    private static String controllerSection;
    private LinkedOrganization organization;
    private List<LinkedOrganization> allLinkedOrganization;

    public void initialize(URL url, ResourceBundle rb) {
        startTableOrganizations();
    }
    public void startTableOrganizations() {
        organization = new LinkedOrganization();
        allLinkedOrganization = new ArrayList<>();
        allLinkedOrganization = organization.listOrganization();
        TableColumn<LinkedOrganization, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<LinkedOrganization, String>("name"));
        TableColumn<LinkedOrganization, String> email = new TableColumn<>("Correo Electrónico");
        email.setCellValueFactory(new PropertyValueFactory<LinkedOrganization, String>("email"));
        tvLinkedOrganizations.getColumns().addAll(name, email);
        ObservableList<LinkedOrganization> organizations = FXCollections.observableArrayList(allLinkedOrganization);
        tvLinkedOrganizations.setItems(organizations);
        tvLinkedOrganizations.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    }

    public void controllerSection (String controllerSection){
        this.controllerSection = controllerSection;
    }

    public void cancel () {
        Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
        stagePrincipal.close();
    }

    public void accept () {
        organization = new LinkedOrganization();
        ReadOnlyObjectProperty<LinkedOrganization> organizationSelect;
        organizationSelect =  tvLinkedOrganizations.getSelectionModel().selectedItemProperty();
        organization = organizationSelect.getValue();
        if(tvLinkedOrganizations.getSelectionModel().getSelectedItem() != null) {
            if(controllerSection != "register") {
                FXMLUpdateProjectController updateProject = new FXMLUpdateProjectController();
                //updateProject.assignLinkedOrganization(rganizationSelect.getName());
            }else {
                FXMLRegisterProjectController registerProject = new FXMLRegisterProjectController();
                registerProject.assignLinkedOrganization(organization.getName());
            }
            Stage stagePrincipal = (Stage) btnAccept.getScene().getWindow();
            stagePrincipal.close();
        }
    }

}
