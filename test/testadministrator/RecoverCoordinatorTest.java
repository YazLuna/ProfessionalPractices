package test.testadministrator;

import org.junit.Assert;
import org.junit.Test;
import domain.Coordinator;

import java.sql.SQLException;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public class RecoverCoordinatorTest {
    @Test
    public void testRecoverCoordinator() throws SQLException {
        boolean result;
        Coordinator coordinator = new Coordinator();
        coordinator.setStatus("Active");
        coordinator.setStaffNumber(4);
        result = coordinator.recoverCoordinator();
        Assert.assertTrue(result);
    }

    @Test
    public void testRecoverCoordinatorNull() throws SQLException {
        boolean result;
        Coordinator coordinator = new Coordinator();
        coordinator.setStatus("Active");
        result = coordinator.recoverCoordinator();
        Assert.assertFalse(result);
    }

    @Test
    public void testRecoverCoordinatorNotExist() throws SQLException {
        boolean result;
        int staffNumber = 20;
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(staffNumber);
        coordinator.setStatus("Active");
        result = coordinator.recoverCoordinator();
        Assert.assertFalse(result);
    }

    @Test
    public void testRecoverCoordinatorWhenOtherIsActive() throws SQLException {
        boolean result;
        int staffNumber = 1;
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(staffNumber);
        result = coordinator.recoverCoordinator();
        Assert.assertFalse(result);
    }
}
