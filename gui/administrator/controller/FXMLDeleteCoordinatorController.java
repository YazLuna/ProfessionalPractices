package gui.administrator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import gui.FXMLGeneralController;
import domain.Coordinator;
import domain.Gender;


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
    @FXML private ImageView imgCoordinator;
    private Coordinator coordinator = new Coordinator();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocateCoordinator();
    }

    private void colocateCoordinator() {
            coordinator = coordinator.getCoordinator();
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
        if(coordinator.getProfilePicture()==null){
            //imgCoordinator.setImage("/images/Add.png");
        }else{
            //imgCoordinator.setImage(coordinator.getProfilePicture());
        }
    }

    public void logOut() {
        logOutGeneral();
    }

    public void cancel() {
        openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnCancel);
    }

    public void delete()  {
        boolean delete;
        Date actualDate = new Date();
        boolean replyConfirmation;
        replyConfirmation= generateConfirmation("¿Seguro que desea eliminar el coordinador?");
        if(replyConfirmation){
            coordinator.setDischargeDate(new SimpleDateFormat("yyyy-MM-dd").format(actualDate));
            delete = coordinator.deleteCoordinator("Inactive",coordinator.getDischargeDate());
            if(delete){
                openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnDelete);
                generateInformation("El coordinador fue eliminado exitosamente");
            }else{
                generateError("No se pudo eliminar el Coordinador");
            }
        }
    }
}
