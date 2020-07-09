package test.testproject;

import domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UpdateTestProject {
    @Test
    public void testModifyProject(){
        boolean isModifyProject;
        Project project = new Project();
        List<String> datesUpdate = new ArrayList<>();
        project.setIdProject(43);
        project.setNameProject("Gestion del sistema YGT");
        project.setDescription("Disenia el diagrama de Casos de Uso con el fin de identificar las funcionalidades");
        project.setObjectiveGeneral("Instalación del SGBD Oracle para poder empezar el desarrollo y pruebas");
        project.setObjectiveInmediate("Creación de una tabla y algunos procedimientos con el fin de probar que la instalacion");
        project.setObjectiveMediate("Refinar requerimientos");
        project.setQuantityPractitioner(3);
        datesUpdate.add("nameProject");
        datesUpdate.add("Description");
        datesUpdate.add("ObjectiveGeneral");
        datesUpdate.add("ObjetiveInmediate");
        datesUpdate.add("ObjectiveMediate");
        datesUpdate.add("QuantityPractitioner");
        isModifyProject = project.modifyProject(project,datesUpdate);
        Assert.assertEquals(true,isModifyProject);
    }

    @Test
    public void testModifySchedulingActivities(){
        boolean isModifySchedulingActivities;
        SchedulingActivities schedulingActivities = new SchedulingActivities();
        List<String> datesUpdate = new ArrayList<>();
        schedulingActivities.setIdSchedulingActivities(14);
        schedulingActivities.setMonth("Enero");
        datesUpdate.add("Month");
        isModifySchedulingActivities = schedulingActivities.modifySchedulingActivities(schedulingActivities,datesUpdate);
        Assert.assertEquals(true,isModifySchedulingActivities);
    }

    @Test
    public void testModifyLinkedOrganization(){
        boolean isModifyLinkedOrganization;
        List<String> datesUpdate = new ArrayList<>();
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganization.setName("BANX");
        linkedOrganization.setEmail("banx@gmail.com");
        datesUpdate.add("Name");
        datesUpdate.add("Email");
        isModifyLinkedOrganization = linkedOrganization.modifyOrganization(linkedOrganization,datesUpdate);
        Assert.assertEquals(true,isModifyLinkedOrganization);
    }

    public void testModifyPhoneNumber(){
        boolean isModifyPhoneNumber;
        List<String> datesUpdate = new ArrayList<>();
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setIdPhoneNumber(1);
        phoneNumber.setPhoneNumber("9711609017");
        datesUpdate.add("PhoneNumber");
        isModifyPhoneNumber = phoneNumber.modifyPhoneNumber(phoneNumber,datesUpdate);
        Assert.assertEquals(true,isModifyPhoneNumber);
    }

    @Test
    public void testModifyResponsibleProject(){
        boolean result;
        ResponsibleProject responsible = new ResponsibleProject();
        responsible.setName("Jahir");
        responsible.setLastName("Betanzos");
        responsible.setEmail("jar_12@outlook.com");
        responsible.setCharge("Gerente");
        ///result = project.modifyProject(project);
        ///Assert.assertEquals(true,result);
    }

}
