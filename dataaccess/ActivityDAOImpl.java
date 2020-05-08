package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import domain.Activity;

/**
 * Implementation of the interface IActivityDAO
 * @author Ivana Correa
 * @version 08/05/2020
 */

public class ActivityDAOImpl implements IActivityDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consult;
    private ResultSet result;

    public ActivityDAOImpl() { connexion= new Connexion(); }

    @Override
    public int addActivity(Activity activity) {
        int finalScore = 0;
        try {
            connection = connexion.getConnection();
            String queryActivity = "INSERT INTO Activity (value, description, name, deliverDate) VALUES (?,?,?,?)";
            PreparedStatement sentence = connection.prepareStatement(queryActivity);
            result = sentence.executeQuery();
            while (result.next()){
                sentence.setInt(1, activity.getValue());
                sentence.setString(2, activity.getDescription());
                sentence.setString(3, activity.getName());
                sentence.setString(4, activity.getDeliverDate());
            }
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public int deleteActivity(String name) {
        int finalScore = 0;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("DELETE Activity INNER JOIN user ON teacher.idUser = user.idUser WHERE name = ?");
            sentence.setString(1, name);
            sentence.executeQuery();
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public int visualizeActivity(String name) {
        int finalScore = 0;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("SELECT * FROM Activity WHERE name = ?");
            sentence.setString(1, name);
            sentence.executeQuery();
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public int updateActivity(String name, Activity activity) {
        int finalScore = 0;
        try{
            connection =connexion.getConnection();
            String queryActivity = "UPDATE Activity INNER JOIN user ON teacher.idUser = user.idUser SET activity.value = ?, activity.description = ?, activity.name = ?, activity.deliverDate = ? WHERE activity.name = ?";
            PreparedStatement sentence=connection.prepareStatement(queryActivity);
            sentence.setInt(1, activity.getValue());
            sentence.setString(2, activity.getDescription());
            sentence.setString(3, activity.getDeliverDate());
            sentence.setString(4, activity.getName());
            sentence.setString(5, name);
            sentence.executeQuery();
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public List <Activity> allActivity() {
        List<Activity> activities = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consult = connection.createStatement();
            result = consult.executeQuery("SELECT * FROM Activity");
            while(result.next()){
                Activity activity = new Activity();
                activity.setValue(result.getInt("value"));
                activity.setDescription(result.getString("description"));
                activity.setName(result.getString("name"));
                activity.setDeliverDate(result.getString("deliverDate"));
                activities.add(activity);
            }
        }catch(SQLException ex){
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return activities;
    }
}
