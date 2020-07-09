package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Search;

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
    public TermDAOImpl(){
        connexion= new Connexion();
    }

    /**
     * Method to add a Term
     * @param term The term parameter defines the period of the semester
     * @return if the term was added
     */
    @Override
    public boolean addTerm(String term) {
        boolean isAddTerm=false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceLapse = connection.prepareStatement("INSERT INTO Term (term) VALUES (?)");
            sentenceLapse.setString(1, term);
            sentenceLapse.executeUpdate();
            isAddTerm=true;
        }catch(SQLException ex){
            Logger.getLogger(TermDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAddTerm;
    }

    /**
     * Method to search for a term
     * @param term The lapse parameter defines the period of the semester
     * @return The idLapse of the searched lapse
     */
    public int getIdTerm(String term) {
        int idTerm = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryLapse= "SELECT idLapse FROM Term WHERE term=?";
            PreparedStatement sentence =connection.prepareStatement(queryLapse);
            sentence.setString(1, term);
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

}
