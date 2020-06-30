package test.testproject;

import dataaccess.LinkedOrganizationDAOImpl;
import dataaccess.ProjectDAOImpl;
import dataaccess.ResponsibleProjectDAOImpl;
import dataaccess.SchedulingActivitiesDAOImpl;
import domain.LinkedOrganization;
import domain.Project;
import domain.ResponsibleProject;
import domain.SchedulingActivities;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetTestProject {
    @Test
    public void testGetAllProject () {
        List<Project> result = new ArrayList<>();;
        Project project = new Project();
        result = project.listProjects();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetAllProjectAvailableNotAssing () {
        List<Project> allProjectNotAssing = new ArrayList<>();
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        allProjectNotAssing = projectDAO.getAllProjectsAvailableNotAssing();
        for(int indexProject=0;indexProject<allProjectNotAssing.size();indexProject++){
            System.out.println(allProjectNotAssing.get(indexProject).getNameProject());
        }

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
    public void testGetResponsible () {
        ResponsibleProject responsibleProject = new ResponsibleProject();
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        responsibleProject = responsibleProjectDAO.getIdResponsibleProject(20);
        System.out.println( responsibleProject.getResponsible().getName());
        System.out.println( responsibleProject.getResponsible().getLastName());
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
        result = organization.listState();
        Assert.assertNotNull(result);
    }

}
