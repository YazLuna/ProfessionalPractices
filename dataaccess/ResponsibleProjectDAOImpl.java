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
import java.lang.reflect.Method;
import domain.ResponsibleProject;
import domain.Search;


/**
 * Implementation of the IResponsibleProjectDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class ResponsibleProjectDAOImpl implements IResponsibleProjectDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the ResponsibleProjectDAOImpl class
     */
    public ResponsibleProjectDAOImpl (){
        connexion = new Connexion();
    }

    /**
     * Method to add a Responsible of the project
     * @param responsible The data of the responsible of the project
     * @return if the responsible the of project was added
     */
    @Override
    public boolean addResponsibleProject (ResponsibleProject responsible) {
        int idStatus;
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        idStatus = statusDAO.searchIdStatus(responsible.getStatus());
        boolean result = false;
        int idCharge;
        ChargeDAOImpl chargeDAO = new ChargeDAOImpl();
        idCharge = getIdCharge(responsible.getCharge());
        if(idCharge == Search.NOTFOUND.getValue()){
            addCharge(responsible.getCharge());
            idCharge = getIdCharge(responsible.getCharge());
        }
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceResponsible = connection.prepareStatement("insert into ResponsibleProject(name,lastName" +
                    ",email,idStatus,idCharge) values(?,?,?,?,?)");
            sentenceResponsible.setString(1,responsible.getName());
            sentenceResponsible.setString(2,responsible.getLastName());
            sentenceResponsible.setString(3,responsible.getEmail());
            sentenceResponsible.setInt(4, idStatus);
            sentenceResponsible.setInt(5,idCharge);
            sentenceResponsible.executeUpdate();
            result = true;
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return result;
        }
    }

    /**
     * Method to add a Charge
     * @param name defines the charge of responsible of the project
     * @return if the charge was successfully add
     */
    @Override
    public boolean addCharge (String name) {
        boolean resultAddCharge = false;
        String queryCharge = "INSERT INTO Charge (nameCharge) VALUES(?)";
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
     * Method to obtain the responsible of the project
     * @param idResponsible defines the id of the Responsible Project
     * @return The Responsible of the searched idResponsible
     */
    @Override
    public ResponsibleProject getResponsibleProjectWithId(int idResponsible) {
        ResponsibleProject responsible = new ResponsibleProject();
        try{
            connection = connexion.getConnection();
            String queryGetResponsible = "SELECT * FROM ResponsibleProject INNER JOIN Charge ON ResponsibleProject.idCharge " +
                    "= Charge.idCharge INNER JOIN Status ON ResponsibleProject.idStatus " +
                    "= Status.idStatus WHERE idResponsibleProject=?";
            PreparedStatement sentence =connection.prepareStatement(queryGetResponsible);
            sentence.setInt(1,idResponsible);
            results= sentence.executeQuery();
            while(results.next()){
                responsible = new ResponsibleProject();
                responsible.setIdResponsible(results.getInt("idResponsibleProject"));
                responsible.setName(results.getString("name"));
                responsible.setLastName(results.getString("lastName"));
                responsible.setEmail(results.getString("email"));
                responsible.setStatus(results.getString("status"));
                responsible.setCharge(results.getString("nameCharge"));
            }
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return responsible;
    }

    /**
     * Method to obtain the responsible of the project
     * @param email defines the email of the Responsible Project
     * @return The Responsible of the project of the searched email
     */
    @Override
    public ResponsibleProject getResponsibleProject (String email) {
        ResponsibleProject responsible= new ResponsibleProject();
        try{
            connection = connexion.getConnection();
            String queryGetResponsible = "SELECT * FROM ResponsibleProject INNER JOIN Charge ON ResponsibleProject.idCharge " +
                    "= Charge.idCharge INNER JOIN Status ON ResponsibleProject.idStatus " +
                    "= Status.idStatus WHERE email=?";
            PreparedStatement sentence =connection.prepareStatement(queryGetResponsible);
            sentence.setString(1,email);
            results= sentence.executeQuery();
            while(results.next()){
                responsible = new ResponsibleProject();
                responsible.setIdResponsible(results.getInt("idResponsibleProject"));
                responsible.setName(results.getString("name"));
                responsible.setLastName(results.getString("lastName"));
                responsible.setEmail(results.getString("email"));
                responsible.setStatus(results.getString("status"));
                responsible.setCharge(results.getString("Charge.nameCharge"));
            }
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return responsible;
    }

    /**
     * Method for finding a id responsible the of project
     * @param email defines the email of the Responsible Project
     * @return The idResponsibleProject of the searched email
     */
    @Override
    public int getIdResponsibleProject(String email) {
        int idResponsibleProject= Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryResponsible= "SELECT idResponsibleProject FROM ResponsibleProject WHERE email=?";
            PreparedStatement sentence =connection.prepareStatement(queryResponsible);
            sentence.setString(1,email);
            results= sentence.executeQuery();
            while(results.next()){
                idResponsibleProject =results.getInt("idResponsibleProject");
            }
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idResponsibleProject;
    }

    /**
     * Method for obtaining all those responsible for the project
     * @return List of responsible of the project
     */
    @Override
    public List<ResponsibleProject> getAllResponsible() {
        List<ResponsibleProject> responsibles = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("SELECT name,lastName,email FROM ResponsibleProject");
            while(results.next()){
                ResponsibleProject responsibleProject = new ResponsibleProject();
                responsibleProject.setName(results.getString("name"));
                responsibleProject.setLastName(results.getString("lastName"));
                responsibleProject.setEmail(results.getString("email"));
                responsibles.add(responsibleProject);
            }
        }catch (SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return responsibles;
    }

    /**
     * Method for obtaining all those responsible for the project available
     * @return List of responsible of the project available
     */
    @Override
    public List<ResponsibleProject> getAllResponsibleAvailable() {
        List<ResponsibleProject> responsibles = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            String queryAllResponsibleProject = "SELECT idResponsibleProject,name,lastName,email " +
                    "FROM ResponsibleProject INNER JOIN Status ON ResponsibleProject.idStatus " +
                    "= Status.idStatus WHERE status =? ";
            PreparedStatement sentenceAllResponsible = connection.prepareStatement(queryAllResponsibleProject);
            sentenceAllResponsible.setString(1, "available");
            results= sentenceAllResponsible.executeQuery();
            while(results.next()){
                ResponsibleProject responsibleProject = new ResponsibleProject();
                responsibleProject.setName(results.getString("name"));
                responsibleProject.setLastName(results.getString("lastName"));
                responsibleProject.setEmail(results.getString("email"));
                responsibles.add(responsibleProject);
            }
        }catch (SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return responsibles;
    }

    /**
     * Method to obtaining all responsible of the project available without assigned project available
     * @return List of responsible of the project available without assigned project available
     */
    @Override
    public List<ResponsibleProject> getAllResponsibleAvailableNotAssing() {
        List<ResponsibleProject> responsibles = new ArrayList<>();
        int idResponsibleProject;
        try {
            connection = connexion.getConnection();
            String queryAllResponsibleProject = "SELECT idResponsibleProject,name,lastName,email FROM ResponsibleProject " +
                    "INNER JOIN Status ON ResponsibleProject.idStatus " +
                    "= Status.idStatus WHERE status =? ";
            PreparedStatement sentenceAllResponsible = connection.prepareStatement(queryAllResponsibleProject);
            sentenceAllResponsible.setString(1, "available");
            results= sentenceAllResponsible.executeQuery();
            while(results.next()){
                idResponsibleProject = results.getInt("idResponsibleProject");
                boolean isAssingnedProject;
                isAssingnedProject = validateAssignedProject(idResponsibleProject);
                if(isAssingnedProject){
                    boolean isAssingnedProjectAvailable;
                    isAssingnedProjectAvailable = validateAssignedProjectAvailable(idResponsibleProject);
                    if(!isAssingnedProjectAvailable) {
                        ResponsibleProject responsibleProject = new ResponsibleProject();
                        responsibleProject.setName(results.getString("name"));
                        responsibleProject.setLastName(results.getString("lastName"));
                        responsibleProject.setEmail(results.getString("email"));
                        responsibles.add(responsibleProject);
                    }
                }else {
                    ResponsibleProject responsibleProject = new ResponsibleProject();
                    responsibleProject.setName(results.getString("name"));
                    responsibleProject.setLastName(results.getString("lastName"));
                    responsibleProject.setEmail(results.getString("email"));
                    responsibles.add(responsibleProject);
                }
            }
        }catch (SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return responsibles;
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

    /**
     * The method for modify data of the project
     * @param responsibleEdit The data of the responsible of the project
     * @param datesUpdate The data to modify of the Responsible of the Project
     * @return if the responsible of the project was modified
     */
    @Override
    public boolean modifyResponsibleProject (ResponsibleProject responsibleEdit,List<String> datesUpdate) {
        boolean result = false;
        int idCharge;
        String sentenceDatesUpdate="";
        List<String> change = new ArrayList<>();
        for (int indexDatesUpdate = Number.ZERO.getNumber(); indexDatesUpdate < datesUpdate.size(); indexDatesUpdate++) {
            if (indexDatesUpdate == datesUpdate.size() - 1) {
                if(datesUpdate.get(indexDatesUpdate).equals("Charge")) {
                    sentenceDatesUpdate = sentenceDatesUpdate + "idCharge = ?";
                }else {
                    sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?";
                }
            } else {
                if(datesUpdate.get(indexDatesUpdate).equals("Charge")) {
                    sentenceDatesUpdate = sentenceDatesUpdate + "idCharge = ?,";
                }else{
                    sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?,";
                }
            }
            change.add("get" + datesUpdate.get(indexDatesUpdate));
        }
        String sentence = "UPDATE ResponsibleProject SET "+sentenceDatesUpdate+ " WHERE idResponsibleProject " +
                "= "+ responsibleEdit.getIdResponsible();
        try{
            connection = connexion.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            Class classResponsible = responsibleEdit.getClass();
            for(int indexPreparedStatement = Number.ONE.getNumber() ; indexPreparedStatement
                    <= datesUpdate.size(); indexPreparedStatement++){
                Method methodResponsible;
                if(change.get(indexPreparedStatement - 1).equals("getCharge")){
                    ChargeDAOImpl chargeDAO = new ChargeDAOImpl();
                    idCharge= getIdCharge(responsibleEdit.getCharge());
                    if(idCharge == Search.NOTFOUND.getValue()){
                        addCharge(responsibleEdit.getCharge());
                        idCharge = getIdCharge(responsibleEdit.getCharge());
                    }
                    preparedStatement.setInt(indexPreparedStatement, idCharge);
                } else{
                    methodResponsible = classResponsible.getMethod(change.get(indexPreparedStatement - 1));
                    String word = (String) methodResponsible.invoke(responsibleEdit, new Object[] {});
                    preparedStatement.setString(indexPreparedStatement,word);
                }
            }
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    /**
     * Method to delete a project of the project
     * @param email defines the email of the Responsible Project
     * @return if the responsible of the project was deleted
     */
    @Override
    public boolean deleteResponsibleProject (String email) {
        boolean result = false;
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idStatus = statusDAO.searchIdStatus("not available");
        try{
            connection = connexion.getConnection();
            String queryDelete = "UPDATE ResponsibleProject SET idStatus=? WHERE email =?";
            PreparedStatement sentence = connection.prepareStatement(queryDelete);
            sentence.setInt(1, idStatus);
            sentence.setString(2, email);
            sentence.executeUpdate();
            result= true;
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
            return result;
        }
    }

    /**
     * Method to validate if there is another responsible for the project with the same email
     * @param email defines the email of the Responsible Project
     * @return if the responsible for the project exists
     */
    @Override
    public boolean validateRepeatResponsibleProject (String email) {
        boolean isRepeatResponsibleProject = false;
        try{
            connection = connexion.getConnection();
            String queryResponsible= "SELECT idResponsibleProject FROM ResponsibleProject WHERE email=?";
            PreparedStatement sentence =connection.prepareStatement(queryResponsible);
            sentence.setString(1,email);
            results= sentence.executeQuery();
            if(results.next()){
                isRepeatResponsibleProject = true;
            }
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isRepeatResponsibleProject;
    }

    /**
     * Method to validate if a responsible of the project this assigned to a project
     * @param idResponsibleProject The id of the responsible of the project
     * @return if the responsible of the project this assigned
     */
    @Override
    public boolean validateAssignedProject(int idResponsibleProject) {
        boolean isAssignedProject= false;
        try {
            connection = connexion.getConnection();
            String queryAssingResponsible = "SELECT idProject FROM Project,ResponsibleProject WHERE ResponsibleProject.idResponsibleProject " +
                    "= Project.idResponsibleProject AND ResponsibleProject.idResponsibleProject=?";
            PreparedStatement sentenceAssing = connection.prepareStatement(queryAssingResponsible);
            sentenceAssing.setInt(1,idResponsibleProject);
            ResultSet resultAssing;
            resultAssing = sentenceAssing.executeQuery();
            if(resultAssing.next()){
                isAssignedProject= true;
            }
        }catch (SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAssignedProject;
    }

    /**
     * Method to validate if a responsible of the project this assigned to a project available
     * @param idResponsibleProject The id of the responsible of the project
     * @return if the responsible of the project this assigned available
     */
    @Override
    public boolean validateAssignedProjectAvailable (int idResponsibleProject) {
        boolean isAssignedProjectAvailable= false;
        try {
            connection = connexion.getConnection();
            String queryAssingResponsibleAvailable = "SELECT idProject FROM Project,ResponsibleProject" +
                    ",Status WHERE ResponsibleProject.idResponsibleProject = Project.idResponsibleProject " +
                    "AND Project.idStatus = Status.idStatus AND status" +
                    "=? AND ResponsibleProject.idResponsibleProject=?";
            PreparedStatement sentenceAssingAvailable = connection.prepareStatement(queryAssingResponsibleAvailable);
            sentenceAssingAvailable.setString(1,"available");
            sentenceAssingAvailable.setInt(2,idResponsibleProject);
            ResultSet resultAssingAvailable;
            resultAssingAvailable = sentenceAssingAvailable.executeQuery();
            if(!resultAssingAvailable.next()) {
                isAssignedProjectAvailable=true;
            }
        }catch (SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAssignedProjectAvailable;
    }

    /**
     * Method to know if there are responsible of the project available
     * @return if there are responsible of the project available
     */
    @Override
    public boolean thereAreResponsibleProjectAvailable (){
        boolean areResponsibleProject = false;
        try {
            connection = connexion.getConnection();
            String queryAreResponsibleProjectAvailable = "SELECT name FROM ResponsibleProject " +
                    "INNER JOIN Status ON ResponsibleProject.idStatus " +
                    "= Status.idStatus where status =?";
            PreparedStatement sentence = connection.prepareStatement(queryAreResponsibleProjectAvailable);
            sentence.setString(1, "available");
            results= sentence.executeQuery();
            if(results.next()) {
                areResponsibleProject = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areResponsibleProject;
    }

    /**
     * Method to know if there are responsible of the project available without assigned project available
     * @return if there are responsible of the project available without assigned project available
     */
    @Override
    public boolean thereAreResponsibleProjectAvailableNotAssing(){
        boolean areResponsibleProject = false;
        int idResponsibleProject;
        try {
            connection = connexion.getConnection();
            String queryAreResponsibleProject = "SELECT idResponsibleProject,name,lastName,email " +
                    "FROM ResponsibleProject INNER JOIN Status ON ResponsibleProject.idStatus " +
                    "= Status.idStatus WHERE status =? ";
            PreparedStatement sentenceAllResponsible = connection.prepareStatement(queryAreResponsibleProject);
            sentenceAllResponsible.setString(1, "available");
            results= sentenceAllResponsible.executeQuery();
            while(results.next() && !areResponsibleProject){
                idResponsibleProject = results.getInt("idResponsibleProject");
                boolean isAssignedProject = validateAssignedProject(idResponsibleProject);
                if(isAssignedProject){
                    boolean isAssignedProjectAvalable = validateAssignedProjectAvailable(idResponsibleProject);
                    if(!isAssignedProjectAvalable){
                        areResponsibleProject=true;
                    }
                }else{
                    areResponsibleProject=true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areResponsibleProject;
    }

    /**
     * Method to know if there are responsible of the project
     * @return if there are responsible of the project
     */
    @Override
    public boolean thereAreResponsibleProject(){
        boolean areResponsibleProject = false;
        try {
            connection = connexion.getConnection();
            String queryAreResponsibleProject = "SELECT idResponsibleProject FROM ResponsibleProject";
            PreparedStatement sentenceAllResponsible = connection.prepareStatement(queryAreResponsibleProject);
            results= sentenceAllResponsible.executeQuery();
            if(results.next()){
                areResponsibleProject = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areResponsibleProject;
    }
}
