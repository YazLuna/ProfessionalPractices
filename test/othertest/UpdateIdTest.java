package test.othertest;

import dataaccess.TeacherDAOImpl;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UpdateIdTest {


    @Test
    public void testUpdatePractitionerEnrollment() {
        boolean result ;
        String enrollment = "s18098984";
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Ale");
        practitioner.setLastName("Luna");
        practitioner.setGender(0);
        practitioner.setEmail("ale@gmail.com");
        practitioner.setAlternateEmail("aleLuna@gmail.com");
        practitioner.setPhone("2281564676");
        practitioner.setEnrollment("s19012124");
        practitioner.setTerm("2020-2021");
        //result = practitioner.updatePractitioner(enrollment);
        //Assert.assertEquals(1,result);
    }

    @Test
    public void testUpdateTeacherStaffNumber() {
        int result;
        int staffNumber = 4;
        Teacher teacher = new Teacher();
        teacher.setName("Pablo");
        teacher.setLastName("Hernandez");
        teacher.setGender(1);
        teacher.setEmail("pablo@gmail.com");
        teacher.setAlternateEmail("pabloHer@gmail.com");
        teacher.setPhone("2281334676");
        teacher.setStaffNumber(5);
        teacher.setRegistrationDate("2020-04-29");
        //result = teacher.updateTeacher(staffNumber);
       // Assert.assertEquals(1,result);
    }

    @Test
    public void testUpdateDinnamic() {
        TeacherDAOImpl coordinatorDAO = new TeacherDAOImpl();
        Teacher teacher = new Teacher();
        int staffNumber = 2;
        teacher.setName("Ana María");
        teacher.setLastName("Espinosa");
        //teacher.setGender(0);
        //teacher.setPhone("2289123400");
        // coordinator.setEmail("este@gmail.com");
        //coordinator.setAlternateEmail("estebaGa@gmail.com");
        //coordinator.setPhone("2289000024");
        //coordinator.setPassword("esteban4j1");
        List<String> Colums = new ArrayList<>();
        List<String> DatesUpdate = new ArrayList<>();
        Colums.add("Name");
        Colums.add("LastName");
        //Colums.add("Gender");
        //Colums.add("Phone");


        boolean sentence = coordinatorDAO.updateTeacher(2, teacher, Colums);
        System.out.println(sentence);
        Assert.assertTrue(sentence);
    }
}
