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
import domain.Project;
import domain.Search;


/**
 * Implementation of the IProjectDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class ProjectDAOImpl implements IProjectDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the ProjectDAOImpl class
     */
    public ProjectDAOImpl (){
        connexion= new Connexion();
    }


    /**
     * Method to get the Project list
     * @return The Project list
     */
    @Override
    public List<Project> getAllProjects (){
        List<Project> projects = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consultation  = connection.createStatement();
            results = consultation.executeQuery("select * from Project inner join Lapse on Project.idLapse = Lapse.idLapse");
            LinkedOrganizationDAOImpl implementOrganization = new LinkedOrganizationDAOImpl();
            ResponsibleProjectDAOImpl implementResponsible = new ResponsibleProjectDAOImpl();

            while(results.next()){
                Project project = new Project();
                project.setIdProject(results.getInt("idProject"));
                project.setNameProject(results.getString("nameProject"));
                project.setDescription(results.getString("description"));
                project.setObjectiveGeneral(results.getString("objectiveGeneral"));
                project.setObjectiveInmediate(results.getString("objectiveInmediate"));
                project.setObjectiveMediate(results.getString("objectiveMediate"));
                project.setMethodology(results.getString("methodology"));
                project.setResources(results.getString("resources"));
                project.setStatus(results.getString("status"));
                project.setActivities(results.getString("activities"));
                project.setResponsabilities(results.getString("responsabilities"));
                project.setDuration(results.getInt("duration"));
                project.setQuantityPractitioner(results.getInt("quiantityPractitioner"));
                project.setLapse(results.getString("lapse"));
                project.setStaffNumberCoordinator(results.getInt("staffNumberCoordinator"));
                project.setOrganization(implementOrganization.getIdLinkedOrganization(results.getInt("idLinkedOrganization")));
                project.setResponsible(implementResponsible.getResponsibleProject(results.getInt("idResponsibleProject")));
                projects.add(project);
            }
        }catch (SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return projects;
    }

    @Override
    public List<Project> getAllProjectsAvailable (){
        List<Project> projects = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consultation  = connection.createStatement();
            results = consultation.executeQuery("select * from Project inner join Lapse on Project.idLapse = Lapse.idLapse"+
                    " where status='available'");
            LinkedOrganizationDAOImpl implementOrganization = new LinkedOrganizationDAOImpl();
            ResponsibleProjectDAOImpl implementResponsible = new ResponsibleProjectDAOImpl();

            while(results.next()){
                Project project = new Project();
                project.setIdProject(results.getInt("idProject"));
                project.setNameProject(results.getString("nameProject"));
                project.setDescription(results.getString("description"));
                project.setObjectiveGeneral(results.getString("objectiveGeneral"));
                project.setObjectiveInmediate(results.getString("objectiveInmediate"));
                project.setObjectiveMediate(results.getString("objectiveMediate"));
                project.setMethodology(results.getString("methodology"));
                project.setResources(results.getString("resources"));
                project.setStatus(results.getString("status"));
                project.setActivities(results.getString("activities"));
                project.setResponsabilities(results.getString("responsabilities"));
                project.setDuration(results.getInt("duration"));
                project.setQuantityPractitioner(results.getInt("quiantityPractitioner"));
                project.setLapse(results.getString("lapse"));
                project.setStaffNumberCoordinator(results.getInt("staffNumberCoordinator"));
                project.setOrganization(implementOrganization.getIdLinkedOrganization(results.getInt("idLinkedOrganization")));
                project.setResponsible(implementResponsible.getResponsibleProject(results.getInt("idResponsibleProject")));
                projects.add(project);
            }
        }catch (SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return projects;
    }

    /**
     * Method to get the Project
     * @param nameProject The nameProject parameter defines the name of the Project
     * @return The Project of the searched nameProject
     */
    @Override
    public Project getProject (String nameProject) {
        Project project = null;
        try{
            connection = connexion.getConnection();
            String queryProject = "select * from Project inner join Lapse on Project.idLapse = Lapse.idLapse where nameProject=?";
            PreparedStatement sentence = connection.prepareStatement(queryProject);
            sentence.setString(1,nameProject);
            results = sentence.executeQuery();
            LinkedOrganizationDAOImpl implementOrganization = new LinkedOrganizationDAOImpl();
            ResponsibleProjectDAOImpl implementResponsible = new ResponsibleProjectDAOImpl();
            while(results.next()){
                project = new Project();
                project.setIdProject(results.getInt("idProject"));
                project.setNameProject(results.getString("nameProject"));
                project.setDescription(results.getString("description"));
                project.setObjectiveGeneral(results.getString("objectiveGeneral"));
                project.setObjectiveInmediate(results.getString("objectiveInmediate"));
                project.setObjectiveMediate(results.getString("objectiveMediate"));
                project.setMethodology(results.getString("methodology"));
                project.setResources(results.getString("resources"));
                project.setStatus(results.getString("status"));
                project.setActivities(results.getString("activities"));
                project.setResponsabilities(results.getString("responsabilities"));
                project.setDuration(results.getInt("duration"));
                project.setLapse(results.getString("lapse"));
                project.setStaffNumberCoordinator(results.getInt("staffNumberCoordinator"));
                project.setQuantityPractitioner(results.getInt("quiantityPractitioner"));
                project.setOrganization(implementOrganization.getIdLinkedOrganization(results.getInt("idLinkedOrganization")));
                project.setResponsible(implementResponsible.getResponsibleProject(results.getInt("idResponsibleProject")));
            }
        }catch (SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return project;
        
    }

    /**
     * Method to add a Project
     * @param project The data of the Project
     * @return The message if the Project was added
     */
    @Override
    public String updateProject (Project project) {
        String result = "The project could not be registered";
        int idResponsibleProject;
        int idOrganization;
        int idLapse;
        LinkedOrganizationDAOImpl organizationImpl = new LinkedOrganizationDAOImpl();
        idOrganization = organizationImpl.searchIdLinkedOrganization(project.getOrganization().getName(),project.getOrganization().getEmail(), project.getOrganization().getPhoneNumber());
        if(idOrganization == Search.NOTFOUND.getValue()) {
            result = organizationImpl.addLinkedOrganization(project.getOrganization());
            idOrganization = organizationImpl.searchIdLinkedOrganization(project.getOrganization().getName(),project.getOrganization().getEmail(),project.getOrganization().getPhoneNumber());
        }
        ResponsibleProjectDAOImpl responsibleImpl = new ResponsibleProjectDAOImpl();
        idResponsibleProject = responsibleImpl.searchIdResponsibleProject(project.getResponsible().getEmail());
        if(idResponsibleProject == Search.NOTFOUND.getValue()) {
            result = responsibleImpl.addResponsibleProject(project.getResponsible());
            idResponsibleProject = responsibleImpl.searchIdResponsibleProject(project.getResponsible().getEmail());
        }
        LapseDAOImpl lapse = new LapseDAOImpl();
        idLapse = lapse.searchLapse(project.getLapse());
        if(idLapse == Search.NOTFOUND.getValue()) {
            lapse.addLapse(project.getLapse());
            idLapse=lapse.searchLapse(project.getLapse());
        }

        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("insert into Project(nameProject,description,objectiveGeneral,"+
                    "objectiveInmediate,objectiveMediate,Methodology,resources,status,activities,"+
                    "responsabilities,duration,quiantityPractitioner,idLinkedOrganization,idResponsibleProject,"+
                    "staffNumberCoordinator,idLapse) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sentenceProject.setString(1, project.getNameProject());
            sentenceProject.setString(2, project.getDescription());
            sentenceProject.setString(3, project.getObjectiveGeneral());
            sentenceProject.setString(4, project.getObjectiveInmediate());
            sentenceProject.setString(5, project.getObjectiveMediate());
            sentenceProject.setString(6, project.getMethodology());
            sentenceProject.setString(7, project.getResources());
            sentenceProject.setString(8, project.getStatus());
            sentenceProject.setString(9, project.getActivities());
            sentenceProject.setString(10, project.getResponsabilities());
            sentenceProject.setInt(11, project.getDuration());
            sentenceProject.setInt(12, project.getQuantityPractitioner());
            sentenceProject.setInt(13, idOrganization);
            sentenceProject.setInt(14,idResponsibleProject);
            sentenceProject.setInt(15,project.getStaffNumberCoordinator());
            sentenceProject.setInt(16,idLapse);
            sentenceProject.executeUpdate();
            result="The project was successfully registered";
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
            return result;
        }
    }

    /**
     * Method to delete a Project
     * @param project The data of the Project
     * @return The message if the Project was deleted
     */
    @Override
    public String deleteProject (Project project) {
        String result= "The project could not be removed";
        try{
            connection = connexion.getConnection();
            String queryDelete = "update Project set status='not available' where idProject =?";
            PreparedStatement sentence = connection.prepareStatement(queryDelete);
            sentence.setInt(1, project.getIdProject());
            sentence.executeUpdate();
            result= "The project was successfully removed";
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
            return result;
        }
    }

    /**
     * Update method of the Project
     * @param project The data of the Project
     * @return The message if the Project was updated
     */
    @Override
    public String actualizationProject (Project project) {
        String result = "The project could not be updated";
        int idResponsibleProject;
        int idOrganization;
        int idLapse;
        LinkedOrganizationDAOImpl organizationImpl = new LinkedOrganizationDAOImpl();
        idOrganization = organizationImpl.searchIdLinkedOrganization(project.getOrganization().getName(),project.getOrganization().getEmail(),project.getOrganization().getPhoneNumber());
        if(idOrganization == Search.NOTFOUND.getValue() || idOrganization == project.getOrganization().getIdLinkedOrganization()) {
            result = organizationImpl.modifyLinkedOrganization(project.getOrganization());
        }else{
            project.getOrganization().setIdLinkedOrganization(idOrganization);
        }
        
        ResponsibleProjectDAOImpl responsibleImpl = new ResponsibleProjectDAOImpl();
        idResponsibleProject = responsibleImpl.searchIdResponsibleProject(project.getResponsible().getEmail());
        if(idResponsibleProject == Search.NOTFOUND.getValue() || idResponsibleProject == project.getResponsible().getIdResponsible()) {
            result = responsibleImpl.modifyResponsibleProject(project.getResponsible());
        }else{
            project.getResponsible().setIdResponsible(idResponsibleProject);
        }
        LapseDAOImpl lapse = new LapseDAOImpl();
        idLapse = lapse.searchLapse(project.getLapse());
        if(idLapse== Search.NOTFOUND.getValue()){
            lapse.addLapse(project.getLapse());
            idLapse=lapse.searchLapse(project.getLapse());
        }
        try{
            connection = connexion.getConnection();
            String queryProject = "update Project set nameProject=?, description=?, objectiveGeneral=?,"+
                    " objectiveInmediate=?, objectiveMediate=?, methodology=?, resources=?,"
                    + " status=?, activities=?, responsabilities=?, duration=?, quiantityPractitioner=?,"+
                    " idLinkedOrganization=?, idResponsibleProject=?, staffNumberCoordinator=?, idLapse=?  where idProject=?";
            PreparedStatement sentenceProject = connection.prepareStatement(queryProject);
            sentenceProject.setString(1, project.getNameProject());
            sentenceProject.setString(2, project.getDescription());
            sentenceProject.setString(3, project.getObjectiveGeneral());
            sentenceProject.setString(4, project.getObjectiveInmediate());
            sentenceProject.setString(5, project.getObjectiveMediate());
            sentenceProject.setString(6, project.getMethodology());
            sentenceProject.setString(7, project.getResources());
            sentenceProject.setString(8, project.getStatus());
            sentenceProject.setString(9, project.getActivities());
            sentenceProject.setString(10, project.getResponsabilities());
            sentenceProject.setInt(11, project.getDuration());
            sentenceProject.setInt(12, project.getQuantityPractitioner());
            sentenceProject.setInt(13, project.getOrganization().getIdLinkedOrganization());
            sentenceProject.setInt(14, project.getResponsible().getIdResponsible());
            sentenceProject.setInt(15, project.getIdProject());
            sentenceProject.setInt(16, project.getStaffNumberCoordinator());
            sentenceProject.setInt(17,idLapse);
            sentenceProject.executeUpdate();
            result = "The project was successfully updated";
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
            return result;
        }
    }

    /**
     * Method to make a request for a project
     * @param enrollment The enrollment parameter defines the enrollment of the Practitioner
     * @param idProject The idProject parameter defines the id of the Project
     * @return The message if the project request was made
     */
    @Override
    public String requestProject (String enrollment, int idProject) {
        String message=null;
        try{  
            connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("insert into Request values(?,?,'active')");
            sentenceProject.setString(1, enrollment);
            sentenceProject.setInt(2, idProject);
            sentenceProject.executeUpdate();
            message = "The project is request correctly";
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return message;
    }

    /**
     * Method for assigning a project
     * @param enrollment The enrollment parameter defines the enrollment of the Practitioner
     * @param idProject The idProject parameter defines the id of the Project
     * @return The message if the project assignment was made
     */
    @Override
    public String assignProject (String enrollment, int idProject) {
        String status=null;
        int quiantityPractitioner= Search.NOTFOUND.getValue();
        String message=null;
        try{  
            connection = connexion.getConnection();
            String queryProject = "select status,quiantityPractitioner from Project where idProject=?";
            PreparedStatement sentence = connection.prepareStatement(queryProject);
            sentence.setInt(1,idProject);
            results = sentence.executeQuery();
            while(results.next()){
                status= results.getString("status");
                quiantityPractitioner = results.getInt("quiantityPractitioner");
            }
            if(status !="not available"){
                PreparedStatement sentencePractitioner = connection.prepareStatement("update Practitioner set idProject=? where enrollment=?");
                sentencePractitioner.setInt(1, idProject);
                sentencePractitioner.setString(2, enrollment);
                sentencePractitioner.executeUpdate();
                quiantityPractitioner--;
                
                if(quiantityPractitioner == Search.NOTFOUND.getValue()){
                    PreparedStatement sentenceProject = connection.prepareStatement("update Project set status=? where idProject=?");
                    sentenceProject.setString(1, "not available");
                    sentenceProject.setInt(2, idProject);
                    sentenceProject.executeUpdate();
                }
                message = "The practitioner was assigned correctly";
            }else{
                message = "The project is not available to assign";
            }
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return message;
    }

}
