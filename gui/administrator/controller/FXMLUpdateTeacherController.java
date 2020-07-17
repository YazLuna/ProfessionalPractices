package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import domain.Search;
import domain.Teacher;
import domain.Gender;
import gui.FXMLGeneralController;
import logic.ValidateUser;

/**
 * Update Teacher Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLUpdateTeacherController extends FXMLGeneralController implements Initializable  {
    @FXML private TextField tfStaffNumber;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private Button btnCancel;
    @FXML private Button btnUpdate;
    @FXML private Button btnRecoverTeacher;
    public static int staffNumber;
    Teacher teacherOriginal = new Teacher();
    List<String> datesUpdate = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
        colocateTeacher();
    }

    /**
     * Method to exit to the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method to return to the list
     */
    public void backList() {
        openWindowGeneral("/gui/administrator/fxml/FXMLUpdateTeacherList.fxml", btnCancel);
    }

    /**
     * Method to update a Teacher
     */
    public void updateTeacher(){
        boolean registerComplete;
        removeStyle();
        boolean validateFields = validateFields();
        if(validateFields){
            Teacher teacherNew = new Teacher();
            boolean areChanges = createObjectTeacherDifferent(teacherNew);
            if (areChanges){
                int validTeacher = Teacher.validateAcademicUpdate(teacherNew.getStaffNumber(), teacherNew.getEmail()
                        , teacherNew.getAlternateEmail(), teacherNew.getPhone());
                if (validTeacher == Search.NOTFOUND.getValue()) {
                    boolean confirmUpdate = generateConfirmation("¿Seguro que desea modificar al Profesor?");
                    if(confirmUpdate){
                        registerComplete = Teacher.updateTeacher(staffNumber, teacherNew, datesUpdate);
                        if(registerComplete){
                            generateInformation("Profesor modificado exitosamente");
                        }else{
                            generateError("No hay conexión a la base de datos. Intente más tarde");
                        }
                        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                    }
                } else {
                    if (validTeacher == Search.FOUND.getValue()) {
                        generateInformation("Este profesor ya está registrado");
                    } else {
                        if (validTeacher == Search.EXCEPTION.getValue()) {
                            generateError("No hay conexión a la base de datos. Intente más tarde");
                            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to recover a deleted teacher
     */
    public void recoverTeacher() {
        boolean recoverOk = generateConfirmation("¿Seguro que desea reactivar este profesor?");
        if(recoverOk){
            boolean recoverSuccessful = Teacher.recoverTeacher(staffNumber);
            if(recoverSuccessful){
                generateInformation("Profesor reactivado exitosamente");
            }else{
                generateError("No hay conexión con la base de datos. Intente más tarde");
            }
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRecoverTeacher);
        }
    }

    private void setLimitsTextFields() {
        limitTextField(tfName,30);
        limitTextField(tfLastName,30);
        limitTextField(tfEmail,50);
        limitTextField(tfAlternateEmail,50);
        limitTextField(tfPhone,10);
        limitTextField(tfStaffNumber,10);
        prohibitNumberTextField(tfName);
        prohibitNumberTextField(tfLastName);
        prohibitWordTextField(tfStaffNumber);
        prohibitWordTextField(tfPhone);
    }

    private void colocateTeacher() {
        teacherOriginal = Teacher.getTeacherSelected(staffNumber);
        if (teacherOriginal.getStaffNumber() == Search.NOTFOUND.getValue()) {
            generateError("No hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
        } else {
            tfName.setText(teacherOriginal.getName());
            tfLastName.setText(teacherOriginal.getLastName());
            tfEmail.setText(teacherOriginal.getEmail());
            tfAlternateEmail.setText(teacherOriginal.getAlternateEmail());
            if(teacherOriginal.getGender()== Gender.MALE.getGender()){
                rbMale.fire();
            }else{
                rbFemale.fire();
            }
            tfPhone.setText(teacherOriginal.getPhone());
            tfStaffNumber.setText(String.valueOf(teacherOriginal.getStaffNumber()));
            int activeCoordinator = Teacher.activeTeachers();
            if(teacherOriginal.getStatus().equalsIgnoreCase("Inactive") && activeCoordinator <= Search.FOUND.getValue()){
                btnRecoverTeacher.setVisible(true);
            }
        }
    }

    private void removeStyle(){
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
        rbMale.getStyleClass().remove("ok");
        rbFemale.getStyleClass().remove("ok");
        tfStaffNumber.getStyleClass().remove("error");
        tfName.getStyleClass().remove("error");
        tfLastName.getStyleClass().remove("error");
        tfEmail.getStyleClass().remove("error");
        tfAlternateEmail.getStyleClass().remove("error");
        tfPhone.getStyleClass().remove("error");
        rbMale.getStyleClass().remove("error");
        rbFemale.getStyleClass().remove("error");
    }

    private boolean validateFields(){
        ValidateUser validateUser = new ValidateUser();
        boolean validation= true;
        if((validateUser.validateEmpty(tfStaffNumber.getText()))){
            tfStaffNumber.getStyleClass().add("ok");
        }else{
            tfStaffNumber.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfName.getText())) && (validateUser.validateNameUser(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
            tfName.setText(validateUser.deleteSpace(tfName.getText()));
            tfName.setText(validateUser.createCorrectProperName(tfName.getText()));
        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfLastName.getText())) && (validateUser.validateNameUser(tfLastName.getText()))) {
            tfLastName.getStyleClass().add("ok");
            tfLastName.setText(validateUser.deleteSpace(tfLastName.getText()));
            tfLastName.setText(validateUser.createCorrectProperName(tfLastName.getText()));
        }else{
            tfLastName.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfEmail.getText())) && (validateUser.validateEmail(tfEmail.getText())) &&
                (!tfEmail.getText().equalsIgnoreCase(tfAlternateEmail.getText()))) {
            tfEmail.getStyleClass().add("ok");
        }else{
            tfEmail.getStyleClass().add("error");
            validation = false;
        }

        if((validateUser.validateEmpty(tfAlternateEmail.getText())) && (validateUser.validateEmail(tfAlternateEmail.getText())) &&
                (!tfEmail.getText().equalsIgnoreCase(tfAlternateEmail.getText()))) {
            tfAlternateEmail.getStyleClass().add("ok");
        }else{
            tfAlternateEmail.getStyleClass().add("error");
            validation = false;
        }

        if(validateUser.validateEmpty(tfPhone.getText()) && validateUser.validatePhoneNumber(tfPhone.getText())) {
            tfPhone.getStyleClass().add("ok");
        }else{
            tfPhone.getStyleClass().add("error");
            validation = false;
        }

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
            rbMale.getStyleClass().add("error");
            rbFemale.getStyleClass().add("error");
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
                rbMale.getStyleClass().add("error");
                rbFemale.getStyleClass().add("error");
            } else {
                rbMale.getStyleClass().add("ok");
                rbFemale.getStyleClass().add("ok");
            }
        }
        return validation;
    }

    private boolean createObjectTeacherDifferent(Teacher teacherNew) {
        boolean areChanges = false;
        if(teacherOriginal.getStaffNumber() != Integer.parseInt(tfStaffNumber.getText())) {
            teacherNew.setStaffNumber(Integer.parseInt(tfStaffNumber.getText()));
            datesUpdate.add("StaffNumber");
            areChanges = true;
        }
        if(!teacherOriginal.getName().equalsIgnoreCase(tfName.getText())) {
            teacherNew.setName(tfName.getText());
            datesUpdate.add("Name");
            areChanges = true;
        }
        if(!teacherOriginal.getLastName().equalsIgnoreCase(tfLastName.getText())) {
            teacherNew.setLastName(tfLastName.getText());
            datesUpdate.add("LastName");
            areChanges = true;
        }
        if(!teacherOriginal.getEmail().equalsIgnoreCase(tfEmail.getText())) {
            teacherNew.setEmail(tfEmail.getText());
            datesUpdate.add("Email");
            areChanges = true;
        }
        if(!teacherOriginal.getAlternateEmail().equalsIgnoreCase(tfAlternateEmail.getText())) {
            teacherNew.setAlternateEmail(tfAlternateEmail.getText());
            datesUpdate.add("AlternateEmail");
            areChanges = true;
        }
        if(!teacherOriginal.getPhone().equalsIgnoreCase(tfPhone.getText())) {
            teacherNew.setPhone(tfPhone.getText());
            datesUpdate.add("Phone");
            areChanges = true;
        }
        if(rbMale.isSelected()){
            if(teacherOriginal.getGender() != Gender.MALE.getGender()){
                teacherNew.setGender(Gender.MALE.getGender());
                datesUpdate.add("Gender");
                areChanges = true;
            }
        }else{
            if(rbFemale.isSelected()){
                if(teacherOriginal.getGender() != Gender.FEMALE.getGender()){
                    teacherNew.setGender(Gender.FEMALE.getGender());
                    datesUpdate.add("Gender");
                    areChanges = true;
                }
            }
        }
        return areChanges;
    }

}
