package test;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class UpdateTest {
    @Test
    public void testUpdateCoordinator() {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Julio");
        coordinator.setLastName("Lopez");
        coordinator.setGender("Masculino");
        coordinator.setEmail("julio@gmail.com");
        coordinator.setAlternateEmail("juLopez@gmail.com");
        coordinator.setPhone("2281901879");
        coordinator.setStaffNumber(01);
        coordinator.setRegistrationDate("2020-04-29");
        coordinator.setStatus("Active");
        result = coordinator.updateCoordinator();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testUpdatePractitioner() {
        int result = 0;
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Ale");
        practitioner.setLastName("Luna");
        practitioner.setGender("Female");
        practitioner.setEmail("ale@gmail.com");
        practitioner.setAlternateEmail("aleLuna@gmail.com");
        practitioner.setPhone("2281564676");
        practitioner.setEnrollment("s18098984");
        practitioner.setPeriod("2020-2021");
        practitioner.setTurn("Vespertino");
        result = practitioner.updatePractitioner();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testUpdateTeacher() {
        int result;
        Teacher teacher = new Teacher();
        teacher.setName("Pedro");
        teacher.setLastName("Hernandez");
        teacher.setGender("Masculino");
        teacher.setEmail("pablo@gmail.com");
        teacher.setAlternateEmail("pabloHer@gmail.com");
        teacher.setPhone("2281334676");
        teacher.setStaffNumber(04);
        teacher.setTurn("Vespertino");
        teacher.setRegistrationDate("2020-04-29");
        result = teacher.updateTeacher();
        Assert.assertEquals(1,result);
    }

}
