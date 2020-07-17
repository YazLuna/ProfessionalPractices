package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import gui.FXMLGeneralController;
import domain.Coordinator;
import domain.Gender;

/**
 * Delete Coordinator Controller
 * @author Yazmin
 * @version 05/07/2020
 */
public class FXMLDeleteCoordinatorController extends FXMLGeneralController implements Initializable {
    @FXML private Label lbName;
    @FXML private Label lbLastName;
    @FXML private Label lbGender;
    @FXML private Label lbEmail;
    @FXML private Label lbAlternateEmail;
    @FXML private Label lbPhone;
    @FXML private Label lbRegistrationDate;
    @FXML private Label lbStaffNumber;
    @FXML private Button btnCancel;
    @FXML private Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateCoordinator();
    }

    private void colocateCoordinator() {
        Coordinator coordinator = Coordinator.getCoordinator();
        lbName.setText(coordinator.getName());
        lbLastName.setText(coordinator.getLastName());
        lbEmail.setText(coordinator.getEmail());
        lbAlternateEmail.setText(coordinator.getAlternateEmail());
        if(coordinator.getGender()== Gender.MALE.getGender()){
            lbGender.setText("Masculino");
        }else{
            lbGender.setText("Femenino");
        }
        lbPhone.setText(coordinator.getPhone());
        lbRegistrationDate.setText(coordinator.getRegistrationDate());
        lbStaffNumber.setText(String.valueOf(coordinator.getStaffNumber()));
    }

    /**
     * Method to exit the system
     */
    public void logOutAdministrator() {
        logOutGeneral();
    }

    /**
     * Method to cancel the deletion and return to the menu
     */
    public void backMenu() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnCancel);
    }

    /**
     * Method to delete the coordinator
     */
    public void deleteCoordinator()  {
        boolean replyConfirmation = generateConfirmation("¿Seguro que desea eliminar el coordinador?");
        if(replyConfirmation){
            Date actualDate = new Date();
            String dischargeDate = (new SimpleDateFormat("yyyy-MM-dd").format(actualDate));
            boolean delete = Coordinator.deleteCoordinator("Inactive", dischargeDate);
            if(delete){
                generateInformation("Coordinador eliminado exitosamente");
            }else{
                generateError("No hay conexión a la base de datos. Intente más tarde");
            }
            openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml", btnDelete);
        }
    }
}
