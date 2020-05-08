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
import domain.LinkedOrganization;
/**
 *
 * @author: Martha M. Ortiz
 * @version: 08/05/2020
 */

public class LinkedOrganizationDAOImpl implements ILinkedOrganizationDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;
    
    public LinkedOrganizationDAOImpl (){
        connexion= new Connexion();
    }
    
    @Override
    public List<LinkedOrganization> getAllLinkedOrganization () {
        List<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consultation  = connection.createStatement();
            results = consultation.executeQuery("select * from LinkedOrganization inner join City on LinkedOrganization.idCity = City.idCyti inner join State LinkedOrganization.idState = State.idState inner join Sector on LinkedOrganization.idSector = Sector.idSector");
            while(results.next()){
                LinkedOrganization linkedOrganization = new LinkedOrganization();
                linkedOrganization.setIdLinkedOrganization(results.getInt("idLinkedOrganization"));
                linkedOrganization.setName(results.getString("name"));
                linkedOrganization.setDirectUsers(results.getInt("directUsers"));
                linkedOrganization.setIndirectUsers(results.getInt("indirectUsers"));
                linkedOrganization.setEmail(results.getString("email"));
                linkedOrganization.setPhoneNumber(results.getString("phoneNumber"));
                linkedOrganization.setAddress(results.getString("address"));
                linkedOrganization.setCity(results.getString("Cyti.nameCity"));
                linkedOrganization.setState(results.getString("State.nameState"));
                linkedOrganization.setSector(results.getString("Sector.nameSector"));
                linkedOrganizations.add(linkedOrganization);
            }
        }catch (SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        
        return linkedOrganizations;
    }

    @Override
    public LinkedOrganization getLinkedOrganization (int idOrganization) {
        LinkedOrganization linkedOrganization = null;
        try{
            connection = connexion.getConnection();
            String queryLikendOrganization = "select * from LinkedOrganization inner join City on LinkedOrganization.idCity = City.idCity inner join State on LinkedOrganization.idState = State.idState inner join Sector on LinkedOrganization.idSector = Sector.idSector where idLinkedOrganization =?";
            PreparedStatement sentence = connection.prepareStatement(queryLikendOrganization);
            sentence.setInt(1,idOrganization);
            results= sentence.executeQuery();
            while(results.next()){
                linkedOrganization = new LinkedOrganization();
                linkedOrganization.setIdLinkedOrganization(results.getInt("idLinkedOrganization"));
                linkedOrganization.setName(results.getString("name"));
                linkedOrganization.setDirectUsers(results.getInt("directUsers"));
                linkedOrganization.setIndirectUsers(results.getInt("indirectUsers"));
                linkedOrganization.setEmail(results.getString("email"));
                linkedOrganization.setPhoneNumber(results.getString("phoneNumber"));
                linkedOrganization.setAddress(results.getString("address"));
                linkedOrganization.setCity(results.getString("City.nameCity"));
                linkedOrganization.setState(results.getString("State.nameState"));
                linkedOrganization.setSector(results.getString("Sector.nameSector"));
            }
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linkedOrganization;
    }

    @Override
    public String updateLinkedOrganization(LinkedOrganization organization) {
        int idCity;
        int idState;
        int idSector;
        String result = "Could not register the linked organization";
        idState = searchState(organization.getState());
        if(idState == 0){
            updateState(organization.getState());
            idState = searchState(organization.getState());
        }
        
        idCity = searchCity(organization.getCity());
        if(idCity == 0){
            updateCity(organization.getCity());
            idCity = searchCity(organization.getCity());
        }
        
        idSector = searchSector(organization.getSector());
        if(idSector == 0){
            updateSector(organization.getSector());
            idSector = searchSector(organization.getSector());
        }
        
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceOrganization = connection.prepareStatement("insert into LinkedOrganization(name,"+
                    "directUsers,indirectUsers,email,phoneNumber,address,idCity,idState,idSector) values (?,?,?,?,?,?,?,?,?)");
            sentenceOrganization.setString(1,organization.getName());
            sentenceOrganization.setInt(2,organization.getDirectUsers());
            sentenceOrganization.setInt(3,organization.getIndirectUsers());
            sentenceOrganization.setString(4,organization.getEmail());
            sentenceOrganization.setString(5,organization.getPhoneNumber());
            sentenceOrganization.setString(6,organization.getAddress());
            sentenceOrganization.setInt(7,idCity);
            sentenceOrganization.setInt(8,idState);
            sentenceOrganization.setInt(9,idSector);
            sentenceOrganization.executeUpdate();
            result = "The linked organization was successfully registered";
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return result;
        }
    }

    @Override
    public String actualizationOrganization (LinkedOrganization organization) {
        int idState;
        int idCity;
        int idSector;
        String result = "Could not update linked organization";
        idState = searchState(organization.getState());
        if(idState == 0){
            updateState(organization.getState());
            idState = searchState(organization.getState());
        }
            
        idCity = searchCity(organization.getCity());
        if(idCity == 0){
            updateCity(organization.getCity());
            idCity = searchCity(organization.getCity());
        }
            
        idSector = searchSector(organization.getSector());
        if(idState == 0){
            updateSector(organization.getSector());
            idSector = searchState(organization.getSector());
        }
        try{
            connection = connexion.getConnection();
            String queryOrganization = "update LinkedOrganization set name=?, directUsers=?, indirectUsers=?, email=?,"+
                    " phoneNumber=?, address=?, idCity=?, idState=?, idSector=? where idLinkedOrganization=?";
            PreparedStatement sentence = connection.prepareStatement(queryOrganization);
            sentence.setString(1, organization.getName());
            sentence.setInt(2, organization.getDirectUsers());
            sentence.setInt(3, organization.getIndirectUsers());
            sentence.setString(4, organization.getEmail());
            sentence.setString(5, organization.getPhoneNumber());
            sentence.setString(6, organization.getAddress());
            sentence.setInt(7, idCity);
            sentence.setInt(8, idState);
            sentence.setInt(9, idSector);
            sentence.setInt(10, organization.getIdLinkedOrganization());
            sentence.executeUpdate();
            result = "The linked organization was successfully updated";
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return result;
        }
    }

    @Override
    public int searchLinkedOrganization (String name, String email) {
        int idLinkedOrganization=0;
        try{
            connection = connexion.getConnection();
            String queryOrganization= "Select idLinkedOrganization from LinkedOrganization where name=? or email=?";
            PreparedStatement sentence =connection.prepareStatement(queryOrganization);
            sentence.setString(1,name);
            sentence.setString(1,email);
            results= sentence.executeQuery();
            while(results.next()){
                idLinkedOrganization =results.getInt("idLinkedOrganization");
            }
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idLinkedOrganization;
    }

    public void updateCity (String name) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCity = connection.prepareStatement("insert into City (nameCity) values (?)");
            sentenceCity.setString(1,name);
            sentenceCity.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    public int searchCity (String name) {
        int idCity=0;
        try{
            connection = connexion.getConnection();
            String queryCity= "Select idCity from City where nameCity=?";
            PreparedStatement sentence =connection.prepareStatement(queryCity);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()){
                idCity =results.getInt("idCity");
            }
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idCity;
    }
    public List<String> getAllCity() {
        List<String> cities = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select nameCity from City");
            while(results.next()){
                cities.add(results.getString("nameCity"));
            }
        }catch (SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return cities;
    }

    public void updateState (String name) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceState = connection.prepareStatement("insert into State (nameState) values (?)");
            sentenceState.setString(1,name);
            sentenceState.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    public int searchState (String name) {
        int idState=0;
        try{
            connection = connexion.getConnection();
            String queryState= "Select idState from State where nameState=?";
            PreparedStatement sentence =connection.prepareStatement(queryState);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()){
                idState =results.getInt("idState");
            }
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idState;
    }

    public List<String> getAllState() {
        List<String> states = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select nameState from State");
            while(results.next()){
                states.add(results.getString("nameState"));
            }
        }catch (SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return states;
    }

    public void updateSector (String name) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceSector = connection.prepareStatement("insert into Sector (nameSector) values (?)");
            sentenceSector.setString(1,name);
            sentenceSector.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    public int searchSector (String name) {
        int idSector=0;
        try{
            connection = connexion.getConnection();
            String querySector= "Select idSector from Sector where nameSector=?";
            PreparedStatement sentence =connection.prepareStatement(querySector);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()){
                idSector =results.getInt("idSector");
            }
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idSector;
    }

    public List<String> getAllSector() {
        List<String> sectors = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select nameSector from Sector");
            while(results.next()){
                sectors.add(results.getString("nameSector"));
            }
        }catch (SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return sectors;
    }
}
