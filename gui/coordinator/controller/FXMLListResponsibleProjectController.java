package gui.coordinator.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import domain.Number;
import domain.ResponsibleProject;
import gui.FXMLGeneralController;

/**
 * Class FXMLListResponsibleProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLListResponsibleProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private TableView<ResponsibleProject> tvResponsibleProject;
    private static String controllerSection;
    private List<ResponsibleProject> allResponsibleProject;

    public void initialize(URL url, ResourceBundle rb) {
        startTableResponsibles();
        selectResponsibleProject ();
    }

    public void startTableResponsibles() {
        allResponsibleProject = new ArrayList<>();
        if(controllerSection!= "delete"){
            allResponsibleProject = ResponsibleProject.getListResponsibleProject();
        }else{
            allResponsibleProject = ResponsibleProject.getListResponsibleProjectAvailableNotAssign();
        }
        TableColumn<ResponsibleProject, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("name"));
        TableColumn<ResponsibleProject, String> lastName = new TableColumn<>("Apellido");
        lastName.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("lastName"));
        TableColumn<ResponsibleProject, String> email = new TableColumn<>("Correo Electrónico");
        email.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("email"));
        tvResponsibleProject.getColumns().addAll(name, lastName, email);
        ObservableList<ResponsibleProject>responsiblesProject = FXCollections.observableArrayList(allResponsibleProject);
        tvResponsibleProject.setItems(responsiblesProject);
        tvResponsibleProject.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void logOut() {
        logOutGeneral();
    }

    public void behindMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }

    public void controllerSection (String controllerSection){
        this.controllerSection = controllerSection;
    }

    public void selectResponsibleProject () {
        tvResponsibleProject.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == Number.TWO.getNumber()) {
                    ResponsibleProject responsible = new ResponsibleProject();
                    ReadOnlyObjectProperty<ResponsibleProject> responsibleProjectSelect;
                    responsibleProjectSelect =  tvResponsibleProject.getSelectionModel().selectedItemProperty();
                    responsible = responsibleProjectSelect.getValue();
                    if(tvResponsibleProject.getSelectionModel().getSelectedItem() != null) {
                        if(controllerSection != "delete") {
                            FXMLUpdateResponsibleProjectController updateResponsible = new FXMLUpdateResponsibleProjectController();
                            updateResponsible .assignEmailResponsible(responsible.getEmail());
                            openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateResponsibleProject.fxml",tvResponsibleProject);
                        }else {
                            FXMLDeleteResponsibleProjectController deleteResponsible = new FXMLDeleteResponsibleProjectController();
                            deleteResponsible.assignEmailResponsible(responsible.getEmail());
                            openWindowGeneral("/gui/coordinator/fxml/FXMLDeleteResponsibleProject.fxml",tvResponsibleProject);
                        }
                    }
                }
            }
        });
    }

}