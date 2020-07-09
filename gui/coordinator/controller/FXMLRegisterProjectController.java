package gui.coordinator.controller;

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
import dataaccess.Number;
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
    @FXML private TextField tfActivityScheduling;
    @FXML private ComboBox cbMonthScheduling;
    @FXML private TextArea taDescription;
    @FXML private TextArea taObjectiveGeneral;
    @FXML private TextArea taObjectiveInmediate;
    @FXML private TextArea taObjectiveMediate;
    @FXML private TextArea taResource;
    @FXML private TextArea taActivitiesAndFunctions;
    @FXML private TextArea taResponsabilities;
    @FXML private GridPane gpActivity;
    @FXML private Label lbTerm;
    private String emailResponsible;
    private String nameLinkedOrganization ;
    private static int StaffNumberCoordinator;
    private List<String> allMonth;
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
        boolean message;
        if(!validateDataProject()) {
            Alert alertDataProject = new Alert(Alert.AlertType.NONE);
            alertDataProject.setAlertType(Alert.AlertType.WARNING);
            alertDataProject.setHeaderText("Enter correct data in the red fields");
            alertDataProject.setTitle("Warning");
            alertDataProject.show();
        }else{
            getDataProject();
            message = project.registerProject(project);
            Alert alertDataProject = new Alert(Alert.AlertType.NONE);
            alertDataProject.setAlertType(Alert.AlertType.INFORMATION);
            alertDataProject.show();
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

        limitTextField(tfActivityScheduling,100);
        prohibitSpacesTextField(tfActivityScheduling);
        prohibitNumberAllowSpecialCharTextField(tfActivityScheduling);

        String term = createTerm();
        lbTerm.setText(term);
    }

    public void startComboBox () {
        schedulingActivities = new SchedulingActivities();
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
        cbMonthScheduling.getItems().addAll(allMonth);
    }

    public void getDataProject () {
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
        project.setQuantityPractitioner(quiantityPractitioner);
    }

    public void addActivity () {
        if(positionGrindActivity<Number.SEVEN.getNumber()) {
            Label lbMonth = new Label();
            lbMonth.setText("Mes:  ");
            Label lbActivity = new Label();
            lbActivity.setText("Actividad:  ");
            TextField tfActivity = new TextField();
            ComboBox cbMonth = new ComboBox();
            lbMonth.autosize();
            lbActivity.autosize();
            tfActivity.setMaxWidth(173);
            cbMonth.setPrefWidth(150);
            lbMonth.getStyleClass().add("details");
            cbMonth.getEditor().getStyleClass().add("details");
            lbActivity.getStyleClass().add("details");
            tfActivity.getStyleClass().add("details");
            prohibitSpacesTextField(tfActivity);
            prohibitNumberTextField(tfActivity);
            cbMonth.getItems().addAll(allMonth);
            gpActivity.add(lbMonth, 0, positionGrindActivity);
            gpActivity.add(cbMonth, 1, positionGrindActivity);
            gpActivity.add(lbActivity, 2, positionGrindActivity);
            gpActivity.add(tfActivity, 3, positionGrindActivity);
            positionGrindActivity++;
        }else{
            generateInformation("Sólo se permite seis regitros de actividades");
        }
    }

    public Node getNodeByRowColumnIndex (final int row, final int column) {
        Node result = null; ObservableList childrens = gpActivity.getChildren();
        for (Object node : childrens) {
            if(gpActivity.getRowIndex((Node) node) == row && gpActivity.getColumnIndex((Node) node) == column) {
                result = (Node) node; break;
            }
        }
        return result;
    }
    public void deleteActivity () {
        if(positionGrindActivity>Number.ONE.getNumber()){
            gpActivity.add(null, 0, positionGrindActivity);
            gpActivity.add(null, 1, positionGrindActivity);
            gpActivity.add(null, 2, positionGrindActivity);
            gpActivity.add(null, 3, positionGrindActivity);

            positionGrindActivity--;
            gpActivity.getRowConstraints().remove(positionGrindActivity);
            /*Node deleteNodeOne =getNodeByRowColumnIndex(positionGrindActivity,);
            Node deleteNodeTwo =getNodeByRowColumnIndex(positionGrindActivity,1);
            Node deleteNodeThree =getNodeByRowColumnIndex(positionGrindActivity,2);
            Node deleteNodeFour =getNodeByRowColumnIndex(positionGrindActivity,3);
            gpActivity.getChildren().removeAll(deleteNodeOne,deleteNodeTwo,deleteNodeThree,deleteNodeFour);
            //gpActivity.getRowConstraints().remove(positionGrindActivity);
            //gpActivity.getChildren().removeIf(node -> GridPane.getRowIndex(node) == positionGrindActivity);
            /*gpActivity.getChildren().remove(1,positionGrindActivity);
            gpActivity.getChildren().remove(1,positionGrindActivity);
            gpActivity.getChildren().remove(2,positionGrindActivity);
            gpActivity.getChildren().remove(3,positionGrindActivity);*/
            //gpActivity.getRowConstraints().remove(positionGrindActivity);
            //gpActivity.getChildren().removeIf(node -> GridPane.getRowIndex(node) == positionGrindActivity);
            /*while(gpActivity.getRowConstraints().size() > 0){
                gpActivity.getRowConstraints().remove(0);
            }*/

        }else{
            generateInformation("Registre al menos una actividad");
        }
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
        if(!validateProject.validateText(taActivitiesAndFunctions.getText())){
            taActivitiesAndFunctions.getStyleClass().add("error");
            result= false;
        }else {
            taActivitiesAndFunctions.getStyleClass().remove("error");
        }
        if(!validateProject.validateText(taResponsabilities.getText())){
            taResponsabilities.getStyleClass().add("error");
            result= false;
        }else {
            taResponsabilities.getStyleClass().remove("error");
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
