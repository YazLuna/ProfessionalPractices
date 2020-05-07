package test.testadministrator;

import org.junit.Assert;
import org.junit.Test;
import domain.Coordinator;

public class DeleteCoordinatorTest {
    @Test
    public void testDeleteCoordinator() {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(2);
        coordinator.setDischargeDate("2020-05-06");
        result = coordinator.deleteCoordinator();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testDeleteCoordinatorNull() {
        int result;
        Coordinator coordinator = new Coordinator();
        result = coordinator.deleteCoordinator();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testDeleteCoordinatorNotExist() {
        int result;
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(200);
        result = coordinator.deleteCoordinator();
        Assert.assertEquals(0,result);
    }
}
