package test.othertest;

import dataaccess.CoordinatorDAOImpl;
import domain.Coordinator;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPassword {
    @Test
    public void createSentence() throws SQLException {
        CoordinatorDAOImpl coordinatorDAO = new CoordinatorDAOImpl();
        Coordinator coordinator = new Coordinator();
        int staffNumber = 1;
        coordinator.setName("ana");
        coordinator.setLastName("frank");
        coordinator.setGender(0);
        coordinator.setPhone("2289123400");
       // coordinator.setEmail("este@gmail.com");
        //coordinator.setAlternateEmail("estebaGa@gmail.com");
        //coordinator.setPhone("2289000024");
        //coordinator.setPassword("esteban4j1");
        List<String> Colums = new ArrayList<>();
        List<String> DatesUpdate = new ArrayList<>();
        Colums.add("Name");
        Colums.add("LastName");
        Colums.add("Gender");
        Colums.add("Phone");


        boolean sentence = coordinatorDAO.updateCoordinator(1, coordinator,Colums);
        System.out.println(sentence);
        Assert.assertFalse(sentence);
    }
}
