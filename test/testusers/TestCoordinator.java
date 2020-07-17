package test.testusers;

import dataaccess.Connexion;
import domain.Coordinator;
import domain.Search;
import domain.User;
import exception.Exception;
import exception.TelegramBot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestCoordinator {
	@Before
	public void testAddCoordinator() {
		Coordinator coordinator = new Coordinator();
		coordinator.setName("Amairani");
		coordinator.setLastName("Hernandez");
		coordinator.setGender(0);
		coordinator.setEmail("ama@gmail.com");
		coordinator.setAlternateEmail("amaHer@gmail.com");
		coordinator.setPhone("2281034676");
		coordinator.setStaffNumber(460);
		coordinator.setRegistrationDate("2020-04-29");
		coordinator.setPassword("Wigetazd54");
		coordinator.setUserName("hermandez12A");
		Coordinator.addUser((User)coordinator);
		Coordinator.addCoordinator(coordinator);
		Coordinator.deleteCoordinator("Inactive","2020-07-07");
	}

	@After
	public void deleteCoordinator () {
		final Connexion connexion = new Connexion();
		boolean result = false;
		try {
			Connection connection = connexion.getConnection();
			String queryDeleteCoordinator = "DELETE Coordinator, LoginAccount, User FROM Coordinator, LoginAccount, User WHERE" +
					" User.idUser = Coordinator.idUser AND LoginAccount.idUser = Coordinator.idUser AND" +
					" Coordinator.staffNumber =?";
			PreparedStatement preparedStatement = connection.prepareStatement(queryDeleteCoordinator);
			preparedStatement.setInt(1, 460);
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		Assert.assertTrue(result);
	}

	@Test
	public void testCoordinatorTeacherNull() {
		boolean result = false;
		Coordinator coordinator = new Coordinator();
		User user = (User)coordinator;
		result = Coordinator.addUser(user);
		result = Coordinator.addCoordinator(coordinator);
		Assert.assertFalse(result);
	}

	@Test
	public void testDeleteCoordinatorNull() {
		boolean result;
		result = Coordinator.deleteCoordinator("Inactive","2020-07-07");
		Assert.assertTrue(result);
	}

	@Test
	public void testGetCoordinator() {
		Coordinator result = Coordinator.getCoordinatorSelected(460);
		Assert.assertEquals(460, result.getStaffNumber());
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
		Assert.assertEquals(0, result.getStaffNumber());
	}

	@Test
	public void testUpdateCoordinator() {
		Coordinator coordinator = new Coordinator();
		coordinator.setName("Yaz");
		coordinator.setLastName("Yoongi");
		List<String> Colums = new ArrayList<>();
		Colums.add("Name");
		Colums.add("LastName");
		boolean update = Coordinator.updateCoordinator(460, coordinator, Colums);
		Assert.assertTrue(update);
	}

	@Test
	public void areCoordinator () {
		int areCoordinator= Coordinator.areCoordinator();
		Assert.assertEquals(Search.FOUND.getValue(), areCoordinator);
	}

	@Test
	public void activeCoordinator () {
		int activeCoordinator= Coordinator.activeCoordinator();
		Assert.assertEquals(Search.NOTFOUND.getValue(), activeCoordinator);
	}

	@Test
	public void validAcademicAdd () {
		Coordinator coordinator = new Coordinator();
		coordinator.setEmail("ama@gmail.com");
		coordinator.setAlternateEmail("amaHer@gmail.com");
		coordinator.setPhone("2281034676");
		coordinator.setUserName("hermandez12A");
		int validateAcademicAdd= Coordinator.validateAcademicAdd(coordinator.getStaffNumber(), coordinator.getEmail()
				, coordinator.getAlternateEmail(), coordinator.getPhone(), coordinator.getUserName());
		Assert.assertEquals(Search.FOUND.getValue(), validateAcademicAdd);
	}

	@Test
	public void validAcademicUpdate () {
		Coordinator coordinator = new Coordinator();
		coordinator.setEmail("ama@gmail.com");
		coordinator.setAlternateEmail("amaHer@gmail.com");
		coordinator.setPhone("2281034676");
		coordinator.setUserName("hermandez12A");
		int validateAcademicAdd= Coordinator.validateAcademicUpdate(coordinator.getStaffNumber(), coordinator.getEmail()
				, coordinator.getAlternateEmail(), coordinator.getPhone());
		Assert.assertEquals(Search.FOUND.getValue(), validateAcademicAdd);
	}

}
