package test.testproject;

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
        result = result.getProject("System management TTB");
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetAllState () {
        List<String> result = new ArrayList<>();;
        Project project = new Project();
        result = project.getOrganization().listState();
        Assert.assertNotNull(result);
    }

}
