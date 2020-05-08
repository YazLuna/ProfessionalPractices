package test.testproject;

import domain.LinkedOrganization;
import domain.Project;
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
    public void testGetProject () {
        Project result = new Project();
        result = result.getProject("system management TBB");
        Assert.assertEquals("system management TBB",result.getNameProject());
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
