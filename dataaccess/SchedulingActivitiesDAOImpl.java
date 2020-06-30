package dataaccess;

import domain.SchedulingActivities;
import domain.Search;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SchedulingActivitiesDAOImpl implements ISchedulingActivitiesDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the SchedulingActivitiesDAOImpl class
     */
    public SchedulingActivitiesDAOImpl (){
        connexion= new Connexion();
    }

    @Override
    public boolean addSchedulingActivities(int idProject, SchedulingActivities schedulingActivitiesProject) {
        boolean result = false;
        int idMonth;
        idMonth = getIdMonth(schedulingActivitiesProject.getMonth());
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceAddScheduling = connection.prepareStatement("insert into SchedulingActivities" +
                    "(activity,idProject,idMonth) values(?,?,?)");
            sentenceAddScheduling.setString(1, schedulingActivitiesProject.getActivity());
            sentenceAddScheduling.setInt(2, idProject);
            sentenceAddScheduling.setInt(3, idMonth);
            sentenceAddScheduling.executeUpdate();
            result = true;
        }catch(SQLException ex){
            Logger.getLogger(SchedulingActivitiesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if (connexion != null) {
                connexion.closeConnection();
            }
            return result;
        }
    }

    @Override
    public List<SchedulingActivities> getAllSchedulingActivities(int idProject) {
        List<SchedulingActivities>  listSchedulingActivities = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            String querySchedulingActivities = "select * from SchedulingActivities inner join Month on SchedulingActivities.idMonth = Month.idMonth" +
                    " where idProject=?";
            PreparedStatement sentence = connection.prepareStatement(querySchedulingActivities);
            sentence.setInt(1,idProject);
            results = sentence.executeQuery();
            while(results.next()){
                SchedulingActivities schedulingActivities = new SchedulingActivities();
                schedulingActivities.setIdSchedulingActivities(results.getInt("idSchedulingActivities"));
                schedulingActivities.setActivity(results.getString("activity"));
                schedulingActivities.setMonth(results.getString("month"));
                listSchedulingActivities.add(schedulingActivities);
            }
        }catch (SQLException ex){
            Logger.getLogger(SchedulingActivitiesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return listSchedulingActivities;
    }

    public List<String> getAllMonth() {
        List<String> months = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select month from Month");
            while(results.next()){
                months.add(results.getString("month"));
            }
        }catch (SQLException ex){
            Logger.getLogger(SchedulingActivitiesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return months;
    }

    public int getIdMonth(String month) {
        int idMonth= Search.NOTFOUND.getValue();;
        try {
            connection = connexion.getConnection();
            String queryMonth = "select idMonth from Month where month =?";
            PreparedStatement sentence = connection.prepareStatement(queryMonth);
            sentence.setString(1,month);
            results = sentence.executeQuery();
            while(results.next()){
                idMonth= results.getInt("idMonth");
            }
        }catch (SQLException ex){
            Logger.getLogger(SchedulingActivitiesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idMonth;
    }
}
