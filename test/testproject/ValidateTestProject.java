package test.testproject;

import dataaccess.LinkedOrganizationDAOImpl;
import dataaccess.PhoneNumberDAOImpl;
import dataaccess.ResponsibleProjectDAOImpl;
import dataaccess.SchedulingActivitiesDAOImpl;
import domain.*;
import logic.ValidateLinkedOrganization;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidateTestProject {
    @Test
    public void testRepeatProject(){
        int isRepeatProject;
        Project project = new Project();
        isRepeatProject = project.validateRepeatProject("Sistema Integral Académico");
        Assert.assertEquals(Search.NOTFOUND.getValue(),isRepeatProject);
    }

    @Test
    public void testRepeatLinkedOrganization (){
        int isRepeatLinkedOganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        isRepeatLinkedOganization = linkedOrganization.validateRepeatLinkedOrganization("Dirección de Desarrollo Informático de Apoyo Académico","acolunga@uv.mx");
        Assert.assertEquals(Search.NOTFOUND.getValue(),isRepeatLinkedOganization);
    }

    @Test
    public void testRepeatResponsibleProject(){
        int isRepeatResponsibleProject;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        isRepeatResponsibleProject = responsibleProject.validateRepeatResponsibleProject("guruiz@uv.mx");
        Assert.assertEquals(Search.NOTFOUND.getValue(),isRepeatResponsibleProject);
    }

    @Test
    public void testRepeatPhoneNumber (){
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        int isRepeatPhone = phoneNumberDAO.validateRepeatPhoneNumber("9711609017");
        Assert.assertEquals(Search.NOTFOUND.getValue(),isRepeatPhone);
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
