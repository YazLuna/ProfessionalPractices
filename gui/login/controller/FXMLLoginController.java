package gui.login.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.ValidateAddUser;
import dataaccess.LoginAccountDAOImpl;
import gui.FXMLGeneralController;
import gui.coordinator.controller.FXMLMenuCoordinatorController;
import gui.teacher.controller.FXMLMenuTeacherController;
import domain.Number;

/**
 * Login Controller
 * @author Yazmin
 * @version 01/07/2020
 */
public class FXMLLoginController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnLogin;
    @FXML private TextField tfUser;
    @FXML private PasswordField pfPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limitTextField(tfUser,50);
        limitTextField(pfPassword,20);
    }

    /**
     * Method to login the system
     */
    public void loginSystem() {
        removeStyle();
        boolean validate= validateInformation();
        if(validate){
            LoginAccountDAOImpl newLogin = new LoginAccountDAOImpl();
            String password= encryptPassword(pfPassword.getText());
            String user = tfUser.getText();
            boolean isFirstLogin = newLogin.firstLogin(user, password);
            if(isFirstLogin){
                FXMLFirstLoginController.password= password;
                FXMLFirstLoginController.userName= user;
                openWindowGeneral("/gui/login/fxml/FXMLFirstLogin.fxml", btnLogin);
            }else{
                boolean searchLoginAccount = newLogin.searchLoginAccount(user, password);
                if(searchLoginAccount){
                    openMenu(user, password);
                }else{
                    generateError("Usuario o contraseña incorrectos");
                }
            }
        }
    }

    private void removeStyle() {
        tfUser.getStyleClass().remove("error");
        pfPassword.getStyleClass().remove("error");
        tfUser.getStyleClass().remove("ok");
        pfPassword.getStyleClass().remove("ok");
    }

    private boolean validateInformation() {
        boolean validation = true;
        ValidateAddUser validateAddUser = new ValidateAddUser();
        if (!validateAddUser.validateEmpty(tfUser.getText())) {
            tfUser.getStyleClass().add("error");
            validation = false;
        }

        if (!validateAddUser.validateEmpty(pfPassword.getText())) {
            pfPassword.getStyleClass().add("error");
            validation = false;
        }
        return  validation;
    }

    private void openMenu(String user, String password) {
        LoginAccountDAOImpl newLogin = new LoginAccountDAOImpl();
        List<String> userTypes=newLogin.searchUserTypeWithLoginAccount(user, password);
        if(userTypes.size() == Number.ONE.getNumber()){
            String userType= userTypes.get(Number.ZERO.getNumber());
            switch (userType){
                case "Coordinator":
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml", btnLogin);
                    break;
                case "Administrator":
                    openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnLogin);
                    break;
                case "Teacher":
                    openWindowGeneral("/gui/teacher/fxml/FXMLMenuTeacher.fxml", btnLogin);
                    break;
                case "Practitioner":
                    openWindowGeneral("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml", btnLogin);
                    break;
            }
        }else{
            if(userTypes.size() == Number.TWO.getNumber()) {
                FXMLMenuCoordinatorController.isTeacher = true;
                FXMLMenuTeacherController.isCoordinator = true;
                openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml", btnLogin);
            }
        }
    }

}
