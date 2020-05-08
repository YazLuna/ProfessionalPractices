package test;

import domain.ReportPartial;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class TestReportPartialDAOImpl {

    public void setUp() {
        ReportPartial reportPartial = new ReportPartial();
        reportPartial.setNumberReport(2);
        reportPartial.setMethodology("Modelo en espiral");
        reportPartial.setObjective("Lograr implementar de forma correcta el modelo en espiral.");
        reportPartial.setResultsObtained("Se logró correctamente implementar.");
        reportPartial.setHoursCovered(35);
        reportPartial.setObservations("Todo salio bien.");
        reportPartial.addReportPartial();
    }

    @Test
    public void testAddReportPartial() {
        int result;
        ReportPartial reportPartial = new ReportPartial();
        reportPartial.setNumberReport(1);
        reportPartial.setMethodology("Modelo en espiral");
        reportPartial.setObjective("Lograr implementar de forma correcta el modelo en espiral.");
        reportPartial.setResultsObtained("Se logró correctamente implementar.");
        reportPartial.setHoursCovered(35);
        reportPartial.setObservations("Todo salio bien.");
        result = reportPartial.addReportPartial();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testAddReportPartialFailed() {
        int result;
        ReportPartial reportPartial = new ReportPartial();
        reportPartial.setMethodology("Modelo en espiral");
        reportPartial.setObjective("Lograr implementar de forma correcta el modelo en espiral.");
        reportPartial.setResultsObtained("Se logró correctamente implementar.");
        reportPartial.setHoursCovered(35);
        reportPartial.setObservations("Todo salio bien.");
        result = reportPartial.addReportPartial();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testDeleteReportPartial() {
        ReportPartial result = new ReportPartial();
        int numberReport = 1;
        result.deleteActivity(numberReport);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testVisualizeReportPartial() {
        ReportPartial result = new ReportPartial();
        int numberReport = 2;
        result.visualizeReportPartial(numberReport);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testAllReportPartial() {
        List<ReportPartial> result = new ArrayList<>();
        ReportPartial reportPartial = new ReportPartial();
        result = reportPartial.allReportPartial();
        Assert.assertNotNull(result);
    }

    public void tearDown() {
        int numberReport = 2;
        ReportPartial reportPartial = new ReportPartial();
        reportPartial.deleteActivity(numberReport);
    }
}
