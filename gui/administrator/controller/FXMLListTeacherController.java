package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import domain.Teacher;
import gui.FXMLGeneralController;

public class FXMLListTeacherController extends FXMLGeneralController {
    public TableView<Teacher> tableTeachers;
    public TableColumn<Teacher, Integer> staffNumber;
    public TableColumn<Teacher, String> name;
    public TableColumn<Teacher, String> lastName;
    public TableColumn<Teacher, String> email;
    public TableColumn<Teacher, String> alternateEmail;
    public TableColumn<Teacher, String> phone;
    public TableColumn<Teacher, String> status;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListTeachers();
    }

    private void colocateListTeachers() {
        Teacher teacher = new Teacher();
        List<Teacher> teacherList=teacher.getInformationAllTeacher();
        staffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        alternateEmail.setCellValueFactory(new PropertyValueFactory<>("alternateEmail"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableTeachers.getItems().setAll(teacherList);
    }

    public void cancel() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
    }

    public void logOut() {
        logOutGeneral();
    }
}
