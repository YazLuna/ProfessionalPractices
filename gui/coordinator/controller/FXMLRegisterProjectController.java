package gui.coordinator.controller;

import dataaccess.ISchedulingActivitiesDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import domain.Project;
import logic.ValidateProject;
import dataaccess.Month;
import domain.SchedulingActivities;
import gui.FXMLGeneralController;

/**
 * class FXMLRegisterProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLRegisterProjectController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnBehind;
    @FXML private Button btnRegisterProject;
    @FXML private Button btnCancelProject;
    @FXML private TextField tfNameProject;
    @FXML private TextField tfMethodology;
    @FXML private TextField tfDuration;
    @FXML private TextField tfQuiantityPractitioners;
    @FXML private TextField tfDaysAndHours;
    @FXML private TextField tfLinkedOrganization;
    @FXML private TextField tfResponsibleProject;
    @FXML private TextField tfActivitySchedulingOne;
    @FXML private TextField tfActivitySchedulingTwo;
    @FXML private TextField tfActivitySchedulingThree;
    @FXML private TextField tfActivitySchedulingFour;
    @FXML private ComboBox cbMonthSchedulingOne;
    @FXML private ComboBox cbMonthSchedulingTwo;
    @FXML private ComboBox cbMonthSchedulingThree;
    @FXML private ComboBox cbMonthSchedulingFour;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivitiesAndFunctions;
    @FXML private TextArea taResponsabilities;
    @FXML private GridPane gpActivity;
    @FXML private Label lbTerm;
    private boolean isValidScheduling;
    private boolean isValidDataProject;
    private String emailResponsible;
    private String nameLinkedOrganization;
    private static int StaffNumberCoordinator;
    private List<String> allMonth;
    private List<SchedulingActivities> schedulingActivitiesList;
    private Project project;
    private SchedulingActivities schedulingActivities;
    private ValidateProject validateProject;
    private int positionGrindActivity=1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limitComponentProject();
        startComboBox();

    }

    public void behindMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }

    public void backMenu() {
        generateCancel("¿Seguro desea cancelar?",btnCancelProject,"/gui/coordinator/fxml/FXMLMenuCoordinator.fxml");
    }


    public void registerProject() {
        isValidScheduling=true;
        boolean isValidDataResponsible = validateDataProject();
        boolean isValidDataScheduling = validateSchedulingActivities();
        if(!isValidDataResponsible || !isValidDataScheduling) {
            generateAlert("Ingresar datos válidos");
        }else{
            Project = getDataProject();
            boolean isValidateRepeatResponsibleProject;
            isValidateRepeatResponsibleProject = responsible.validateRepeatResponsibleProject(responsible.getEmail());
            if(!isValidateRepeatResponsibleProject){
                boolean isRegisterResponsibleProject;
                isRegisterResponsibleProject = responsible.registerResponsibleProject(responsible);
                if(isRegisterResponsibleProject){
                    generateInformation("El responsable del proyecto se registro exitosamente");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                } else {
                    generateError("El responsable del proyecto no pudo registrarse");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegister);
                }
            }else  {
                generateError("Existe un responsable del proyecto con el mismo correo electrónico registrado");
            }
        }
    }

    public void limitComponentProject(){
        limitTextField(tfNameProject,50);
        prohibitNumberTextField(tfNameProject);

        limitTextField(tfMethodology,100);
        prohibitNumberTextField(tfMethodology);

        limitTextField(tfDuration,3);
        prohibitWordTextField(tfDuration);

        limitTextField(tfQuiantityPractitioners,1);
        prohibitWordTextField(tfQuiantityPractitioners);

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

        limitTextArea(taActivitiesAndFunctions,255);
        prohibitSpacesTextArea(taActivitiesAndFunctions);

        limitTextArea(taResponsabilities,255);
        prohibitSpacesTextArea(taResponsabilities);

        limitTextField(tfDaysAndHours,150);
        prohibitSpacesTextField(tfDaysAndHours);

        limitTextField(tfActivitySchedulingOne,255);
        prohibitSpacesTextField(tfActivitySchedulingOne);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingOne);

        limitTextField(tfActivitySchedulingTwo,255);
        prohibitSpacesTextField(tfActivitySchedulingTwo);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingTwo);

        limitTextField(tfActivitySchedulingThree,255);
        prohibitSpacesTextField(tfActivitySchedulingThree);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingThree);

        limitTextField(tfActivitySchedulingFour,255);
        prohibitSpacesTextField(tfActivitySchedulingFour);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingFour);

        String term = createTerm();
        lbTerm.setText(term);
    }

    public void startComboBox () {
        schedulingActivities = new SchedulingActivities();
        addMonth();
        cbMonthSchedulingOne.getItems().addAll(allMonth);
        cbMonthSchedulingTwo.getItems().addAll(allMonth);
        cbMonthSchedulingThree.getItems().addAll(allMonth);
        cbMonthSchedulingFour.getItems().addAll(allMonth);
    }

    public void addMonth () {
        allMonth = new ArrayList<>();
        allMonth.add(Month.JANUARY.getMonth());
        allMonth.add(Month.FEBRUARY.getMonth());
        allMonth.add(Month.MARCH.getMonth());
        allMonth.add(Month.APRIL.getMonth());
        allMonth.add(Month.MAY.getMonth());
        allMonth.add(Month.JUNE.getMonth());
        allMonth.add(Month.JULY.getMonth());
        allMonth.add(Month.AUGUST.getMonth());
        allMonth.add(Month.SEPTEMBER.getMonth());
        allMonth.add(Month.OCTOBER.getMonth());
        allMonth.add(Month.NOVEMBER.getMonth());
        allMonth.add(Month.DECEMBER.getMonth());
    }

    public void getDataProject () {
        project = new Project();
        project.setNameProject(validateProject.deleteSpace(tfNameProject.getText()));
        project.setDescription(validateProject.deleteSpace(taDescription.getText()));
        project.setObjectiveGeneral(validateProject.deleteSpace(taObjectiveGeneral.getText()));
        project.setObjectiveInmediate(validateProject.deleteSpace(taObjectiveInmediate.getText()));
        project.setObjectiveMediate(validateProject.deleteSpace(taObjectiveMediate.getText()));
        project.setMethodology(validateProject.deleteSpace(tfMethodology.getText()));
        project.setResources(validateProject.deleteSpace(taResource.getText()));
        project.setActivitiesAndFunctions(validateProject.deleteSpace(taActivitiesAndFunctions.getText()));
        project.setResponsabilities(validateProject.deleteSpace(taResponsabilities.getText()));
        project.getOrganization().setName(nameLinkedOrganization);
        project.getResponsible().setEmail(emailResponsible);
        project.setTerm(lbTerm.getText());
        project.setStaffNumberCoordinator(StaffNumberCoordinator);
        int duration = Integer.parseInt(tfDuration.getText());
        project.setDuration(duration);
        int quiantityPractitioner = Integer.parseInt(tfQuiantityPractitioners.getText());
        project.setQuiantityPractitioner(quiantityPractitioner);

    }

    public void getSchedulingActivities (TextField tfActivity, TextField tfMonth){
        SchedulingActivities schedulingActivities = new SchedulingActivities();
        schedulingActivities.setMonth(tfMonth.getText());
        schedulingActivities.setActivity(validateProject.deleteSpace(tfActivity.getText()));
        schedulingActivitiesList.add(schedulingActivities);
    }

    public void chooseResponsibleProject () {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseResponsibleProject.fxml"));
        Stage stage = new Stage();
        try {
            Parent root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        FXMLChooseResponsibleProjectController chooseResponsibleProject = new FXMLChooseResponsibleProjectController();
        chooseResponsibleProject.controllerSection("register");
        String nameResponsible = chooseResponsibleProject.getNameResponsibleProject();
        emailResponsible = chooseResponsibleProject.getEmailResponsibleProject();
        tfResponsibleProject.setText(nameResponsible);
    }

    public void chooseLinkedOrganization () {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLChooseLinkedOrganization.fxml"));
        Stage stage = new Stage();
        try {
            Parent root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        FXMLChooseLinkedOrganizationController chooseLinkedOrganization = new FXMLChooseLinkedOrganizationController();
        chooseLinkedOrganization.controllerSection("register");
        nameLinkedOrganization = chooseLinkedOrganization.getNameLinkedOrganization();
        tfLinkedOrganization.setText(nameLinkedOrganization);
    }

    public void validateSchedulingActivities (TextField tfActivitySchedulingTwo, ComboBox cbMonthSchedulingTwo){
        if(!tfActivitySchedulingTwo.getText().trim().isEmpty() && !cbMonthSchedulingTwo.getEditor().getText().trim().isEmpty()){
            if(validateProject.validateTextArea(tfActivitySchedulingTwo.getText()))  {
                tfActivitySchedulingTwo.getStyleClass().remove("error");
                getSchedulingActivities(tfActivitySchedulingTwo,cbMonthSchedulingTwo.getEditor());
            }else{
                tfActivitySchedulingTwo.getStyleClass().add("error");
                isValidScheduling= false;
            }
        }else{
            if(!tfActivitySchedulingTwo.getText().trim().isEmpty() && cbMonthSchedulingTwo.getEditor().getText().trim().isEmpty()) {
                cbMonthSchedulingTwo.getStyleClass().add("error");
                isValidScheduling= false;
            }else{
                if(tfActivitySchedulingTwo.getText().trim().isEmpty() && !cbMonthSchedulingTwo.getEditor().getText().trim().isEmpty()) {
                    tfActivitySchedulingTwo.getStyleClass().add("error");
                    isValidScheduling = false;
                }
            }
        }
    }

    public void validateName (){
        tfNameProject.getStyleClass().remove("error");
        tfNameProject.getStyleClass().remove("ok");
        boolean isValidName = validateProject.validateName(tfNameProject.getText());
        if(isValidName)  {
            tfNameProject.getStyleClass().add("ok");
        }else{
            tfNameProject.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateDescription (){
        taDescription.getStyleClass().remove("error");
        taDescription.getStyleClass().remove("ok");
        boolean isValidDescription = validateProject.validateTextArea(taDescription.getText());
        if(isValidDescription){
            taDescription.getStyleClass().add("ok");
        }else{
            taDescription.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateObjectiveGeneral () {
        taObjectiveGeneral.getStyleClass().remove("error");
        taObjectiveGeneral.getStyleClass().remove("error");
        boolean isValidObjectiveGeneral = validateProject.validateTextArea(taObjectiveGeneral.getText());
        if(isValidObjectiveGeneral){
            taObjectiveGeneral.getStyleClass().add("ok");
        }else{
            taObjectiveGeneral.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateObjectiveInmediate () {
        taObjectiveInmediate.getStyleClass().remove("error");
        taObjectiveInmediate.getStyleClass().remove("ok");
        boolean isValidObjectiveInmediate = validateProject.validateTextArea(taObjectiveInmediate.getText());
        if(isValidObjectiveInmediate){
            taObjectiveInmediate.getStyleClass().add("ok");
        }else{
            taObjectiveInmediate.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateObjectiveMediate () {
        taObjectiveMediate.getStyleClass().remove("error");
        taObjectiveMediate.getStyleClass().remove("ok");
        boolean isValidObjectiveMediate = !validateProject.validateTextArea(taObjectiveMediate.getText());
        if(isValidObjectiveMediate){
            taObjectiveMediate.getStyleClass().add("ok");
        }else {
            taObjectiveMediate.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateDataProject (){
        validateProject = new ValidateProject();


        if(!validateProject.validateNotEmpty(tfMethodology.getText()) ||
                !validateProject.validateMethology(tfMethodology.getText())){
            tfMethodology.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            tfMethodology.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taResource.getText())){
            taResource.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            taResource.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taActivitiesAndFunctions.getText())){
            taActivitiesAndFunctions.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            taActivitiesAndFunctions.getStyleClass().remove("error");
        }
        if(!validateProject.validateTextArea(taResponsabilities.getText())){
            taResponsabilities.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            taResponsabilities.getStyleClass().remove("error");
        }
        if(!validateProject.validateText(tfDaysAndHours.getText())){
            tfDaysAndHours.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            tfDaysAndHours.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfDuration.getText())){
            int duration = Integer.parseInt(tfDuration.getText());

            tfDuration.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            tfDuration.getStyleClass().remove("error");
        }
        if(!validateProject.validateNotEmpty(tfQuiantityPractitioners.getText())){
            tfQuiantityPractitioners.getStyleClass().add("error");
            isValidDataProject= false;
        }else{
            tfQuiantityPractitioners.getStyleClass().remove("error");
        }
        validateSchedulingActivities(tfActivitySchedulingTwo,cbMonthSchedulingTwo);
        validateSchedulingActivities(tfActivitySchedulingOne,cbMonthSchedulingOne);
        validateSchedulingActivities(tfActivitySchedulingThree,cbMonthSchedulingThree);
        validateSchedulingActivities(tfActivitySchedulingFour,cbMonthSchedulingFour);
        return isValidDataProject;
    }
}
