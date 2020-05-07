package test.testadministrator;

import org.junit.Assert;
import org.junit.Test;
import domain.Coordinator;

public class RecoverCoordinatorTest {
    @Test
    public void testRecoverCoordinator() {
        int result = 0;
        int staffNumber = 2;
        Coordinator Coordinator = new Coordinator();
        Coordinator.setStaffNumber(staffNumber);
        result = Coordinator.recoverCoordinator();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testRecoverCoordinatorNull() {
        int result = 0;
        Coordinator Coordinator = new Coordinator();
        result = Coordinator.recoverCoordinator();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testRecoverCoordinatorNotExist() {
        int result = 0;
        int staffNumber = 20;
        Coordinator Coordinator = new Coordinator();
        Coordinator.setStaffNumber(staffNumber);
        result = Coordinator.recoverCoordinator();
        Assert.assertEquals(1,result);
    }
}
