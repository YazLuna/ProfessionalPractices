
package gui.coordinator.controller;

import domain.*;
import domain.Number;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.ValidateProject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class FXMLUpdateProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLUpdateProjectController extends FXMLGeneralController implements Initializable {
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
    private String nameCompleteResponsibleProject;
    private List<String> datesUpdate;
    private Project project;
    private Project projectEdit;
    private ValidateProject validateProject;
    private static String nameProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limitComponentProject();
        startComponentResponsibleProject();
    }

    public void behindListProject() {
        openWindowGeneral("/gui/coordinator/fxml/FXMLLisProject.fxml",btnBehind);
    }

    public void backListProject() {
        generateConfirmationCancel("¿Seguro desea cancelar?",btnCancelProject,"/gui/coordinator/fxml/FXMLListProject.fxml");
    }

    public void ModifyProject() {
        validateDataProject();
        if(datesUpdate.size()!=Number.ZERO.getNumber()) {
            if (isValidDataProject && isValidScheduling) {
                int isValidateRepeatProject;
                isValidateRepeatProject = Project.validateRepeatProject(projectEdit.getNameProject());
                if(isValidateRepeatProject == Search.NOTFOUND.getValue()){
                    boolean isModifyProject;
                    isModifyProject = Project.modifyProject(projectEdit,datesUpdate);
                    if(isModifyProject){
                        generateInformation("El proyecto se modifico exitosamente");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml",btnRegisterProject);
                    } else {
                        generateError("El proyecto no pudo modificarse");
                        openWindowGeneral("/gui/coordinator/fxml/FXMLListProject.fxml",btnRegisterProject);
                    }
                }else  {
                    if(isValidateRepeatProject == Search.FOUND.getValue()) {
                        generateError("Existe un proyecto con el mismo nombre registrado");
                    }else {
                        generateError("No se puso obtener la información de la base de datos");
                    }
                }
            } else {
                generateAlert("Ingresar datos válidos");
                datesUpdate.clear();
            }
        }else  {
            removeStyleClass();
            generateAlert("Modifique datos");
        }
    }

    void startComponentResponsibleProject (){
        project = new Project();
        project = Project.getProject(nameProject);
        tfNameProject.setText(project.getNameProject());
        tfMethodology.setText(project.getMethodology());
        tfDuration.setText(Integer. toString(project.getDuration()));
        tfQuiantityPractitioners.setText(Integer. toString(project.getQuiantityPractitioner()));
        tfDaysAndHours.setText(project.getDaysHours());
        tfLinkedOrganization.setText(project.getOrganization().getName());
        nameCompleteResponsibleProject = project.getResponsible().getName() +" "+project.getResponsible().getLastName();
        tfResponsibleProject.setText(nameCompleteResponsibleProject);
        taDescription.setText(project.getDescription());
        taObjectiveGeneral.setText(project.getObjectiveGeneral());
        taObjectiveInmediate.setText(project.getObjectiveInmediate());
        taObjectiveMediate.setText(project.getObjectiveMediate());
        taResource.setText(project.getResources());
        taActivitiesAndFunctions.setText(project.getActivitiesAndFunctions());
        taResponsabilities.setText(project.getResponsabilities());
        tfActivitySchedulingOne.setEditable(false);
        cbMonthSchedulingOne.setEditable(false);
        tfActivitySchedulingTwo.setEditable(false);
        cbMonthSchedulingTwo.setEditable(false);
        tfActivitySchedulingThree.setEditable(false);
        cbMonthSchedulingThree.setEditable(false);
        tfActivitySchedulingFour.setEditable(false);
        cbMonthSchedulingFour.setEditable(false);
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

        String term = createTerm();
        lbTerm.setText(term);
    }

    public void assignNameProject (String nameProject){
        this.nameProject = nameProject;
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

    public void validateName (){
        boolean isValidName = validateProject.validateName(tfNameProject.getText());
        if(isValidName)  {
            tfNameProject.getStyleClass().add("ok");
            projectEdit.setNameProject(validateProject.deleteSpace(tfNameProject.getText()));
        }else{
            tfNameProject.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("NameProject");
    }

    public void validateDescription (){
        boolean isValidDescription = validateProject.validateTextArea(taDescription.getText());
        if(isValidDescription){
            taDescription.getStyleClass().add("ok");
            projectEdit.setDescription(validateProject.deleteSpace(taDescription.getText()));
        }else{
            taDescription.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("Description");
    }

    public void validateObjectiveGeneral () {
        boolean isValidObjectiveGeneral = validateProject.validateTextArea(taObjectiveGeneral.getText());
        if(isValidObjectiveGeneral){
            taObjectiveGeneral.getStyleClass().add("ok");
            projectEdit.setObjectiveGeneral(validateProject.deleteSpace(taObjectiveGeneral.getText()));
        }else{
            taObjectiveGeneral.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("ObjectiveGeneral");
    }

    public void validateObjectiveInmediate () {
        boolean isValidObjectiveInmediate = validateProject.validateTextArea(taObjectiveInmediate.getText());
        if(isValidObjectiveInmediate){
            taObjectiveInmediate.getStyleClass().add("ok");
            projectEdit.setObjectiveInmediate(validateProject.deleteSpace(taObjectiveInmediate.getText()));
        }else{
            taObjectiveInmediate.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("ObjectiveInmediate");
    }

    public void validateObjectiveMediate () {
        boolean isValidObjectiveMediate = validateProject.validateTextArea(taObjectiveMediate.getText());
        if(isValidObjectiveMediate){
            taObjectiveMediate.getStyleClass().add("ok");
            projectEdit.setObjectiveMediate(validateProject.deleteSpace(taObjectiveMediate.getText()));
        }else {
            taObjectiveMediate.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("ObjectiveMediate");
    }

    public void validateMethodology (){
        boolean isValidMethodology = validateProject.validateMethology(tfMethodology.getText());
        if(isValidMethodology){
            tfMethodology.getStyleClass().add("ok");
            projectEdit.setMethodology(validateProject.deleteSpace(tfMethodology.getText()));
        }else {
            tfMethodology.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("Methodology");
    }

    public void validateResource (){
        boolean isValidResource =validateProject.validateTextArea(taResource.getText());
        if(isValidResource){
            taResource.getStyleClass().add("ok");
            projectEdit.setResources(validateProject.deleteSpace(taResource.getText()));
        }else {
            taResource.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("Resources");
    }

    public void validateActivitiesAndFunctions (){
        boolean isValidActivitiesAndFunctions =validateProject.validateTextArea(taActivitiesAndFunctions.getText());
        if(isValidActivitiesAndFunctions){
            taActivitiesAndFunctions.getStyleClass().add("ok");
            projectEdit.setActivitiesAndFunctions(validateProject.deleteSpace(taActivitiesAndFunctions.getText()));
        }else {
            taActivitiesAndFunctions.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("ActivitiesAndFunctions");
    }

    public void validateResponsabilities (){
        boolean isValidResponsabilities =validateProject.validateTextArea(taResponsabilities.getText());
        if(isValidResponsabilities){
            taResponsabilities.getStyleClass().add("ok");
            projectEdit.setResponsabilities(validateProject.deleteSpace(taResponsabilities.getText()));
        }else {
            taResponsabilities.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("Responsabilities");
    }

    public void validateDaysAndHours (){
        boolean isValidDaysAndHours = validateProject.validateText(tfDaysAndHours.getText());
        if(isValidDaysAndHours){
            tfDaysAndHours.getStyleClass().add("ok");
            projectEdit.setDaysHours(validateProject.deleteSpace(tfDaysAndHours.getText()));
        }else {
            tfDaysAndHours.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("DaysHours");
    }

    public void validateDuration () {
        boolean isValidDuration = validateProject.validateNotEmpty(tfDuration.getText());
        if(isValidDuration){
            int duration = Integer.parseInt(tfDuration.getText());
            isValidDuration = validateProject.validateDuration(duration);
            if(isValidDuration){
                projectEdit.setDuration(duration);
                tfDuration.getStyleClass().add("ok");
            }else {
                tfDuration.getStyleClass().add("error");
                isValidDataProject= false;
            }
        }else {
            tfDuration.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("Duration");
    }

    public void validateQuiantityPractitioners (){
        boolean isValidQuiantityPractitioners = validateProject.validateNotEmpty(tfQuiantityPractitioners.getText());
        if(isValidQuiantityPractitioners){
            int quiantityPractitioners = Integer.parseInt(tfQuiantityPractitioners.getText());
            isValidQuiantityPractitioners = validateProject.validateQuiantityPractitioner(quiantityPractitioners);
            if(isValidQuiantityPractitioners){
                projectEdit.setQuiantityPractitioner(quiantityPractitioners);
                tfQuiantityPractitioners.getStyleClass().add("ok");
            }else {
                tfQuiantityPractitioners.getStyleClass().add("error");
                isValidDataProject= false;
            }
        }else{
            tfQuiantityPractitioners.getStyleClass().add("error");
            isValidDataProject= false;
        }
        datesUpdate.add("QuiantityPractitioners");
    }

    public void validateLinkedOrganization ( ){
        if(tfLinkedOrganization.getText().trim().isEmpty()){
            tfLinkedOrganization.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            LinkedOrganization linkedOrganization = new LinkedOrganization();
            tfLinkedOrganization.getStyleClass().add("ok");
            linkedOrganization.setName(nameLinkedOrganization);
            projectEdit.setOrganization(linkedOrganization);
        }
        datesUpdate.add("ResponsibilitiesProject");
    }

    public void validateResponsibleProject ( ){
        if(tfResponsibleProject.getText().trim().isEmpty()){
            tfResponsibleProject.getStyleClass().add("error");
            isValidDataProject= false;
        }else {
            ResponsibleProject responsibleProject = new ResponsibleProject();
            responsibleProject.setEmail(emailResponsible);
            projectEdit.setResponsible(responsibleProject);
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
        isValidDataProject=true;
        datesUpdate = new ArrayList<>();
        validateProject = new ValidateProject();
        projectEdit = new Project();
        removeStyleClass();
        if(!tfNameProject.getText().equals(project.getNameProject())) {
            validateName();
        }
        if(!taDescription.getText().equals(project.getDescription())) {
            validateDescription();
        }
        if(!taObjectiveGeneral.getText().equals(project.getObjectiveGeneral())) {
            validateObjectiveGeneral();
        }
        if(!taObjectiveInmediate.getText().equals(project.getObjectiveInmediate())) {
            validateObjectiveInmediate();
        }
        if(!taObjectiveMediate.getText().equals(project.getObjectiveMediate())) {
            validateObjectiveMediate();
        }
        if(!taActivitiesAndFunctions.getText().equals(project.getActivitiesAndFunctions())) {
            validateActivitiesAndFunctions();
        }
        if(!tfDaysAndHours.getText().equals(project.getDaysHours())) {
            validateDaysAndHours();
        }
        if(!taResource.getText().equals(project.getResources())) {
            validateResource();
        }
        if(!tfMethodology.getText().equals(project.getMethodology())) {
            validateMethodology();
        }
        if(!taResponsabilities.getText().equals(project.getResponsabilities())) {
            validateResponsabilities();
        }
        if(!tfDuration.getText().equals(Integer.toString(project.getDuration()))){
            validateDuration();
        }
        if(!tfQuiantityPractitioners.getText().equals(Integer.toString(project.getQuiantityPractitioner()))) {
            validateQuiantityPractitioners();
        }
        if(!tfLinkedOrganization.getText().equals(project.getOrganization().getName())) {
            validateLinkedOrganization();
        }
        if(!tfResponsibleProject.getText().equals(nameCompleteResponsibleProject)) {
            validateResponsibleProject();
        }
    }

}
