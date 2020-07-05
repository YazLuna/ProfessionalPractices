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
     * Constructor for the ResponsbleProjectDAOImpl class
     */
    public ResponsibleProjectDAOImpl (){
        connexion = new Connexion();
    }

    /**
     * Method to get the Responsible of the Project
     * @param idResponsible The idResponsible parameter defines the id of the Responsible Project
     * @return The Responsible of the searched idResponsible
     */
    @Override
    public ResponsibleProject getIdResponsibleProject (int idResponsible) {
        ResponsibleProject responsible = null;     
        try{
            connection = connexion.getConnection();
            String query= "Select * from ResponsibleProject inner join Charge on ResponsibleProject.idCharge " +
                    "= Charge.idCharge inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where idResponsibleProject=?";
            PreparedStatement sentence =connection.prepareStatement(query);
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

    @Override
    public ResponsibleProject getResponsibleProject (String email) {
        ResponsibleProject responsible= null;
        try{
            connection = connexion.getConnection();
            String query= "Select * from ResponsibleProject inner join Charge on ResponsibleProject.idCharge = Charge.idCharge inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where email=?";
            PreparedStatement sentence =connection.prepareStatement(query);
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
     * Method to add a Responsible of the project
     * @param responsible The data of the responsible of the project
     * @return The responsible the of project was added
     */
    @Override
    public boolean addResponsibleProject (ResponsibleProject responsible) {
        int idStatus;
        StatusDAOImpl status = new StatusDAOImpl();
        idStatus = status.searchIdStatus(responsible.getStatus());
        boolean result = false;
        int idCharge;
        idCharge = searchIdCharge(responsible.getCharge());
        if(idCharge == Search.NOTFOUND.getValue()){
            addCharge(responsible.getCharge());
            idCharge = searchIdCharge(responsible.getCharge());
        }
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceResponsible = connection.prepareStatement("insert into ResponsibleProject(name,lastName,email,idStatus,idCharge) values(?,?,?,?,?)");
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
     * Update method of the modify of the project
     * @param responsibleEdit The data of the responsible of the project
     * @return The person of the project was successfully modified
     */
    @Override
    public boolean modifyResponsibleProject (ResponsibleProject responsibleEdit,List<String>DatesUpdate) {
        boolean result = false;
        int idCharge;
        idCharge= searchIdCharge(responsibleEdit.getCharge());
        if(idCharge == Search.NOTFOUND.getValue()){
            addCharge(responsibleEdit.getCharge());
            idCharge = searchIdCharge(responsibleEdit.getCharge());
        }
        String datesUpdate= DatesUpdate.get(0)+ "= ?, ";
        List<String> Change = new ArrayList<>();
        Change.add("get"+DatesUpdate.get(0));
        for (int indexDatesUpdate = 1; indexDatesUpdate < DatesUpdate.size(); indexDatesUpdate++) {
            if (indexDatesUpdate == DatesUpdate.size() - 1) {
                datesUpdate = datesUpdate + DatesUpdate.get(indexDatesUpdate) + "= ?";
            } else {
                datesUpdate = datesUpdate + DatesUpdate.get(indexDatesUpdate) + "= ?,";
            }
            Change.add("get" + DatesUpdate.get(indexDatesUpdate));
        }

        String sentence = "UPDATE ResponsibleProject set "+datesUpdate+ " where idResponsibleProject = "+ responsibleEdit.getIdResponsible();
        try{
            connection = connexion.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            Class classResponsible = responsibleEdit.getClass();
            for(int indexPreparedStatement = 1 ; indexPreparedStatement <= DatesUpdate.size(); indexPreparedStatement++){
                Method methodResponsible;
                boolean isString = true;
                /*try {
                    methodCoordinator = classCoordinator.getMethod(Change.get(indexPreparedStatement - 1));
                    String isWord = (String) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
                } catch (ClassCastException e) {
                    isString = false;
                    //Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, e);
                }
                if(isString){
                    methodCoordinator = classCoordinator.getMethod(Change.get(indexPreparedStatement - 1));
                    String word = (String) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
                    preparedStatement.setString(indexPreparedStatement,word);
                } else{
                    methodCoordinator = classCoordinator.getMethod(Change.get(indexPreparedStatement - 1));
                    int integer = (int) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
                    preparedStatement.setInt(indexPreparedStatement, integer);
                }*/
            }
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException | NoSuchMethodException | IllegalAccessException /*| InvocationTargetException*/ ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }

        /*idCharge = searchIdCharge(responsible.getCharge());
        if(idCharge == Search.NOTFOUND.getValue()){
            addCharge(responsible.getCharge());
            idCharge = searchIdCharge(responsible.getCharge());
        }
        String queryResponsible = "update ResponsibleProject set name=?, lastName=?, email=?, idCharge=? where idResponsibleProject=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceResponsible = connection.prepareStatement(queryResponsible);
            sentenceResponsible.setString(1, responsible.getName());
            sentenceResponsible.setString(2, responsible.getLastName());
            sentenceResponsible.setString(3, responsible.getEmail());
            sentenceResponsible.setInt(4, idCharge);
            sentenceResponsible.setInt(5, responsible.getIdResponsible());
            sentenceResponsible.executeUpdate();
            result = true;
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return  result;
        }*/
        return result;
    }





    @Override
    public List<ResponsibleProject> getAllResponsible() {
        List<ResponsibleProject> responsibles = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select name,lastName,email from ResponsibleProject");
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

    @Override
    public List<ResponsibleProject> getAllResponsibleAvailable() {
        List<ResponsibleProject> responsibles = new ArrayList<>();
        int idResponsibleProject;
        try {
            connection = connexion.getConnection();
            String queryAllResponsibleProject = "select idResponsibleProject,name,lastName,email from ResponsibleProject inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where status =? ";
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

    @Override
    public List<ResponsibleProject> getAllResponsibleAvailableNotAssing() {
        List<ResponsibleProject> responsibles = new ArrayList<>();
        int idResponsibleProject;
        try {
            connection = connexion.getConnection();
            String queryAllResponsibleProject = "select idResponsibleProject,name,lastName,email from ResponsibleProject inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where status =? ";
            PreparedStatement sentenceAllResponsible = connection.prepareStatement(queryAllResponsibleProject);
            sentenceAllResponsible.setString(1, "available");
            results= sentenceAllResponsible.executeQuery();
            while(results.next()){
                idResponsibleProject = results.getInt("idResponsibleProject");
                String queryAssingResponsible = "select idProject from Project,ResponsibleProject WHERE " +
                        "ResponsibleProject.idResponsibleProject = Project.idResponsibleProject AND ResponsibleProject.idResponsibleProject=?";
                PreparedStatement sentenceAssing = connection.prepareStatement(queryAssingResponsible);
                sentenceAssing.setInt(1,idResponsibleProject);
                ResultSet resultAssing;
                resultAssing = sentenceAssing.executeQuery();
                if(resultAssing.next()){
                    String queryAssingResponsibleAvailable = "select idProject from Project,ResponsibleProject,Status WHERE " +
                            "ResponsibleProject.idResponsibleProject = Project.idResponsibleProject AND " +
                            "Project.idStatus = Status.idStatus AND status=? AND ResponsibleProject.idResponsibleProject=?";
                    PreparedStatement sentenceAssingAvailable = connection.prepareStatement(queryAssingResponsibleAvailable);
                    sentenceAssingAvailable.setString(1,"available");
                    sentenceAssingAvailable.setInt(2,idResponsibleProject);
                    ResultSet resultAssingAvailable;
                    resultAssingAvailable = sentenceAssingAvailable.executeQuery();
                    if(!resultAssingAvailable.next()) {
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
     * Method for finding a responsible the of project
     * @param email The email parameter defines the email of the Responsible Project
     * @return The idResponsibleProject of the searched email
     */
    @Override
    public int searchIdResponsibleProject (String email) {
        int idResponsibleProject= Search.NOTFOUND.getValue();;
        try{
            connection = connexion.getConnection();
            String queryResponsible= "Select idResponsibleProject from ResponsibleProject where email=?";
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
     * Method to add a Charge
     * @param name The name parameter defines the charge of responsible of the project
     * @return The charge was successfully registered
     */
    public String addCharge (String name) {
        String resultAddCharge = "El Cargo no se pudo registrar";
        String queryCharge = "insert into Charge (nameCharge) values (?)";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCharge = connection.prepareStatement(queryCharge);
            sentenceCharge.setString(1,name);
            sentenceCharge.executeUpdate();
            resultAddCharge = "El cargo se registro con exito";
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return resultAddCharge;
    }

    /**
     * Method to search for a charge
     * @param name The name parameter defines the charge of responsible of the project
     * @return The idCharge of the charge searched
     */
    public int searchIdCharge(String name) {
        int idCharge = Search.NOTFOUND.getValue();;
        String queryCharge= "Select idCharge from Charge where nameCharge=?";
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
    public List<String> getAllCharge() {
        List<String> charges = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select nameCharge from Charge");
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

    @Override
    public boolean deleteResponsibleProject (int idResponsibleProject) {
       boolean result= false;
        StatusDAOImpl status = new StatusDAOImpl();
        int idStatus = status.searchIdStatus("not available");
        try{
            connection = connexion.getConnection();
            String queryDelete = "update ResponsibleProject set idStatus=? where idResponsibleProject =?";
            PreparedStatement sentence = connection.prepareStatement(queryDelete);
            sentence.setInt(1, idStatus);
            sentence.setInt(2, idResponsibleProject);
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

    @Override
    public boolean thereAreResponsibleProjectAvailable (){
        boolean areResponsibleProject = false;
        try {
            connection = connexion.getConnection();
            String queryAreResponsibleProjectAvailable = "select name from ResponsibleProject inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where status =?";
            PreparedStatement sentence = connection.prepareStatement(queryAreResponsibleProjectAvailable);
            sentence.setString(1, "available");
            results= sentence.executeQuery();
            while (results.next()) {

                areResponsibleProject = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LinkedOrganizationDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areResponsibleProject;
    }

    @Override
    public boolean thereAreResponsibleProject(){
        boolean areResponsibleProject = false;
        int idResponsibleProject;
        try {
            connection = connexion.getConnection();
            String queryAllResponsibleProject = "select idResponsibleProject,name,lastName,email from ResponsibleProject inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where status =? ";
            PreparedStatement sentenceAllResponsible = connection.prepareStatement(queryAllResponsibleProject);
            sentenceAllResponsible.setString(1, "available");
            results= sentenceAllResponsible.executeQuery();
            while(results.next() && !areResponsibleProject){
                idResponsibleProject = results.getInt("idResponsibleProject");
                String queryAssingResponsible = "select idProject from Project,ResponsibleProject WHERE " +
                        "ResponsibleProject.idResponsibleProject = Project.idResponsibleProject AND ResponsibleProject.idResponsibleProject=?";
                PreparedStatement sentenceAssing = connection.prepareStatement(queryAssingResponsible);
                sentenceAssing.setInt(1,idResponsibleProject);
                ResultSet resultAssing;
                resultAssing = sentenceAssing.executeQuery();
                if(resultAssing.next()){
                    String queryAssingResponsibleAvailable = "select idProject from Project,ResponsibleProject,Status WHERE " +
                            "ResponsibleProject.idResponsibleProject = Project.idResponsibleProject AND " +
                            "Project.idStatus = Status.idStatus AND status=? AND ResponsibleProject.idResponsibleProject=?";
                    PreparedStatement sentenceAssingAvailable = connection.prepareStatement(queryAssingResponsibleAvailable);
                    sentenceAssingAvailable.setString(1,"available");
                    sentenceAssingAvailable.setInt(2,idResponsibleProject);
                    ResultSet resultAssingAvailable;
                    resultAssingAvailable = sentenceAssingAvailable.executeQuery();
                    if(!resultAssingAvailable.next()) {
                        areResponsibleProject = true;
                    }
                }else {
                   areResponsibleProject = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areResponsibleProject;
    }

    @Override
    public boolean thereAreResponsibleProjectAvailableNotAssing(){
        boolean areResponsibleProject = false;
        try {
            connection = connexion.getConnection();
            String queryAllResponsibleProject = "select idResponsibleProject,name,lastName,email from ResponsibleProject inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where status =? ";
            PreparedStatement sentenceAllResponsible = connection.prepareStatement(queryAllResponsibleProject);
            sentenceAllResponsible.setString(1, "available");
            results= sentenceAllResponsible.executeQuery();
            while(results.next() && !areResponsibleProject){
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
