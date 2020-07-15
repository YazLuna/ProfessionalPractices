package test.othertest;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class DeleteTest {

    @Test
    public void testDeletePractitioner() {
        boolean result;
        result = Practitioner.deletePractitioner("S18098984","Inactive");
        Assert.assertTrue(result);

    }

    @Test
    public void testDeletePractitionerNull() {
        boolean result;
        result = Practitioner.deletePractitioner(null,"Inactive");
        Assert.assertFalse(result);

    }

    @Test
    public void testDeleteTeacher() {
        boolean result;
        result = Teacher.deleteTeacher("Inactive","2020-07-09",34);
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteTeacherNull() {
        boolean result;
        result = Teacher.deleteTeacher("Inactive","2020-07-09",600);
        Assert.assertFalse(result);
    }

    @Test
    public void testDeleteCoordinator() throws SQLException {
        boolean result;
        Coordinator coordinator = new Coordinator();
        result = Coordinator.deleteCoordinator("Inactive","2020-07-07");
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteCoordinatorNull() {
        boolean result;
        result = Coordinator.deleteCoordinator("Inactive","2020-07-07");
        Assert.assertTrue(result);
    }
}
