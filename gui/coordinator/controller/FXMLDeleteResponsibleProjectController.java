package gui.coordinator.controller;

import domain.ResponsibleProject;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLDeleteResponsibleProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnDelete;
    @FXML private Button btnCancel;
    @FXML private Label lbNameResponsible;
    @FXML private Label lbLastNameResponsible;
    @FXML private Label lbEmail;
    @FXML private Label lbCharge;
    private static String emailResponsibleProject;
    private ResponsibleProject responsible;

    public void initialize(URL url, ResourceBundle rb) {
        startComponent();
    }

    public void assignEmailResponsible (String emailResponsibleProject){
        this.emailResponsibleProject = emailResponsibleProject;
    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml",btnBehind);
    }

    public void startComponent () {
        responsible = new ResponsibleProject();
        responsible.setEmail(emailResponsibleProject);
        responsible = responsible.getResponsible();
        lbNameResponsible.setText(responsible.getName());
        lbLastNameResponsible.setText(responsible.getLastName());
        lbEmail.setText(responsible.getEmail());
        lbCharge.setText(responsible.getCharge());
    }

    public void delete (){
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro desea eliminar el Responsable del proyecto?", YES, NO);
        cancel.setTitle("Confirmación Eliminar");
        Optional<ButtonType> action = cancel.showAndWait();
        if (action.orElse(NO) == YES) {
            boolean isDeleteResponsibleProject;
            isDeleteResponsibleProject  = responsible.deleteResponsibleProject();
            if(!isDeleteResponsibleProject) {
                generateError("El responsable del proyecto no pudo eliminarse");
                openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml",btnDelete);
            }else {
                generateInformation("El Responsable del proyecto se eliminó exitosamente");
                boolean areResponsible;
                areResponsible = responsible.thereAreResponsibleProjectAvailable();
                if (!areResponsible) {
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnDelete);
                } else {
                    openWindowGeneral("/gui/coordinator/fxml/FXMLListResponsibleProject.fxml",btnDelete);
                }
            }
        }
    }

    public void cancel(){
        generateConfirmationCancel("¿Seguro desea cancelar el proceso?",btnCancel,"/gui/coordinator/fxml/FXMLListResponsibleProject.fxml");
    }
}