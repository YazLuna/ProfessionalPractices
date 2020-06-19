package gui.coordinator.controller;

import domain.LinkedOrganization;
import gui.FXMLGeneralController;
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
import domain.ResponsibleProject;

public class FXMLChooseResponsibleProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnAccept;
    @FXML private Button btnCancel;
    @FXML private TableView tvResponsibleProject;
    private static String controllerSection;
    private ResponsibleProject responsibleProject;
    private List<ResponsibleProject> allResponsibleProject;

    public void initialize(URL url, ResourceBundle rb) {
        startTableOrganizations();
    }

    public void startTableOrganizations() {
        this.responsibleProject = new ResponsibleProject();
        allResponsibleProject = new ArrayList<>();
        allResponsibleProject = this.responsibleProject.listResponsibleProject();
        TableColumn<ResponsibleProject, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("name"));
        TableColumn<ResponsibleProject, String> lastName = new TableColumn<>("Apellido");
        lastName.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("lastName"));
        TableColumn<ResponsibleProject, String> email = new TableColumn<>("Correo Electrónico");
        email.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("email"));
        tvResponsibleProject.getColumns().addAll(name, lastName, email);
        ObservableList<ResponsibleProject> responsibleProject = FXCollections.observableArrayList(allResponsibleProject);
        tvResponsibleProject.setItems(responsibleProject);
        tvResponsibleProject.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void controllerSection (String controllerSection){
        this.controllerSection = controllerSection;
    }

    public void cancel () {
        Stage stagePrincipal = (Stage) btnCancel.getScene().getWindow();
        stagePrincipal.close();
    }

    public void accept () {
        responsibleProject = (ResponsibleProject) tvResponsibleProject.getSelectionModel().getSelectedItem();
        if(responsibleProject!=null){
            if(controllerSection != "register") {
                FXMLUpdateProjectController updateProject = new FXMLUpdateProjectController();
                //updateProject.assignLinkedOrganization(organization.getName());
            }else {
                FXMLRegisterProjectController registerProject = new FXMLRegisterProjectController();
                registerProject.assignResponsibleProject(responsibleProject.getName());
            }
            Stage stagePrincipal = (Stage) btnAccept.getScene().getWindow();
            stagePrincipal.close();
        }
    }
}
