package test.othertest;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class GetTest {

    @Test
    public void testGetPractitioner() {
        Practitioner practitioner = new Practitioner();
        practitioner.setEnrollment("S18012124");
        practitioner = Practitioner.getPractitioner(practitioner.getEnrollment());
        Assert.assertEquals("S18012124", practitioner.getEnrollment());
    }

    @Test
    public void testGetPractitionerNotExist() {
        Practitioner practitioner = new Practitioner();
        practitioner.setEnrollment("S18000000");
        practitioner = Practitioner.getPractitioner(practitioner.getEnrollment());
        Assert.assertNull(practitioner.getEnrollment());
    }

    @Test
    public void testGetAllPractitioner() {
        List<Practitioner> result = Practitioner.getPractitioners();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetPractitionersActive() {
        List<Practitioner> result = Practitioner.getPractitionersActive();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetCoordinator() {
        Coordinator result = Coordinator.getCoordinatorSelected(1);
        Assert.assertEquals(1, result.getStaffNumber());
    }

    @Test
    public void testGetCoordinatorNoExit() {
        Coordinator result = Coordinator.getCoordinatorSelected(666);
        Assert.assertEquals(0, result.getStaffNumber());
    }

    @Test
    public void testGetAllCoordinator() {
        List<Coordinator> result = Coordinator.getCoordinators();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetCoordinatorActive() {
        Coordinator result = Coordinator.getCoordinator();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetTeacher() {
        Teacher result = Teacher.getTeacherSelected(1);
        Assert.assertEquals(1, result.getStaffNumber());
    }

    @Test
    public void testGetTeacherNoExit() {
        Teacher result = Teacher.getTeacherSelected(666);
        Assert.assertEquals(0, result.getStaffNumber());
    }

    @Test
    public void testGetAllTeacher() {
        List<Teacher> result = Teacher.getTeachers();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetTeacherActive() {
        List<Teacher> result = Teacher.getTeachersActive();
        Assert.assertNotNull(result);
    }


}
