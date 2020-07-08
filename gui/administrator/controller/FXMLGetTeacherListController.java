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

/**
 * FXMLGetTeacherListController
 * @author Yazmin
 * @version 05/07/2020
 */
public class FXMLGetTeacherListController extends FXMLGeneralController {
    public TableView<Teacher> tvTeachers;
    public TableColumn<Teacher, Integer> tcStaffNumber;
    public TableColumn<Teacher, String> tcName;
    public TableColumn<Teacher, String> tcLastName;
    public TableColumn<Teacher, String> tcEmail;
    public TableColumn<Teacher, String> tcAlternateEmail;
    public TableColumn<Teacher, String> tcPhone;
    public TableColumn<Teacher, String> tcStatus;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateListTeachers();
    }

    private void colocateListTeachers() {
        List<Teacher> teacherList = Teacher.getTeachersInformation();
        tcStaffNumber.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcAlternateEmail.setCellValueFactory(new PropertyValueFactory<>("alternateEmail"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tvTeachers.getItems().setAll(teacherList);
    }

    /**
     * Method to cancel the list display
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
}
