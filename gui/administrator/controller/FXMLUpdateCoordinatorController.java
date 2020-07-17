package gui.administrator.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import domain.Search;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import domain.Coordinator;
import gui.FXMLGeneralController;
import domain.Gender;
import logic.ValidateUser;

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
                int validCoordinator = Coordinator.validateAcademicUpdate(coordinatorNew.getStaffNumber(), coordinatorNew.getEmail()
                        , coordinatorNew.getAlternateEmail(), coordinatorNew.getPhone());
                if (validCoordinator == Search.NOTFOUND.getValue()) {
                    boolean confirmUpdate = generateConfirmation("¿Seguro que desea modificar el Coordinador?");
                    if(confirmUpdate){
                        registerComplete = Coordinator.updateCoordinator(staffNumber, coordinatorNew, datesUpdate);
                        if(registerComplete){
                            generateInformation("Coordinator modificado exitosamente");
                        }else{
                            generateError("No hay conexión a la base de datos. Intente más tarde");
                        }
                        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                    }
                } else {
                    if (validCoordinator == Search.FOUND.getValue()) {
                        generateInformation("Este coordinador ya está registrado");
                    } else {
                        if (validCoordinator == Search.EXCEPTION.getValue()) {
                            generateError("No hay conexión a la base de datos. Intente más tarde");
                            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
                        }
                    }
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
            }else{
                generateError("No hay conexión con la base de datos. Intente más tarde");
            }
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRecoverCoordinator);
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
        if (coordinatorOriginal.getStaffNumber() == Search.NOTFOUND.getValue()) {
            generateError("No hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnUpdate);
        } else {
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
            int activeCoordinator = Coordinator.activeCoordinator();
            if(coordinatorOriginal.getStatus().equalsIgnoreCase("Inactive") && activeCoordinator == Search.NOTFOUND.getValue()){
                btnRecoverCoordinator.setVisible(true);
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

    private boolean validateFields() {
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
