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

    public void logOut() {
        logOutGeneral();
    }

    public void registerCoordinator() {
        boolean isActive;
        Coordinator coordinator = new Coordinator();
        isActive = coordinator.activeCoordinator();
        if(!isActive){
            openWindowGeneral("/gui/administrator/fxml/FXMLRegisterCoordinator.fxml",btnRegisterCoordinator);
        }else{
            generateError("Ya existe un coordinador activo");
        }
    }

    public void deleteCoordinator()  {
        boolean isActive;
        Coordinator coordinator = new Coordinator();
        isActive = coordinator.activeCoordinator();
        if(isActive){
            openWindowGeneral("/gui/administrator/fxml/FXMLDeleteCoordinator.fxml",btnDeleteCoordinator);
        }else{
            generateError("No hay ningún coordinador activo");
        }
    }

    public void updateCoordinator() {
        Coordinator coordinator = new Coordinator();
        boolean areCoordinator = coordinator.areCoordinator();
        if(areCoordinator){
            openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinatorList.fxml",btnUpdateCoordinator);
        }else{
            generateError("No hay ningún coordinador registrado");
        }
    }

    public void listCoordinator() {
        Coordinator coordinator = new Coordinator();
        boolean areCoordinator = coordinator.areCoordinator();
        if(areCoordinator){
            openWindowGeneral("/gui/administrator/fxml/FXMLListCoordinator.fxml",btnListCoordinator);
        }else{
            generateError("No hay ningún coordinador registrado");
        }
    }

    public void registerTeacher() {
        Teacher teacher = new Teacher();
        int isActive = teacher.activeTeachers();
        if(isActive <= Search.FOUND.getValue()){
            openWindowGeneral("/gui/administrator/fxml/FXMLRegisterTeacher.fxml", btnRegisterTeacher);
        }else{
            generateError("Ya hay dos profesores activos");
        }
    }

    public void deleteTeacher() {
        Teacher teacher = new Teacher();
        int isActive = teacher.activeTeachers();
        if(isActive != Search.NOTFOUND.getValue()){
            openWindowGeneral("/gui/administrator/fxml/FXMLDeleteTeacherList.fxml", btnDeleteTeacher);
        }else{
            generateError("No hay ningún profesor activo");
        }
    }

    public void updateTeacher() {
        Teacher teacher = new Teacher();
        int areTeacher = teacher.activeTeachers();
        if(areTeacher == Search.NOTFOUND.getValue()){
            generateError("No hay ningún profesor registrado");
        }else{
            openWindowGeneral("/gui/administrator/fxml/FXMLUpdateTeacherList.fxml", btnUpdateTeacher);
        }
    }

    public void listTeacher() {
        Teacher teacher = new Teacher();
        int areTeacher = teacher.activeTeachers();
        if(areTeacher == Search.NOTFOUND.getValue()){
            generateError("No hay ningún profesor registrado");
        }else{
            openWindowGeneral("/gui/administrator/fxml/FXMLListTeacher.fxml",btnListTeacher);
        }
    }

}
