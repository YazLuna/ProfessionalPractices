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
    public void testRepeatProject(){
        boolean isRepeatProject;
        Project project = new Project();
        isRepeatProject = project.validateRepeatProject("Sistema Integral Académico");
        Assert.assertFalse(isRepeatProject);
    }

    @Test
    public void testRepeatLinkedOrganization (){
        boolean isRepeatLinkedOganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        isRepeatLinkedOganization = linkedOrganization.validateRepeatLinkedOrganization("BANX","banx@gmail.com");
        Assert.assertFalse(isRepeatLinkedOganization);
    }

    @Test
    public void testRepeatResponsibleProject(){
        boolean isRepeatResponsibleProject;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        isRepeatResponsibleProject = responsibleProject.validateRepeatResponsibleProject("Lucia");
        Assert.assertFalse(isRepeatResponsibleProject);
    }

    @Test
    public void testRepeatPhoneNumber (){
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        boolean isRepeatPhone = phoneNumberDAO.validateRepeatPhoneNumber("9711609017");
        Assert.assertFalse(isRepeatPhone);
    }

    @Test
    public void testThereAreProject(){
        boolean isRepeatProject;
        Project project = new Project();
        isRepeatProject = project.thereAreProject();
        Assert.assertTrue(isRepeatProject);
    }

    @Test
    public void testThereAreProjectAvailable(){
        boolean isRepeatProject;
        Project project = new Project();
        isRepeatProject = project.thereAreProjectAvailable();
        Assert.assertTrue(isRepeatProject);
    }

    @Test
    public void testThereAreLinkedOrganization (){
        boolean isRepeatLinkedOganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        isRepeatLinkedOganization = linkedOrganization.thereAreLinkedOrganization();
        Assert.assertTrue(isRepeatLinkedOganization);
    }

    @Test
    public void testThereAreLinkedOrganizationAvailable(){
        boolean isRepeatLinkedOganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        isRepeatLinkedOganization = linkedOrganization.thereAreLinkedOrganizationAvailable();
        Assert.assertTrue(isRepeatLinkedOganization);
    }

    @Test
    public void testThereAreLinkedOrganizationAvailableNotAssign(){
        boolean isRepeatLinkedOganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        isRepeatLinkedOganization = linkedOrganization.thereAreLinkedOrganizationAvailableNotAssign();
        Assert.assertTrue(isRepeatLinkedOganization);
    }

    @Test
    public void testThereAreResponsibleProject(){
        boolean isRepeatResponsibleProject;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        isRepeatResponsibleProject = responsibleProject.thereAreResponsibleProject();
        Assert.assertTrue(isRepeatResponsibleProject);
    }

    @Test
    public void testThereAreResponsibleProjectAvailable(){
        boolean isRepeatResponsibleProject;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        isRepeatResponsibleProject = responsibleProject.thereAreResponsibleProjectAvailable();
        Assert.assertTrue(isRepeatResponsibleProject);
    }

    @Test
    public void testThereAreResponsibleProjectAvailableNotAssign(){
        boolean isRepeatResponsibleProject;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        isRepeatResponsibleProject = responsibleProject.thereAreResponsibleProjectAvailableNotAssing();
        Assert.assertTrue(isRepeatResponsibleProject);
    }
}
