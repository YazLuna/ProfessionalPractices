package test.testproject;

import dataaccess.LinkedOrganizationDAOImpl;
import dataaccess.PhoneNumberDAOImpl;
import dataaccess.ResponsibleProjectDAOImpl;
import dataaccess.SchedulingActivitiesDAOImpl;
import domain.LinkedOrganization;
import domain.Project;
import domain.ResponsibleProject;
import domain.SchedulingActivities;
import logic.ValidateLinkedOrganization;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidateTestProject {
    @Test
    public void testValidateExtensions (){
        ValidateLinkedOrganization validateLinkedOrganization = new ValidateLinkedOrganization();
        boolean resultValid = validateLinkedOrganization.validateExtensions("15878, 5487, 2534");
        Assert.assertTrue(resultValid);
    }

    @Test
    public void testGetProject () {
        Project result = new Project();
        result = result.getProject("Requerimientos de practicas profesionales");
        System.out.println(result.getNameProject());
        System.out.println(result.getResponsible().getName());
        System.out.println(result.getResponsible().getLastName());
        System.out.println(result.getOrganization().getName());
        Assert.assertEquals("Requerimientos de practicas profesionales",result.getNameProject());
    }

    @Test
    public void testRepeatPhoneNumber (){
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        boolean isRepeatPhone = phoneNumberDAO.validateRepeatPhoneNumber("9711609017");
        Assert.assertFalse(isRepeatPhone);
    }

    @Test
    public void testModifyResponsibleProject () {
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProject.setName("Sofia");
        //responsibleProject.setCharge("Recursos humanos");
        responsibleProject.setIdResponsible(19);
        List<String> datesUpdate= new ArrayList<>();
        datesUpdate.add("Name");
        //datesUpdate.add("Charge");
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        boolean result = responsibleProjectDAO.modifyResponsibleProject(responsibleProject,datesUpdate);
        Assert.assertTrue(result);
    }

    @Test
    public void testGetResponsible () {
        ResponsibleProject responsibleProject = new ResponsibleProject();
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        responsibleProject = responsibleProjectDAO.getResponsibleProjectWithId(20);
        /*System.out.println( responsibleProject.getResponsibleProject().getName());
        System.out.println( responsibleProject.getResponsibleProject().getLastName());*/
    }

    @Test
    public void testGetSchedulingProject () {
        List<SchedulingActivities> schedulingActivitiesList = new ArrayList<>();
        SchedulingActivitiesDAOImpl schedulingActivitiesDAO = new SchedulingActivitiesDAOImpl();
        schedulingActivitiesList = schedulingActivitiesDAO.getAllSchedulingActivities(36);
        System.out.println(schedulingActivitiesList.get(0).getActivity());
        System.out.println(schedulingActivitiesList.get(1).getActivity());
    }

    @Test
    public void testGetAllLinkedOrganizationAvailable () {
        List<LinkedOrganization> organizations = new ArrayList<>();
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        organizations = linkedOrganizationDAO.getAllLinkedOrganizationAvailable();
        for(int indexOrganization=0;indexOrganization<organizations.size();indexOrganization++){
            System.out.println(organizations.get(indexOrganization).getName());
        }
    }

    @Test
    public void testGetAllResponsibleAvailable () {
        List<ResponsibleProject> responsibles = new ArrayList<>();
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        responsibles = responsibleProjectDAO.getAllResponsibleAvailable();
        for(int indexResponsible=0;indexResponsible<responsibles.size();indexResponsible++){
            System.out.println(responsibles.get(indexResponsible).getName());
        }
    }

    @Test
    public void testGetProjectError () {
        Project result = new Project();
        result = result.getProject("system management TYY");
        Assert.assertEquals("system management TTB",result.getNameProject());
    }

    @Test
    public void testGetAllState () {
        List<String> result = new ArrayList<>();;
        LinkedOrganization organization = new LinkedOrganization();
        result = organization.getListState();
        Assert.assertNotNull(result);
    }
}
