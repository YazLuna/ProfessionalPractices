package gui.coordinator.controller;

import domain.*;
import domain.Number;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
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

import logic.ValidateProject;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limitComponentProject();
        startComboBox();

    }

    public void behindMenu() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnBehind);
    }

    public void backMenu() {
        generateConfirmationCancel("¿Seguro desea cancelar?",btnCancelProject,"/gui/coordinator/fxml/FXMLMenuCoordinator.fxml");
    }


    public void registerProject() {
        validateDataProject();
        if(isValidDataProject  && isValidScheduling) {
            int isValidateRepeatProject;
            getDataProject();
            isValidateRepeatProject = Project.validateRepeatProject(project.getNameProject());
            if(isValidateRepeatProject == Search.NOTFOUND.getValue()){
                boolean isRegisterProject;
                isRegisterProject = Project.registerProject(project);
                if(isRegisterProject){
                    generateInformation("El proyecto se registro exitosamente");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegisterProject);
                } else {
                    generateError("El proyecto no pudo registrarse");
                    openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnRegisterProject);
                }
            }else  {
                if(isValidateRepeatProject == Search.FOUND.getValue()) {
                    generateError("Existe un proyecto con el mismo nombre registrado");
                }else {
                    generateError("No se puso obtener la información de la base de datos");
                }
            }
        }else {
            generateAlert("Ingresar datos válidos");
            schedulingActivitiesList.clear();
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

        limitTextArea(taDescription,300);
        prohibitSpacesTextArea(taDescription);

        limitTextArea(taObjectiveGeneral,300);
        prohibitSpacesTextArea(taObjectiveGeneral);

        limitTextArea(taObjectiveInmediate,300);
        prohibitSpacesTextArea(taObjectiveInmediate);

        limitTextArea(taObjectiveMediate,300);
        prohibitSpacesTextArea(taObjectiveMediate);

        limitTextArea(taResource,300);
        prohibitSpacesTextArea(taResource);

        limitTextArea(taActivitiesAndFunctions,300);
        prohibitSpacesTextArea(taActivitiesAndFunctions);

        limitTextArea(taResponsabilities,300);
        prohibitSpacesTextArea(taResponsabilities);

        limitTextField(tfDaysAndHours,150);
        prohibitSpacesTextField(tfDaysAndHours);

        limitTextField(tfActivitySchedulingOne,300);
        prohibitSpacesTextField(tfActivitySchedulingOne);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingOne);

        limitTextField(tfActivitySchedulingTwo,300);
        prohibitSpacesTextField(tfActivitySchedulingTwo);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingTwo);

        limitTextField(tfActivitySchedulingThree,300);
        prohibitSpacesTextField(tfActivitySchedulingThree);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingThree);

        limitTextField(tfActivitySchedulingFour,300);
        prohibitSpacesTextField(tfActivitySchedulingFour);
        prohibitNumberAllowSpecialCharTextField(tfActivitySchedulingFour);

        cbMonthSchedulingOne.setValue(Month.NOTMONTH.getMonth());
        cbMonthSchedulingTwo.setValue(Month.NOTMONTH.getMonth());
        cbMonthSchedulingThree.setValue(Month.NOTMONTH.getMonth());
        cbMonthSchedulingFour.setValue(Month.NOTMONTH.getMonth());
        String term = createTerm();
        lbTerm.setText(term);
    }

    public void startComboBox () {
        schedulingActivities = new SchedulingActivities();
        createListMonth();
        cbMonthSchedulingOne.getItems().addAll(allMonth);
        cbMonthSchedulingTwo.getItems().addAll(allMonth);
        cbMonthSchedulingThree.getItems().addAll(allMonth);
        cbMonthSchedulingFour.getItems().addAll(allMonth);
    }

    public void createListMonth() {
        allMonth = new ArrayList<>();
        allMonth.add(Month.NOTMONTH.getMonth());
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
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganization.setName(nameLinkedOrganization);
        project.setOrganization(linkedOrganization);
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProject.setEmail(emailResponsible);
        project.setResponsible(responsibleProject);
        project.setTerm(lbTerm.getText());
        project.setStaffNumberCoordinator(StaffNumberCoordinator);
        int duration = Integer.parseInt(tfDuration.getText());
        project.setDuration(duration);
        int quiantityPractitioner = Integer.parseInt(tfQuiantityPractitioners.getText());
        project.setQuiantityPractitioner(quiantityPractitioner);
        project.setSchedulingActivitiesProject(schedulingActivitiesList);
    }

    public void getSchedulingActivities (TextField tfActivity, String month){
        SchedulingActivities schedulingActivities = new SchedulingActivities();
        schedulingActivities.setMonth(month);
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

    public void validateSchedulingActivitiesOne (){
        if(tfActivitySchedulingOne.getText().length() !=Number.ZERO.getNumber() &&
                !cbMonthSchedulingOne.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())){
            boolean isValidAcitvity = validateProject.validateTextArea(tfActivitySchedulingOne.getText());
            if(isValidAcitvity)  {
                tfActivitySchedulingOne.getStyleClass().add("ok");
                cbMonthSchedulingOne.getStyleClass().add("ok");
                getSchedulingActivities(tfActivitySchedulingOne,cbMonthSchedulingOne.getSelectionModel().getSelectedItem().toString());
            }else{
                tfActivitySchedulingOne.getStyleClass().add("error");
                isValidScheduling= false;
            }
        }else{
            if(tfActivitySchedulingOne.getText().length()!=Number.ZERO.getNumber() &&
                    cbMonthSchedulingOne.getEditor().getText().equals(Month.NOTMONTH.getMonth())) {
                cbMonthSchedulingOne.getStyleClass().add("error");
                isValidScheduling= false;
            }else{
                if(tfActivitySchedulingOne.getText().length()==Number.ZERO.getNumber() &&
                        !cbMonthSchedulingOne.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())) {
                    tfActivitySchedulingOne.getStyleClass().add("error");
                    isValidScheduling = false;
                }
            }
        }
    }

    public void validateSchedulingActivitiesTwo (){
        if(tfActivitySchedulingTwo.getText().length() !=Number.ZERO.getNumber() &&
                !cbMonthSchedulingTwo.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())){
            boolean isValidAcitvity = validateProject.validateTextArea(tfActivitySchedulingTwo.getText());
            if(isValidAcitvity)  {
                tfActivitySchedulingTwo.getStyleClass().add("ok");
                cbMonthSchedulingTwo.getStyleClass().add("ok");
                getSchedulingActivities(tfActivitySchedulingTwo,cbMonthSchedulingTwo.getSelectionModel().getSelectedItem().toString());
            }else{
                tfActivitySchedulingTwo.getStyleClass().add("error");
                isValidScheduling= false;
            }
        }else{
            if(tfActivitySchedulingTwo.getText().length()!=Number.ZERO.getNumber() &&
                    cbMonthSchedulingTwo.getEditor().getText().equals(Month.NOTMONTH.getMonth())) {
                cbMonthSchedulingTwo.getStyleClass().add("error");
                isValidScheduling= false;
            }else{
                if(tfActivitySchedulingTwo.getText().length()==Number.ZERO.getNumber() &&
                        !cbMonthSchedulingTwo.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())) {
                    tfActivitySchedulingTwo.getStyleClass().add("error");
                    isValidScheduling = false;
                }
            }
        }
    }

    public void validateSchedulingActivitiesThree (){
        if(tfActivitySchedulingThree.getText().length() !=Number.ZERO.getNumber() &&
                !cbMonthSchedulingThree.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())){
            boolean isValidAcitvity = validateProject.validateTextArea(tfActivitySchedulingThree.getText());
            if(isValidAcitvity)  {
                tfActivitySchedulingThree.getStyleClass().add("ok");
                cbMonthSchedulingThree.getStyleClass().add("ok");
                getSchedulingActivities(tfActivitySchedulingThree,cbMonthSchedulingThree.getSelectionModel().getSelectedItem().toString());
            }else{
                tfActivitySchedulingThree.getStyleClass().add("error");
                isValidScheduling= false;
            }
        }else{
            if(tfActivitySchedulingThree.getText().length()!=Number.ZERO.getNumber() &&
                    cbMonthSchedulingThree.getEditor().getText().equals(Month.NOTMONTH.getMonth())) {
                cbMonthSchedulingThree.getStyleClass().add("error");
                isValidScheduling= false;
            }else{
                if(tfActivitySchedulingThree.getText().length()==Number.ZERO.getNumber() &&
                        !cbMonthSchedulingThree.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())) {
                    tfActivitySchedulingThree.getStyleClass().add("error");
                    isValidScheduling = false;
                }
            }
        }
    }

    public void validateSchedulingActivitiesFour (){
        if(tfActivitySchedulingFour.getText().length() !=Number.ZERO.getNumber() &&
                !cbMonthSchedulingFour.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())){
            boolean isValidAcitvity = validateProject.validateTextArea(tfActivitySchedulingFour.getText());
            if(isValidAcitvity)  {
                tfActivitySchedulingFour.getStyleClass().add("ok");
                cbMonthSchedulingFour.getStyleClass().add("ok");
                getSchedulingActivities(tfActivitySchedulingFour,cbMonthSchedulingFour.getSelectionModel().getSelectedItem().toString());
            }else{
                tfActivitySchedulingFour.getStyleClass().add("error");
                isValidScheduling= false;
            }
        }else{
            if(tfActivitySchedulingFour.getText().length()!=Number.ZERO.getNumber() &&
                    cbMonthSchedulingFour.getEditor().getText().equals(Month.NOTMONTH.getMonth())) {
                cbMonthSchedulingFour.getStyleClass().add("error");
                isValidScheduling= false;
            }else{
                if(tfActivitySchedulingFour.getText().length()==Number.ZERO.getNumber() &&
                        !cbMonthSchedulingFour.getSelectionModel().getSelectedItem().toString().equals(Month.NOTMONTH.getMonth())) {
                    tfActivitySchedulingFour.getStyleClass().add("error");
                    isValidScheduling = false;
                }
            }
        }
    }

    public void validateName (){
        boolean isValidName = validateProject.validateName(tfNameProject.getText());
        if(isValidName)  {
            tfNameProject.getStyleClass().add("ok");
        }else{
            tfNameProject.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateDescription (){
        boolean isValidDescription = validateProject.validateTextArea(taDescription.getText());
        if(isValidDescription){
            taDescription.getStyleClass().add("ok");
        }else{
            taDescription.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateObjectiveGeneral () {
        boolean isValidObjectiveGeneral = validateProject.validateTextArea(taObjectiveGeneral.getText());
        if(isValidObjectiveGeneral){
            taObjectiveGeneral.getStyleClass().add("ok");
        }else{
            taObjectiveGeneral.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateObjectiveInmediate () {
        boolean isValidObjectiveInmediate = validateProject.validateTextArea(taObjectiveInmediate.getText());
        if(isValidObjectiveInmediate){
            taObjectiveInmediate.getStyleClass().add("ok");
        }else{
            taObjectiveInmediate.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateObjectiveMediate () {
        boolean isValidObjectiveMediate = validateProject.validateTextArea(taObjectiveMediate.getText());
        if(isValidObjectiveMediate){
            taObjectiveMediate.getStyleClass().add("ok");
        }else {
            taObjectiveMediate.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateMethodology (){
        boolean isValidMethodology = validateProject.validateMethology(tfMethodology.getText());
        if(isValidMethodology){
            tfMethodology.getStyleClass().add("ok");
        }else {
            tfMethodology.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateResource (){
        boolean isValidResource =validateProject.validateTextArea(taResource.getText());
        if(isValidResource){
            taResource.getStyleClass().add("ok");
        }else {
            taResource.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateActivitiesAndFunctions (){
        boolean isValidActivitiesAndFunctions =validateProject.validateTextArea(taActivitiesAndFunctions.getText());
        if(isValidActivitiesAndFunctions){
            taActivitiesAndFunctions.getStyleClass().add("ok");
        }else {
            taActivitiesAndFunctions.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateResponsabilities (){
        boolean isValidResponsabilities =validateProject.validateTextArea(taResponsabilities.getText());
        if(isValidResponsabilities){
            taResponsabilities.getStyleClass().add("ok");
        }else {
            taResponsabilities.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateDaysAndHours (){
        boolean isValidDaysAndHours = validateProject.validateText(tfDaysAndHours.getText());
        if(isValidDaysAndHours){
            tfDaysAndHours.getStyleClass().add("ok");
        }else {
            tfDaysAndHours.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateDuration () {
        boolean isValidDuration = validateProject.validateNotEmpty(tfDuration.getText());
        if(isValidDuration){
            int duration = Integer.parseInt(tfDuration.getText());
            isValidDuration = validateProject.validateDuration(duration);
            if(isValidDuration){
                tfDuration.getStyleClass().add("ok");
            }else {
                tfDuration.getStyleClass().add("error");
                isValidDataProject= false;
            }
        }else {
            tfDuration.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateQuiantityPractitioners (){
        boolean isValidQuiantityPractitioners = validateProject.validateNotEmpty(tfQuiantityPractitioners.getText());
        if(isValidQuiantityPractitioners){
            int quiantityPractitioners = Integer.parseInt(tfQuiantityPractitioners.getText());
            isValidQuiantityPractitioners = validateProject.validateQuiantityPractitioner(quiantityPractitioners);
            if(isValidQuiantityPractitioners){
                tfQuiantityPractitioners.getStyleClass().add("ok");
            }else {
                tfQuiantityPractitioners.getStyleClass().add("error");
                isValidDataProject= false;
            }
        }else{
            tfQuiantityPractitioners.getStyleClass().add("error");
            isValidDataProject= false;
        }
    }

    public void validateLinkedOrganization ( ){
        if(tfLinkedOrganization.getText().trim().isEmpty()){
            tfLinkedOrganization.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            tfLinkedOrganization.getStyleClass().add("ok");
        }
    }

    public void validateResponsibleProject ( ){
        if(tfResponsibleProject.getText().trim().isEmpty()){
            tfResponsibleProject.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            tfResponsibleProject.getStyleClass().add("ok");
        }
    }

    public void removeStyleClass (){
        tfNameProject.getStyleClass().remove("error");
        tfNameProject.getStyleClass().remove("ok");
        taDescription.getStyleClass().remove("error");
        taDescription.getStyleClass().remove("ok");
        taObjectiveGeneral.getStyleClass().remove("error");
        taObjectiveGeneral.getStyleClass().remove("ok");
        taObjectiveInmediate.getStyleClass().remove("error");
        taObjectiveInmediate.getStyleClass().remove("ok");
        taObjectiveMediate.getStyleClass().remove("error");
        taObjectiveMediate.getStyleClass().remove("ok");
        tfMethodology.getStyleClass().remove("error");
        tfMethodology.getStyleClass().remove("ok");
        taResource.getStyleClass().remove("error");
        taResource.getStyleClass().remove("ok");
        taActivitiesAndFunctions.getStyleClass().remove("error");
        taActivitiesAndFunctions.getStyleClass().remove("ok");
        taResponsabilities.getStyleClass().remove("error");
        taResponsabilities.getStyleClass().remove("ok");
        tfDaysAndHours.getStyleClass().remove("error");
        tfDaysAndHours.getStyleClass().remove("ok");
        tfDuration.getStyleClass().remove("error");
        tfDuration.getStyleClass().remove("ok");
        tfQuiantityPractitioners.getStyleClass().remove("error");
        tfQuiantityPractitioners.getStyleClass().remove("ok");
        tfLinkedOrganization.getStyleClass().remove("error");
        tfLinkedOrganization.getStyleClass().remove("ok");
        tfResponsibleProject.getStyleClass().remove("error");
        tfResponsibleProject.getStyleClass().remove("ok");

        tfActivitySchedulingOne.getStyleClass().remove("error");
        tfActivitySchedulingOne.getStyleClass().remove("ok");
        tfActivitySchedulingTwo.getStyleClass().remove("error");
        tfActivitySchedulingTwo.getStyleClass().remove("ok");
        tfActivitySchedulingThree.getStyleClass().remove("error");
        tfActivitySchedulingThree.getStyleClass().remove("ok");
        tfActivitySchedulingFour.getStyleClass().remove("error");
        tfActivitySchedulingFour.getStyleClass().remove("ok");
        cbMonthSchedulingOne.getStyleClass().remove("error");
        cbMonthSchedulingOne.getStyleClass().remove("ok");
        cbMonthSchedulingTwo.getStyleClass().remove("error");
        cbMonthSchedulingTwo.getStyleClass().remove("ok");
        cbMonthSchedulingThree.getStyleClass().remove("error");
        cbMonthSchedulingThree.getStyleClass().remove("ok");
        cbMonthSchedulingFour.getStyleClass().remove("error");
        cbMonthSchedulingFour.getStyleClass().remove("ok");
    }

    public void validateDataProject (){
        isValidScheduling=true;
        isValidDataProject=true;
        validateProject = new ValidateProject();
        removeStyleClass();
        validateName();
        validateDescription();
        validateObjectiveGeneral();
        validateObjectiveInmediate();
        validateObjectiveMediate();
        validateActivitiesAndFunctions();
        validateDaysAndHours();
        validateResource();
        validateMethodology();
        validateResponsabilities();
        validateDuration();
        validateQuiantityPractitioners();
        validateLinkedOrganization();
        validateResponsibleProject();
        validateSchedulingActivitiesOne();
        validateSchedulingActivitiesTwo();
        validateSchedulingActivitiesThree();
        validateSchedulingActivitiesFour();
    }
}
