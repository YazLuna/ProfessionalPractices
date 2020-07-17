package test.testusers;

import dataaccess.Connexion;
import domain.Practitioner;
import domain.Search;
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

public class TestPractitioner {
	@Before
	public void testAddPractitioner() {
		Practitioner practitioner = new Practitioner();
		practitioner.setEnrollment("S18098984");
		practitioner.setName("Lucio");
		practitioner.setLastName("Garcia");
		practitioner.setGender(1);
		practitioner.setEmail("lu@gmail.com");
		practitioner.setAlternateEmail("luGar@gmail.com");
		practitioner.setPhone("2281901267");
		practitioner.setUserName("S18098984");
		practitioner.setTerm("FEBRERO-JULIO 2020");
		practitioner.setCredits(298);
		practitioner.setPassword("lucio244");
		Practitioner.addPractitioner(practitioner);
		Practitioner.deletePractitioner("S18098984","Inactive");
	}

	@After
	public void deletePractitioner () {
		final Connexion connexion = new Connexion();
		boolean result = false;
		try {
			Connection connection = connexion.getConnection();
			String queryDeletePractitioner = "DELETE Practitioner, LoginAccount, User FROM Practitioner, LoginAccount, User WHERE" +
					" User.idUser = Practitioner.idUser AND LoginAccount.idUser = Practitioner.idUser AND" +
					" Practitioner.enrollment =?";
			PreparedStatement preparedStatement = connection.prepareStatement(queryDeletePractitioner);
			preparedStatement.setString(1, "S18098984" );
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
	public void testAddPractitionerNull() {
		boolean result;
		Practitioner practitioner = new Practitioner();
		result = Practitioner.addPractitioner(practitioner);
		Assert.assertFalse(result);
	}


	@Test
	public void testDeletePractitionerNull() {
		boolean result;
		result = Practitioner.deletePractitioner(null,"Inactive");
		Assert.assertFalse(result);
	}

	@Test
	public void testGetPractitioner() {
		Practitioner practitioner = new Practitioner();
		practitioner.setEnrollment("S18098984");
		practitioner = Practitioner.getPractitioner(practitioner.getEnrollment());
		Assert.assertEquals("S18098984", practitioner.getEnrollment());
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
	public void testRecoverPractitioner() {
		boolean result;
		String enrollment = "S18098984";
		Practitioner practitioner = new Practitioner();
		practitioner.setEnrollment(enrollment);
		result = Practitioner.recoverPractitioner(practitioner.getEnrollment());
		Assert.assertTrue(result);
	}

	@Test
	public void testUpdatePractitioner() {
		Practitioner practitioner = new Practitioner();
		practitioner.setName("Lucio Alexis");
		practitioner.setLastName("Garcia Perez");
		practitioner.setPhone("2281901255");
		List<String> Colums = new ArrayList<>();
		Colums.add("Name");
		Colums.add("LastName");
		Colums.add("Phone");
		boolean update = Practitioner.updatePractitioner("S18098984", practitioner, Colums);
		Assert.assertTrue(update);
	}

	@Test
	public void arePractitioner () {
		int arePractitioner= Practitioner.arePractitioner();
		Assert.assertEquals(Search.FOUND.getValue(), arePractitioner);
	}

	@Test
	public void activePractitioner () {
		int activeTeacher= Practitioner.activePractitioner();
		Assert.assertEquals(Search.NOTFOUND.getValue(), activeTeacher);
	}

	@Test
	public void validPractitionerAdd () {
		Practitioner practitioner = new Practitioner();
		practitioner.setEnrollment("S18098984");
		practitioner.setEmail("lu@gmail.com");
		practitioner.setAlternateEmail("luGar@gmail.com");
		int validPractitionerAdd= Practitioner.validPractitionerAdd(practitioner);
		Assert.assertEquals(Search.FOUND.getValue(), validPractitionerAdd);
	}

	@Test
	public void validPractitionerUpdate () {
		Practitioner practitioner = new Practitioner();
		practitioner.setEnrollment("S18098984");
		practitioner.setEmail("yaz@hotmail.com");
		practitioner.setAlternateEmail("yazmin@hotmail.com");
		practitioner.setPhone("2281564676");
		int validPractitionerAdd= Practitioner.validPractitionerUpdate(practitioner);
		Assert.assertEquals(Search.FOUND.getValue(), validPractitionerAdd);
	}
}
