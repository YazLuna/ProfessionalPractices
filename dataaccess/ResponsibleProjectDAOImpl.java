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
    public ResponsibleProject getResponsibleProject (int idResponsible) {
        ResponsibleProject responsible = null;     
        try{
            connection = connexion.getConnection();
            String query= "Select * from ResponsibleProject inner join Charge on ResponsibleProject.idCharge = Charge.idCharge inner join Status on " +
                    "ResponsibleProject.idStatus = Status.idStatus where email=?";
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
    public String addResponsibleProject (ResponsibleProject responsible) {
        int idStatus;
        StatusDAOImpl status = new StatusDAOImpl();
        idStatus = status.searchIdStatus(responsible.getStatus());
        if(idStatus == Search.NOTFOUND.getValue()){
            status.addStatus(responsible.getStatus());
            idStatus = status.searchIdStatus(responsible.getStatus());
        }
        int idCharge;
        String result = "El responsable del proyecto no pudo registrarse";
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
            result = "El responsable del proyecto se registro exitosamente";
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return result;
        }
    }

    /**
     * Update method of the modify of the project
     * @param responsible The data of the responsible of the project
     * @return The person of the project was successfully modified
     */
    @Override
    public String modifyResponsibleProject (ResponsibleProject responsible) {
        int idCharge;
        String result = "El responsable del proyecto no pudo modificarse";
        idCharge = searchIdCharge(responsible.getCharge());
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
            result = "El responsable del proyecto se modificó con éxito";
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return  result;
        }
    }

    @Override
    public List<String> getAllResponsible() {
        List<String> responsibles = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consultation = connection.createStatement();
            results = consultation.executeQuery("select name,lastName,email from ResponsibleProject");
            while(results.next()){
                responsibles.add(results.getString("name"));
                responsibles.add(results.getString("lastName"));
                responsibles.add(results.getString("email"));
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
        String result = "El Cargo no se pudo registrar";
        String queryCharge = "insert into Charge (nameCharge) values (?)";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCharge = connection.prepareStatement(queryCharge);
            sentenceCharge.setString(1,name);
            sentenceCharge.executeUpdate();
            result = "El cargo se registro con exito";
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return result;
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
}
