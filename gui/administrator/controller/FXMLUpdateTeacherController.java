package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import domain.Search;
import domain.Teacher;
import domain.Gender;
import gui.FXMLGeneralController;
import logic.ValidateAddUser;

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
    private final ValidateAddUser validateAddUser = new ValidateAddUser();
    Teacher teacher = new Teacher();
    List<String> datesUpdate = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
        colocateTeacher();
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        generateConfirmationCancel("¿Deseas cancelar?",btnCancel, "/gui/administrator/fxml/FXMLUpdateTeacherList.fxml");
    }

    public void loadProfilePicture() {
        loadImage();
    }

    public void update(){
        boolean validate;
        boolean registerComplete;
        removeStyle();
        validate = validate();
        if(validate){
            boolean confirm = generateConfirmation("¿Seguro que desea modificar el Coordinador?");
            if(confirm){
                Teacher teacherNew = new Teacher();
                createObjectTeacher(teacherNew);
                registerComplete = teacherNew.updateTeacher(staffNumber, teacher,datesUpdate);
                if(registerComplete){
                    openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                    generateInformation("Modifiación Exitoso");
                }else{
                    generateError("Este coordinador ya esta registrado");
                }
            }
        }
    }

    private void setLimitsTextFields() {
        limitTextField(tfName,30);
        limitTextField(tfLastName,30);
        limitTextField(tfEmail,50);
        limitTextField(tfAlternateEmail,50);
        limitTextField(tfPhone,10);
        limitTextField(tfStaffNumber,10);
    }

    private void colocateTeacher() {
        teacher = new Teacher();
        teacher.setStaffNumber(staffNumber);
        teacher = Teacher.getTeacherSelected(teacher.getStaffNumber());
        tfName.setText(teacher.getName());
        tfLastName.setText(teacher.getLastName());
        tfEmail.setText(teacher.getEmail());
        tfAlternateEmail.setText(teacher.getAlternateEmail());
        if(teacher.getGender()== Gender.MALE.getGender()){
            rbMale.isSelected();
        }else{
            rbFemale.isSelected();
        }
        tfPhone.setText(teacher.getPhone());
        tfStaffNumber.setText(String.valueOf(teacher.getStaffNumber()));

        int activeCoordinator = teacher.activeTeachers();
        if(teacher.getStatus().equalsIgnoreCase("Inactive") && activeCoordinator <= Search.FOUND.getValue()){
            btnRecoverTeacher.setVisible(true);
        }
    }

    public void removeStyle(){
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
    }

    public boolean validate(){
        boolean validation= true;
        if((validateAddUser.validateEmpty(tfStaffNumber.getText()))){
            tfStaffNumber.getStyleClass().add("ok");
        }else{
            tfStaffNumber.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfName.getText())) && (validateAddUser.validateNameUser(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfLastName.getText())) && (validateAddUser.validateNameUser(tfLastName.getText()))) {
            tfLastName.getStyleClass().add("ok");
        }else{
            tfLastName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfEmail.getText())) && (validateAddUser.validateEmail(tfEmail.getText()))) {
            tfEmail.getStyleClass().add("ok");
        }else{
            tfEmail.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfAlternateEmail.getText())) && (validateAddUser.validateEmail(tfAlternateEmail.getText()))) {
            tfAlternateEmail.getStyleClass().add("ok");
        }else{
            tfAlternateEmail.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfPhone.getText())) && (validateAddUser.validatePhoneNumber(tfPhone.getText()))) {
            tfPhone.getStyleClass().add("ok");
        }else{
            tfPhone.getStyleClass().add("error");
            validation = false;
        }

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
            rbMale.getStyleClass().add("error");
            rbFemale.getStyleClass().add("error");
            generateAlert("Select the gender");
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
                rbMale.getStyleClass().add("error");
                rbFemale.getStyleClass().add("error");
                generateAlert("Select only one gender");
            }
        }

        return validation;
    }

    private void createObjectTeacher(Teacher teacherNew) {
        if(teacher.getStaffNumber() != Integer.parseInt(tfStaffNumber.getText())) {
            teacherNew.setStaffNumber(Integer.parseInt(tfStaffNumber.getText()));
            datesUpdate.add("StaffNumber");
        }
        if(!teacher.getName().equalsIgnoreCase(tfName.getText())) {
            teacherNew.setName(tfName.getText());
            datesUpdate.add("Name");
        }
        if(!teacher.getLastName().equalsIgnoreCase(tfLastName.getText())) {
            teacherNew.setLastName(tfLastName.getText());
            datesUpdate.add("LastName");
        }
        if(!teacher.getEmail().equalsIgnoreCase(tfEmail.getText())) {
            teacherNew.setEmail(tfEmail.getText());
            datesUpdate.add("Email");
        }
        if(!teacher.getAlternateEmail().equalsIgnoreCase(tfAlternateEmail.getText())) {
            teacherNew.setAlternateEmail(tfAlternateEmail.getText());
            datesUpdate.add("AlternateEmail");
        }
        if(!teacher.getPhone().equalsIgnoreCase(tfPhone.getText())) {
            teacherNew.setPhone(tfPhone.getText());
            datesUpdate.add("Phone");
        }
        if(rbMale.isSelected()){
            if(teacher.getGender() != Gender.MALE.getGender()){
                teacherNew.setGender(Gender.MALE.getGender());
                datesUpdate.add("Gender");
            }
        }else{
            if(rbFemale.isSelected()){
                if(teacher.getGender() != Gender.FEMALE.getGender()){
                    teacherNew.setGender(Gender.FEMALE.getGender());
                    datesUpdate.add("Gender");
                }
            }
        }
    }

    public void recoverTeacher() {
        Teacher teacher = new Teacher();
        teacher.setStaffNumber(staffNumber);
        boolean recoverOk = generateConfirmation("¿Seguro que desea reactivar este profesor?");
        if(recoverOk){
            boolean recoverSuccessful = teacher.recoverTeacher(staffNumber);
            if(recoverSuccessful){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRecoverTeacher);
                generateInformation("Profesor reactivado exitosamente");
            }else{
                generateError("No se puedo reactivar el Profesor");
            }
        }
    }
}
