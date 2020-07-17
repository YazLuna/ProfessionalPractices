package gui.administrator.controller;

import domain.Search;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import domain.Teacher;
import gui.FXMLGeneralController;

/**
 * Update Teacher List Controller
 * @author Yazmin
 * @version 08/07/2020
 */
public class FXMLUpdateTeacherListController extends FXMLGeneralController implements Initializable {
    @FXML private TableView<Teacher> tvTeachers;
    @FXML private TableColumn<Teacher, Integer> tcStaffNumber;
    @FXML private TableColumn<Teacher, String> tcName;
    @FXML private TableColumn<Teacher, String> tcLastName;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListTeachers();
    }

    private void colocateListTeachers() {
        List<Teacher> teacherList = Teacher.getTeachers();
        if (teacherList.size() == Search.NOTFOUND.getValue()) {
            generateError("No hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
        } else {
            tcStaffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tvTeachers.getItems().setAll(teacherList);
        }

    }

    /**
     * Method to cancel the selection and return to the menu
     */
    public void backMenu() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
    }

    /**
     * Method to exit to the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method to choose the teacher to modify
     */
    public void updateTeacher() {
        Teacher TeacherSelected = tvTeachers.getSelectionModel().getSelectedItem();
        if(TeacherSelected == null){
            generateAlert("Por favor seleccione algún profesor");
        }else{
            FXMLUpdateTeacherController.staffNumber = TeacherSelected.getStaffNumber();
            openWindowGeneral("/gui/administrator/fxml/FXMLUpdateTeacher.fxml", btnUpdate);
        }
    }
}
