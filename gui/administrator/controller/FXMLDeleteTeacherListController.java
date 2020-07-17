package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import domain.Teacher;
import gui.FXMLGeneralController;

/**
 * Delete Teacher List Controller
 * @author Yazmin
 * @version 05/07/2020
 */
public class FXMLDeleteTeacherListController extends FXMLGeneralController implements Initializable {
    @FXML private TableView<Teacher> tvTeachers;
    @FXML private TableColumn<Teacher, Integer> tcStaffNumber;
    @FXML private TableColumn<Teacher, String> tcName;
    @FXML private TableColumn<Teacher, String> tcLastName;
    @FXML private TableColumn<Teacher, String> tcEmail;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateTeachers();
    }

    private void colocateTeachers() {
        List<Teacher> teacherList=Teacher.getTeachersActive();
        tcStaffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tvTeachers.getItems().setAll(teacherList);
    }

    /**
     * Method to cancel the deletion and return to the menu
     */
    public void backMenu() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
    }

    /**
     * Method to exit the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method to delete the teacher
     */
    public void deleteTeacher() {
        Teacher teacherSelected = tvTeachers.getSelectionModel().getSelectedItem();
        if(teacherSelected == null){
            generateAlert("Por favor seleccione algún profesor");
        }else{
            boolean deleteConfirm = generateConfirmation("¿Seguro que desea eliminar este profesor?");
            if(deleteConfirm){
                Date actualDate  = new Date();
                String dischargeDate = new SimpleDateFormat("yyyy-MM-dd").format(actualDate);
                boolean deleteOk = Teacher.deleteTeacher("Inactive", dischargeDate, teacherSelected.getStaffNumber());
                if(deleteOk){
                    generateInformation("Profesor eliminado exitosamente");
                }else{
                    generateError("No se pudo conectar con la base de datos. Intente mas tarde");
                }
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnUpdate);
            }
        }
    }
}
