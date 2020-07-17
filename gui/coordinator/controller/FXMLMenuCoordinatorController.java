package gui.coordinator.controller;

import domain.*;
import gui.teacher.controller.FXMLMenuTeacherController;
import javafx.event.ActionEvent;
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
    @FXML private Button btnChangeRole;
    public static boolean isTeacher;
    public static int staffNumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(isTeacher){
            btnChangeRole.setVisible(true);
        }
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
        boolean areProjectAvailable;
        areProjectAvailable = Project.thereAreProjectAvailable();
        if(!areProjectAvailable) {
            generateInformation("No hay algún Proyecto disponible");
        } else{
            FXMLListProjectController listProject = new FXMLListProjectController();
            listProject.controllerSection("delete");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml", btnDeleteProject);
        }
    }

    public void updateProject() {
        boolean areProject;
        areProject = Project.thereAreProject();
        if(!areProject) {
            generateInformation("No hay algún Proyecto registrado");
        } else {
            FXMLListProjectController chooseProject = new FXMLListProjectController();
            chooseProject.controllerSection("update");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml", btnUpdateProject);
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
        boolean areLinkedOrganization;
        areLinkedOrganization = LinkedOrganization.thereAreLinkedOrganizationAvailable();
        if(!areLinkedOrganization) {
            generateInformation("No hay alguna Organizacion vinculada disponible");
        }else {
            FXMLListLinkedOrganizationController listLinkedOrganization = new FXMLListLinkedOrganizationController();
            listLinkedOrganization.controllerSection("delete");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml", btnDeleteLinkedOrganization);
        }
    }

    public void updateLinkedOrganization() {
        boolean areLinkedOrganizationAvailable;
        areLinkedOrganizationAvailable = LinkedOrganization.thereAreLinkedOrganization();
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
        boolean areResponsibleProject;
        areResponsibleProject = ResponsibleProject.thereAreResponsibleProject();
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
        boolean areResponsibleProject;
        areResponsibleProject = ResponsibleProject.thereAreResponsibleProjectAvailableNotAssing();
        if(!areResponsibleProject) {
            generateInformation("No hay algún Responsable del proyecto disponible");
        } else {
            FXMLListResponsibleProjectController listResponsibleProjectController = new FXMLListResponsibleProjectController();
            listResponsibleProjectController.controllerSection("delete");
            openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml", btnDeleteResponsible);
        }
    }

    public void changeRole() {
        FXMLMenuTeacherController.isCoordinator = true;
        openWindowGeneral("/gui/teacher/fxml/FXMLMenuTeacher.fxml",btnChangeRole);
    }
}
