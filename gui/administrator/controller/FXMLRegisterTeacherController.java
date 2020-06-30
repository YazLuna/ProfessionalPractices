package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import gui.FXMLGeneralController;
import logic.ValidateAddUser;
import domain.Teacher;
import domain.Gender;

public class FXMLRegisterTeacherController extends FXMLGeneralController implements Initializable {
    public ImageView imgProfilePicture;
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
    private File imgFile;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        generateConfirmationCancel("¿Deseas cancelar?",btnCancel,"/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }

    public void loadProfilePicture() {
        loadImage();
    }

    public void register() {
        removeStyle();
        boolean validate = validate();
        if(validate){
            Teacher teacher = new Teacher();
            createObjectTeacher(teacher);
            boolean registerComplete = teacher.addTeacher();
            if(registerComplete){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRegister);
                generateInformation("Registro Exitoso");
            }else{
                generateError("Este profesor ya esta registrado");
            }
        }
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
    }

    public void removeStyle(){
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfUserName.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("ok");
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

        if(validateAddUser.validateEmpty(tfPassword.getText())) {
            tfPassword.getStyleClass().add("ok");
        }else{
            tfPassword.getStyleClass().add("error");
            validation = false;
        }

        if(validateAddUser.validateEmpty(tfUserName.getText())) {
            tfUserName.getStyleClass().add("ok");
        }else{
            tfUserName.getStyleClass().add("error");
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

    private void createObjectTeacher(Teacher teacher) {
        teacher.setStaffNumber(Integer.parseInt(validateAddUser.deleteAllSpace(tfStaffNumber.getText())));
        teacher.setName(validateAddUser.deleteSpace(tfName.getText()));
        teacher.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
        teacher.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
        teacher.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
        teacher.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
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
        teacher.setProfilePicture(imgFile);
    }


}
