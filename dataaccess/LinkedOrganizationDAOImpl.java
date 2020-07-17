package dataaccess;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.LinkedOrganization;
import domain.Number;
import domain.PhoneNumber;
import domain.Search;
import exception.Exception;
import telegram.TelegramBot;

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
        idState = getIdState(organization.getState());
        if(idState == Search.NOTFOUND.getValue()){
            addState(organization.getState());
            idState = getIdState(organization.getState());
        }
        int idCity;
        idCity = getIdCity(organization.getCity());
        if(idCity == Search.NOTFOUND.getValue()){
            addCity(organization.getCity());
            idCity = getIdCity(organization.getCity());
        }

        int idSector;
        idSector = getIdSector(organization.getSector());
        if(idSector == Search.NOTFOUND.getValue()){
            addSector(organization.getSector());
            idSector = getIdSector(organization.getSector());
        }
        int idStatus;
        StatusDAOImpl status = new StatusDAOImpl();
        idStatus = status.searchIdStatus(organization.getStatus());
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceOrganization = connection.prepareStatement("INSERT INTO LinkedOrganization(name,directUsers"+
                    ",indirectUsers,email,address,idCity,idState,idSector,idStatus) VALUES (?,?,?,?,?,?,?,?,?)");
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            if (connexion != null) {
                connexion.closeConnection();
            }
            return result;
        }
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return isAddCity;
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return isAddSector;
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return isAddState;
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
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization INNER JOIN" +
                    " Status ON LinkedOrganization.idStatus = Status.idStatus where status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next()){
                LinkedOrganization linkedOrganization = new LinkedOrganization();
                linkedOrganization.setName(results.getString("name"));
                linkedOrganization.setEmail(results.getString("email"));
                linkedOrganizations.add(linkedOrganization);
            }

        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization INNER JOIN Status ON" +
                    " LinkedOrganization.idStatus = Status.idStatus WHERE status =?";
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
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
            String queryLikendOrganization = "select * FROM LinkedOrganization INNER JOIN City ON" +
                    " LinkedOrganization.idCity = City.idCity INNER JOIN State ON" +
                    " LinkedOrganization.idState = State.idState INNER JOIN" +
                    " Sector ON LinkedOrganization.idSector =" +
                    " Sector.idSector WHERE name =?";
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
                linkedOrganization.setPhoneNumbers(PhoneNumber.getListPhoneNumber(linkedOrganization.getIdLinkedOrganization()));
            }
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
            String queryLikendOrganization = "SELECT * FROM LinkedOrganization INNER JOIN City ON LinkedOrganization.idCity =" +
                    " City.idCity INNER JOIN State ON LinkedOrganization.idState = State.idState INNER JOIN" +
                    " Sector ON LinkedOrganization.idSector =" +
                    " Sector.idSector WHERE idLinkedOrganization =?";
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
                linkedOrganization.setAddress(results.getString("address"));
                linkedOrganization.setCity(results.getString("City.nameCity"));
                linkedOrganization.setState(results.getString("State.nameState"));
                linkedOrganization.setSector(results.getString("Sector.nameSector"));
                linkedOrganization.setPhoneNumbers(PhoneNumber.getListPhoneNumber(linkedOrganization.getIdLinkedOrganization()));
            }
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return idLinkedOrganization;
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return cities;
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return sectors;
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return states;
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
        String sentence = "UPDATE LinkedOrganization SET "+sentenceDatesUpdate+ " WHERE idLinkedOrganization =" +
                organizationEdit.getIdLinkedOrganization();
        try{
            connection = connexion.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            Class classOganization = organizationEdit.getClass();
            for(int indexPreparedStatement = Number.ONE.getNumber() ; indexPreparedStatement
                    <= datesUpdate.size(); indexPreparedStatement++){
                Method methodLinkedOrganization;
                if(Change.get(indexPreparedStatement - 1).equals("getCity")){
                    idCity = getIdCity(organizationEdit.getCity());
                    if(idCity == Search.NOTFOUND.getValue()){
                        addCity(organizationEdit.getCity());
                        idCity = getIdCity(organizationEdit.getCity());
                    }
                    preparedStatement.setInt(indexPreparedStatement, idCity);
                } else{
                    if(Change.get(indexPreparedStatement - 1).equals("getState")) {
                        idState = getIdState(organizationEdit.getState());
                        if(idState == Search.NOTFOUND.getValue()){
                            addState(organizationEdit.getState());
                            idState = getIdState(organizationEdit.getState());
                        }
                        preparedStatement.setInt(indexPreparedStatement, idState);
                    }else {
                        if(Change.get(indexPreparedStatement - 1).equals("getSector")) {
                            idSector = getIdSector(organizationEdit.getSector());
                            if(idSector == Search.NOTFOUND.getValue()){
                                addSector(organizationEdit.getSector());
                                idSector = getIdSector(organizationEdit.getSector());
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
        } catch (SQLException | ReflectiveOperationException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return result;
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
            String queryAssingOrganization = "SELECT idProject FROM Project,LinkedOrganization WHERE" +
                    " LinkedOrganization.idLinkedOrganization=Project.idLinkedOrganization AND" +
                    " LinkedOrganization.idLinkedOrganization=?";
            PreparedStatement sentenceAssing = connection.prepareStatement(queryAssingOrganization);
            sentenceAssing.setInt(1,idLinkedOrganization);
            ResultSet resultAssing;
            resultAssing = sentenceAssing.executeQuery();
            if(resultAssing.next()){
                isAssignedProject= true;
            }
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
            String queryAssingOrganizationAvailable  = "SELECT idProject FROM Project,LinkedOrganization,Status WHERE" +
                    " LinkedOrganization.idLinkedOrganization = Project.idLinkedOrganization AND" +
                    " Project.idStatus = Status.idStatus AND status=? AND" +
                    " LinkedOrganization.idLinkedOrganization=?";
            PreparedStatement sentenceAssingAvailable = connection.prepareStatement(queryAssingOrganizationAvailable);
            sentenceAssingAvailable.setString(1,"available");
            sentenceAssingAvailable.setInt(2,idLinkedOrganization);
            ResultSet resultAssingAvailable;
            resultAssingAvailable = sentenceAssingAvailable.executeQuery();
            if(!resultAssingAvailable.next()) {
                isAssignedProjectAvailable= true;
            }
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
    public int validateRepeatLinkedOrganization(String name, String email) {
        int isRepeatLinkedOrganization = Search.NOTFOUND.getValue();
        String queryOrganization= "SELECT idLinkedOrganization FROM LinkedOrganization WHERE name=? OR email=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryOrganization);
            sentence.setString(1,name);
            sentence.setString(2,email);
            results= sentence.executeQuery();
            if(results.next()){
                isRepeatLinkedOrganization= Search.FOUND.getValue();
            }
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
            isRepeatLinkedOrganization = Search.EXCEPTION.getValue();
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
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization INNER JOIN Status ON" +
                    " LinkedOrganization.idStatus = Status.idStatus WHERE status =?";
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
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
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
            String queryAllLikendOrganization = "SELECT idLinkedOrganization,name,email FROM LinkedOrganization INNER JOIN Status ON" +
                    " LinkedOrganization.idStatus = Status.idStatus WHERE status =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllLikendOrganization);
            sentenceAllOrganization.setString(1, "available");
            results= sentenceAllOrganization.executeQuery();
            while(results.next() && !areLinkedOrganization){
                areLinkedOrganization = true;
            }
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        } finally {
            connexion.closeConnection();
        }
        return areLinkedOrganization;
    }
}
