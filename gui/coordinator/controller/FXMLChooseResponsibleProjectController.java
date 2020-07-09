package gui.coordinator.controller;

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
import domain.ResponsibleProject;

/**
 * Class FXMLChooseResponsibleProjectController
 * @author MARTHA
 * @version 01/06/2020
 */
public class FXMLChooseResponsibleProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnAccept;
    @FXML private Button btnCancel;
    @FXML private TableView tvResponsibleProject;
    private static String controllerSection;
    private List<ResponsibleProject> allResponsibleProject;
    private static String answerNameResponsible;
    private static  String answerEmailResponsible;

    public void initialize(URL url, ResourceBundle rb) {
        startTableResponsiblesProject();
    }

    public void startTableResponsiblesProject() {
        ResponsibleProject responsibleProject = new ResponsibleProject();
        allResponsibleProject = new ArrayList<>();
        allResponsibleProject = responsibleProject.getListResponsibleProjectAvailable();
        TableColumn<ResponsibleProject, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("name"));
        TableColumn<ResponsibleProject, String> lastName = new TableColumn<>("Apellido");
        lastName.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("lastName"));
        TableColumn<ResponsibleProject, String> email = new TableColumn<>("Correo Electrónico");
        email.setCellValueFactory(new PropertyValueFactory<ResponsibleProject, String>("email"));
        tvResponsibleProject.getColumns().addAll(name, lastName, email);
        ObservableList<ResponsibleProject> responsiblesProject = FXCollections.observableArrayList(allResponsibleProject);
        tvResponsibleProject.setItems(responsiblesProject);
        tvResponsibleProject.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void controllerSection (String controllerSection){
        this.controllerSection = controllerSection;
    }

    public void backRegisterProject() {
        Stage stage = (Stage) btnAccept.getScene().getWindow();
        stage.close();
    }

    public void chooseResponsibleProject() {
        ResponsibleProject responsible = new ResponsibleProject();
        ReadOnlyObjectProperty<ResponsibleProject> responsibleProjectSelect;
        responsibleProjectSelect =  tvResponsibleProject.getSelectionModel().selectedItemProperty();
        responsible = responsibleProjectSelect.getValue();
        if(tvResponsibleProject.getSelectionModel().getSelectedItem() !=null){
            answerNameResponsible = responsible.getName()+" "+responsible.getLastName();
            answerEmailResponsible = responsible.getEmail();
            Stage stage = (Stage) btnAccept.getScene().getWindow();
            stage.close();
        }else{
            generateInformation("Elige un Responsable del proyecto");
        }
    }
    public static String getNameResponsibleProject(){
        return answerNameResponsible;
    }

    public static String getEmailResponsibleProject(){
        return answerEmailResponsible;
    }
}
