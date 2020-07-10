package gui.coordinator.controller;

import dataaccess.Number;
import domain.LinkedOrganization;
import domain.PhoneNumber;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class FXMLDeleteLinkedOrganizationController
 * @author MARTHA
 * @version 01/06/2020
 */
public class FXMLDeleteLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnDelete;
    @FXML private Button btnCancel;
    @FXML private Text txtNameOrganization;
    @FXML private Label lbUsersDirect;
    @FXML private Label lbUsersIndirect;
    @FXML private Label lbEmail;
    @FXML private Label lbAddress;
    @FXML private Label lbPhoneNumberOne;
    @FXML private Label lbPhoneNumberTwo;
    @FXML private Label lbExtensionsOne;
    @FXML private Label lbExtensionsTwo;
    @FXML private Label lbState;
    @FXML private Label lbCity;
    @FXML private Label lbSector;
    @FXML private GridPane gpPhoneNumber;
    private static String nameLinkedOrganization;
    private LinkedOrganization organization;

    public void initialize(URL url, ResourceBundle rb) {
        startComponentOrganization();
    }
    public void behindListLinkedOrganization() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml",btnBehind);
    }


    public void assignNameLinkedOrganization(String nameLinkedOrganization){
        this.nameLinkedOrganization = nameLinkedOrganization;
    }

    public void startComponentOrganization() {
        organization = new LinkedOrganization();
        organization = organization.getLinkedOrganization(nameLinkedOrganization);
        txtNameOrganization.setText(organization.getName());
        lbUsersDirect.setText(organization.getDirectUsers());
        lbUsersIndirect.setText(organization.getIndirectUsers());
        lbEmail.setText(organization.getEmail());
        lbAddress.setText(organization.getAddress());
        lbState.setText(organization.getState());
        lbCity.setText(organization.getCity());
        lbSector.setText(organization.getSector());
        PhoneNumber phoneNumber = new PhoneNumber();
        List<PhoneNumber> phoneNumberList = phoneNumber.getListPhoneNumber(organization.getIdLinkedOrganization());
        lbPhoneNumberOne.setText(phoneNumberList.get(0).getPhoneNumber());
        lbExtensionsOne.setText(phoneNumberList.get(0).getExtensions());
        if(phoneNumberList.size()== Number.TWO.getNumber()){
            lbPhoneNumberTwo.setText(phoneNumberList.get(1).getPhoneNumber());
            lbPhoneNumberTwo.getStyleClass().add("details");
            lbExtensionsTwo.setText(phoneNumberList.get(1).getExtensions());
            lbExtensionsTwo.getStyleClass().add("details");
            gpPhoneNumber.add(lbPhoneNumberTwo, 1, 0);
            gpPhoneNumber.add(lbExtensionsTwo, 1, 1);
        }
    }

    public void deleteLinkedOrganization(){
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro desea eliminar la Organización vinculada?", YES, NO);
        cancel.setTitle("Confirmación Eliminar");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.orElse(NO) == YES) {
            boolean isDeleteLinkedOrganization;
            isDeleteLinkedOrganization = organization.deleteLinkedOrganization(organization.getName());
            if(!isDeleteLinkedOrganization){
                generateError("La organización vinculada no pudo eliminarse");
                openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml", btnDelete);
            }else{
                generateInformation("La Organización Vinculada se eliminó exitosamente");
                boolean areLinkedOrganization;
                areLinkedOrganization = organization.thereAreLinkedOrganizationAvailable();
                if(!areLinkedOrganization) {
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnDelete);
                } else {
                    openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml", btnDelete);
                }
            }
        }
    }

    public void backListLinkedOrganization(){
        generateCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml");
    }

}
