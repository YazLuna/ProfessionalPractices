package test.testproject;

import dataaccess.*;
import domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetTestProject {
    @Test
    public void testGetAllProject () {
        List<Project> projectList = new ArrayList<>();
        Project project = new Project();
        projectList = project.getListProject();
        Assert.assertNotNull(projectList);
    }

    @Test
    public void testGetAllProjectAvailable () {
        List<Project> projectList = new ArrayList<>();
        Project project = new Project();
        projectList = project.getListProjectAvailable();
        Assert.assertNotNull(projectList);
    }

    @Test
    public void testGetProject () {
        Project project = new Project();
        project = project.getProject("Sistema Integral Académico");
        Assert.assertEquals("Sistema Integral Académico",project.getNameProject());
    }

    @Test
    public void testGetAllCity () {
        List<String> cityList = new ArrayList<>();
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        cityList = linkedOrganization.getListCity();
        Assert.assertNotNull(cityList);
    }

    @Test
    public void testGetAllSector () {
        List<String> sectorList = new ArrayList<>();
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        sectorList = linkedOrganization.getListSector();
        Assert.assertNotNull(sectorList);
    }

    @Test
    public void testGetAllState () {
        List<String> stateList = new ArrayList<>();
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        stateList = linkedOrganization.getListState();
        Assert.assertNotNull(stateList);
    }

    @Test
    public void testGetAllLinkedOrganization () {
        List<LinkedOrganization> linkedOrganizationList = new ArrayList<>();
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganizationList = linkedOrganization.getListOrganization();
        Assert.assertNotNull(linkedOrganizationList);
    }

    @Test
    public void testGetAllLinkedOrganizationAvailable () {
        List<LinkedOrganization> linkedOrganizationAvailbleList = new ArrayList<>();
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganizationAvailbleList = linkedOrganization.getListOrganizationAvailable();
        Assert.assertNotNull(linkedOrganizationAvailbleList);
    }

    @Test
    public void testGetAllLinkedOrganizationAvailableNotAssign () {
        List<LinkedOrganization> linkedOrganizationList = new ArrayList<>();
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganizationList = linkedOrganization.getListOrganizationAvailableNotAssign();
        Assert.assertNotNull(linkedOrganizationList);
    }

    @Test
    public void testGetLinkedOrganization () {
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganization = linkedOrganization.getLinkedOrganization("Dirección de Desarrollo Informático de Apoyo Académico");
        Assert.assertEquals("Dirección de Desarrollo Informático de Apoyo Académico",linkedOrganization.getName());
    }

    @Test
    public void testGetAllCharge () {
        List<String> chargeList = new ArrayList<>();
        ResponsibleProject responsibleProject = new ResponsibleProject();
        chargeList = responsibleProject.getListCharge();
        Assert.assertNotNull(chargeList);
    }

    @Test
    public void testGetAllResponsible () {
        List<ResponsibleProject> responsibleProjectList = new ArrayList<>();
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProjectList = responsibleProject.getListResponsibleProject();
        Assert.assertNotNull(responsibleProjectList);
    }

    @Test
    public void testGetAllResponsibleAvailable () {
        List<ResponsibleProject> responsibleProjectAvailableList = new ArrayList<>();
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProjectAvailableList = responsibleProject.getListResponsibleProjectAvailable();
        Assert.assertNotNull(responsibleProjectAvailableList);
    }

    @Test
    public void testGetAllResponsibleAvailableNotAssign () {
        List<ResponsibleProject> responsibleProjectList = new ArrayList<>();
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProjectList = responsibleProject.getListResponsibleProjectAvailableNotAssign();
        Assert.assertNotNull(responsibleProjectList);
    }

    @Test
    public void testGetResponsibleProject () {
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProject = responsibleProject.getResponsibleProject("guruiz@uv.mx");
        Assert.assertEquals("guruiz@uv.mx",responsibleProject.getName());
    }

    @Test
    public void testGetSchedulingProject () {
        List<SchedulingActivities> schedulingActivitiesList = new ArrayList<>();
        SchedulingActivitiesDAOImpl schedulingActivitiesDAO = new SchedulingActivitiesDAOImpl();
        schedulingActivitiesList = schedulingActivitiesDAO.getAllSchedulingActivities(36);
        Assert.assertNotNull(schedulingActivitiesList);
    }

}
