package gui.coordinator.controller;

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
 * @version 19/05/2020
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
        openWindowGeneral("/gui/coordinator/fxml/FXMLDeletePractitioner.fxml",btnDeletePractitioner);
   }

    public void updatePractitioner() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLUpdatePractitioner.fxml",btnUpdatePractitioner);
    }

    public void listPractitioner() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListOfPractitioner.fxml",btnListPractitioner);
    }

    public void assignProject() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLAssignPractitioner.fxml",btnAssignProject);
    }

    public void registerProject() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterProject.fxml",btnRegisterProject);
    }

    public void deleteProject() {
        FXMLChooseProjectController chooseProject = new FXMLChooseProjectController();
        chooseProject.controllerSection("delete");
        openWindowGeneral("/gui/coordinator/fxml/FXMLChooseProject.fxml",btnDeleteProject);
    }

    public void updateProject() {
        FXMLChooseProjectController chooseProject = new FXMLChooseProjectController();
        chooseProject.controllerSection("update");
        openWindowGeneral("/gui/coordinator/fxml/FXMLChooseSection.fxml",btnUpdateProject);
    }

    public void listProject() {
        FXMLChooseProjectController chooseProject = new FXMLChooseProjectController();
        chooseProject.controllerSection("listProject");
        openWindowGeneral("/gui/coordinator/fxml/FXMLChooseProject.fxml",btnListProject);
    }

    public void registerLinkedOrganization() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterLinkedOrganization.fxml",btnRegisterLinkedOrganization);
    }

    public void deleteLinkedOrganization() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLDeleteLinkedOrganization.fxml",btnDeleteLinkedOrganization);
    }

    public void updateLinkedOrganization() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateLinkedOrganization.fxml",btnUpdateLinkedOrganization);
    }

    public void updateGroup() {
        //openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterGroup.fxml",btnUpdateGroup);
    }

    public void registerGroup() {
        //openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterGroup.fxml",btnRegisterGroup);
    }

    public void updateResponsible() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateResponsibleProject.fxml",btnUpdateResponsible);
    }

    public void registerReponsible() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLRegisterResponsibleProject.fxml",btnUpdateResponsible);
    }

    public void deleteResponsible() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLDeleteResponsibleProject.fxml",btnUpdateResponsible);
    }
}
