package test.othertest;

import dataaccess.CoordinatorDAOImpl;
import dataaccess.UserMethodDAOImpl;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class AddTest {

    @Test
    public void testAddPractitioner() {
        int result;
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Lucio");
        practitioner.setLastName("Garcia");
        practitioner.setGender(1);
        practitioner.setEmail("lu@gmail.com");
        practitioner.setAlternateEmail("luGar@gmail.com");
        practitioner.setPhone("2281901267");
        practitioner.setEnrollment("s18098984");
        practitioner.setPeriod("FEBRERO-JULIO 2020");
        practitioner.setPassword("lucio244");
        result = practitioner.addPractitioner();
        Assert.assertEquals(1,result);

    }

    @Test
    public void testAddTeacher() {
        int result = 0;
        Teacher teacher = new Teacher();
        teacher.setName("Pablo");
        teacher.setLastName("Hernandez");
        teacher.setGender(1);
        teacher.setEmail("pablo@gmail.com");
        teacher.setAlternateEmail("pabloHer@gmail.com");
        teacher.setPhone("2281334676");
        teacher.setStaffNumber(4);
        teacher.setRegistrationDate("2020-04-29");
        result= teacher.addTeacher();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testSearch() throws SQLException {
        int result =0;
        UserMethodDAOImpl us = new UserMethodDAOImpl();
        result=us.searchIdUserStatus("Active");
        Assert.assertEquals(0,result);
    }

    @Test
    public void testSearchIsActive() throws SQLException {
        boolean result =false;
        CoordinatorDAOImpl us = new CoordinatorDAOImpl();
        result=us.activeCoordinator();
        Assert.assertTrue(result);
    }
}
