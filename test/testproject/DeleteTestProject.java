package test.testproject;

import domain.LinkedOrganization;
import domain.Project;
import domain.ResponsibleProject;
import org.junit.Assert;
import org.junit.Test;

public class DeleteTestProject {
    @Test
    public void testDeleteProject(){
        boolean isDeleteProject;
        Project project = new Project();
        isDeleteProject = project.deleteProject("Sistema Integral Académico");
        Assert.assertEquals(true,isDeleteProject);
    }

    @Test
    public void testDeleteLinkedOrganization(){
        boolean isDeleteOrganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        isDeleteOrganization = linkedOrganization.deleteOrganization("Dirección de Desarrollo Informático de Apoyo Académico");
        Assert.assertEquals(true,isDeleteOrganization);
    }

    @Test
    public void testDeleteResponsibleProject(){
        boolean isDeleteResponsible;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        isDeleteResponsible = responsibleProject.deleteResponsibleProject("guruiz@uv.mx");
        Assert.assertEquals(true,isDeleteResponsible);
    }
}
