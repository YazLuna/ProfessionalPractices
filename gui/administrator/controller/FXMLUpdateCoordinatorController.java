package gui.administrator.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import domain.Coordinator;
import gui.FXMLGeneralController;
import domain.Gender;
import logic.ValidateAddUser;

/**
 * Update Coordinator Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLUpdateCoordinatorController extends FXMLGeneralController implements Initializable  {
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
    public static int staffNumber;
    List<String> datesUpdate = new ArrayList<>();
    Coordinator coordinatorOriginal = new Coordinator();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLimitsTextFields();
        colocateCoordinator();
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
        openWindowGeneral("/gui/administrator/fxml/FXMLUpdateCoordinatorList.fxml", btnCancel);
    }

    /**
     * Method to update a Coordinator
     */
    public void updateCoordinator(){
        boolean registerComplete;
        removeStyle();
        boolean validateFields = validateFields();
        if(validateFields){
            Coordinator coordinatorNew = new Coordinator();
            boolean areChanges = createObjectCoordinatorDifferent(coordinatorNew);
            if (areChanges){
                boolean validCoordinator = Coordinator.validateAcademicUpdate(coordinatorNew.getStaffNumber(), coordinatorNew.getEmail()
                        , coordinatorNew.getAlternateEmail(), coordinatorNew.getPhone());
                if (validCoordinator) {
                    boolean confirmUpdate = generateConfirmation("¿Seguro que desea modificar el Coordinador?");
                    if(confirmUpdate){
                        registerComplete = Coordinator.updateCoordinator(staffNumber, coordinatorNew, datesUpdate);
                        if(registerComplete){
                            generateInformation("Coordinator modificado exitosamente");
                            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                        }else{
                            generateError("No hay conexión a la base de datos. Intente más tarde");
                        }
                    }
                } else {
                    generateInformation("Este coordinador ya está registrado");
                }
            }
        }
    }

    /**
     * Method to recover a deleted Coordinator
     */
    public void recoverCoordinator() {
        boolean recoverOk = generateConfirmation("¿Seguro que desea reactivar este coordinador?");
        if(recoverOk){
            boolean recoverSuccessful = Coordinator.recoverCoordinator(staffNumber);
            if(recoverSuccessful){
                generateInformation("Coordinador reactivado exitosamente");
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRecoverCoordinator);
            }else{
                generateError("No hay conexión con la base de datos. Intente más tarde");
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
        prohibitNumberTextField(tfName);
        prohibitNumberTextField(tfLastName);
        prohibitWordTextField(tfStaffNumber);
        prohibitWordTextField(tfPhone);
    }

    private void colocateCoordinator() {
        coordinatorOriginal = Coordinator.getCoordinatorSelected(staffNumber);
        tfName.setText(coordinatorOriginal.getName());
        tfLastName.setText(coordinatorOriginal.getLastName());
        tfEmail.setText(coordinatorOriginal.getEmail());
        tfAlternateEmail.setText(coordinatorOriginal.getAlternateEmail());
        if(coordinatorOriginal.getGender()== Gender.MALE.getGender()){
            rbMale.fire();
        }else{
            rbFemale.fire();
        }
        tfPhone.setText(coordinatorOriginal.getPhone());
        tfStaffNumber.setText(String.valueOf(coordinatorOriginal.getStaffNumber()));
        boolean activeCoordinator = Coordinator.activeCoordinator();
        if(coordinatorOriginal.getStatus().equalsIgnoreCase("Inactive") && !activeCoordinator){
            btnRecoverCoordinator.setVisible(true);
        }
    }

    private void removeStyle(){
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
        tfStaffNumber.getStyleClass().remove("error");
        tfName.getStyleClass().remove("error");
        tfLastName.getStyleClass().remove("error");
        tfEmail.getStyleClass().remove("error");
        tfAlternateEmail.getStyleClass().remove("error");
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

        if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
            validation = false;
        }else{
            if((rbMale.isSelected()) && (rbFemale.isSelected())){
                validation = false;
            }
        }
        return validation;
    }

    private boolean createObjectCoordinatorDifferent(Coordinator coordinatorNew) {
        boolean areChanges = false;
        if(coordinatorOriginal.getStaffNumber() != Integer.parseInt(tfStaffNumber.getText())) {
            coordinatorNew.setStaffNumber(Integer.parseInt(tfStaffNumber.getText()));
            datesUpdate.add("StaffNumber");
            areChanges = true;
        }
        if(!coordinatorOriginal.getName().equalsIgnoreCase(tfName.getText())) {
            coordinatorNew.setName(tfName.getText());
            datesUpdate.add("Name");
            areChanges = true;
        }
        if(!coordinatorOriginal.getLastName().equalsIgnoreCase(tfLastName.getText())) {
            coordinatorNew.setLastName(tfLastName.getText());
            datesUpdate.add("LastName");
            areChanges = true;
        }
        if(!coordinatorOriginal.getEmail().equalsIgnoreCase(tfEmail.getText())) {
            coordinatorNew.setEmail(tfEmail.getText());
            datesUpdate.add("Email");
            areChanges = true;
        }
        if(!coordinatorOriginal.getAlternateEmail().equalsIgnoreCase(tfAlternateEmail.getText())) {
            coordinatorNew.setAlternateEmail(tfAlternateEmail.getText());
            datesUpdate.add("AlternateEmail");
            areChanges = true;
        }
        if(!coordinatorOriginal.getPhone().equalsIgnoreCase(tfPhone.getText())) {
            coordinatorNew.setPhone(tfPhone.getText());
            datesUpdate.add("Phone");
            areChanges = true;
        }
        if(rbMale.isSelected()){
            if(coordinatorOriginal.getGender() != Gender.MALE.getGender()){
                coordinatorNew.setGender(Gender.MALE.getGender());
                datesUpdate.add("Gender");
                areChanges = true;
            }
        }else{
            if(rbFemale.isSelected()){
                if(coordinatorOriginal.getGender() != Gender.FEMALE.getGender()){
                    coordinatorNew.setGender(Gender.FEMALE.getGender());
                    datesUpdate.add("Gender");
                    areChanges = true;
                }
            }
        }
        return areChanges;
    }

}
