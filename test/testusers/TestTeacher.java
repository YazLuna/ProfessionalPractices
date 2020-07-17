package test.testusers;

import dataaccess.Connexion;
import domain.Search;
import domain.Teacher;
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

public class TestTeacher {
	@Before
	public void testAddTeacher() {
		Teacher teacher = new Teacher();
		teacher.setName("Esteban");
		teacher.setLastName("Hernandez");
		teacher.setGender(1);
		teacher.setEmail("pablo@gmail.com");
		teacher.setAlternateEmail("pabloHer@gmail.com");
		teacher.setPhone("2281334676");
		teacher.setStaffNumber(34);
		teacher.setRegistrationDate("2020-04-29");
		teacher.setPassword("d9a11bc382287cf0c7c585e7a79fdfda90cc6f9db586ef2bb6d88d81d9edb97941591" +
				"61229ddcfabc4ec24c29dad037605a5f48a67da5ec535b6a131309812ef");
		teacher.setUserName("pablito");
		Teacher.addUser((User)teacher);
		Teacher.addTeacher(teacher);
		Teacher.deleteTeacher("Inactive","2020-07-09",34);
	}

	@After
	public void deleteTeacher () {
		final Connexion connexion = new Connexion();
		try {
			Connection connection = connexion.getConnection();
			String queryDeleteTeacher = "DELETE Teacher, LoginAccount, User FROM Teacher, LoginAccount, User WHERE" +
					" User.idUser = Teacher.idUser AND LoginAccount.idUser = Teacher.idUser AND" +
					" Teacher.staffNumber =?";
			PreparedStatement preparedStatement = connection.prepareStatement(queryDeleteTeacher);
			preparedStatement.setInt(1, 34);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
	}

	@Test
	public void testAddTeacherNull() {
		boolean result = false;
		Teacher teacher = new Teacher();
		result = Teacher.addUser((User)teacher);
		result = Teacher.addTeacher(teacher);
		Assert.assertFalse(result);
	}

	@Test
	public void testDeleteTeacherNull() {
		boolean result;
		result = Teacher.deleteTeacher("Inactive","2020-07-09",600);
		Assert.assertFalse(result);
	}

	@Test
	public void testGetTeacher() {
		Teacher result = Teacher.getTeacherSelected(34);
		Assert.assertEquals(34, result.getStaffNumber());
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

	@Test
	public void testRecoverTeacher() {
		boolean result = false;
		int staffNumber = 34;
		Teacher teacher = new Teacher();
		teacher.setStaffNumber(staffNumber);
		result = Teacher.recoverTeacher(staffNumber);
		Assert.assertTrue(result);
	}

	@Test
	public void testUpdateTeacher() {
		Teacher teacher = new Teacher();
		teacher.setName("Esteban Gabriel");
		teacher.setLastName("Espinosa");
		List<String> Colums = new ArrayList<>();
		Colums.add("Name");
		Colums.add("LastName");
		boolean update = Teacher.updateTeacher(34, teacher, Colums);
		Assert.assertTrue(update);
	}

	@Test
	public void areTeacher () {
		int areTeacher= Teacher.areTeachers();
		Assert.assertEquals(Search.FOUND.getValue(), areTeacher);
	}

	@Test
	public void activeTeacher () {
		int result = Search.NOTFOUND.getValue();
		int activeTeacher= Teacher.activeTeachers();
		Assert.assertEquals(1, activeTeacher);
	}


}
