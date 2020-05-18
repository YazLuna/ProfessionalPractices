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

public class DeleteCoordinatorTest {
    @Test
    public void testDeleteCoordinator() throws SQLException {
        boolean result;
        Coordinator coordinator = new Coordinator();
        coordinator.setStaffNumber(4);
        coordinator.setStatus("Inactive");
        coordinator.setDischargeDate("2020-05-06");
        result = coordinator.deleteCoordinator(coordinator.getStatus(),coordinator.getDischargeDate());
        Assert.assertTrue(result);
    }

}
