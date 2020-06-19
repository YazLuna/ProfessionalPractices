package gui.coordinator.controller;

import domain.LinkedOrganization;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDeleteLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnDelete;
    @FXML private Button btnCancel;
    @FXML private Label lbNameOrganization;
    @FXML private Label lbUsersDirect;
    @FXML private Label lbUsersIndirect;
    @FXML private Label lbEmail;
    @FXML private Label lbAddress;
    @FXML private Label lbPhoneNumber;
    @FXML private Label lbState;
    @FXML private Label lbCity;
    @FXML private Label lbSector;
    private static String nameLinkedOrganization;
    private LinkedOrganization organization;

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void assignLinkedOrganization (String nameLinkedOrganization){
        this.nameLinkedOrganization = nameLinkedOrganization;
    }

    public void startComponent () {
        organization = new LinkedOrganization();
        organization.setName(nameLinkedOrganization);
        organization = organization.getOrganization();
        lbNameOrganization.setText(organization.getName());
        lbUsersDirect.setText(organization.getDirectUsers());
        lbUsersIndirect.setText(organization.getIndirectUsers());
        lbEmail.setText(organization.getEmail());
        lbAddress.setText(organization.getAddress());
        lbPhoneNumber.setText(organization.getPhoneNumber());
        lbState.setText(organization.getState());
        lbCity.setText(organization.getCity());
        lbSector.setText(organization.getSector());
    }


}
