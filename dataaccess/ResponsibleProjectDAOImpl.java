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
            String query= "Select * from ResponsibleProject inner join Charge on ResponsibleProject.idCharge = Charge.idCharge where email=?";
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
        assert searchResponsibleProject(responsible.getEmail())!=true : "Existe un responsable del proyecto con el mismo correo electrónico registrado";
        int idCharge;
        String result = "El responsable del proyecto no pudo registrarse";
        if(!searchCharge(responsible.getCharge())){
            addCharge(responsible.getCharge());
        }
        idCharge= getCharge(responsible.getCharge());
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceOrganization = connection.prepareStatement("insert into ResponsibleProject(name,lastName,email,status,idCharge) values(?,?,?,?,?)");
            sentenceOrganization.setString(1,responsible.getName());
            sentenceOrganization.setString(2,responsible.getLastName());
            sentenceOrganization.setString(3,responsible.getEmail());
            sentenceOrganization.setString(4, responsible.getStatus());
            sentenceOrganization.setInt(5,idCharge);
            sentenceOrganization.executeUpdate();
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
        if(!searchCharge(responsible.getCharge())){
            addCharge(responsible.getCharge());
        }
        idCharge = getCharge(responsible.getCharge());
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

    /**
     * Method for finding a responsible the of project
     * @param email The email parameter defines the email of the Responsible Project
     * @return The idResponsibleProject of the searched email
     */
    @Override
    public int getIdResponsibleProject (String email) {
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

    @Override
    public boolean searchResponsibleProject (String email) {
        boolean isResponsibleProject=false;
        String queryResponsible= "Select idResponsibleProject from ResponsibleProject where email=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryResponsible);
            sentence.setString(1,email);
            results= sentence.executeQuery();
            if(results.next()){
                isResponsibleProject=true;
            }
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isResponsibleProject;
    }

    /**
     * Method to add a Charge
     * @param name The name parameter defines the charge of responsible of the project
     */
    public String addCharge (String name) {
        assert !searchCharge(name) : "El Cargo ya existe";
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
     * Method to get for a charge
     * @param name The name parameter defines the charge of responsible of the project
     * @return The idCharge of the charge obtained
     */
    public int getCharge (String name) {
        int idCharge = 0;
        String queryCharge= "Select idCharge from Charge where nameCharge=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryCharge);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            idCharge = results.getInt("idCharge");
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idCharge;
    }

    public boolean searchCharge (String name) {
        boolean isCharge = false;
        String queryCharge= "Select idCharge from Charge where nameCharge=?";
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence =connection.prepareStatement(queryCharge);
            sentence.setString(1,name);
            results= sentence.executeQuery();
            if(results.next()){
                isCharge=true;
            }
        }catch(SQLException ex){
            Logger.getLogger(ResponsibleProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isCharge;
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
