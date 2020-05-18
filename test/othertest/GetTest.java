package test.othertest;

import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetTest {


    @Test
    public void testGetPractitioner() {
        Practitioner result = new Practitioner();
        result.setEnrollment("s18098984");
        result = result.getPractitioner();
        Assert.assertEquals("s18098984", result.getEnrollment());
    }


    @Test
    public void testGetAllPractitioner() {
        List<Practitioner> result = new ArrayList<>();;
        Practitioner practitioner = new Practitioner();
        result = practitioner.getAllPractitioner();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetPractitionersActive() {
        List<Practitioner> result = new ArrayList<>();;
        Practitioner practitioner = new Practitioner();
        result = practitioner.getPractitionersActive();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetTeacher() {
        Teacher result = new Teacher();
        result.setStaffNumber(4);
        result = result.getTeacher();
        Assert.assertEquals(4, result.getStaffNumber());
    }

    @Test
    public void testGetAllTeacher() {
        List<Teacher> result = new ArrayList<>();;
        Teacher teacher = new Teacher();
        result = teacher.getAllTeacher();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetTeacherActive() {
        List<Teacher> result = new ArrayList<>();;
        Teacher teacher = new Teacher();
        result = teacher.getTeachersActive();
        Assert.assertNotNull(result);
    }
}
