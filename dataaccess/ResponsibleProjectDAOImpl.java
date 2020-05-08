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
            String query= "Select * from ResponsibleProject inner join Charge on ResponsibleProject.idCharge = Charge.idCharge where idResponsibleProject=?";
            PreparedStatement sentence =connection.prepareStatement(query);
            sentence.setInt(1,idResponsible);
            results= sentence.executeQuery();
            while(results.next()){
                responsible = new ResponsibleProject();
                responsible.setIdResponsible(results.getInt("idResponsibleProject"));
                responsible.setName(results.getString("name"));
                responsible.setLastName(results.getString("lastName"));
                responsible.setEmail(results.getString("email"));
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
     * @return The message if the responsible the of project was added
     */
    @Override
    public String updateResponsibleProject (ResponsibleProject responsible) {
        int idCharge = searchCharge(responsible.getCharge());
        String result = "The responsible for the project could not register";
        if(idCharge==0){
            updateCharge(responsible.getCharge());
            idCharge=searchCharge(responsible.getCharge());
        } 
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceOrganization = connection.prepareStatement("insert into ResponsibleProject(name,lastName,email,idCharge) values(?,?,?,?)");
            sentenceOrganization.setString(1,responsible.getName());
            sentenceOrganization.setString(2,responsible.getLastName());
            sentenceOrganization.setString(3,responsible.getEmail());
            sentenceOrganization.setInt(4,idCharge);   
            sentenceOrganization.executeUpdate();
            result = "The responsible of the project registered correctly";
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return result;
        }
    }

    /**
     * Update method of the responsible of the project
     * @param responsible The data of the responsible of the project
     * @return The message if the responsible for the project was updated
     */
    @Override
    public String actualizationResponsibleProject (ResponsibleProject responsible) {
        int idCharge;
        String result = "The responsible project could not be updated";
        idCharge = searchCharge(responsible.getCharge());
        if(idCharge == 0){
            updateCharge(responsible.getCharge());
            idCharge = searchCharge(responsible.getCharge());
        }
        try{
            connection = connexion.getConnection();
            String queryResponsible = "update ResponsibleProject set name=?, lastName=?, email=?, idCharge=? where idResponsibleProject=?";
            PreparedStatement sentenceResponsible = connection.prepareStatement(queryResponsible);
            sentenceResponsible.setString(1, responsible.getName());
            sentenceResponsible.setString(2, responsible.getLastName());
            sentenceResponsible.setString(3, responsible.getEmail());
            sentenceResponsible.setInt(4, idCharge);
            sentenceResponsible.setInt(5, responsible.getIdResponsible());
            sentenceResponsible.executeUpdate();
            result = "The responsible the project was updated correctly";
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return  result;
        }
    }

    /**
     * Method for finding a responsible the of project
     * @param email The email parameter defines the email of the Responsible Project
     * @return The idResponsibleProject of the searched email
     */
    @Override
    public int searchResponsibleProject (String email) {
        int idResponsibleProject=0;
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
     */
    public void updateCharge (String name) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCharge = connection.prepareStatement("insert into Charge (nameCharge) values (?)");
            sentenceCharge.setString(1,name);
            sentenceCharge.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    /**
     * Method to search for a charge
     * @param name The name parameter defines the charge of responsible of the project
     * @return The idCharge of the searched charge
     */
    public int searchCharge (String name) {
        int idCharge=0;
        try{
            connection = connexion.getConnection();
            String queryCharge= "Select idCharge from Charge where nameCharge=?";
            PreparedStatement sentence =connection.prepareStatement(queryCharge);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            while(results.next()){
                idCharge =results.getInt("idCharge");
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
