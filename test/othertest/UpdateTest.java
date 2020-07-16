package test.othertest;

import dataaccess.TeacherDAOImpl;
import domain.Coordinator;
import domain.Practitioner;
import domain.Teacher;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UpdateTest {
    @Test
    public void testUpdateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Ana María");
        teacher.setLastName("Espinosa");
        teacher.setPhone("2290123456");
        List<String> Colums = new ArrayList<>();
        Colums.add("Name");
        Colums.add("LastName");
        Colums.add("Phone");
        boolean update = Teacher.updateTeacher(1, teacher, Colums);
        Assert.assertTrue(update);
    }

    @Test
    public void testUpdateCoordinator() {
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Yaz");
        coordinator.setLastName("Yoongi");
        coordinator.setPhone("2281564678");
        List<String> Colums = new ArrayList<>();
        Colums.add("Name");
        Colums.add("LastName");
        Colums.add("Phone");
        boolean update = Coordinator.updateCoordinator(1, coordinator, Colums);
        Assert.assertTrue(update);
    }

    @Test
    public void testUpdatePractitioner() {
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Alejandra");
        practitioner.setLastName("Luna");
        practitioner.setPhone("2081901279");
        List<String> Colums = new ArrayList<>();
        Colums.add("Name");
        Colums.add("LastName");
        Colums.add("Phone");
        boolean update = Practitioner.updatePractitioner("S18012124", practitioner, Colums);
        Assert.assertTrue(update);
    }
}
