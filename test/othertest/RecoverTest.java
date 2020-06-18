package test.othertest;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class RecoverTest {

    @Test
    public void testRecoverPractitioner() {
        boolean result;
        String enrollment = "s18098984";
        Practitioner practitioner = new Practitioner();
        practitioner.setEnrollment(enrollment);
        result = practitioner.recoverPractitioner();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testRecoverTeacher() {
        boolean result = false;
        int staffNumber = 4;
        Teacher Teacher = new Teacher();
        Teacher.setStaffNumber(staffNumber);
        result = Teacher.recoverTeacher();
        Assert.assertTrue(result);
    }
}
