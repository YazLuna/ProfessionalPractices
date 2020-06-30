package gui.coordinator.controller;

import domain.SchedulingActivities;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import domain.Project;

/**
 * class FXMLDeleteProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLDeleteProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnDeleteProject;
    @FXML private Button btnCancelProject;
    @FXML private Text txtName;
    @FXML private Text txtDescription;
    @FXML private Text txtObjectiveGeneral;
    @FXML private Text txtObjectiveImmediate;
    @FXML private Text txtObjectiveMediate;
    @FXML private Text txtMethodology;
    @FXML private Text txtResource;
    @FXML private Text txtActivitiesAndFunctions;
    @FXML private Text txtResponsabilities;
    @FXML private Text txtDaysAndHours;
    @FXML private Text txtLinkedOrganization;
    @FXML private Text txtResponsibleProject;
    @FXML private Text txtMonth;
    @FXML private Text txtActivity;
    @FXML private Label lbDuration;
    @FXML private Label lbQuiantityPractitioners;
    @FXML private Label lbLapse;
    @FXML private GridPane gpActivity;
    private static String nameProject;
    private Project project;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startComponent();
    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml",btnBehind);
    }


    public void assignProject (String nameProject){
        this.nameProject = nameProject;
    }

    public void startComponent () {
        project = new Project();
        project.setNameProject(nameProject);
        project = project.getProject(nameProject);
        txtName.setText(project.getNameProject());
        txtDescription.setText(project.getDescription());
        txtObjectiveGeneral.setText(project.getObjectiveGeneral());
        txtObjectiveImmediate.setText(project.getObjectiveInmediate());
        txtObjectiveMediate.setText(project.getObjectiveMediate());
        txtMethodology.setText(project.getMethodology());
        txtResource.setText(project.getResources());
        txtActivitiesAndFunctions.setText(project.getActivitiesAndFunctions());
        txtResponsabilities.setText(project.getResponsabilities());
        txtDaysAndHours.setText(project.getDaysHours());
        txtLinkedOrganization.setText(project.getOrganization().getName());
        String fullName = project.getResponsible().getName()+" "+project.getResponsible().getLastName();
        txtResponsibleProject.setText(fullName);
        List<SchedulingActivities> schedulingActivities = project.getSchedulingActivitiesProject();
        txtMonth.setText(schedulingActivities.get(0).getMonth());
        txtActivity.setText(schedulingActivities.get(0).getActivity());
        lbDuration.setText(String.valueOf(project.getDuration()));
        lbQuiantityPractitioners.setText(String.valueOf(project.getQuantityPractitioner()));
        lbLapse.setText(project.getLapse());

        for(int indexScheduling=1; indexScheduling<schedulingActivities.size();indexScheduling++){
            Label lbMonth = new Label();
            lbMonth.setText("Mes:  ");
            Label lbActivity = new Label();
            lbActivity.setText("Actividad:  ");
            Text txtActivity = new Text();
            Text txtMonth = new Text();
            lbMonth.autosize();
            lbActivity.autosize();
            lbMonth.getStyleClass().add("details");
            lbActivity.getStyleClass().add("details");
            txtActivity.getStyleClass().add("Text");
            txtActivity.setWrappingWidth(241);
            txtMonth.getStyleClass().add("Text");
            txtMonth.setText(schedulingActivities.get(indexScheduling).getMonth());
            txtActivity.setText(schedulingActivities.get(indexScheduling).getActivity());
            gpActivity.add(lbMonth, 0, indexScheduling);
            gpActivity.add(txtMonth, 1, indexScheduling);
            gpActivity.add(lbActivity, 2, indexScheduling);
            gpActivity.add(txtActivity, 3, indexScheduling);
        }

    }

    public void delete (){
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro desea eliminar el Proyecto?", YES, NO);
        cancel.setTitle("Confirmación Eliminar");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.orElse(NO) == YES) {
            boolean isDeleteProject;
            isDeleteProject = project.deleteProject();
            if (!isDeleteProject) {
                generateError("El proyecto no pudo eliminarse");
                openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml", btnDeleteProject);
            } else {
                generateInformation("El proyecto se eliminó exitosamente");
                boolean areProject;
                areProject = project.thereAreProjectAvailableNotAssing();
                if (!areProject) {
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml", btnDeleteProject);
                } else {
                    openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml", btnDeleteProject);
                }
            }
        }
    }

    public void cancel(){
        generateConfirmationCancel("¿Seguro desea cancelar?",btnCancelProject,"/gui/coordinator/fxml/FXMLListProject.fxml");
    }
}
