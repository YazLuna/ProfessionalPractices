package gui.coordinator.controller;

import domain.Gender;
import domain.Practitioner;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;


public class FXMLDeletePractitionerController extends FXMLGeneralController implements Initializable {
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
    private Practitioner practitioner = new Practitioner();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        practitioner.setEnrollment(enrollment);
        colocatePractitioner();
    }

    private void colocatePractitioner() {
        practitioner = practitioner.getPractitioner();
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
        if(practitioner.getProfilePicture() != null){
            //imgPractitioner.setImage(practitioner.getProfilePicture());
        }
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        FXMLUpdateDeletePractitionerListController.action = "Delete";
        openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateDeletePractitionerList.fxml",btnCancel);
    }

    public void delete()  {
        boolean replyConfirmation = generateConfirmation("¿Seguro que desea eliminar el practicante?");
        if(replyConfirmation){
            boolean delete = practitioner.deletePractitioner();
            if(delete){
                openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnDelete);
                generateInformation("El practicante fue eliminado exitosamente");
            }else{
                generateError("No se pudo eliminar el practicante");
            }
        }
    }
  

}
