package test;

import domain.Activity;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class TestActivityDAOImpl {

    public void setUp() {
        Activity activity = new Activity();
        activity.setName("Reporte de lectura1");
        activity.setDescription("Hacer reporte de lectura sobre procesos de software.");
        activity.setValue(8);
        activity.setDeliverDate("2020-05-20-13:30");
        activity.addActivity();

        Activity activity1 = new Activity();
        activity1.setName("Diagrama de actividad");
        activity1.setDeliverDate("2020-11-09-15:40");
        activity1.setValue(5);
        activity1.setDescription("Utilizar colores y arial 12.");
        activity1.addActivity();
    }

    @Test
    public void testAddActivity() {
        int result;
        Activity activity = new Activity();
        activity.setValue(5);
        activity.setName("Reporte de lectura");
        activity.setDeliverDate("2020-10-09-12:40");
        activity.setDescription("Mínimo 2 cuartillas, máximo 3. En formato de PDF.");
        result = activity.addActivity();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testAddActivityFailed() {
        int result;
        Activity activity = new Activity();
        activity.setDescription("Hacer un mapa conceptual. Entregar en archivo PDF.");
        activity.setValue(10);
        activity.setDeliverDate("2020-07-10-13:30");
        result = activity.addActivity();
        Assert.assertEquals(0,result);
    }

    @Test
    public void testDeleteActivity() {
        Activity result = new Activity();
        String name = "Reporte de lectura";
        result.deleteActivity(name);
        Assert.assertEquals(1,result);
    }

    @Test
    public void testVisualizeActivity() {
        Activity result = new Activity();
        String name = "Reporte de lectura1";
        result.visualizeActivity(name);
        Assert.assertEquals(1, result);
    }

    @Test
    public void updateActivity() {
        int result;
        Activity activity = new Activity();
        String name = "Diagrama de actividad";
        activity.setName("Diagrama de secuencia");
        activity.setDeliverDate("2020-06-10-14:40");
        activity.setValue(8);
        activity.setDescription("Entregar en archivo PDF.");
        result = activity.updateActivity(name);
        Assert.assertEquals(1, result);
    }

    @Test
    public void allActivity() {
        List<Activity> result = new ArrayList<>();
        Activity activity = new Activity();
        result = activity.allActivity();
        Assert.assertNotNull(result);
    }

    public void tearDown() {
        String name = "Reporte de lectura1";
        Activity activity = new Activity();
        activity.deleteActivity(name);
        String name1 = "Diagrama de secuencia";
        activity.deleteActivity(name1);
    }
}
