package gui.coordinator.controller;

import domain.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * DAO User
 * @author Yazmin
 * @version 12/06/2020
 */

public class FXMLMenuCoordinatorController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnRegisterGroup;
    @FXML private Button btnUpdateGroup;
    @FXML private Button btnAssignProject;
    @FXML private Button btnUpdateLinkedOrganization;
    @FXML private Button btnDeleteLinkedOrganization;
    @FXML private Button btnRegisterLinkedOrganization;
    @FXML private Button btnUpdateResponsible;
    @FXML private Button btnDeleteResponsible;
    @FXML private Button btnRegisterResponsible;
    @FXML private Button btnListPractitioner;
    @FXML private Button btnListProject;
    @FXML private Button btnRegisterPractitioner;
    @FXML private Button btnRegisterProject;
    @FXML private Button btnDeletePractitioner;
    @FXML private Button btnDeleteProject;
    @FXML private Button btnUpdatePractitioner;
    @FXML private Button btnUpdateProject;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void logOut() {
        logOutGeneral();
    }

    public void registerPractitioner() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterPractitioner.fxml",btnRegisterPractitioner);
    }

    public void deletePractitioner() {
        Practitioner practitioner = new Practitioner();
        FXMLUpdateDeletePractitionerListController.action = "Delete";
        boolean activePractitioner = practitioner.activePractitioner();
        if(activePractitioner){
            openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateDeletePractitionerList.fxml",btnDeletePractitioner);
        } else {
            generateError("No hay algún practicante activo");
        }
   }

    public void updatePractitioner() {
        Practitioner practitioner = new Practitioner();
        FXMLUpdateDeletePractitionerListController.action = "Update";
        boolean arePractitioner = practitioner.arePractitioner();
        if(arePractitioner){
            openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateDeletePractitionerList.fxml",btnUpdatePractitioner);
        } else {
            generateError("No hay algún practicante registrado");
        }
    }

    public void listPractitioner() {
        Practitioner practitioner = new Practitioner();
        boolean arePractitioner = practitioner.arePractitioner();
        if(arePractitioner){
            openWindowGeneral("/gui/coordinator/fxml/FXMLListPractitioner.fxml",btnListPractitioner);
        } else {
            generateError("No hay algún practicante registrado");
        }
    }

    public void assignProject() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLAssignPractitioner.fxml",btnAssignProject);
    }

    public void registerProject() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterProject.fxml",btnRegisterProject);
    }

    public void deleteProject() {
        Project project = new Project();
        boolean areProjectAvailable;
        areProjectAvailable = project.thereAreProjectAvailableNotAssing();
        if(!areProjectAvailable) {
            generateInformation("No hay algún Proyecto disponible");
        } else{
            FXMLListProjectController listProject = new FXMLListProjectController();
            listProject.controllerSection("delete");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml", btnDeleteProject);
        }
    }

    public void updateProject() {
        Project project = new Project();
        boolean areProject;
        areProject = project.thereAreProject();
        if(!areProject) {
            generateInformation("No hay algún Proyecto registrado");
        } else {
            FXMLListProjectController chooseProject = new FXMLListProjectController();
            chooseProject.controllerSection("update");
            openWindowGeneral("/gui/coordinator/fxml/FXMLChooseSection.fxml", btnUpdateProject);
        }
    }

    public void listProject() {
        FXMLListProjectController chooseProject = new FXMLListProjectController();
        chooseProject.controllerSection("listProject");
        openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml",btnListProject);
    }

    public void registerLinkedOrganization() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterLinkedOrganization.fxml",btnRegisterLinkedOrganization);
    }

    public void deleteLinkedOrganization() {
        LinkedOrganization organization = new LinkedOrganization();
        boolean areLinkedOrganization;
        areLinkedOrganization = organization.thereAreLinkedOrganizationAvailableNotAssing();
        if(!areLinkedOrganization) {
            generateInformation("No hay alguna Organizacion vinculada disponible");
        }else {
            FXMLListLinkedOrganizationController listLinkedOrganization = new FXMLListLinkedOrganizationController();
            listLinkedOrganization.controllerSection("delete");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml", btnDeleteLinkedOrganization);
        }
    }

    public void updateLinkedOrganization() {
        LinkedOrganization organization = new LinkedOrganization();
        boolean areLinkedOrganizationAvailable;
        areLinkedOrganizationAvailable = organization.thereAreLinkedOrganization();
        if(!areLinkedOrganizationAvailable) {
            generateInformation("No hay alguna Organizacion vinculada registrada");
        } else{
            FXMLListLinkedOrganizationController listLinkedOrganization = new FXMLListLinkedOrganizationController();
            listLinkedOrganization.controllerSection("update");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml", btnUpdateLinkedOrganization);
        }
    }

    public void updateGroup() {
        //openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterGroup.fxml",btnUpdateGroup);
    }

    public void registerGroup() {
        //openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterGroup.fxml",btnRegisterGroup);
    }

    public void updateResponsible() {
        ResponsibleProject responsibleProject = new ResponsibleProject();
        boolean areResponsibleProject;
        areResponsibleProject = responsibleProject.thereAreResponsibleProject();
        if(!areResponsibleProject) {
            generateInformation("No hay alguna Responsable del proyecto registrado");
        } else {
            FXMLListResponsibleProjectController listResponsibleProjectController = new FXMLListResponsibleProjectController();
            listResponsibleProjectController .controllerSection("update");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml", btnUpdateResponsible);
        }
    }

    public void registerReponsible() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterResponsibleProject.fxml",btnRegisterResponsible);
    }

    public void deleteResponsible() {
        ResponsibleProject responsibleProject = new ResponsibleProject();
        boolean areResponsibleProject;
        areResponsibleProject = responsibleProject.thereAreResponsibleProjectAvailableNotAssing();
        if(!areResponsibleProject) {
            generateInformation("No hay algún Responsable del proyecto disponible");
        } else {
            FXMLListResponsibleProjectController listResponsibleProjectController = new FXMLListResponsibleProjectController();
            listResponsibleProjectController.controllerSection("delete");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml", btnDeleteResponsible);
        }
    }
}
