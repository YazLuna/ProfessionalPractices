package test.testadministrator;

import org.junit.Assert;
import org.junit.Test;
import domain.Coordinator;

public class UpdateCoordinatorTest {
    @Test
    public void testUpdateCoordinator() {
        int result=0;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("este@gmail.com");
        coordinator.setAlternateEmail("estebaGa@gmail.com");
        coordinator.setPhone("2289000024");
        coordinator.setStaffNumber(1);
        coordinator.setPassword("esteban4j1");
        result = coordinator.updateCoordinator(2);
        Assert.assertEquals(1,result);
    }

    @Test
    public void testUpdateCoordinatorNull() {
        int result=0;
        Coordinator coordinator = new Coordinator();
        result = coordinator.updateCoordinator(coordinator.getStaffNumber());
        Assert.assertEquals(0,result);
    }

    @Test
    public void testUpdateCoordinatorEmailExist() {
        int result=0;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("juan@gmail.com");
        coordinator.setAlternateEmail("esteb@gmail.com");
        coordinator.setPhone("2281905671");
        coordinator.setStaffNumber(2);
        coordinator.setPassword("esteban4j1");
        result = coordinator.updateCoordinator(2);
        Assert.assertEquals(0,result);
    }

    @Test
    public void testUpdateCoordinatorAlternateEmailExist() {
        int result=0;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("este@gmail.com");
        coordinator.setAlternateEmail("juanLopez@gmail.com");
        coordinator.setPhone("2289000024");
        coordinator.setStaffNumber(2);
        coordinator.setPassword("esteban4j1");
        result = coordinator.updateCoordinator(1);
        Assert.assertEquals(0,result);
    }

    @Test
    public void testUpdateCoordinatorPhoneExist() {
        int result=0;
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Esteban");
        coordinator.setLastName("Garcia");
        coordinator.setGender(1);
        coordinator.setEmail("este@gmail.com");
        coordinator.setAlternateEmail("estebaGa@gmail.com");
        coordinator.setPhone("2281901879");
        coordinator.setStaffNumber(2);
        coordinator.setPassword("esteban4j1");
        result = coordinator.updateCoordinator(1);
        Assert.assertEquals(0,result);
    }


}
