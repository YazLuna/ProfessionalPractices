package dataaccess;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Search;

/**
 * Implementation of the IStateDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class StateDAOImpl implements IStateDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the StateDAOImpl class
     */
    public StateDAOImpl (){
        connexion = new Connexion();
    }

    /**
     * Method to add a State
     * @param name The name parameter defines the state of Linked Organization
     * @return if the state was added
     */
    @Override
    public boolean addState(String name) {
        boolean isAddState = false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceState = connection.prepareStatement("INSERT INTO State (nameState) VALUES (?)");
            sentenceState.setString(1,name);
            sentenceState.executeUpdate();
            isAddState = true;
        }catch(SQLException ex){
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAddState;
    }

    /**
     * Method to search for a state
     * @param name The name parameter defines the state of linked organization
     * @return The idState of the searched state
     */
    @Override
    public int getIdState(String name) {
        int idState = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryState= "SELECT idState FROM State WHERE nameState=?";
            PreparedStatement sentence =connection.prepareStatement(queryState);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()){
                idState =results.getInt("idState");
            }
        }catch(SQLException ex){
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idState;
    }

    /**
     * Method to get the state list
     * @return The state list
     */
    @Override
    public List<String> getAllState() {
        List<String> states = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("SELECT nameState FROM State");
            while(results.next()){
                states.add(results.getString("nameState"));
            }
        }catch (SQLException ex){
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return states;
    }
}
