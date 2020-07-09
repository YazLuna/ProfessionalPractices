package dataaccess;

import java.lang.reflect.Method;
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
     * Method to add a Linked Organization
     * @param organization The data of the Linked Organization
     * @return if the Linked Organization was added
     */
    @Override
    public boolean addLinkedOrganization(LinkedOrganization organization) {
        boolean result = false;
        int idState;
        StateDAOImpl stateDAO = new StateDAOImpl();
        idState = stateDAO.getIdState(organization.getState());
        if(idState == Search.NOTFOUND.getValue()){
            stateDAO.addState(organization.getState());
            idState = stateDAO.getIdState(organization.getState());
        }
        int idCity;
        CityDAOImpl cityDAO = new CityDAOImpl();
        idCity = cityDAO.getIdCity(organization.getCity());
        if(idCity == Search.NOTFOUND.getValue()){
            cityDAO.addCity(organization.getCity());
            idCity = cityDAO.getIdCity(organization.getCity());
        }

        int idSector;
        SectorDAOImpl sectorDAO = new SectorDAOImpl();
        idSector = sectorDAO.getIdSector(organization.getSector());
        if(idSector == Search.NOTFOUND.getValue()){
            sectorDAO.addSector(organization.getSector());
            idSector = sectorDAO.getIdSector(organization.getSector());
        }
        int idStatus;
        StatusDAOImpl status = new StatusDAOImpl();
        idStatus = status.searchIdStatus(organization.getStatus());
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceOrganization = connection.prepareStatement("INSERT INTO LinkedOrganization(name,"+
                    "directUsers,indirectUsers,email,address,idCity,idState,idSector,idStatus) VALUES (?,?,?,?,?,?,?,?,?)");
            sentenceOrganization.setString(1,organization.getName());
            sentenceOrganization.setString(2,organization.getDirectUsers());
            sentenceOrganization.setString(3,organization.getIndirectUsers());
            sentenceOrganization.setString(4,organization.getEmail());
            sentenceOrganization.setString(5,organization.getAddress());
            sentenceOrganization.setInt(6,idCity);
            sentenceOrganization.setInt(7,idState);
            sentenceOrganization.setInt(8,idSector);
            sentenceOrganization.setInt(9,idStatus);
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
     * Method to get the Linked Organization list
     * @return The Linked Organization list
     */
    @Override
    public List<LinkedOrganization> getAllLinkedOrganization () {
        List<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consultation  = connection.createStatement();
            results = consultation.executeQuery("SELECT name,email FROM LinkedOrganization");
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

    /**
     * Method to get the Linked Organization available list
     * @return The Linked Organization available list
     */
    @Override
    public List<LinkedOrganization> getAllLinkedOrganizationAvailable () {
        List<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization " +
                    "INNER JOIN Status ON LinkedOrganization.idStatus = Status.idStatus where status =?";
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

    /**
     * Method to obtaining all Linked Organization without assigned project available
     * @return List of the Linked Organization without assigned project available
     */
    @Override
    public List<LinkedOrganization> getAllLinkedOrganizationAvailableNotAssing() {
        List<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        int idLinkedOrganization;
        try{
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization INNER JOIN Status " +
                    "ON LinkedOrganization.idStatus = Status.idStatus WHERE status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next()){
                idLinkedOrganization = results.getInt("idLinkedOrganization");
                boolean isAssingnedProject;
                isAssingnedProject = validateAssignedProject(idLinkedOrganization);
                if(isAssingnedProject){
                    boolean isAssingnedProjectAvailable;
                    isAssingnedProjectAvailable = validateAssignedProjectAvailable(idLinkedOrganization);
                    if(!isAssingnedProjectAvailable) {
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
     * Method for finding a id linked organization
     * @param nameOrganization defines the name of the linked organization
     * @return The Linked organization of the searched name
     */
    @Override
    public LinkedOrganization getLinkedOrganization (String nameOrganization) {
        LinkedOrganization linkedOrganization = null;
        try{
            connection = connexion.getConnection();
            String queryLikendOrganization = "select * FROM LinkedOrganization INNER JOIN City " +
                    "ON LinkedOrganization.idCity = City.idCity INNER JOIN " +
                    "State ON LinkedOrganization.idState = State.idState " +
                    "INNER JOIN Sector ON LinkedOrganization.idSector " +
                    "= Sector.idSector WHERE name =?";
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
     * Method to get the Linked Organization of the Project
     * @param idOrganization defines the id of the Linked Organization
     * @return The Linked Organization of the searched idOrganization
     */
    @Override
    public LinkedOrganization getLinkedOrganizationWithId(int idOrganization) {
        LinkedOrganization linkedOrganization = null;
        try{
            connection = connexion.getConnection();
            String queryLikendOrganization = "SELECT * FROM LinkedOrganization INNER JOIN City ON LinkedOrganization.idCity " +
                    "= City.idCity INNER JOIN State ON LinkedOrganization.idState = State.idState" +
                    " INNER JOIN Sector ON LinkedOrganization.idSector " +
                    "= Sector.idSector WHERE idLinkedOrganization =?";
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
                //linkedOrganization.setPhoneNumber(results.getString("phoneNumber"));
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
     * Method for finding a id of Linked Organization
     * @param name The name parameter defines the name of the Linked Organization
     * @return The idLinkedOrganization of the searched email and name
     */
    @Override
    public int getIdLinkedOrganization(String name) {
        int idLinkedOrganization = Search.NOTFOUND.getValue();
        String queryOrganization= "SELECT idLinkedOrganization FROM LinkedOrganization WHERE name=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryOrganization);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            if(results.next()){
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
     * Update method of the Linked Organization
     * @param organizationEdit The data of the Linked Organization
     * @param datesUpdate The data to modify of the Linked Organization
     * @return if the Linked Organization was modify
     */
    @Override
    public boolean modifyLinkedOrganization (LinkedOrganization organizationEdit, List<String> datesUpdate) {
        int idState;
        int idCity;
        int idSector;
        boolean result = false;
        String sentenceDatesUpdate="";
        List<String> Change = new ArrayList<>();
        for (int indexDatesUpdate = Number.ZERO.getNumber(); indexDatesUpdate < datesUpdate.size(); indexDatesUpdate++) {
            if (indexDatesUpdate == datesUpdate.size() - 1) {
                if(datesUpdate.get(indexDatesUpdate).equals("City")) {
                    sentenceDatesUpdate = sentenceDatesUpdate + "idCity = ?";
                }else {
                    if(datesUpdate.get(indexDatesUpdate).equals("State")) {
                        sentenceDatesUpdate = sentenceDatesUpdate + "idState = ?";
                    }else {
                        if(datesUpdate.get(indexDatesUpdate).equals("Sector")) {
                            sentenceDatesUpdate = sentenceDatesUpdate + "idSector = ?";
                        }else {
                            sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?";
                        }
                    }
                }
            } else {
                if(datesUpdate.get(indexDatesUpdate).equals("City")) {
                    sentenceDatesUpdate = sentenceDatesUpdate + "idCity = ?,";
                }else {
                    if(datesUpdate.get(indexDatesUpdate).equals("State")) {
                        sentenceDatesUpdate = sentenceDatesUpdate + "idState = ?,";
                    }else {
                        if(datesUpdate.get(indexDatesUpdate).equals("Sector")) {
                            sentenceDatesUpdate = sentenceDatesUpdate + "idSector = ?,";
                        }else {
                            sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?,";
                        }
                    }
                }
            }
            Change.add("get" + datesUpdate.get(indexDatesUpdate));
        }
        String sentence = "UPDATE LinkedOrganization SET "+sentenceDatesUpdate+ " WHERE idLinkedOrganization " +
                "= "+ organizationEdit.getIdLinkedOrganization();
        try{
            connection = connexion.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            Class classOganization = organizationEdit.getClass();
            for(int indexPreparedStatement = Number.ZERO.getNumber() ; indexPreparedStatement
                    <= datesUpdate.size(); indexPreparedStatement++){
                Method methodLinkedOrganization;
                if(Change.get(indexPreparedStatement - 1).equals("getCity")){
                    CityDAOImpl cityDAO = new CityDAOImpl();
                    idCity = cityDAO.getIdCity(organizationEdit.getCity());
                    if(idCity == Search.NOTFOUND.getValue()){
                        cityDAO.addCity(organizationEdit.getCity());
                        idCity = cityDAO.getIdCity(organizationEdit.getCity());
                    }
                    preparedStatement.setInt(indexPreparedStatement, idCity);
                } else{
                    if(Change.get(indexPreparedStatement - 1).equals("getState")) {
                        StateDAOImpl stateDAO = new StateDAOImpl();
                        idState = stateDAO.getIdState(organizationEdit.getState());
                        if(idState == Search.NOTFOUND.getValue()){
                            stateDAO.addState(organizationEdit.getState());
                            idState = stateDAO.getIdState(organizationEdit.getState());
                        }
                        preparedStatement.setInt(indexPreparedStatement, idState);
                    }else {
                        if(Change.get(indexPreparedStatement - 1).equals("getSector")) {
                            SectorDAOImpl sectorDAO = new SectorDAOImpl();
                            idSector = sectorDAO.getIdSector(organizationEdit.getSector());
                            if(idSector == Search.NOTFOUND.getValue()){
                                sectorDAO.addSector(organizationEdit.getSector());
                                idSector = sectorDAO.getIdSector(organizationEdit.getSector());
                            }
                            preparedStatement.setInt(indexPreparedStatement, idSector);
                        } else {
                            methodLinkedOrganization = classOganization.getMethod(Change.get(indexPreparedStatement - 1));
                            String word = (String) methodLinkedOrganization.invoke(organizationEdit, new Object[]{});
                            preparedStatement.setString(indexPreparedStatement, word);
                        }
                    }
                }
            }
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    /**
     * Method to delete a linked organization
     * @param name defines the name of the linked organization
     * @return if the linked organization was deleted
     */
    @Override
    public boolean deleteLinkedOrganization (String name) {
        boolean result= false;
        StatusDAOImpl status = new StatusDAOImpl();
        int idStatus = status.searchIdStatus("not available");
        try{
            connection = connexion.getConnection();
            String queryDelete = "UPDATE LinkedOrganization SET idStatus=? WHERE name =?";
            PreparedStatement sentence = connection.prepareStatement(queryDelete);
            sentence.setInt(1, idStatus);
            sentence.setString(2, name);
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

    /**
     * Method to validate if a linked organization this assigned to a project
     * @param idLinkedOrganization The id of the linked organization
     * @return if the linked organization this assigned
     */
    @Override
    public boolean validateAssignedProject(int idLinkedOrganization) {
        boolean isAssignedProject= false;
        try{
            connection = connexion.getConnection();
            String queryAssingOrganization = "SELECT idProject FROM Project,LinkedOrganization " +
                    "WHERE LinkedOrganization.idLinkedOrganization=Project.idLinkedOrganization" +
                    " AND LinkedOrganization.idLinkedOrganization=?";
            PreparedStatement sentenceAssing = connection.prepareStatement(queryAssingOrganization);
            sentenceAssing.setInt(1,idLinkedOrganization);
            ResultSet resultAssing;
            resultAssing = sentenceAssing.executeQuery();
            if(resultAssing.next()){
                isAssignedProject= true;
            }
        }catch (SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAssignedProject;
    }

    /**
     * Method to validate if a linked Organization this assigned to a project available
     * @param idLinkedOrganization The id of the linked organization
     * @return if the linked organization this assigned available
     */
    @Override
    public boolean validateAssignedProjectAvailable (int idLinkedOrganization) {
        boolean isAssignedProjectAvailable= false;
        try{
            connection = connexion.getConnection();
            String queryAssingOrganizationAvailable  = "SELECT idProject FROM Project,LinkedOrganization,Status " +
                    "WHERE LinkedOrganization.idLinkedOrganization = Project.idLinkedOrganization " +
                    "AND Project.idStatus = Status.idStatus AND status=? " +
                    "AND LinkedOrganization.idLinkedOrganization=?";
            PreparedStatement sentenceAssingAvailable = connection.prepareStatement(queryAssingOrganizationAvailable);
            sentenceAssingAvailable.setString(1,"available");
            sentenceAssingAvailable.setInt(2,idLinkedOrganization);
            ResultSet resultAssingAvailable;
            resultAssingAvailable = sentenceAssingAvailable.executeQuery();
            if(!resultAssingAvailable.next()) {
                isAssignedProjectAvailable= true;
            }
        }catch (SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAssignedProjectAvailable;
    }

    /**
     * Method to validate if there is another linked organization with the same email or name
     * @param name The name parameter defines the name of the Linked Organization
     * @param email The email parameter defines the email of the Linked Organization
     * @return if the linked organization exists
     */
    @Override
    public boolean validateRepeatLinkedOrganization(String name, String email) {
        boolean isRepeatLinkedOrganization = false;
        String queryOrganization= "SELECT idLinkedOrganization FROM LinkedOrganization WHERE name=? OR email=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryOrganization);
            sentence.setString(1,name);
            sentence.setString(2,email);
            results= sentence.executeQuery();
            if(results.next()){
                isRepeatLinkedOrganization=true;
            }
        }catch(SQLException ex){
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isRepeatLinkedOrganization;
    }

    /**
     * Method to know if there are linked organization available without assigned project available
     * @return if there are linked organization available without assigned project available
     */
    @Override
    public boolean thereAreLinkedOrganizationAvailableNotAssing (){
        boolean areLinkedOrganization = false;
        int idLinkedOrganization;
        try {
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization INNER JOIN Status " +
                    "ON LinkedOrganization.idStatus = Status.idStatus WHERE status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next() && !areLinkedOrganization){
                idLinkedOrganization = results.getInt("idLinkedOrganization");
                boolean isAssignedProject = validateAssignedProject(idLinkedOrganization);
                if(isAssignedProject){
                    boolean isAssignedProjectAvalable = validateAssignedProjectAvailable(idLinkedOrganization);
                    if(!isAssignedProjectAvalable){
                        areLinkedOrganization=true;
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

    /**
     * Method to know if there are linked organization
     * @return if there are linked organization
     */
    @Override
    public boolean thereAreLinkedOrganization(){
        boolean areLinkedOrganization = false;
        try {
            connection = connexion.getConnection();
            String queryAreLikendOrganization = "SELECT name FROM LinkedOrganization";
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

    /**
     * Method to know if there are linked organization available
     * @return if there are linked organization available
     */
    @Override
    public boolean thereAreLinkedOrganizationAvailable () {
        boolean areLinkedOrganization = false;
        try {
            connection = connexion.getConnection();
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization INNER JOIN Status " +
                    "ON LinkedOrganization.idStatus = Status.idStatus WHERE status =?";
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
