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
 * Implementation of the IChargeDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class ChargeDAOImpl implements IChargeDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the ChargeDAOImpl class
     */
    public ChargeDAOImpl (){
        connexion = new Connexion();
    }


    /**
     * Method to add a Charge
     * @param name defines the charge of responsible of the project
     * @return if the charge was successfully add
     */
    @Override
    public boolean addCharge (String name) {
        boolean resultAddCharge = false;
        String queryCharge = "INSER INTO Charge (nameCharge) VALUES(?)";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCharge = connection.prepareStatement(queryCharge);
            sentenceCharge.setString(1,name);
            sentenceCharge.executeUpdate();
            resultAddCharge = true;
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return resultAddCharge;
    }

    /**
     * Method to get for the id  the charge
     * @param name The name parameter defines the charge of responsible of the project
     * @return The idCharge of the charge searched
     */
    @Override
    public int getIdCharge(String name) {
        int idCharge = Search.NOTFOUND.getValue();;
        String queryCharge= "SELECT idCharge FROM Charge WHERE nameCharge=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryCharge);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()) {
                idCharge = results.getInt("idCharge");
            }
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idCharge;
    }

    /**
     * Method to get the charge list
     * @return The charge list
     */
    @Override
    public List<String> getAllCharge() {
        List<String> charges = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("SELECT nameCharge FROM Charge");
            while(results.next()){
                charges.add(results.getString("nameCharge"));
            }
        }catch (SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return charges;
    }
}
