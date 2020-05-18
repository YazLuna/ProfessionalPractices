package gui.administrator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import gui.FXMLGeneralController;
import domain.Coordinator;

public class FXMLDeleteCoordinatorController extends FXMLGeneralController implements Initializable {
    @FXML private Label lbName;
    @FXML private Label lbLastName;
    @FXML private Label lbGender;
    @FXML private Label lbEmail;
    @FXML private Label lbAlternateEmail;
    @FXML private Label lbPhone;
    @FXML private Label lbRegistrationDate;
    @FXML private Label lbStaffNumber;
    Coordinator coordinator = new Coordinator();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            coordinator = coordinator.getCoordinator();
        } catch (SQLException exception) {
            Logger.getLogger(FXMLDeleteCoordinatorController.class.getName()).log(Level.SEVERE, null, exception);
        }
        lbName.setText(coordinator.getName());
        lbLastName.setText(coordinator.getLastName());
        lbEmail.setText(coordinator.getEmail());
        lbAlternateEmail.setText(coordinator.getAlternateEmail());
        if(coordinator.getGender()==1){
            lbGender.setText("Male");
        }else{
            lbGender.setText("Female");
        }
        lbPhone.setText(coordinator.getPhone());
        lbRegistrationDate.setText(coordinator.getRegistrationDate());
        lbStaffNumber.setText(String.valueOf(coordinator.getStaffNumber()));
    }

    public void logOut(ActionEvent actionEvent) {
        logOutGeneral();
    }

    public void cancel(ActionEvent actionEvent) {
        cancelGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        boolean delete;
        Date myDate = new Date();
        coordinator.setDischargeDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
        delete = coordinator.deleteCoordinator("Inactive",coordinator.getDischargeDate());
        if(delete){
            cancelGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml");
            generateInformation("The coordinator was successfully deleted");
        }else{
            generateError("Could not delete coordinator");
        }
    }
}
