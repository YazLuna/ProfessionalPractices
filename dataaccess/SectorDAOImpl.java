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
 * Implementation of the ISectorDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class SectorDAOImpl implements  ISectorDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the SectorDAOImpl class
     */
    public SectorDAOImpl (){
        connexion = new Connexion();
    }

    /**
     * Method to add a Sector
     * @param name The name parameter defines the sector of Linked Organization
     * @return if the sector was added
     */
    public boolean addSector(String name) {
        boolean isAddSector = false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceSector = connection.prepareStatement("INSERT INTO Sector (nameSector) VALUES (?)");
            sentenceSector.setString(1,name);
            sentenceSector.executeUpdate();
            isAddSector = true;
        }catch(SQLException ex){
            Logger.getLogger(SectorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAddSector;
    }

    /**
     * Method to search for a sector
     * @param name The name parameter defines the sector of linked organization
     * @return The idSector of the searched sector
     */
    public int getIdSector(String name) {
        int idSector = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String querySector= "SELECT idSector FROM Sector WHERE nameSector=?";
            PreparedStatement sentence =connection.prepareStatement(querySector);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()){
                idSector =results.getInt("idSector");
            }
        }catch(SQLException ex){
            Logger.getLogger(SectorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idSector;
    }

    /**
     * Method to get the sector list
     * @return The sector list
     */
    public List<String> getAllSector() {
        List<String> sectors = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("SELECT nameSector FROM Sector");
            while(results.next()){
                sectors.add(results.getString("nameSector"));
            }
        }catch (SQLException ex){
            Logger.getLogger(SectorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return sectors;
    }
}
