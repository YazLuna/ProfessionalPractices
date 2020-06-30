package gui.coordinator.controller;

import domain.LinkedOrganization;
import gui.FXMLGeneralController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLListLinkedOrganizationController extends FXMLGeneralController implements Initializable {

    @FXML private Button btnBehind;
    @FXML private TableView<LinkedOrganization> tvLinkedOrganizations;
    private static String controllerSection;
    private LinkedOrganization organization;
    private List<LinkedOrganization> allLinkedOrganization;

    public void initialize(URL url, ResourceBundle rb) {
        startTableOrganizations();
        selectLinkedOrganization ();
    }
    public void startTableOrganizations() {
        organization = new LinkedOrganization();
        allLinkedOrganization = new ArrayList<>();
        if(controllerSection!= "delete"){
            allLinkedOrganization = organization.listOrganization();
        }else{
            allLinkedOrganization = organization.listOrganizationAvailableNotAssing();
        }
        TableColumn<LinkedOrganization, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<LinkedOrganization, String>("name"));
        TableColumn<LinkedOrganization, String> email = new TableColumn<>("Correo Electrónico");
        email.setCellValueFactory(new PropertyValueFactory<LinkedOrganization, String>("email"));
        tvLinkedOrganizations.getColumns().addAll(name, email);
        ObservableList<LinkedOrganization> organizations = FXCollections.observableArrayList(allLinkedOrganization);
        tvLinkedOrganizations.setItems(organizations);
        tvLinkedOrganizations.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    public void logOut() {
        logOutGeneral();
    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }

    public void controllerSection (String controllerSection){
        this.controllerSection = controllerSection;
    }

    public void selectLinkedOrganization () {
        tvLinkedOrganizations.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    organization = new LinkedOrganization();
                    ReadOnlyObjectProperty<LinkedOrganization> organizationSelect;
                    organizationSelect =  tvLinkedOrganizations.getSelectionModel().selectedItemProperty();
                    organization = organizationSelect.getValue();
                    if(tvLinkedOrganizations.getSelectionModel().getSelectedItem() != null) {
                        if(controllerSection != "delete") {
                            FXMLUpdateLinkedOrganizationController updateLinkedOrganization = new FXMLUpdateLinkedOrganizationController();
                            updateLinkedOrganization.assignLinkedOrganization(organization.getName());
                            openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateLinkedOrganization.fxml",tvLinkedOrganizations);
                        }else {
                            FXMLDeleteLinkedOrganizationController deleteLinkedOrganization = new FXMLDeleteLinkedOrganizationController();
                            deleteLinkedOrganization.assignLinkedOrganization(organization.getName());
                            openWindowGeneral("/gui/coordinator/fxml/FXMLDeleteLinkedOrganization.fxml",tvLinkedOrganizations);
                        }
                    }
                }
            }
        });
    }

}

