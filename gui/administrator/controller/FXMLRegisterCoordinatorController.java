package gui.administrator.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logic.ValidateAddUser;
import domain.Coordinator;
import gui.FXMLGeneralController;


public class FXMLRegisterCoordinatorController extends FXMLGeneralController implements Initializable  {
    @FXML private Button btnRegister;
    @FXML private Button btnCancel;
    @FXML private TextField tfStaffNumber;
    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAlternateEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfPassword;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cancel(ActionEvent actionEvent) {
        Alert cancel = new Alert(Alert.AlertType.NONE);
        cancel.setAlertType(Alert.AlertType.CONFIRMATION);
        cancel.setHeaderText("Do you want to cancel?");
        cancel.setTitle("Cancel");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.get() == ButtonType.OK) {
            returnGeneral("/gui/administrator/fxml/FXMLSectionCoordinator.fxml");
        }
    }

    public void loadProfilePicture(ActionEvent actionEvent) {
    }

    public void register(ActionEvent actionEvent) {
        boolean nameValidate;
        boolean lastNameValidate;
        boolean emailValidate;
        boolean alternateEmailValidate;
        boolean staffNumberValidate;
        boolean phoneValidate;
        int band =0;
        int registerComplete = 0;
        Coordinator coordinator = new Coordinator();
        tfStaffNumber.getStyleClass().remove("ok");
        tfName.getStyleClass().remove("ok");
        tfLastName.getStyleClass().remove("ok");
        tfEmail.getStyleClass().remove("ok");
        tfAlternateEmail.getStyleClass().remove("ok");
        tfPassword.getStyleClass().remove("ok");
        tfPhone.getStyleClass().remove("ok");
        if(!validateAddUser.validateEmpty(tfStaffNumber.getText())){
            staffNumberValidate = validateAddUser.validateStaffNumber(tfStaffNumber.getText());
            if(staffNumberValidate){
                tfStaffNumber.getStyleClass().add("ok");
                coordinator.setStaffNumber(Integer.parseInt(validateAddUser.deleteAllSpace(tfStaffNumber.getText())));
                coordinator.setName(tfName.getText());
                coordinator.setLastName(tfLastName.getText());
                coordinator.setEmail(tfEmail.getText());
                coordinator.setAlternateEmail(tfAlternateEmail.getText());
                coordinator.setPhone(tfPhone.getText());
                coordinator.setPassword(tfPassword.getText());

                if(!validateAddUser.validateEmpty(coordinator.getName())){
                    nameValidate = validateAddUser.validateName(tfName.getText());
                    if(nameValidate){
                        tfName.getStyleClass().add("ok");
                        if(!validateAddUser.validateEmpty(coordinator.getLastName())){
                            lastNameValidate = validateAddUser.validateLastName(coordinator.getLastName());
                            if(lastNameValidate){
                                tfLastName.getStyleClass().add("ok");
                                if(!validateAddUser.validateEmpty(coordinator.getEmail())){
                                    emailValidate = validateAddUser.validateEmail(coordinator.getEmail());
                                    if(emailValidate){
                                        tfEmail.getStyleClass().add("ok");
                                        if(!validateAddUser.validateEmpty(coordinator.getAlternateEmail())){
                                            alternateEmailValidate = validateAddUser.validateEmail(coordinator.getAlternateEmail());
                                            if(alternateEmailValidate){
                                                tfAlternateEmail.getStyleClass().add("ok");
                                                if(!validateAddUser.validateEmpty(coordinator.getPhone())){
                                                    phoneValidate = validateAddUser.validatePhone(coordinator.getPhone());
                                                    if(phoneValidate){
                                                        tfPhone.getStyleClass().add("ok");
                                                        if(!validateAddUser.validateEmpty(coordinator.getPassword())){
                                                            coordinator.setName(validateAddUser.deleteSpace(tfName.getText()));
                                                            coordinator.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
                                                            coordinator.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
                                                            coordinator.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
                                                            coordinator.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
                                                            coordinator.setPassword(validateAddUser.deleteSpace(tfPassword.getText()));
                                                            tfPassword.getStyleClass().add("ok");
                                                            band =1;
                                                        }else{
                                                            tfPassword.getStyleClass().add("error");
                                                        }
                                                    }else{
                                                        tfPassword.getStyleClass().add("error");
                                                    }
                                                }else{
                                                    tfPhone.getStyleClass().add("error");
                                                }
                                            }else{
                                                tfAlternateEmail.getStyleClass().add("error");
                                            }
                                        }else{
                                            tfAlternateEmail.getStyleClass().add("error");
                                        }
                                    }else{
                                       tfEmail.getStyleClass().add("error");
                                    }
                                }else{
                                    tfEmail.getStyleClass().add("error");
                                }
                            }else{
                                tfLastName.getStyleClass().add("error");
                            }
                        }else {
                            tfLastName.getStyleClass().add("error");
                        }
                    }else{
                       tfName.getStyleClass().add("error");
                    }
                }else{
                    tfName.getStyleClass().add("error");
                }
            }else{
                tfStaffNumber.getStyleClass().add("error");
            }
        }else{
            tfStaffNumber.getStyleClass().add("error");
        }
        if(band == 1){
            ToggleGroup radioGroup = new ToggleGroup();
            rbFemale.setToggleGroup(radioGroup);
            rbFemale.setToggleGroup(radioGroup);
            if(rbMale.isSelected()){
                coordinator.setGender(1);
            }else{
                if(rbFemale.isSelected()){
                    coordinator.setGender(0);
                }else{
                    band = 0;
                    generateAlert("Select the gender");
                }
            }
        }
        if(band == 1){
            Date myDate = new Date();
            coordinator.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
            registerComplete = coordinator.addCoordinator();
            if(registerComplete==1){
                generateConfirmation("The register was complete");
                returnGeneral("/gui/administrator/fxml/FXMLSectionCoordinator.fxml");
            }else{
                if(registerComplete==0){
                    generateError("This coordinator is already registered ");
                }
            }
        }
    }

    public void logOut(ActionEvent actionEvent) {
       logOutGeneral();
    }
}
