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
 * Implementation of the ITermDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class TermDAOImpl implements ITermDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the TermDAOImpl class
     */
    public TermDAOImpl (){
        connexion= new Connexion();
    }

    /**
     * Method to add a Term
     * @param Term The Term parameter defines the period of the semester
     */
    @Override
    public void addTerm(String Term) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceTerm = connection.prepareStatement("insert into Term (Term) values (?)");
            sentenceTerm.setString(1,Term);
            sentenceTerm.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(TermDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    /**
     * Method to search for a Term
     * @param Term The Term parameter defines the period of the semester
     * @return The idTerm of the searched Term
     */
    public int searchTerm (String Term) {
        int idTerm = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryTerm= "Select idTerm from Term where Term=?";
            PreparedStatement sentence =connection.prepareStatement(queryTerm);
            sentence.setString(1,Term);
            results= sentence.executeQuery();
            while(results.next()){
                idTerm =results.getInt("idTerm");
            }
        }catch(SQLException ex){
            Logger.getLogger(TermDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idTerm;
    }

    /**
     * Method to get the Term list
     * @return The Term list
     */
    @Override
    public List<String> getAllTerm() {
        List<String> Terms = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select Term from Term");
            while(results.next()){
                Terms.add(results.getString("Term"));
            }
        }catch (SQLException ex){
            Logger.getLogger(TermDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return Terms;
    }
}
