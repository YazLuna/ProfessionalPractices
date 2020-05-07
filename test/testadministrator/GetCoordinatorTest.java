package test.testadministrator;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import domain.Coordinator;

public class GetCoordinatorTest {
    @Test
    public void testGetCoordinator() {
        String status = "Active";
        Coordinator result = new Coordinator();
        result = result.getCoordinator();
        Assert.assertEquals(status, result.getStatus());
    }

    @Test
    public void testGetAllCoordinator() {
        List<Coordinator> result;;
        Coordinator coordinator = new Coordinator();
        result = coordinator.getAllCoordinator();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetNullCoordinator() {
        Coordinator result = new Coordinator();
        result = result.getCoordinator();
        Assert.assertNull(result);
    }

}
