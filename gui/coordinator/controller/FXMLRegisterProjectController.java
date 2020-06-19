package gui.coordinator.controller;

import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Project;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.ValidateProject;
import org.w3c.dom.Text;

/**
 * class FXMLRegisterProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLRegisterProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnRegisterProject;
    @FXML private Button btnCancelProject;
    @FXML private Button btnAddActivity;
    @FXML private Button btnDeleteActivity;
    @FXML private Button btnChooseLinkedOrganization;
    @FXML private Button btnChooseResponsibleProject;
    @FXML private TextField tfNameProject;
    @FXML private TextField tfMethodology;
    @FXML private TextField tfDuration;
    @FXML private TextField tfQuiantityPractitioners;
    @FXML private TextField tfDaysAndHours;
    @FXML private TextField tfLinkedOrganization;
    @FXML private TextField tfResponsibleProject;
    @FXML private ComboBox cbLapse;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivities;
    @FXML private TextArea taResponsabilities;
    @FXML private GridPane gpActivity;
    private  static String nameLinkedOrganization;
    private List<String> allLapse;
    private Project project;
    private ValidateProject validateProject;
    private int positionGrindActivity=0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startTextField();
        startComboBox();

    }

    public void behind () {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }

    public void cancelProject () {
        generateCancel("¿Seguro desea cancelar?",btnCancelProject,"/gui/coordinator/fxml/FXMLMenuCoordinator.fxml");
    }

    public void registerProject() {
        String message;
        if(!validateDataProject()) {
            Alert alertDataProject = new Alert(Alert.AlertType.NONE);
            alertDataProject.setAlertType(Alert.AlertType.WARNING);
            alertDataProject.setHeaderText("Enter correct data in the red fields");
            alertDataProject.setTitle("Warning");
            alertDataProject.show();
        }else{
            getDataProject();
            message = project.registerProject();
            Alert alertDataProject = new Alert(Alert.AlertType.NONE);
            alertDataProject.setAlertType(Alert.AlertType.INFORMATION);
            alertDataProject.setHeaderText(message);
            alertDataProject.setTitle("Information");
            alertDataProject.show();
        }
    }
    public void startTextField (){
        limitTextField(tfNameProject,50);
        prohibitNumberTextField(tfNameProject);

        limitTextField(tfMethodology,100);
        prohibitNumberTextField(tfMethodology);

        limitTextField(tfDuration,2);
        prohibitWordTextField(tfDuration);

        limitTextField(tfQuiantityPractitioners,1);
        prohibitWordTextField(tfQuiantityPractitioners);

        limitTextField(cbLapse.getEditor(),30);
        prohibitSpacesTextField(cbLapse.getEditor());

        limitTextArea(taDescription,255);
        prohibitSpacesTextArea(taDescription);

        limitTextArea(taObjectiveGeneral,255);
        prohibitSpacesTextArea(taObjectiveGeneral);

        limitTextArea(taObjectiveInmediate,255);
        prohibitSpacesTextArea(taObjectiveInmediate);

        limitTextArea(taObjectiveMediate,255);
        prohibitSpacesTextArea(taObjectiveMediate);

        limitTextArea(taResource,255);
        prohibitSpacesTextArea(taResource);

        limitTextArea(taActivities,255);
        prohibitSpacesTextArea(taActivities);

        limitTextArea(taResponsabilities,255);
        prohibitSpacesTextArea(taResponsabilities);

        limitTextField(tfDaysAndHours,150);
        prohibitSpacesTextField(tfDaysAndHours);
    }

    public void startComboBox () {
        project = new Project();
        allLapse = project.listLapse();
        cbLapse.getItems().addAll(allLapse);
    }

    public void getDataProject () {
        project.setNameProject(validateProject.deleteSpace(tfNameProject.getText()));
        project.setDescription(validateProject.deleteSpace(taDescription.getText()));
        project.setObjectiveGeneral(validateProject.deleteSpace(taObjectiveGeneral.getText()));
        project.setObjectiveInmediate(validateProject.deleteSpace(taObjectiveInmediate.getText()));
        project.setObjectiveMediate(validateProject.deleteSpace(taObjectiveMediate.getText()));
        project.setMethodology(validateProject.deleteSpace(tfMethodology.getText()));
        project.setResources(validateProject.deleteSpace(taResource.getText()));
        project.setActivities(validateProject.deleteSpace(taActivities.getText()));
        project.setResponsabilities(validateProject.deleteSpace(taResponsabilities.getText()));
        project.setLapse(validateProject.deleteSpace(cbLapse.getEditor().getText()));
        project.setStaffNumberCoordinator(8);
        int duration = Integer.parseInt(tfDuration.getText());
        project.setDuration(duration);
        int quiantityPractitioner = Integer.parseInt(tfQuiantityPractitioners.getText());
        project.setQuantityPractitioner(quiantityPractitioner);
    }

    public void assignLinkedOrganization (String nameLinkedOrganization){
        this.nameLinkedOrganization = nameLinkedOrganization;
    }

    public void assignResponsibleProject (String responsibleProject){
        tfResponsibleProject.setText(responsibleProject);
    }

    public void addActivity () {
        Label lMes = new Label();
        lMes.setText("Mes:  ");
        Label lActivity = new Label();
        lActivity.setText("Actividad:  ");
        TextField tfActivity = new TextField();
        TextField tfMes = new TextField();
        lMes.autosize();
        lActivity.autosize();
        tfActivity.autosize();
        tfMes.autosize();
        lMes.getStyleClass().add("details");
        tfMes.getStyleClass().add("details");
        lActivity.getStyleClass().add("details");
        tfActivity.getStyleClass().add("details");
        prohibitSpacesTextField(tfMes);
        prohibitNumberTextField(tfMes);
        prohibitSpacesTextField(tfActivity);
        prohibitNumberTextField(tfActivity);
        gpActivity.add(lMes,0,positionGrindActivity);
        gpActivity.add(tfMes,1,positionGrindActivity);
        gpActivity.add(lActivity,2,positionGrindActivity);
        gpActivity.add(tfActivity,3,positionGrindActivity);
        positionGrindActivity++;
    }

    public void deleteActivity () {
        positionGrindActivity--;
    }

    public void chooseResponsibleProject () {
        FXMLChooseResponsibleProjectController chooseResponsibleProject = new FXMLChooseResponsibleProjectController();
        chooseResponsibleProject.controllerSection("register");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseResponsibleProject.fxml"));
        Stage stage = new Stage();
        try {
            Parent root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        stage.setResizable(false);
        stage.show();
    }

    public void chooseLinkedOrganization () {
        FXMLChooseLinkedOrganizationController chooseLinkedOrganization = new FXMLChooseLinkedOrganizationController();
        chooseLinkedOrganization.controllerSection("register");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseLinkedOrganization.fxml"));
        Stage stage = new Stage();
        try {
            Parent root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        stage.setResizable(false);
        stage.show();
    }

    public boolean validateDataProject (){
        validateProject = new ValidateProject();
        boolean result = true;
        if(!validateProject.validateName(tfNameProject.getText()))  {
            tfNameProject.getStyleClass().add("error");
            result= false;
        }else{
            tfNameProject.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taDescription.getText())){
            taDescription.getStyleClass().add("error");
            result= false;
        }else{
            taDescription.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taObjectiveGeneral.getText())){
            taObjectiveGeneral.getStyleClass().add("error");
            result= false;
        }else{
            taObjectiveGeneral.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taObjectiveInmediate.getText())){
            taObjectiveInmediate.getStyleClass().add("error");
            result= false;
        }else{
            taObjectiveInmediate.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(taObjectiveMediate.getText()) ||
                !validateProject.validateTextArea(taObjectiveMediate.getText())){
            taObjectiveMediate.getStyleClass().add("error");
            result= false;
        }else {
            taObjectiveMediate.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfMethodology.getText()) ||
                !validateProject.validateMethology(tfMethodology.getText())){
            tfMethodology.getStyleClass().add("error");
            result= false;
        }else {
            tfMethodology.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taResource.getText())){
            taResource.getStyleClass().add("error");
            result= false;
        }else {
            taResource.getStyleClass().remove("error");
        }
        if(!validateProject.validateText(taActivities.getText())){
            taActivities.getStyleClass().add("error");
            result= false;
        }else {
            taActivities.getStyleClass().remove("error");
        }
        if(!validateProject.validateText(taResponsabilities.getText())){
            taResponsabilities.getStyleClass().add("error");
            result= false;
        }else {
            taResponsabilities.getStyleClass().remove("error");
        }
        if(!validateProject.validateLapse(cbLapse.getEditor().getText())){
            cbLapse.getStyleClass().add("error");
            result= false;
        }else {
            cbLapse.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfDuration.getText())){
            tfDuration.getStyleClass().add("error");
            result= false;
        }else {
            tfDuration.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfQuiantityPractitioners.getText())){
            tfQuiantityPractitioners.getStyleClass().add("error");
            result= false;
        }else{
            tfQuiantityPractitioners.getStyleClass().remove("error");
        }
        return result;
    }
}
