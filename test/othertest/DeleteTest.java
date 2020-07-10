package test.othertest;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class DeleteTest {

    @Test
    public void testDeletePractitioner() {
        boolean result;
        Practitioner practitioner = new Practitioner();
        practitioner.setEnrollment("s18098984");
        //result = practitioner.deletePractitioner();
        //Assert.assertEquals(1,result);

    }

    @Test
    public void testDeleteTeacher() {
        int result;
        Teacher Teacher = new Teacher();
        Teacher.setStaffNumber(4);
        //result = Teacher.deleteTeacher();
        //Assert.assertEquals(1,result);
    }
}
