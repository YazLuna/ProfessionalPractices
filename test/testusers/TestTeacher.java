package test.testusers;

import domain.Search;
import domain.Teacher;
import domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestTeacher {
	@Test
	public void testAddTeacher() {
		boolean result = false;
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
		result = Teacher.addUser((User)teacher);
		result = Teacher.addTeacher(teacher);
		Assert.assertTrue(result);
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
	public void testDeleteTeacher() {
		boolean result;
		result = Teacher.deleteTeacher("Inactive","2020-07-09",34);
		Assert.assertTrue(result);
	}

	@Test
	public void testDeleteTeacherNull() {
		boolean result;
		result = Teacher.deleteTeacher("Inactive","2020-07-09",600);
		Assert.assertFalse(result);
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

	@Test
	public void testRecoverTeacher() {
		boolean result = false;
		int staffNumber = 4;
		Teacher Teacher = new Teacher();
		Teacher.setStaffNumber(staffNumber);
		// result = Teacher.recoverTeacher(staffNumber);
		//Assert.assertTrue(result);
	}

	@Test
	public void testUpdateTeacher() {
		Teacher teacher = new Teacher();
		teacher.setName("Ana María");
		teacher.setLastName("Espinosa");
		teacher.setPhone("2290123456");
		List<String> Colums = new ArrayList<>();
		Colums.add("Name");
		Colums.add("LastName");
		Colums.add("Phone");
		boolean update = Teacher.updateTeacher(1, teacher, Colums);
		Assert.assertTrue(update);
	}

	@Test
	public void areTeacher () {
		int areTeacher= Teacher.areTeachers();
		Assert.assertEquals(Search.FOUND.getValue(), areTeacher);
	}

	@Test
	public void activeTeacher () {
		int activeTeacher= Teacher.activeTeachers();
		Assert.assertEquals(Search.FOUND.getValue(), activeTeacher);
	}


}
