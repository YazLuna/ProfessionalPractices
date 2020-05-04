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
        result = project.ListProjects();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetProject () {
        Project result = new Project();
        result = result.getProject("System management TTB");
        Assert.assertNotNull(result);
    }
}
