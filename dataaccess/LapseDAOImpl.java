package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LapseDAOImpl implements ILapseDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    public LapseDAOImpl (){
        connexion= new Connexion();
    }

    @Override
    public void updateLapse (String lapse) {
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

    public int searchLapse (String lapse) {
        int idLapse=0;
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

    @Override
    public List<String> getAllPeriod() {
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
