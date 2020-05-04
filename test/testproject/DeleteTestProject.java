package test.testproject;

import domain.Project;
import org.junit.Assert;
import org.junit.Test;

public class DeleteTestProject {
    @Test
    public void testDeleteProjects(){
        int result;
        Project project = new Project();
        project.setIdProject(4);
        result = project.deleteProject();
        Assert.assertEquals(1,result);
    }
}
