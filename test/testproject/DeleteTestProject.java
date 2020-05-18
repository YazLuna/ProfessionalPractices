package test.testproject;

import domain.Project;
import org.junit.Assert;
import org.junit.Test;

public class DeleteTestProject {
    @Test
    public void testDeleteProjects(){
        String result;
        Project project = new Project();
        project.setIdProject(16);
        result = project.deleteProject();
        Assert.assertEquals("The project was successfully removed",result);
    }
}
