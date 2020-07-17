package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import domain.Search;
import exception.Exception;
import exception.TelegramBot;


/**
 * Implementation of the ITermDAO
 * @author MARTHA
 * @version 09/07/2020
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
            PreparedStatement sentenceTerm = connection.prepareStatement("INSERT INTO Term (term) VALUES (?)");
            sentenceTerm.setString(1, term);
            sentenceTerm.executeUpdate();
            isAddTerm=true;
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return isAddTerm;
    }

    /**
     * Method to search for a term
     * @param term The Term parameter defines the period of the semester
     * @return The idTerm of the searched Term
     */
    public int getIdTerm(String term) {
        int idTerm = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryTerm= "SELECT idTerm FROM Term WHERE term=?";
            PreparedStatement sentence =connection.prepareStatement(queryTerm);
            sentence.setString(1, term);
            results= sentence.executeQuery();
            while(results.next()){
                idTerm =results.getInt("idTerm");
            }
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return idTerm;
    }

}
