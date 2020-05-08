package test;

import domain.Report;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * Creation of the class TestReportDAOImpl
 * @author Ivana Correa
 * @version 08/05/2020
 */

public class TestReportDAOImpl {

    public void setUp() {
        Report report = new Report();
        report.setName("Reporte de practicas");
        report.setScore(10);
        report.setActivities("Visitar la empresa, actualizar.");
        report.setDeliverDate("2020-05-10");
        report.setCompletionDate("2020-05-09");
        report.addReport();
        Report report1 = new Report();
        report1.setName("Reporte de practicas parte 2");
        report1.setScore(10);
        report1.setActivities("Visitar la empresa, actualizar.");
        report1.setDeliverDate("2020-05-10");
        report1.setCompletionDate("2020-05-09");
        report1.addReport();
    }

    @Test
    public void testAddReport() {
        int result;
        Report report = new Report();
        report.setName("Reporte de avances");
        report.setScore(10);
        report.setActivities("Visitar la empresa, actualizar.");
        report.setDeliverDate("2020-05-10");
        report.setCompletionDate("2020-05-09");
        result = report.addReport();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testAddReportFailed() {
        int result;
        Report report = new Report();
        report.setScore(10);
        report.setActivities("Visitar la empresa, actualizar.");
        report.setDeliverDate("2020-05-10");
        report.setCompletionDate("2020-05-09");
        result = report.addReport();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testDeleteReport() {
        int result;
        Report report = new Report();
        String name = "Reporte de avances";
        result = report.deleteReport(name);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testVisualizeReport() {
        Report result = new Report();
        String name = "Reporte de practicas parte 2";
        result.visualizeReport(name);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testUpdateActivity() {
        int result;
        Report report = new Report();
        String name = "Reporte de practicas";
        report.setName("Reporte de avances parte 2");
        report.setScore(10);
        report.setActivities("Actualizar proyectos");
        report.setCompletionDate("2020-10-09");
        report.setDeliverDate("2020-10-10");
        result = report.updateReport(name);
        Assert.assertEquals(1, result);
    }

    @Test
    public void allReport() {
        List<Report> result = new ArrayList<>();
        Report report = new Report();
        result = report.allReport();
        Assert.assertNotNull(result);
    }

    public void tearDown() {
        String name = "Reporte de avances parte 2";
        Report report = new Report();
        report.deleteReport(name);
        String name1 = "Reporte de practicas parte 2";
        report.deleteReport(name1);
    }
}
