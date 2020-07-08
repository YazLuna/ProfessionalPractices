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
    @FXML private TableView<Teacher> tvTeachers;
    @FXML private TableColumn<Teacher, Integer> tcStaffNumber;
    @FXML private TableColumn<Teacher, String> tcName;
    @FXML private TableColumn<Teacher, String> tcLastName;
    @FXML private TableColumn<Teacher, String> tcEmail;
    @FXML private TableColumn<Teacher, String> tcAlternateEmail;
    @FXML private TableColumn<Teacher, String> tcPhone;
    @FXML private TableColumn<Teacher, String> tcStatus;
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
