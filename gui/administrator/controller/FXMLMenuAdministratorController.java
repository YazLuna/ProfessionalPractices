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
	public static int idAdministrator;

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
		int isActive = Coordinator.activeCoordinator();
		if(isActive == Search.NOTFOUND.getValue()){
			FXMLRegisterCoordinatorController.idAdministrator = idAdministrator;
			openWindowGeneral("/gui/administrator/fxml/FXMLRegisterCoordinator.fxml", btnRegisterCoordinator);
		}else{
			if (isActive == Search.FOUND.getValue()) {
				generateError("Ya existe un coordinador activo");
			} else {
				if (isActive == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
		}
	}

	/**
	 * Method that calls the list of coordinators and validates preconditions
	 */
	public void getCoordinatorList() {
		int areCoordinator = Coordinator.areCoordinator();
		if(areCoordinator == Search.FOUND.getValue()){
			openWindowGeneral("/gui/administrator/fxml/FXMLGetCoordinatorList.fxml", btnListCoordinator);
		}else{
			if (areCoordinator == Search.NOTFOUND.getValue()) {
				generateError("No hay ningún coordinador registrado");
			} else {
				if (areCoordinator == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
		}
	}

	/**
	 * Method that calls to update coordinator and validates preconditions
	 */
	public void updateCoordinator() {
		int areCoordinator = Coordinator.areCoordinator();
		if(areCoordinator == Search.FOUND.getValue()){
			openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinatorList.fxml", btnUpdateCoordinator);
		}else{
			if (areCoordinator == Search.NOTFOUND.getValue()) {
				generateError("No hay ningún coordinador registrado");
			} else {
				if (areCoordinator == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
		}
	}

	/**
	 * Method that calls to delete coordinator and validates preconditions
	 */
	public void deleteCoordinator()  {
		int isActive = Coordinator.activeCoordinator();
		if(isActive == Search.FOUND.getValue()){
			openWindowGeneral("/gui/administrator/fxml/FXMLDeleteCoordinator.fxml", btnDeleteCoordinator);
		}else{
			if (isActive == Search.NOTFOUND.getValue()) {
				generateError("No hay ningún coordinador activo");
			} else {
				if (isActive == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
		}
	}

	/**
	 * Method that calls register teacher and validates preconditions
	 */
	public void registerTeacher() {
		int isActive = Teacher.activeTeachers();
		if(isActive <= Search.FOUND.getValue()){
			FXMLRegisterTeacherController.idAdministrator = idAdministrator;
			openWindowGeneral("/gui/administrator/fxml/FXMLRegisterTeacher.fxml", btnRegisterTeacher);
		}else{
			if (isActive == Search.NOTFOUND.getValue()) {
				generateError("Ya hay dos profesores activos");
			}  else {
				if (isActive == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
		}
	}

	/**
	 * Method that calls the list of teachers and validates preconditions
	 */
	public void getTeacherList() {
		int areTeacher = Teacher.areTeachers();
		if(areTeacher == Search.FOUND.getValue()){
			openWindowGeneral("/gui/administrator/fxml/FXMLGetTeacherList.fxml", btnListTeacher);
		}else{
			if (areTeacher == Search.NOTFOUND.getValue()) {
				generateError("No hay ningún profesor registrado");
			} else {
				if (areTeacher == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
		}
	}

	/**
	 * Method that calls to update teacher and validates preconditions
	 */
	public void updateTeacher() {
		int areTeacher = Teacher.areTeachers();
		if(areTeacher == Search.FOUND.getValue()){
			openWindowGeneral("/gui/administrator/fxml/FXMLUpdateTeacherList.fxml", btnUpdateTeacher);
		}else{
			if (areTeacher == Search.NOTFOUND.getValue()) {
				generateError("No hay ningún profesor registrado");
			} else {
				if (areTeacher == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
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
			if (isActive == Search.NOTFOUND.getValue()) {
				generateError("No hay ningún profesor activo");
			} else {
				if (isActive == Search.EXCEPTION.getValue()) {
					generateError("no hay conexión con la base de datos. Intente más tarde");
				}
			}
		}
	}

}
