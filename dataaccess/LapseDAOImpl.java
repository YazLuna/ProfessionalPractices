package dataaccess;

import domain.Search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ILapseDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class LapseDAOImpl implements ILapseDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the LapseDAOImpl class
     */
    public LapseDAOImpl (){
        connexion= new Connexion();
    }

    /**
     * Method to add a lapse
     * @param lapse The lapse parameter defines the period of the semester
     */
    @Override
    public void addLapse(String lapse) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceLapse = connection.prepareStatement("insert into Lapse (lapse) values (?)");
            sentenceLapse.setString(1,lapse);
            sentenceLapse.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(LapseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    /**
     * Method to search for a lapse
     * @param lapse The lapse parameter defines the period of the semester
     * @return The idLapse of the searched lapse
     */
    public int searchLapse (String lapse) {
        int idLapse = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryLapse= "Select idLapse from Lapse where lapse=?";
            PreparedStatement sentence =connection.prepareStatement(queryLapse);
            sentence.setString(1,lapse);
            results= sentence.executeQuery();
            while(results.next()){
                idLapse =results.getInt("idLapse");
            }
        }catch(SQLException ex){
            Logger.getLogger(LapseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idLapse;
    }

    /**
     * Method to get the lapse list
     * @return The lapse list
     */
    @Override
    public List<String> getAllLapse() {
        List<String> lapses = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select lapse from Lapse");
            while(results.next()){
                lapses.add(results.getString("lapse"));
            }
        }catch (SQLException ex){
            Logger.getLogger(LapseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return lapses;
    }
}
