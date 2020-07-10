package test.testproject;

import logic.ValidateDataResponsible;
import logic.ValidateLinkedOrganization;
import logic.ValidateProject;
import org.junit.Assert;
import org.junit.Test;

public class LogicTestProject {
    @Test
    public void testValidateExtensions (){
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        boolean isValidExtensions = validateLinkedOrganization.validateExtensions("15878, 5487, 2534");
        Assert.assertTrue(isValidExtensions);
    }

    @Test
    public void testErrorValidateLastName (){
        boolean isValidLastName;
        ValidateDataResponsible validateDataResponsible = new ValidateDataResponsible();
        isValidLastName =validateDataResponsible.validateLastName("435 563455");
        Assert.assertTrue(isValidLastName);
    }

    @Test
    public void testValidateLastName (){
        boolean isValidLastName;
        ValidateDataResponsible validateDataResponsible = new ValidateDataResponsible();
        isValidLastName =validateDataResponsible.validateLastName("Gustavo Antonio");
        Assert.assertTrue(isValidLastName);
    }

    @Test
    public void testValidateCharge (){
        boolean isValidLastName;
        ValidateDataResponsible validateDataResponsible = new ValidateDataResponsible();
        isValidLastName =validateDataResponsible.validateLastName("Jefe de departamento de Tecnología Educativa");
        Assert.assertTrue(isValidLastName);
    }

    @Test
    public void testErrorValidateCharge (){
        boolean isValidLastName;
        ValidateDataResponsible validateDataResponsible = new ValidateDataResponsible();
        isValidLastName =validateDataResponsible.validateLastName("1 34      ");
        Assert.assertTrue(isValidLastName);
    }

    @Test
    public void testValidateNameLinkedOrganization (){
        boolean isValidNameOrganization;
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        isValidNameOrganization = validateLinkedOrganization.validateName("Dirección de Desarrollo Informático de Apoyo Académicos");
        Assert.assertTrue(isValidNameOrganization);
    }

    @Test
    public void testValidateErrorNameLinkedOrganization (){
        boolean isValidNameOrganization;
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        isValidNameOrganization = validateLinkedOrganization.validateName("835u8435 #12 4");
        Assert.assertTrue(isValidNameOrganization);
    }

    @Test
    public void testValidateAddress (){
        boolean isValidAddress;
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        isValidAddress = validateLinkedOrganization.validateAddress("Circuito Aguirre Beltrán S/N");
        Assert.assertTrue(isValidAddress);
    }

    @Test
    public void testValidateComboBox (){
        boolean isValidComboBox;
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        isValidComboBox = validateLinkedOrganization.validateComboBox("Veracruz");
        Assert.assertTrue(isValidComboBox);
    }

    @Test
    public void testValidateUsersOrganization (){
        boolean isValidUsersOrganization;
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        isValidUsersOrganization = validateLinkedOrganization.validateUsersOrganization("Comunidad universitaria y\npoblación en general");
        Assert.assertTrue(isValidUsersOrganization);
    }

    @Test
    public void testErrorValidateUsersOrganization (){
        boolean isValidUsersOrganization;
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        isValidUsersOrganization = validateLinkedOrganization.validateUsersOrganization("                           ");
        Assert.assertTrue(isValidUsersOrganization);
    }

    @Test
    public void testValidateNameProject (){
        boolean isValidNameProject;
        ValidateProject validateProject = new ValidateProject();
        isValidNameProject = validateProject.validateName("Sistema Integral Académico");
        Assert.assertTrue(isValidNameProject);
    }

    @Test
    public void testValidateMethodology (){
        boolean isValidMethodology;
        ValidateProject validateProject = new ValidateProject();
        isValidMethodology = validateProject.validateMethology("Proceso de desarrollo iterativo y Design Sprint, SCRUM");
        Assert.assertTrue(isValidMethodology);
    }

    @Test
    public void testValidateText (){
        boolean isValidText;
        ValidateProject validateProject = new ValidateProject();
        isValidText = validateProject.validateText("A acordar con el estudiante (en horario de oficina)");
        Assert.assertTrue(isValidText);
    }

    @Test
    public void testValidateTextArea (){
        boolean isValidTextArea;
        ValidateProject validateProject = new ValidateProject();
        isValidTextArea = validateProject.validateText("Cumplir con las funciones y actividades que sean asignadas\n" +
                "Cumplir en tiempo y forma con las entregas de prototipos y productos\n" +
                "Desarrollar en un ambiente colaborativo\n" +
                "Trabajar de acuerdo a los estándares establecidos");
        Assert.assertTrue(isValidTextArea);
    }

    @Test
    public void testErrorValidateDuration (){
        boolean isValidText;
        ValidateProject validateProject = new ValidateProject();
        isValidText = validateProject.validateDuration(0);
        Assert.assertTrue(isValidText);
    }

    @Test
    public void testValidateDuration (){
        boolean isValidDuraton;
        ValidateProject validateProject = new ValidateProject();
        isValidDuraton = validateProject.validateDuration(200);
        Assert.assertTrue(isValidDuraton);
    }

    @Test
    public void testValidateQuiantityPractitioner (){
        boolean isValidQuiantityPractitioner;
        ValidateProject validateProject = new ValidateProject();
        isValidQuiantityPractitioner = validateProject.validateQuiantityPractitioner(3);
        Assert.assertTrue(isValidQuiantityPractitioner);
    }

    @Test
    public void testErrorValidateQuiantityPractitioner (){
        boolean isValidQuiantityPractitioner;
        ValidateProject validateProject = new ValidateProject();
        isValidQuiantityPractitioner = validateProject.validateQuiantityPractitioner(0);
        Assert.assertTrue(isValidQuiantityPractitioner);
    }

    @Test
    public void testErrorValidateQuiantityPractitionerTwo (){
        boolean isValidQuiantityPractitioner;
        ValidateProject validateProject = new ValidateProject();
        isValidQuiantityPractitioner = validateProject.validateQuiantityPractitioner(10000);
        Assert.assertTrue(isValidQuiantityPractitioner);
    }
}
