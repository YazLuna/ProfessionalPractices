package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import logic.ValidateAddUser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import domain.Search;
import domain.Teacher;
import domain.Gender;
import gui.FXMLGeneralController;

public class FXMLUpdateTeacherController extends FXMLGeneralController implements Initializable  {
    public ImageView imgProfilePicture;
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
    private File imgFile;
    public static int staffNumber;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
        colocateTeacher();
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        generateConfirmationCancel("¿Deseas cancelar?",btnCancel,"/gui/administrator/fxml/FXMLUpdateTeacherList.fxml");
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
                Teacher teacher = new Teacher();
                createObjectTeacher(teacher);
            /*registerComplete = Teacher.addTeacher();
            if(registerComplete){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                generateInformation("Registro Exitoso");
            }else{
                generateError("Este coordinador ya esta registrado");
            }*/
                System.out.println("Ya es válido");
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
        Teacher teacher = new Teacher();
        teacher = teacher.getTeacherSelected(staffNumber);
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
        if(teacher.getProfilePicture()!=null){
            //imgProfilePicture = new ImageView(imageGeneric);
            //imgProfilePicture.setImage(imageGeneric);
            //imgTeacher.setImage(Teacher.getProfilePicture());
        }


        int activeCoordinator = teacher.activeTeacher();
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

    private void createObjectTeacher(Teacher teacher) {
        teacher.setStaffNumber(Integer.parseInt(validateAddUser.deleteAllSpace(tfStaffNumber.getText())));
        teacher.setName(validateAddUser.deleteSpace(tfName.getText()));
        teacher.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
        teacher.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
        teacher.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
        teacher.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
        if(rbMale.isSelected()){
            teacher.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                teacher.setGender(Gender.FEMALE.getGender());
            }
        }
        teacher.setProfilePicture(imgFile);
    }

    public void recoverTeacher() {
        Teacher teacher = new Teacher();
        teacher.setStaffNumber(staffNumber);
        boolean recoverOk = generateConfirmation("¿Seguro que desea reactivar este profesor?");
        if(recoverOk){
            boolean recoverSuccessful = teacher.recoverTeacher();
            if(recoverSuccessful){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRecoverTeacher);
                generateInformation("Profesor reactivado exitosamente");
            }else{
                generateError("No se puedo reactivar el Profesor");
            }
        }
    }
}
