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
 * Implementation of the ICityDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class CityDAOImpl implements ICityDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the CityDAOImpl class
     */
    public CityDAOImpl (){
        connexion = new Connexion();
    }

    /**
     * Method to add a City
     * @param name The name parameter defines the city
     * @return if the city was added
     */
    @Override
    public boolean addCity(String name) {
        boolean isAddCity = false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCity = connection.prepareStatement("INSERT INTO City (nameCity) VALUES (?)");
            sentenceCity.setString(1,name);
            sentenceCity.executeUpdate();
            isAddCity = true;
        }catch(SQLException ex){
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAddCity;
    }

    /**
     * Method to search for a city
     * @param name The name parameter defines the city
     * @return The idCity of the searched city
     */
    @Override
    public int getIdCity(String name) {
        int idCity = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryCity= "SELECT idCity FROM City WHERE nameCity=?";
            PreparedStatement sentence =connection.prepareStatement(queryCity);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()){
                idCity =results.getInt("idCity");
            }
        }catch(SQLException ex){
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idCity;
    }

    /**
     * Method to get the city list
     * @return The city list
     */
    @Override
    public List<String> getAllCity() {
        List<String> cities = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("SELECT nameCity FROM City");
            while(results.next()){
                cities.add(results.getString("nameCity"));
            }
        }catch (SQLException ex){
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return cities;
    }
}
