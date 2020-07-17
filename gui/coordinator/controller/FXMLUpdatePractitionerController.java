package gui.coordinator.controller;

import domain.Practitioner;
import domain.Gender;
import domain.Search;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import logic.ValidateUser;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Update Practitioner Controller
 * @author Yazmin
 * @version 09/07/2020
 */
public class FXMLUpdatePractitionerController extends FXMLGeneralController implements Initializable {
	@FXML private Label lbTerm;
	@FXML private TextField tfCredits;
	@FXML private TextField tfEnrollment;
	@FXML private TextField tfName;
	@FXML private TextField tfLastName;
	@FXML private TextField tfEmail;
	@FXML private TextField tfAlternateEmail;
	@FXML private TextField tfPhone;
	@FXML private RadioButton rbMale;
	@FXML private RadioButton rbFemale;
	@FXML private Button btnCancel;
	@FXML private Button btnUpdate;
	@FXML private Button btnRecoverPractitioner;
	public static String enrollment;
	private Practitioner practitionerOriginal = new Practitioner();
	List<String> datesUpdate = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		practitionerOriginal.setEnrollment(enrollment);
		setLimitsTextFields();
		colocatePractitioner();
	}

	/**
	 * Method to exit the system
	 */
	public void logOutCoordinator() {
		logOutGeneral();
	}

	/**
	 * Method to cancel registration and return to the menu
	 */
	public void backList() {
		FXMLUpdateDeletePractitionerListController.action = "Update";
		openWindowGeneral("/gui/coordinator/fxml/FXMLUpdateDeletePractitionerList.fxml", btnCancel);
	}

	/**
	 * Method to update a Practitioner
	 */
	public void updatePractitioner(){
		boolean registerComplete;
		removeStyle();
		boolean validateFields = validateFields();
		if(validateFields){
			Practitioner practitionerNew = new Practitioner();
			boolean areChanges = createObjectPractitionerDifferent(practitionerNew);
			if (areChanges) {
				int validTeacher = Practitioner.validPractitionerUpdate(practitionerNew);
				if (validTeacher == Search.NOTFOUND.getValue()) {
					boolean confirmUpdate = generateConfirmation("¿Seguro que desea modificar al Practicante?");
					if(confirmUpdate){
						registerComplete = Practitioner.updatePractitioner(enrollment, practitionerNew, datesUpdate);
						if(registerComplete){
							generateInformation("Practicante modificado exitosamente");
						}else{
							generateError("No hay conexión a la base de datos. Intente más tarde");
						}
						openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnUpdate);
					}
				} else {
					if (validTeacher == Search.FOUND.getValue()) {
						generateInformation("Este practicante ya está registrado");
					} else {
						if (validTeacher == Search.EXCEPTION.getValue()) {
							generateError("No hay conexión a la base de datos. Intente más tarde");
							openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnUpdate);
						}
					}
				}
			}
		}
	}

	/**
	 * Method to recover a deleted Practitioner
	 */
	public void recoverPractitioner() {
		boolean recoverOk = generateConfirmation("¿Seguro que desea reactivar este practicante?");
		if(recoverOk){
			boolean recoverSuccessful = Practitioner.recoverPractitioner(enrollment);
			if(recoverSuccessful){
				generateInformation("Practicante reactivado exitosamente");
			}else{
				generateError("No hay conexión con la base de datos. Intente más tarde");
			}
			openWindowGeneral("/gui/administrator/fxml/FXMLMenuAdministrator.fxml",btnRecoverPractitioner);
		}
	}

	private void setLimitsTextFields() {
		limitTextField(tfName,30);
		limitTextField(tfLastName,30);
		limitTextField(tfEmail,50);
		limitTextField(tfAlternateEmail,50);
		limitTextField(tfPhone,10);
		limitTextField(tfEnrollment,9);
		prohibitNumberTextField(tfName);
		prohibitNumberTextField(tfLastName);
		prohibitWordTextField(tfPhone);
		limitTextField(tfEnrollment,9);
		limitTextField(tfCredits, 3);
		prohibitWordTextField(tfCredits);
	}

	private void colocatePractitioner() {
		practitionerOriginal = Practitioner.getPractitioner(enrollment);
		if (practitionerOriginal.getEnrollment() == null) {
			generateError("No hay conexión con la base de datos. Intente más tarde");
			openWindowGeneral("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml",btnUpdate);
		} else {
			tfName.setText(practitionerOriginal.getName());
			tfLastName.setText(practitionerOriginal.getLastName());
			tfEmail.setText(practitionerOriginal.getEmail());
			tfAlternateEmail.setText(practitionerOriginal.getAlternateEmail());
			if(practitionerOriginal.getGender()== Gender.MALE.getGender()){
				rbMale.fire();
			}else{
				rbFemale.fire();
			}
			tfPhone.setText(practitionerOriginal.getPhone());
			tfEnrollment.setText(practitionerOriginal.getEnrollment());
			lbTerm.setText(practitionerOriginal.getTerm());
			tfCredits.setText(String.valueOf(practitionerOriginal.getCredits()));
			if(practitionerOriginal.getStatus().equalsIgnoreCase("Inactive")){
				btnRecoverPractitioner.setVisible(true);
			}
		}
	}

	private void removeStyle(){
		tfEnrollment.getStyleClass().remove("ok");
		tfName.getStyleClass().remove("ok");
		tfLastName.getStyleClass().remove("ok");
		tfEmail.getStyleClass().remove("ok");
		tfAlternateEmail.getStyleClass().remove("ok");
		tfPhone.getStyleClass().remove("ok");
		rbFemale.getStyleClass().remove("ok");
		rbMale.getStyleClass().remove("ok");
		tfCredits.getStyleClass().remove("ok");
		tfEnrollment.getStyleClass().remove("error");
		tfName.getStyleClass().remove("error");
		tfLastName.getStyleClass().remove("error");
		tfEmail.getStyleClass().remove("error");
		tfAlternateEmail.getStyleClass().remove("error");
		tfPhone.getStyleClass().remove("error");
		rbFemale.getStyleClass().remove("error");
		rbMale.getStyleClass().remove("error");
		tfCredits.getStyleClass().remove("error");
	}

	private boolean validateFields(){
		ValidateUser validateUser = new ValidateUser();
		boolean validation= true;
		if(validateUser.validateEmpty(tfCredits.getText()) && validateUser.validateCreditsPractitioner(tfCredits.getText())){
			tfCredits.getStyleClass().add("ok");
		}else{
			tfCredits.getStyleClass().add("error");
			validation = false;
		}

		if(validateUser.validateEmpty(tfEnrollment.getText()) && validateUser.validateEnrollment(tfEnrollment.getText())){
			tfEnrollment.getStyleClass().add("ok");
		}else{
			tfEnrollment.getStyleClass().add("error");
			validation = false;
		}

		if((validateUser.validateEmpty(tfName.getText())) && (validateUser.validateNameUser(tfName.getText()))) {
			tfName.getStyleClass().add("ok");
			tfName.setText(validateUser.deleteSpace(tfName.getText()));
			tfName.setText(validateUser.createCorrectProperName(tfName.getText()));
		}else{
			tfName.getStyleClass().add("error");
			validation = false;
		}

		if((validateUser.validateEmpty(tfLastName.getText())) && (validateUser.validateNameUser(tfLastName.getText()))) {
			tfLastName.getStyleClass().add("ok");
			tfLastName.setText(validateUser.deleteSpace(tfLastName.getText()));
			tfLastName.setText(validateUser.createCorrectProperName(tfLastName.getText()));
		}else{
			tfLastName.getStyleClass().add("error");
			validation = false;
		}

		if((validateUser.validateEmpty(tfEmail.getText())) && (validateUser.validateEmail(tfEmail.getText())) &&
				(!tfEmail.getText().equalsIgnoreCase(tfAlternateEmail.getText()))) {
			tfEmail.getStyleClass().add("ok");
		}else{
			tfEmail.getStyleClass().add("error");
			validation = false;
		}

		if((validateUser.validateEmpty(tfAlternateEmail.getText())) && (validateUser.validateEmail(tfAlternateEmail.getText())) &&
				(!tfEmail.getText().equalsIgnoreCase(tfAlternateEmail.getText()))) {
			tfAlternateEmail.getStyleClass().add("ok");
		}else{
			tfAlternateEmail.getStyleClass().add("error");
			validation = false;
		}

		if((validateUser.validateEmpty(tfPhone.getText())) && (validateUser.validatePhoneNumber(tfPhone.getText()))) {
			tfPhone.getStyleClass().add("ok");
		}else{
			tfPhone.getStyleClass().add("error");
			validation = false;
		}

		if((!rbMale.isSelected()) && (!rbFemale.isSelected())){
			validation = false;
			rbMale.getStyleClass().add("error");
			rbFemale.getStyleClass().add("error");
		}else{
			if((rbMale.isSelected()) && (rbFemale.isSelected())){
				validation = false;
				rbMale.getStyleClass().add("error");
				rbFemale.getStyleClass().add("error");
			} else {
				rbMale.getStyleClass().add("ok");
				rbFemale.getStyleClass().add("ok");
			}
		}
		return validation;
	}

	private boolean createObjectPractitionerDifferent(Practitioner practitionerNew) {
		boolean areChanges = false;
		if(!practitionerOriginal.getEnrollment().equalsIgnoreCase(tfEnrollment.getText())) {
			practitionerNew.setEnrollment(tfEnrollment.getText());
			datesUpdate.add("Enrollment");
			areChanges = true;
		}

		if(practitionerOriginal.getCredits() != Integer.parseInt(tfCredits.getText())) {
			practitionerNew.setCredits(Integer.parseInt(tfCredits.getText()));
			datesUpdate.add("Credits");
			areChanges = true;
		}

		if(!practitionerOriginal.getName().equalsIgnoreCase(tfName.getText())) {
			practitionerNew.setName(tfName.getText());
			datesUpdate.add("Name");
			areChanges = true;
		}
		if(!practitionerOriginal.getLastName().equalsIgnoreCase(tfLastName.getText())) {
			practitionerNew.setLastName(tfLastName.getText());
			datesUpdate.add("LastName");
			areChanges = true;
		}
		if(!practitionerOriginal.getEmail().equalsIgnoreCase(tfEmail.getText())) {
			practitionerNew.setEmail(tfEmail.getText());
			datesUpdate.add("Email");
			areChanges = true;
		}
		if(!practitionerOriginal.getAlternateEmail().equalsIgnoreCase(tfAlternateEmail.getText())) {
			practitionerNew.setAlternateEmail(tfAlternateEmail.getText());
			datesUpdate.add("AlternateEmail");
			areChanges = true;
		}
		if(!practitionerOriginal.getPhone().equalsIgnoreCase(tfPhone.getText())) {
			practitionerNew.setPhone(tfPhone.getText());
			datesUpdate.add("Phone");
			areChanges = true;
		}
		if(rbMale.isSelected()){
			if(practitionerOriginal.getGender() != Gender.MALE.getGender()){
				practitionerNew.setGender(Gender.MALE.getGender());
				datesUpdate.add("Gender");
				areChanges = true;
			}
		}else{
			if(rbFemale.isSelected()){
				if(practitionerOriginal.getGender() != Gender.FEMALE.getGender()){
					practitionerNew.setGender(Gender.FEMALE.getGender());
					datesUpdate.add("Gender");
					areChanges = true;
				}
			}
		}
		return areChanges;
	}

}
