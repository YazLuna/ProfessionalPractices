

package gui.coordinator.controller;

import domain.LinkedOrganization;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.ValidateLinkedOrganization;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * class FXMLUpdateLinkedOrganizationController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateLinkedOrganizationController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnModify;
    @FXML private Button btnCancel;
    @FXML private Button btnBehind;
    @FXML private TextField tfNameOrganization;
    @FXML private TextField tfUsersDirect;
    @FXML private TextField tfUsersIndirect;
    @FXML private TextField tfEmailOrganization;
    @FXML private TextField tfPhoneNumber;
    @FXML private TextField tfAddress;
    @FXML private ComboBox cbState;
    @FXML private ComboBox cbCity;
    @FXML private ComboBox cbSector;
    private static String nameLinkedOrganization;
    private List<String> allSector;
    private List<String> allCity;
    private List<String> allState;
    private LinkedOrganization organization;
    private ValidateLinkedOrganization validateDataOrganization;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startDataComponentOrganization();
        startComboBox();
        startComponent();
    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml",btnBehind);
    }

    public void cancel () {
        generateConfirmationCancel("¿Seguro desea cancelar?",btnCancel,"/gui/coordinator/fxml/FXMLListLinkedOrganization.fxml");
    }

    public void assignLinkedOrganization (String nameLinkedOrganization){
        this.nameLinkedOrganization = nameLinkedOrganization;
    }

    public void startDataComponentOrganization () {
        organization = new LinkedOrganization();
        organization.setName(nameLinkedOrganization);
        organization = organization.getOrganization();
        tfNameOrganization.setText(organization.getName());
        tfUsersDirect.setText(organization.getDirectUsers());
        tfUsersIndirect.setText(organization.getIndirectUsers());
        tfEmailOrganization.setText(organization.getEmail());
        tfAddress.setText(organization.getAddress());
        tfPhoneNumber.setText(organization.getPhoneNumber());
        cbState.getEditor().setText(organization.getState());
        cbCity.getEditor().setText(organization.getCity());
        cbSector.getEditor().setText(organization.getSector());
    }

    public void startComponent (){
        limitTextField(tfNameOrganization,100);
        prohibitNumberTextField(tfNameOrganization);

        limitTextField(tfUsersIndirect,100);
        prohibitNumberAllowSpecialCharTextField(tfUsersIndirect);

        limitTextField(tfUsersDirect,100);
        prohibitNumberAllowSpecialCharTextField(tfUsersDirect);

        limitTextField(cbSector.getEditor(),30);
        prohibitNumberTextField(cbSector.getEditor());

        limitTextField(cbState.getEditor(),30);
        prohibitNumberTextField(cbState.getEditor());

        limitTextField(cbCity.getEditor(),30);
        prohibitNumberTextField(cbCity.getEditor());

        limitTextField(tfEmailOrganization,50);
        prohibitSpacesTextField(tfEmailOrganization);

        limitTextField(tfAddress,100);
        prohibitSpacesTextField(tfAddress);

        limitTextField(tfPhoneNumber,10);
        prohibitWordTextField(tfPhoneNumber);
    }

    public void startComboBox () {
        organization = new LinkedOrganization();
        allCity = new ArrayList<>();
        allCity = organization.listCity();
        cbCity.getItems().addAll(allCity);

        allSector = new ArrayList<>();
        allSector = organization.listSector();
        cbSector.getItems().addAll(allSector);

        allState = new ArrayList<>();
        allState = organization.listState();
        cbState.getItems().addAll(allState);
    }

    public  void modify () {

    }
}
