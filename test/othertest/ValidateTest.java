package test.othertest;

import domain.Coordinator;
import domain.Practitioner;
import domain.Search;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class ValidateTest {


	@Test
	public void validAcademicAdd () {
		Teacher teacher = new Teacher();
		teacher.setStaffNumber(11265432);
		teacher.setEmail("yaz@hotmail.com");
		teacher.setAlternateEmail("yazmin@hotmail.com");
		teacher.setPhone("2281564676");
		teacher.setUserName("toñito12345");
		int validateAcademicAdd= Teacher.validateAcademicAdd(teacher.getStaffNumber(), teacher.getEmail()
				, teacher.getAlternateEmail(), teacher.getPhone(), teacher.getUserName());
		Assert.assertEquals(Search.FOUND.getValue(), validateAcademicAdd);
	}

	@Test
	public void validAcademicUpdate () {
		Teacher teacher = new Teacher();
		teacher.setStaffNumber(11265432);
		teacher.setEmail("yaz@hotmail.com");
		teacher.setAlternateEmail("yazmin@hotmail.com");
		teacher.setPhone("2281564676");
		int validateAcademicAdd= Teacher.validateAcademicUpdate(teacher.getStaffNumber(), teacher.getEmail()
				, teacher.getAlternateEmail(), teacher.getPhone());
		Assert.assertEquals(Search.FOUND.getValue(), validateAcademicAdd);
	}


}
