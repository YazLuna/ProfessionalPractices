package test;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class AddTest {
    @Test
    public void testAddCoordinator() {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Juan");
        coordinator.setLastName("Lopez");
        coordinator.setGender("Masculino");
        coordinator.setEmail("juan@gmail.com");
        coordinator.setAlternateEmail("juanLopez@gmail.com");
        coordinator.setPhone("2281901879");
        coordinator.setStaffNumber(01);
        coordinator.setRegistrationDate("2020-04-29");
        result = coordinator.addCoordinator();
        Assert.assertEquals(1,result);

    }

    @Test
    public void testAddPractitioner() {
        int result;
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Pablo");
        practitioner.setLastName("Lopez");
        practitioner.setGender("Male");
        practitioner.setEmail("pablo@gmail.com");
        practitioner.setAlternateEmail("pabloLopez@gmail.com");
        practitioner.setPhone("228012124");
        practitioner.setEnrollment("s17098984");
        practitioner.setPeriod("2020-2021");
        practitioner.setTurn("Vespertino");
        result = practitioner.addPractitioner();
        Assert.assertEquals(1,result);

    }


    @Test
    public void testAddTeacher() {
        int result = 0;
        Teacher teacher = new Teacher();
        teacher.setName("Pablo");
        teacher.setLastName("Hernandez");
        teacher.setGender("Masculino");
        teacher.setEmail("pablo@gmail.com");
        teacher.setAlternateEmail("pabloHer@gmail.com");
        teacher.setPhone("2281334676");
        teacher.setStaffNumber(04);
        teacher.setTurn("Vespertino");
        teacher.setRegistrationDate("2020-04-29");
        result= teacher.addTeacher();
        Assert.assertEquals(1,result);
    }
}
