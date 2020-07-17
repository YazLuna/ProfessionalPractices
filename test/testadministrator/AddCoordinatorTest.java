package test.testadministrator;

import org.junit.Assert;
import org.junit.Test;
import domain.Coordinator;
import java.sql.SQLException;

/**
 * DAO User
 * @author Yazmin
 * @version 17/05/2020
 */

public class AddCoordinatorTest {
   /*
   @Before
    public void Add() throws SQLException {
        boolean result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("esteGa@gmail.com");
        coordinator.setPhone("2281564676");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setUserName("Esteban23");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator();
        Assert.assertTrue(result);
    }
    @After
    public void Delete() {
        final Connexion connexion;
        connexion = new Connexion();
        try {
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceDeleteCoordinator =
                    connection.prepareStatement("DELETE FROM Coordinator, UserStatus, UserType, LoginAccount, User WHERE Coordinator.idUser = User.idUser AND Coordinator.staffNumber = ?");
            sentenceDeleteCoordinator.setInt(1, 2);
            sentenceDeleteCoordinator.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    @Test
    public void testAddNewCoordinator() throws SQLException {
       /* int result;
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
        result = coordinatorTest.addCoordinator(coordinatorTest);
        Assert.assertTrue(result);
    }

    @Test
    public void testAddDuplicatedCoordinator() throws SQLException {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Ana");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("ana@gmail.com");
        coordinator.setAlternateEmail("anaGa@gmail.com");
        coordinator.setPhone("2281675432");
        coordinator.setStaffNumber(4);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setPassword("esteban4j1");
        coordinator.setUserName("anaG");
        result = coordinator.addCoordinator(coordinator);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddEmptyCoordinator() throws SQLException {
        int result ;
        Coordinator coordinatorEmpty = new Coordinator();
        result = coordinatorEmpty.addCoordinator(coordinatorEmpty);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddOtherCoordinator() throws SQLException {
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
        coordinator.setUserName("anna234");
        coordinator.setPassword("annita422j1");
        result = coordinator.addCoordinator(coordinator);
        Assert.assertFalse(result);
    }

    @Test
    public void testACoordinatorEmailExist() throws SQLException {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("este@gmail.com");
        coordinator.setAlternateEmail("juan@gmail.com");
        coordinator.setPhone("2281564676");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setUserName("Esteban23");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator(coordinator);
        Assert.assertFalse(result);
    }

    @Test
    public void testCoordinatorAlternateEmailExist() throws SQLException {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("juanLopez@gmail.com");
        coordinator.setPhone("2281564676");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setUserName("Esteban23");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator(coordinator);
        Assert.assertFalse(result);
    }

    @Test
    public void testCoordinatorPhoneExist() throws SQLException {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("esteGa@gmail.com");
        coordinator.setPhone("2281901879");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setUserName("Esteban23");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator(coordinator);
        Assert.assertFalse(result);
    }

    @Test
    public void testUserExist() throws SQLException {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("esteGa@gmail.com");
        coordinator.setPhone("2281564676");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setUserName("Juan25");
        coordinator.setPassword("esteban4j1");
        result = coordinator.addCoordinator(coordinator);
        Assert.assertFalse(result);
    }

}*/}}
