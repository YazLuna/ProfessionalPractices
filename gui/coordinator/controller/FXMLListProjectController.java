package gui.coordinator.controller;

import domain.Project;
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

import gui.FXMLGeneralController;


/**
 * class FXMLChooseProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLListProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private TableView<Project> tvProject;
    private static String controllerSection;
    private List<Project> allProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startTableProjects();
        selectProject ();
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

    public void startTableProjects() {
        Project project = new Project();
        allProject = new ArrayList<>();
        if(controllerSection!= "delete"){
            allProject = project.listProjects();
        }else{
            allProject = project.listProjectsAvailableNotAssing();
        }
        TableColumn<Project, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<Project, String>("nameProject"));
        tvProject.getColumns().addAll(name);
        ObservableList<Project> projects = FXCollections.observableArrayList(allProject);
        tvProject.setItems(projects);
        tvProject.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void selectProject () {
        tvProject.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Project project;
                    ReadOnlyObjectProperty<Project> projectSelect;
                    projectSelect =  tvProject.getSelectionModel().selectedItemProperty();
                    project = projectSelect.getValue();
                    if(tvProject.getSelectionModel().getSelectedItem() != null) {
                        if(controllerSection != "delete") {
                            FXMLUpdateProjectController projectController = new FXMLUpdateProjectController();
                            //updateResponsible .assignEmailResponsible(responsible.getEmail());
                            openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateProject.fxml",tvProject);
                        }else {
                            //boolean areAssingProject;
                            //areAssingProject = project.areAssingProject();
                            //if(!areAssingProject) {
                                //generateInformation("No puede eliminarse un proyecto asignado");
                            //} else {
                                FXMLDeleteProjectController projectController = new FXMLDeleteProjectController();
                                projectController.assignProject(project.getNameProject());
                                openWindowGeneral("/gui/coordinator/fxml/FXMLDeleteProject.fxml", tvProject);
                            //}
                        }
                    }
                }
            }
        });
    }
    
}
