package gui.administrator.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.ValidateAddUser;
import domain.Coordinator;
import gui.FXMLGeneralController;


public class FXMLRegisterCoordinatorController extends FXMLGeneralController implements Initializable  {
    @FXML private Button btnRegister;
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
        returnGeneral("/gui/administrator/fxml/FXMLSectionCoordinator.fxml");
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
        if(!validateAddUser.validateEmpty(tfStaffNumber.getText())){
            staffNumberValidate = validateAddUser.validateStaffNumber(tfStaffNumber.getText());
            if(staffNumberValidate){
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
                        if(!validateAddUser.validateEmpty(coordinator.getLastName())){
                            lastNameValidate = validateAddUser.validateLastName(coordinator.getLastName());
                            if(lastNameValidate){
                                if(!validateAddUser.validateEmpty(coordinator.getEmail())){
                                    emailValidate = validateAddUser.validateEmail(coordinator.getEmail());
                                    if(emailValidate){
                                        if(!validateAddUser.validateEmpty(coordinator.getAlternateEmail())){
                                            alternateEmailValidate = validateAddUser.validateEmail(coordinator.getAlternateEmail());
                                            if(alternateEmailValidate){
                                                if(!validateAddUser.validateEmpty(coordinator.getPhone())){
                                                    phoneValidate = validateAddUser.validatePhone(coordinator.getPhone());
                                                    if(phoneValidate){
                                                        if(!validateAddUser.validateEmpty(coordinator.getPassword())){
                                                            coordinator.setName(validateAddUser.deleteSpace(tfName.getText()));
                                                            coordinator.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
                                                            coordinator.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
                                                            coordinator.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
                                                            coordinator.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
                                                            coordinator.setPassword(validateAddUser.deleteSpace(tfPassword.getText()));
                                                            band =1;
                                                        }else{
                                                            generateAlert("The password is empty");
                                                        }
                                                    }else{
                                                        generateAlert("The phone is wrong");
                                                    }
                                                }else{
                                                    generateAlert("The phone is empty");
                                                }
                                            }else{
                                                generateAlert("The alternate email is wrong");
                                            }
                                        }else{
                                            generateAlert("The alternate email is empty");
                                        }
                                    }else{
                                        generateAlert("The email is wrong");
                                    }
                                }else{
                                    generateAlert("The email is empty");
                                }
                            }else{
                                generateAlert("The lastName is wrong");
                            }
                        }else {
                            generateAlert("The lastName is empty");
                        }
                    }else{
                       generateAlert("The name is wrong");
                    }
                }else{
                    generateAlert("The name is empty");
                }
            }else{
                generateAlert("The staffNumber is wrong");
            }
        }else{
            generateAlert("The staffNumber is empty");
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
                    generateError("Registration could not be completed dataAccessError");
                }
            }
        }
    }

    public void logOut(ActionEvent actionEvent) {
       logOutGeneral();
    }
}
