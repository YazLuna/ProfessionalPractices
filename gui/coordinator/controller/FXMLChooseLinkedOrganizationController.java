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
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class FXMLChooseLinkedOrganizationController
 * @author MARTHA
 * @version 01/06/2020
 */
public class FXMLChooseLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnAccept;
    @FXML private Button btnCancel;
    @FXML private TableView<LinkedOrganization> tvLinkedOrganizations;
    private static String controllerSection;
    private static String answerNameOrganization;
    private List<LinkedOrganization> allLinkedOrganization;

    public void initialize(URL url, ResourceBundle rb) {
        startTableOrganizations();
    }

    public void startTableOrganizations() {
        LinkedOrganization organization = new LinkedOrganization();
        allLinkedOrganization = new ArrayList<>();
        allLinkedOrganization = organization.getListOrganizationAvailable();
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

    public void backRegisterProject() {
        Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
        stagePrincipal.close();
    }

    public void chooseLinkedOrganization() {
        LinkedOrganization organization = new LinkedOrganization();
        ReadOnlyObjectProperty<LinkedOrganization> organizationSelect;
        organizationSelect =  tvLinkedOrganizations.getSelectionModel().selectedItemProperty();
        organization = organizationSelect.getValue();
        if(tvLinkedOrganizations.getSelectionModel().getSelectedItem() != null) {
            answerNameOrganization = organization.getName();
            Stage stagePrincipal = (Stage) btnAccept.getScene().getWindow();
            stagePrincipal.close();
            Stage stage = (Stage) btnAccept.getScene().getWindow();
            stage.close();
        }else{
            generateInformation("Elige una Organización vinculada");
        }
    }
    public static String getNameLinkedOrganization(){
        return answerNameOrganization;
    }

}
