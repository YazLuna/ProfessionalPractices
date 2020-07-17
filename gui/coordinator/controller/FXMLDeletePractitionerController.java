package gui.coordinator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import domain.Gender;
import domain.Practitioner;
import gui.FXMLGeneralController;

/**
 * Delete Practitioner Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLDeletePractitionerController extends FXMLGeneralController implements Initializable {
    @FXML private Label lbTerm;
    @FXML private Label lbName;
    @FXML private Label lbLastName;
    @FXML private Label lbGender;
    @FXML private Label lbEmail;
    @FXML private Label lbAlternateEmail;
    @FXML private Label lbPhone;
    @FXML private Label lbEnrollment;
    @FXML private Button btnCancel;
    @FXML private Button btnDelete;
    public static String enrollment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocatePractitioner();
    }

    /**
     * Method to exit the system
     */
    public void logOutCoordinator() {
        logOutGeneral();
    }

    /**
     * Method to cancel the deletion and return to the list
     */
    public void backList() {
        FXMLUpdateDeletePractitionerListController.action = "Delete";
        openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateDeletePractitionerList.fxml",btnCancel);
    }

    /**
     * Method to delete the practitioner
     */
    public void deletePractitioner()  {
        boolean replyConfirmation = generateConfirmation("¿Seguro que desea eliminar el practicante?");
        if(replyConfirmation){
            boolean delete = Practitioner.deletePractitioner(enrollment, "Inactive");
            if(delete){
                generateInformation("Practicante eliminado exitosamente");
            }else{
                generateError("No hay conexión a la base de datos. Intente más tarde");
            }
            openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnDelete);
        }
    }

    private void colocatePractitioner() {
        Practitioner practitioner = Practitioner.getPractitioner(enrollment);
        if (practitioner.getEnrollment() == null) {
            generateError("No hay conexión con la base de datos. Intente más tarde");
            openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnDelete);
        } else {
            lbName.setText(practitioner.getName());
            lbLastName.setText(practitioner.getLastName());
            lbEmail.setText(practitioner.getEmail());
            lbAlternateEmail.setText(practitioner.getAlternateEmail());
            if(practitioner.getGender()== Gender.MALE.getGender()){
                lbGender.setText("Masculino");
            }else{
                lbGender.setText("Femenino");
            }
            lbPhone.setText(practitioner.getPhone());
            lbEnrollment.setText(practitioner.getEnrollment());
            lbTerm.setText(practitioner.getTerm());
        }
    }
}
