package gui.administrator.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import logic.ValidateAddUser;
import domain.Coordinator;
import gui.FXMLGeneralController;
import domain.Gender;

public class FXMLUpdateCoordinatorController extends FXMLGeneralController implements Initializable  {
    public ImageView imgProfilePicture;
    @FXML private Button btnRecoverCoordinator;
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
    private File imgFile;
    public static int staffNumber;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
        colocateCoordinator();
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        generateConfirmationCancel("¿Deseas cancelar?",btnCancel,"/gui/administrator/fxml/FXMLUpdateCoordinatorList.fxml");
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
                Coordinator coordinator = new Coordinator();
                createObjectCoordinator(coordinator);
            /*registerComplete = coordinator.addCoordinator();
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

    private void colocateCoordinator() {
        Coordinator coordinator = new Coordinator();
        coordinator = coordinator.getCoordinatorSelected(staffNumber);
        tfName.setText(coordinator.getName());
        tfLastName.setText(coordinator.getLastName());
        tfEmail.setText(coordinator.getEmail());
        tfAlternateEmail.setText(coordinator.getAlternateEmail());
        if(coordinator.getGender()== Gender.MALE.getGender()){
            rbMale.isSelected();
        }else{
            rbFemale.isSelected();
        }
        tfPhone.setText(coordinator.getPhone());
        tfStaffNumber.setText(String.valueOf(coordinator.getStaffNumber()));
        if(coordinator.getProfilePicture()==null){
            //imgCoordinator.setImage("/images/Add.png");
        }else{
            //imgCoordinator.setImage(coordinator.getProfilePicture());
        }
        boolean activeCoordinator = coordinator.activeCoordinator();
        if(coordinator.getStatus().equalsIgnoreCase("Inactive") && !activeCoordinator){
            btnRecoverCoordinator.setVisible(true);
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

    private void createObjectCoordinator(Coordinator coordinator) {
        coordinator.setStaffNumber(Integer.parseInt(validateAddUser.deleteAllSpace(tfStaffNumber.getText())));
        coordinator.setName(validateAddUser.deleteSpace(tfName.getText()));
        coordinator.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
        coordinator.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
        coordinator.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
        coordinator.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
        if(rbMale.isSelected()){
            coordinator.setGender(Gender.MALE.getGender());
        }else{
            if(rbFemale.isSelected()){
                coordinator.setGender(Gender.FEMALE.getGender());
            }
        }
        coordinator.setProfilePicture(imgFile);
    }

    public void recoverCoordinator() {
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(staffNumber);
        boolean recoverOk = generateConfirmation("¿Seguro que desea reactivar este coordinador?");
        if(recoverOk){
            boolean recoverSuccessful = coordinator.recoverCoordinator();
            if(recoverSuccessful){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRecoverCoordinator);
                generateInformation("Coordinador reactivado exitosamente");
            }else{
                generateError("No se puedo reactivar el Coordinador");
            }
        }
    }
}
