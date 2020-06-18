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

public class FXMLDeleteTeacherListController extends FXMLGeneralController implements Initializable {
    public TableView<Teacher> tableTeachers;
    public TableColumn<Teacher, Integer> staffNumber;
    public TableColumn<Teacher, String> name;
    public TableColumn<Teacher, String> lastName;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListTeachers();
    }

    private void colocateListTeachers() {
        Teacher teacher = new Teacher();
        List<Teacher> teacherList=teacher.getAllTeacherActive();
        staffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableTeachers.getItems().setAll(teacherList);
    }

    public void cancel() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }

    public void delete() {
        Teacher teacherSelected = tableTeachers.getSelectionModel().getSelectedItem();
        if(teacherSelected == null){
            generateAlert("Por favor seleccione algún profesor");
        }else{
            boolean deleteConfirm = generateConfirmation("¿Seguro que desea eliminar este profesor?");
            if(deleteConfirm){
                Date registerDate = new Date();
                String dateDischarge = new SimpleDateFormat("yyyy-MM-dd").format(registerDate);
                boolean deleteOk = teacherSelected.deleteTeacher("Inactive",dateDischarge,teacherSelected.getStaffNumber());
                if(deleteOk){
                    openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                    generateInformation("El profesor se ha eliminado exitosamente");
                }else{
                    generateError("No se puede eliminar al coordinador");
                }
            }
        }
    }
}
