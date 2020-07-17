package test.testusers;

import domain.Coordinator;
import domain.Search;
import domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestCoordinator {
	@Test
	public void testAddCoordinator() {
		boolean result = false;
		Coordinator coordinator = new Coordinator();
		coordinator.setName("Amairani");
		coordinator.setLastName("Hernandez");
		coordinator.setGender(0);
		coordinator.setEmail("ama@gmail.com");
		coordinator.setAlternateEmail("amaHer@gmail.com");
		coordinator.setPhone("2281034676");
		coordinator.setStaffNumber(46);
		coordinator.setRegistrationDate("2020-04-29");
		coordinator.setPassword("Wigetazd54");
		coordinator.setUserName("hermandez12A");
		result = Coordinator.addUser((User)coordinator);
		result = Coordinator.addCoordinator(coordinator);
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
	public void testUpdateCoordinator() {
		Coordinator coordinator = new Coordinator();
		coordinator.setName("Yaz");
		coordinator.setLastName("Yoongi");
		coordinator.setPhone("2281564678");
		List<String> Colums = new ArrayList<>();
		Colums.add("Name");
		Colums.add("LastName");
		Colums.add("Phone");
		boolean update = Coordinator.updateCoordinator(1, coordinator, Colums);
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
		Assert.assertEquals(Search.FOUND.getValue(), activeCoordinator);
	}

}
