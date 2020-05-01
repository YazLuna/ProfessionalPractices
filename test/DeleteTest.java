package test;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class DeleteTest {
    @Test
    public void testDeleteCoordinator() {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(01);
        result = coordinator.deleteCoordinator();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testDeletePractitioner() {
        int result;
        Practitioner practitioner = new Practitioner();
        practitioner.setEnrollment("s18098984");
        result = practitioner.deletePractitioner();
        Assert.assertEquals(1,result);

    }

    @Test
    public void testDeleteTeacher() {
        int result;
        Teacher Teacher = new Teacher();
        Teacher.setStaffNumber(04);
        result = Teacher.deleteTeacher();
        Assert.assertEquals(1,result);
    }
}
