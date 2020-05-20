package gui.coordinator.controller;

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
    @FXML private Button btnListPractitioner;
    @FXML private Button btnListProject;
    @FXML private Button btnRegisterPractitioner;
    @FXML private Button btnRegisterProject;
    @FXML private Button btnDeletePractitioner;
    @FXML private Button btnDeleteProject;
    @FXML private Button btnUpdatePractitioner;
    @FXML private Button btnUpdateProject;
    @FXML private Button btnAssignProject;

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

}
