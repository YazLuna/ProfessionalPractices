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
import domain.Search;

/**
 * Implementation of the IProjectDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class LinkedOrganizationDAOImpl implements ILinkedOrganizationDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the LinkedOrganizationDAOImpl class
     */
    public LinkedOrganizationDAOImpl (){
        connexion= new Connexion();
    }

    /**
     * Method to get the Linked Organization list
     * @return The Linked Organization list
     */
    @Override
    public List<LinkedOrganization> getAllLinkedOrganization () {
        List<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consultation  = connection.createStatement();
            results = consultation.executeQuery("select name,email from LinkedOrganization");
            while(results.next()){
                LinkedOrganization linkedOrganization = new LinkedOrganization();
                linkedOrganization.setName(results.getString("name"));
                linkedOrganization.setEmail(results.getString("email"));
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
    public List<LinkedOrganization> getAllLinkedOrganizationAvailable () {
        List<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "select idLinkedOrganization,name,email from LinkedOrganization inner join Status on " +
                    "LinkedOrganization.idStatus = Status.idStatus where status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next()){
                LinkedOrganization linkedOrganization = new LinkedOrganization();
                linkedOrganization.setName(results.getString("name"));
                linkedOrganization.setEmail(results.getString("email"));
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
    public List<LinkedOrganization> getAllLinkedOrganizationAvailableNotAssing() {
        List<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        int idLinkedOrganization;
        try{
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "select idLinkedOrganization,name,email from LinkedOrganization inner join Status on " +
                    "LinkedOrganization.idStatus = Status.idStatus where status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next()){
                idLinkedOrganization = results.getInt("idLinkedOrganization");
                String queryAssingOrganization = "select idProject from Project,LinkedOrganization WHERE " +
                        "LinkedOrganization.idLinkedOrganization = Project.idLinkedOrganization AND LinkedOrganization.idLinkedOrganization=?";
                PreparedStatement sentenceAssing = connection.prepareStatement(queryAssingOrganization);
                sentenceAssing.setInt(1,idLinkedOrganization);
                ResultSet resultAssing;
                resultAssing = sentenceAssing.executeQuery();
                if(resultAssing.next()){
                    String queryAssingOrganizationAvailable  = "select idProject from Project,LinkedOrganization,Status WHERE " +
                            "LinkedOrganization.idLinkedOrganization = Project.idLinkedOrganization AND " +
                            "Project.idStatus = Status.idStatus AND status=? AND LinkedOrganization.idLinkedOrganization=?";
                    PreparedStatement sentenceAssingAvailable = connection.prepareStatement(queryAssingOrganizationAvailable);
                    sentenceAssingAvailable.setString(1,"available");
                    sentenceAssingAvailable.setInt(2,idLinkedOrganization);
                    ResultSet resultAssingAvailable;
                    resultAssingAvailable = sentenceAssingAvailable.executeQuery();
                    if(!resultAssingAvailable.next()) {
                        LinkedOrganization linkedOrganization = new LinkedOrganization();
                        linkedOrganization.setName(results.getString("name"));
                        linkedOrganization.setEmail(results.getString("email"));
                        linkedOrganizations.add(linkedOrganization);
                    }
                }else {
                    LinkedOrganization linkedOrganization = new LinkedOrganization();
                    linkedOrganization.setName(results.getString("name"));
                    linkedOrganization.setEmail(results.getString("email"));
                    linkedOrganizations.add(linkedOrganization);
                }
            }
        }catch (SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return linkedOrganizations;
    }


    /**
     * Method to get the Linked Organization of the Project
     * @param idOrganization The idOrganization parameter defines the id of the Linked Organization
     * @return The Linked Organization of the searched idOrganization
     */
    @Override
    public LinkedOrganization getIdLinkedOrganization (int idOrganization) {
        LinkedOrganization linkedOrganization = null;
        try{
            connection = connexion.getConnection();
            String queryLikendOrganization = "select * from LinkedOrganization inner join City on LinkedOrganization.idCity = City.idCity inner join " +
                    "State on LinkedOrganization.idState = State.idState inner join Sector on LinkedOrganization.idSector = Sector.idSector where idLinkedOrganization =?";
            PreparedStatement sentence = connection.prepareStatement(queryLikendOrganization);
            sentence.setInt(1,idOrganization);
            results= sentence.executeQuery();
            while(results.next()){
                linkedOrganization = new LinkedOrganization();
                linkedOrganization.setIdLinkedOrganization(results.getInt("idLinkedOrganization"));
                linkedOrganization.setName(results.getString("name"));
                linkedOrganization.setDirectUsers(results.getString("directUsers"));
                linkedOrganization.setIndirectUsers(results.getString("indirectUsers"));
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
    public LinkedOrganization getLinkedOrganization (String nameOrganization) {
        LinkedOrganization linkedOrganization = null;
        try{
            connection = connexion.getConnection();
            String queryLikendOrganization = "select * from LinkedOrganization inner join City on LinkedOrganization.idCity = City.idCity inner join " +
                    "State on LinkedOrganization.idState = State.idState inner join Sector on LinkedOrganization.idSector = Sector.idSector where name =?";
            PreparedStatement sentence = connection.prepareStatement(queryLikendOrganization);
            sentence.setString(1,nameOrganization);
            results= sentence.executeQuery();
            while(results.next()){
                linkedOrganization = new LinkedOrganization();
                linkedOrganization.setIdLinkedOrganization(results.getInt("idLinkedOrganization"));
                linkedOrganization.setName(results.getString("name"));
                linkedOrganization.setDirectUsers(results.getString("directUsers"));
                linkedOrganization.setIndirectUsers(results.getString("indirectUsers"));
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
    /**
     * Method to add a Linked Organization
     * @param organization The data of the Linked Organization
     * @return The message if the Linked Organization was added
     */
    @Override
    public boolean addLinkedOrganization(LinkedOrganization organization) {
        boolean result = false;
        int idState;
        idState = searchIdState(organization.getState());
        if(idState == Search.NOTFOUND.getValue()){
            addState(organization.getState());
            idState = searchIdState(organization.getState());
        }
        int idCity;
        idCity = searchIdCity(organization.getCity());
        if(idCity == Search.NOTFOUND.getValue()){
            addCity(organization.getCity());
            idCity = searchIdCity(organization.getCity());
        }
        int idSector;
        idSector = searchIdSector(organization.getSector());
        if(idSector == Search.NOTFOUND.getValue()){
            addSector(organization.getSector());
            idSector = searchIdSector(organization.getSector());
        }
        int idStatus;
        StatusDAOImpl status = new StatusDAOImpl();
        idStatus = status.searchIdStatus(organization.getStatus());
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceOrganization = connection.prepareStatement("insert into LinkedOrganization(name,"+
                    "directUsers,indirectUsers,email,phoneNumber,address,idCity,idState,idSector,idStatus) values (?,?,?,?,?,?,?,?,?,?)");
            sentenceOrganization.setString(1,organization.getName());
            sentenceOrganization.setString(2,organization.getDirectUsers());
            sentenceOrganization.setString(3,organization.getIndirectUsers());
            sentenceOrganization.setString(4,organization.getEmail());
            sentenceOrganization.setString(5,organization.getPhoneNumber());
            sentenceOrganization.setString(6,organization.getAddress());
            sentenceOrganization.setInt(7,idCity);
            sentenceOrganization.setInt(8,idState);
            sentenceOrganization.setInt(9,idSector);
            sentenceOrganization.setInt(10,idStatus);
            sentenceOrganization.executeUpdate();
            result = true;
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if (connexion != null) {
                connexion.closeConnection();
            }
            return result;
        }
    }

    /**
     * Update method of the Linked Organization
     * @param organization The data of the Linked Organization
     * @return The message if the Linked Organization was updated
     */
    @Override
    public boolean modifyLinkedOrganization (LinkedOrganization organization) {
        int idState;
        int idCity;
        int idSector;
        boolean result = false;
        idState = searchIdState(organization.getState());
        if(idState == Search.NOTFOUND.getValue()){
            addState(organization.getState());
            idState = searchIdState(organization.getState());
        }
            
        idCity = searchIdCity(organization.getCity());
        if(idCity == Search.NOTFOUND.getValue()){
            addCity(organization.getCity());
            idCity = searchIdCity(organization.getCity());
        }
            
        idSector = searchIdSector(organization.getSector());
        if(idState == Search.NOTFOUND.getValue()){
            addSector(organization.getSector());
            idSector = searchIdState(organization.getSector());
        }
        try{
            connection = connexion.getConnection();
            String queryOrganization = "update LinkedOrganization set name=?, directUsers=?, indirectUsers=?, email=?,"+
                    " phoneNumber=?, address=?, idCity=?, idState=?, idSector=? where idLinkedOrganization=?";
            PreparedStatement sentence = connection.prepareStatement(queryOrganization);
            sentence.setString(1, organization.getName());
            sentence.setString(2, organization.getDirectUsers());
            sentence.setString(3, organization.getIndirectUsers());
            sentence.setString(4, organization.getEmail());
            sentence.setString(5, organization.getPhoneNumber());
            sentence.setString(6, organization.getAddress());
            sentence.setInt(7, idCity);
            sentence.setInt(8, idState);
            sentence.setInt(9, idSector);
            sentence.setInt(10, organization.getIdLinkedOrganization());
            sentence.executeUpdate();
            result = true;
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return result;
        }
    }

    /**
     * Method for finding a Linked Organization
     * @param name The name parameter defines the name of the Linked Organization
     * @param email The email parameter defines the email of the Linked Organization
     * @return The idLinkedOrganization of the searched email and name
     */
    @Override
    public int searchIdLinkedOrganization (String name, String email, String phoneNumber) {
        int idLinkedOrganization = Search.NOTFOUND.getValue();
        String queryOrganization= "Select idLinkedOrganization from LinkedOrganization where name=? or email=? or phoneNumber=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryOrganization);
            sentence.setString(1,name);
            sentence.setString(2,email);
            sentence.setString(3, phoneNumber);
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

    /**
     * Method to add a City
     * @param name The name parameter defines the city of Linked Organization
     */
    public void addCity(String name) {
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

    /**
     * Method to search for a city
     * @param name The name parameter defines the city of linked organization
     * @return The idCity of the searched city
     */
    public int searchIdCity(String name) {
        int idCity = Search.NOTFOUND.getValue();
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

    /**
     * Method to get the city list
     * @return The city list
     */
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

    /**
     * Method to add a State
     * @param name The name parameter defines the state of Linked Organization
     */
    public void addState(String name) {
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

    /**
     * Method to search for a state
     * @param name The name parameter defines the state of linked organization
     * @return The idState of the searched state
     */
    public int searchIdState(String name) {
        int idState = Search.NOTFOUND.getValue();
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

    /**
     * Method to get the state list
     * @return The state list
     */
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

    /**
     * Method to add a Sector
     * @param name The name parameter defines the sector of Linked Organization
     */
    public void addSector(String name) {
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

    /**
     * Method to search for a sector
     * @param name The name parameter defines the sector of linked organization
     * @return The idSector of the searched sector
     */
    public int searchIdSector(String name) {
        int idSector = Search.NOTFOUND.getValue();
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

    /**
     * Method to get the sector list
     * @return The sector list
     */
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

    @Override
    public boolean deleteLinkedOrganization (int idLinkedOrganization) {
        boolean result= false;
        StatusDAOImpl status = new StatusDAOImpl();
        int idStatus = status.searchIdStatus("not available");
        try{
            connection = connexion.getConnection();
            String queryDelete = "update LinkedOrganization set idStatus=? where idLinkedOrganization =?";
            PreparedStatement sentence = connection.prepareStatement(queryDelete);
            sentence.setInt(1, idStatus);
            sentence.setInt(2, idLinkedOrganization);
            sentence.executeUpdate();
            result= true;
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
            return result;
        }
    }

    @Override
    public boolean thereAreLinkedOrganizationAvailable (){
        boolean areLinkedOrganization = false;
        int idLinkedOrganization;
        try {
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "select idLinkedOrganization,name,email from LinkedOrganization inner join Status on " +
                    "LinkedOrganization.idStatus = Status.idStatus where status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next() && !areLinkedOrganization){
                idLinkedOrganization = results.getInt("idLinkedOrganization");
                String queryAssingOrganization = "select idProject from Project,LinkedOrganization WHERE " +
                        "LinkedOrganization.idLinkedOrganization = Project.idLinkedOrganization AND LinkedOrganization.idLinkedOrganization=?";
                PreparedStatement sentenceAssing = connection.prepareStatement(queryAssingOrganization);
                sentenceAssing.setInt(1,idLinkedOrganization);
                ResultSet resultAssing;
                resultAssing = sentenceAssing.executeQuery();
                if(resultAssing.next()){
                    String queryAssingOrganizationAvailable  = "select idProject from Project,LinkedOrganization,Status WHERE " +
                            "LinkedOrganization.idLinkedOrganization = Project.idLinkedOrganization AND " +
                            "Project.idStatus = Status.idStatus AND status=? AND LinkedOrganization.idLinkedOrganization=?";
                    PreparedStatement sentenceAssingAvailable = connection.prepareStatement(queryAssingOrganizationAvailable);
                    sentenceAssingAvailable.setString(1,"available");
                    sentenceAssingAvailable.setInt(2,idLinkedOrganization);
                    ResultSet resultAssingAvailable;
                    resultAssingAvailable = sentenceAssingAvailable.executeQuery();
                    if(!resultAssingAvailable.next()) {
                        areLinkedOrganization = true;
                    }
                }else {
                    areLinkedOrganization = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areLinkedOrganization;
    }

    @Override
    public boolean thereAreLinkedOrganization(){
        boolean areLinkedOrganization = false;
        try {
            connection = connexion.getConnection();
            String queryAreLikendOrganization = "select name from LinkedOrganization";
            PreparedStatement sentence = connection.prepareStatement(queryAreLikendOrganization);
            results= sentence.executeQuery();
            while (results.next()) {
                areLinkedOrganization = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areLinkedOrganization;
    }

    @Override
    public boolean thereAreLinkedOrganizationAvailableNotAssing() {
        boolean areLinkedOrganization = false;
        try {
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "select idLinkedOrganization,name,email from LinkedOrganization inner join Status on " +
                    "LinkedOrganization.idStatus = Status.idStatus where status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next() && !areLinkedOrganization){
                areLinkedOrganization = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areLinkedOrganization;
    }
}
