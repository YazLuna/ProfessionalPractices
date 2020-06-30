package gui.coordinator.controller;

import domain.LinkedOrganization;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLDeleteLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnDelete;
    @FXML private Button btnCancel;
    @FXML private Text txtNameOrganization;
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
        startComponent();
    }
    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml",btnBehind);
    }


    public void assignLinkedOrganization (String nameLinkedOrganization){
        this.nameLinkedOrganization = nameLinkedOrganization;
    }

    public void startComponent () {
        organization = new LinkedOrganization();
        organization.setName(nameLinkedOrganization);
        organization = organization.getOrganization();
        txtNameOrganization.setText(organization.getName());
        lbUsersDirect.setText(organization.getDirectUsers());
        lbUsersIndirect.setText(organization.getIndirectUsers());
        lbEmail.setText(organization.getEmail());
        lbAddress.setText(organization.getAddress());
        lbPhoneNumber.setText(organization.getPhoneNumber());
        lbState.setText(organization.getState());
        lbCity.setText(organization.getCity());
        lbSector.setText(organization.getSector());
    }

    public void delete (){
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro desea eliminar la Organización vinculada?", YES, NO);
        cancel.setTitle("Confirmación Eliminar");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.orElse(NO) == YES) {
            boolean isDeleteLinkedOrganization;
            isDeleteLinkedOrganization = organization.deleteOrganization();
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

    public void cancel(){
        generateConfirmationCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml");
    }

}
