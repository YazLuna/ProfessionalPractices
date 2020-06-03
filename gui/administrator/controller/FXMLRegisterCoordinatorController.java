package gui.administrator.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import logic.ValidateAddUser;
import domain.Coordinator;
import gui.FXMLGeneralController;

/**
 * DAO User
 * @author Yazmin
 * @version 18/05/2020
 */

public class FXMLRegisterCoordinatorController extends FXMLGeneralController implements Initializable  {
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
    @FXML private Button btnLoadProfilePicture;
    File imgFile;
    FileChooser fileChooser = new FileChooser();
    private final ValidateAddUser validateAddUser = new ValidateAddUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cancel() {
        generateConfirmationCancel("do you want to cancel?","Cancel","/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
    }

    public void loadProfilePicture() throws IOException {
            fileChooser.setTitle("Buscar Imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );

            imgFile = fileChooser.showOpenDialog(null);

            if (imgFile != null) {
                Image image = new Image("file:" + imgFile.getAbsolutePath());
                imgProfilePicture.setImage(image);
            }

    }

    public boolean validate(){
        boolean validation= true;
        if((validateAddUser.validateEmpty(tfStaffNumber.getText())) && (validateAddUser.validateStaffNumber(tfStaffNumber.getText()))){
            tfStaffNumber.getStyleClass().add("ok");
        }else{
            tfStaffNumber.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfName.getText())) && (validateAddUser.validateName(tfName.getText()))) {
            tfName.getStyleClass().add("ok");
        }else{
            tfName.getStyleClass().add("error");
            validation = false;
        }

        if((validateAddUser.validateEmpty(tfLastName.getText())) && (validateAddUser.validateLastName(tfLastName.getText()))) {
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

        if((validateAddUser.validateEmpty(tfPhone.getText())) && (validateAddUser.validatePhone(tfPhone.getText()))) {
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

        /*ToggleGroup radioGroup = new ToggleGroup();
        * rbFemale.setToggleGroup(radioGroup);
        * rbFemale.setToggleGroup(radioGroup);
        */
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


    public void register() throws SQLException {
        boolean validate;
        boolean registerComplete;
        Date myDate = new Date();
        removeStyle();
        validate = validate();
        if(validate){
            Coordinator coordinator = new Coordinator();
            coordinator.setStaffNumber(Integer.parseInt(validateAddUser.deleteAllSpace(tfStaffNumber.getText())));
            coordinator.setName(validateAddUser.deleteSpace(tfName.getText()));
            coordinator.setLastName(validateAddUser.deleteSpace(tfLastName.getText()));
            coordinator.setEmail(validateAddUser.deleteSpace(tfEmail.getText()));
            coordinator.setAlternateEmail(validateAddUser.deleteSpace(tfAlternateEmail.getText()));
            coordinator.setPhone(validateAddUser.deleteSpace(tfPhone.getText()));
            coordinator.setUserName(tfPassword.getText());
            
            coordinator.setPassword(tfPassword.getText());

            ToggleGroup radioGroup = new ToggleGroup();
            rbFemale.setToggleGroup(radioGroup);
            rbFemale.setToggleGroup(radioGroup);
            if(rbMale.isSelected()){
                coordinator.setGender(1);
            }else{
                if(rbFemale.isSelected()){
                    coordinator.setGender(0);
                }
            }

            coordinator.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
            coordinator.setProfilePicture(imgFile);
            registerComplete = coordinator.addCoordinator();
            if(registerComplete){
                generateConfirmation("The register was complete");
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRegister);
            }else{
                generateError("This coordinator is already registered ");
            }
        }
    }

    public void logOut() {
       logOutGeneral();
    }
}
