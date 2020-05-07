package test.testadministrator;

import org.junit.Assert;
import org.junit.Test;
import domain.Coordinator;

public class UpdateCoordinatorTest {
    @Test
    public void testUpdateCoordinatorStaffNumber() {
        int result=0;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("esteban@gmail.com");
        coordinator.setAlternateEmail("esteGa@gmail.com");
        coordinator.setPhone("2281675432");
        coordinator.setStaffNumber(1);
        coordinator.setRegistrationDate("2020-05-05");
        coordinator.setPassword("esteban4j1");
        result = coordinator.updateCoordinator(2);
        Assert.assertEquals(0,result);
    }
}
