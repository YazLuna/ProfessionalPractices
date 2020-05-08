package test;

import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class UpdateIdTest {
    @Test
    public void testUpdateCoordinatorStaffNumber() {
        int result;
        int staffNumber =1;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Juana");
        coordinator.setLastName("Luna");
        coordinator.setGender(0);
        coordinator.setEmail("juana@gmail.com");
        coordinator.setAlternateEmail("juanaL@gmail.com");
        coordinator.setPhone("2281001879");
        coordinator.setStaffNumber(2);
        coordinator.setRegistrationDate("2020-04-29");
        result = coordinator.updateCoordinator(staffNumber);
        Assert.assertEquals(1,result);
    }

    @Test
    public void testUpdatePractitionerEnrollment() {
        int result = 0;
        String enrollment = "s18098984";
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Ale");
        practitioner.setLastName("Luna");
        practitioner.setGender(0);
        practitioner.setEmail("ale@gmail.com");
        practitioner.setAlternateEmail("aleLuna@gmail.com");
        practitioner.setPhone("2281564676");
        practitioner.setEnrollment("s19012124");
        practitioner.setPeriod("2020-2021");
        result = practitioner.updatePractitioner(enrollment);
        Assert.assertEquals(1,result);
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
        result = teacher.updateTeacher(staffNumber);
        Assert.assertEquals(1,result);
    }
}