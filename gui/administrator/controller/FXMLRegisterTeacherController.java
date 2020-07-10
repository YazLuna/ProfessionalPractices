package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import logic.ValidateAddUser;
import domain.Teacher;
import domain.Gender;
import domain.User;

/**
 * Register Teacher Controller
 * @author Yazmin
 * @version 08/07/2020
 */
public class FXMLRegisterTeacherController extends FXMLGeneralController implements Initializable {
    @FXML private TextField tfStaffNumber;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfUserName;
    @FXML private TextField tfPassword;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private Button btnCancel;
    @FXML private Button btnRegister;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
    }

    /**
     * Method to exit the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method to cancel registration and return to the menu
     */
    public void backMenu() {
        generateCancel("¿Seguro que desea cancelar?",btnCancel,"/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }

    /**
     * Method to register a teacher
     */
    public void registerTeacher() {
        removeStyle();
        boolean validate = validateFields();
        if(validate){
            Teacher teacher = createObjectTeacher();
            boolean validUserTeacher = userValidateNotExist(teacher);
            if (validUserTeacher) {
                boolean registerComplete = Teacher.addTeacher(teacher);
                if(registerComplete){
                    generateInformation("Profesor registrado exitosamente");
                    openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRegister);
                }else{
                    generateError("No hay conexión con la base de datos. Intente más tarde");
                }
            } else {
                generateInformation("Este profesor ya esta registrado");
            }
        }
    }

    private  boolean userValidateNotExist(Teacher teacher){
        boolean validUserTeacher = Teacher.validateAcademicAdd(teacher.getStaffNumber(), teacher.getEmail(), teacher.getAlternateEmail(),
                teacher.getPhone(), teacher.getUserName());
        if (validUserTeacher) {
            User user = (User)teacher;
            Teacher.addUser(user, "Teacher");
        } else {
            validUserTeacher = Teacher.isCoordinator(teacher);
        }
        return  validUserTeacher;
    }

    private void setLimitsTextFields() {
        limitTextField(tfName,30);
        limitTextField(tfLastName,30);
        limitTextField(tfEmail,50);
        limitTextField(tfAlternateEmail,50);
        limitTextField(tfPhone,10);
        limitTextField(tfUserName,50);
        limitTextField(tfPassword,20);
        limitTextField(tfStaffNumber,10);
        prohibitNumberTextField(tfName);
        prohibitNumberTextField(tfLastName);
        prohibitWordTextField(tfStaffNumber);
        prohibitWordTextField(tfPhone);
    }

    private void removeStyle(){
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfUserName.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
        tfStaffNumber.getStyleClass().remove("error");
        tfName.getStyleClass().remove("error");
        tfLastName.getStyleClass().remove("error");
        tfEmail.getStyleClass().remove("error");
        tfAlternateEmail.getStyleClass().remove("error");
        tfUserName.getStyleClass().remove("error");
        tfPassword.getStyleClass().remove("error");
        tfPhone.getStyleClass().remove("error");
    }

    private boolean validateFields(){
        ValidateAddUser validateAddUser = new ValidateAddUser();
        boolean validation= true;
        if((validateAddUser.validateEmpty(tfStaffNumber.getText()))){
            tfStaffNumber.getStyleClass().add("ok");
        }else{
            tfStaffNumber.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfName.getText())) && (validateAddUser.validateNameUser(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
            tfName.setText(validateAddUser.deleteSpace(tfName.getText()));
            tfName.setText(validateAddUser.createCorrectProperName(tfName.getText()));
        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfLastName.getText())) && (validateAddUser.validateNameUser(tfLastName.getText()))) {
            tfLastName.getStyleClass().add("ok");
            tfLastName.setText(validateAddUser.deleteSpace(tfLastName.getText()));
            tfLastName.setText(validateAddUser.createCorrectProperName(tfLastName.getText()));
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

        if(validateAddUser.validateEmpty(tfPhone.getText()) && validateAddUser.validatePhoneNumber(tfPhone.getText())) {
            tfPhone.getStyleClass().add("ok");
        }else{
            tfPhone.getStyleClass().add("error");
            validation = false;
        }

        if(validateAddUser.validateEmpty(tfPassword.getText()) && validateAddUser.validatePassword(tfPassword.getText())) {
            tfPassword.getStyleClass().add("ok");
        }else{
            tfPassword.getStyleClass().add("error");
            validation = false;
        }

        if(validateAddUser.validateEmpty(tfUserName.getText()) && validateAddUser.validateUserName(tfUserName.getText())) {
            tfUserName.getStyleClass().add("ok");
        }else{
            tfUserName.getStyleClass().add("error");
            validation = false;
        }

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
            }
        }
        return validation;
    }

    private Teacher createObjectTeacher() {
        Teacher teacher = new Teacher();
        teacher.setStaffNumber(Integer.parseInt(tfStaffNumber.getText()));
        teacher.setName(tfName.getText());
        teacher.setLastName(tfLastName.getText());
        teacher.setEmail(tfEmail.getText());
        teacher.setAlternateEmail(tfAlternateEmail.getText());
        teacher.setPhone(tfPhone.getText());
        teacher.setUserName(tfUserName.getText());
        String passwordEncryption = encryptPassword(tfPassword.getText());
        teacher.setPassword(passwordEncryption);
        if(rbMale.isSelected()){
            teacher.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                teacher.setGender(Gender.FEMALE.getGender());
            }
        }
        Date registerDate = new Date();
        teacher.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(registerDate));
        return teacher;
    }

}
