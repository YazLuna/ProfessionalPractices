package test.testadministrator;

import dataaccess.UserMethodDAOImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import domain.Coordinator;

import java.sql.SQLException;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public class AddCoordinatorTest {
    /*@Before
    public void Add()  {
         Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("esteGa@gmail.com");
        coordinator.setPhone("2281675432");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator();
        Assert.assertEquals(1,result);
    }*/

    @After
    public void Delete() {

    }

    @Test
    public void testAddNewCoordinator() {
        int result;
        Coordinator coordinatorTest = new Coordinator();
        coordinatorTest.setName("Juan");
        coordinatorTest.setLastName("Lopez");
        coordinatorTest.setGender(1);
        coordinatorTest.setEmail("juan@gmail.com");
        coordinatorTest.setAlternateEmail("juanLopez@gmail.com");
        coordinatorTest.setPhone("2281901879");
        coordinatorTest.setStaffNumber(4);
        coordinatorTest.setRegistrationDate("2020-04-29");
        coordinatorTest.setPassword("Wigettaz4BTS");
        coordinatorTest.setUserName("Juan25");
        result = coordinatorTest.addCoordinator();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testAddDuplicatedCoordinator()  {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("esteGa@gmail.com");
        coordinator.setPhone("2281675432");
        coordinator.setStaffNumber(1);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testAddEmptyCoordinator()  {
        int result= 0 ;
        Coordinator coordinatorEmpty = new Coordinator();
        result = coordinatorEmpty.addCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testAddOtherCoordinator()  {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Ana");
        coordinator.setLastName("Garcia");
        coordinator.setGender(0);
        coordinator.setEmail("ana@gmail.com");
        coordinator.setAlternateEmail("anaGa@gmail.com");
        coordinator.setPhone("2282563899");
        coordinator.setStaffNumber(3);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setPassword("annita422j1");
        result = coordinator.addCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testAddDifferentUserDuplicatedCoordinator()  {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Ana");
        coordinator.setLastName("Garcia");
        coordinator.setGender(0);
        coordinator.setEmail("ana@gmail.com");
        coordinator.setAlternateEmail("anaGa@gmail.com");
        coordinator.setPhone("2282563899");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setPassword("ana11234567");
        result = coordinator.addCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testAddDuplicatedUser()  {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("esteGa@gmail.com");
        coordinator.setPhone("2281675432");
        coordinator.setStaffNumber(6);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testACoordinatorEmailExist() {
        int result;
        Coordinator coordinatorTest = new Coordinator();
        coordinatorTest.setName("Juan");
        coordinatorTest.setLastName("Lopez");
        coordinatorTest.setGender(1);
        coordinatorTest.setEmail("esteban@gmail.com");
        coordinatorTest.setAlternateEmail("juanLopez@gmail.com");
        coordinatorTest.setPhone("2281901879");
        coordinatorTest.setStaffNumber(1);
        coordinatorTest.setRegistrationDate("2020-04-29");
        coordinatorTest.setPassword("Wigettaz4BTS");
        result = coordinatorTest.addCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testCoordinatorAlternateEmailExist()  {
        int result;
        Coordinator coordinatorTest = new Coordinator();
        coordinatorTest.setName("Juan");
        coordinatorTest.setLastName("Lopez");
        coordinatorTest.setGender(1);
        coordinatorTest.setEmail("juan@gmail.com");
        coordinatorTest.setAlternateEmail("esteGa@gmail.com");
        coordinatorTest.setPhone("2281901879");
        coordinatorTest.setStaffNumber(1);
        coordinatorTest.setRegistrationDate("2020-04-29");
        coordinatorTest.setPassword("Wigettaz4BTS");
        result = coordinatorTest.addCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testCoordinatorPhoneExist()  {
        int result;
        Coordinator coordinatorTest = new Coordinator();
        coordinatorTest.setName("Juan");
        coordinatorTest.setLastName("Lopez");
        coordinatorTest.setGender(1);
        coordinatorTest.setEmail("juan@gmail.com");
        coordinatorTest.setAlternateEmail("juanLopez@gmail.com");
        coordinatorTest.setPhone("2281675432");
        coordinatorTest.setStaffNumber(1);
        coordinatorTest.setRegistrationDate("2020-04-29");
        coordinatorTest.setPassword("Wigettaz4BTS");
        result = coordinatorTest.addCoordinator();
        Assert.assertEquals(0,result);
    }

}
