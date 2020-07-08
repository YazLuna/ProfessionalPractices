package gui.administrator.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import domain.Coordinator;
import gui.FXMLGeneralController;
import domain.Search;
import domain.Teacher;

public class FXMLMenuAdministratorController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnListCoordinator;
    @FXML private Button btnListTeacher;
    @FXML private Button btnRegisterCoordinator;
    @FXML private Button btnRegisterTeacher;
    @FXML private Button btnDeleteCoordinator;
    @FXML private Button btnDeleteTeacher;
    @FXML private Button btnUpdateCoordinator;
    @FXML private Button btnUpdateTeacher;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method that calls register coordinator and validates preconditions
     */
    public void registerCoordinator() {
        boolean isActive = Coordinator.activeCoordinator();
        if(!isActive){
            openWindowGeneral("/gui/administrator/fxml/FXMLRegisterCoordinator.fxml", btnRegisterCoordinator);
        }else{
            generateError("Ya existe un coordinador activo");
        }
    }

    /**
     * Method that calls the list of coordinators and validates preconditions
     */
    public void getCoordinatorList() {
        boolean areCoordinator = Coordinator.areCoordinator();
        if(areCoordinator){
            openWindowGeneral("/gui/administrator/fxml/FXMLGetCoordinatorList.fxml", btnListCoordinator);
        }else{
            generateError("No hay ningún coordinador registrado");
        }
    }

    /**
     * Method that calls to update coordinator and validates preconditions
     */
    public void updateCoordinator() {
        boolean areCoordinator = Coordinator.areCoordinator();
        if(areCoordinator){
            openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinatorList.fxml", btnUpdateCoordinator);
        }else{
            generateError("No hay ningún coordinador registrado");
        }
    }

    /**
     * Method that calls to delete coordinator and validates preconditions
     */
    public void deleteCoordinator()  {
        boolean isActive = Coordinator.activeCoordinator();
        if(isActive){
            openWindowGeneral("/gui/administrator/fxml/FXMLDeleteCoordinator.fxml", btnDeleteCoordinator);
        }else{
            generateError("No hay ningún coordinador activo");
        }
    }

    /**
     * Method that calls register teacher and validates preconditions
     */
    public void registerTeacher() {
        int isActive = Teacher.activeTeachers();
        if(isActive <= Search.FOUND.getValue()){
            openWindowGeneral("/gui/administrator/fxml/FXMLRegisterTeacher.fxml", btnRegisterTeacher);
        }else{
            generateError("Ya hay dos profesores activos");
        }
    }

    /**
     * Method that calls the list of teachers and validates preconditions
     */
    public void getTeacherList() {
        int areTeacher = Teacher.activeTeachers();
        if(areTeacher == Search.NOTFOUND.getValue()){
            generateError("No hay ningún profesor registrado");
        }else{
            openWindowGeneral("/gui/administrator/fxml/FXMLGetTeacherList.fxml", btnListTeacher);
        }
    }

    /**
     * Method that calls to update teacher and validates preconditions
     */
    public void updateTeacher() {
        int areTeacher = Teacher.activeTeachers();
        if(areTeacher == Search.NOTFOUND.getValue()){
            generateError("No hay ningún profesor registrado");
        }else{
            openWindowGeneral("/gui/administrator/fxml/FXMLUpdateTeacherList.fxml", btnUpdateTeacher);
        }
    }

    /**
     * Method that calls to delete teacher and validates preconditions
     */
    public void deleteTeacher() {
        int isActive = Teacher.activeTeachers();
        if(isActive != Search.NOTFOUND.getValue()){
            openWindowGeneral("/gui/administrator/fxml/FXMLDeleteTeacherList.fxml", btnDeleteTeacher);
        }else{
            generateError("No hay ningún profesor activo");
        }
    }

}
