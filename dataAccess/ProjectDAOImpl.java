package dataAccess;

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
import domain.ResponsibleProject;

public class ProjectDAOImpl implements IProjectDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;
    
    public ProjectDAOImpl (){
        connexion= new Connexion();
    }
    

    @Override
    public List<Project> getAllProjects (){
        List<Project> projects = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consultation  = connection.createStatement();
            results = consultation.executeQuery("select * from Project");
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
                project.setOrganization(implementOrganization.getLinkedOrganization(results.getInt("idLinkedOrganization")));      
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
    public Project getProject (String nameProject) {
        Project project = null;
        try{
            connection = connexion.getConnection();
            String queryProject = "select * from Project where nameProject=?";
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
                project.setQuantityPractitioner(results.getInt("quiantityPractitioner"));
                project.setOrganization(implementOrganization.getLinkedOrganization(results.getInt("idLinkedOrganization")));      
                project.setResponsible(implementResponsible.getResponsibleProject(results.getInt("idResponsibleProject")));
            }
        }catch (SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return project;
        
    }

    @Override
    public int updateProject (Project project) {
        int result=0;
        int idResponsibleProject;
        int idOrganization;
        LinkedOrganizationDAOImpl organizationImpl = new LinkedOrganizationDAOImpl();
        idOrganization = organizationImpl.searchLinkedOrganization(project.getOrganization().getName());
        if(idOrganization == 0) {
            organizationImpl.updateLinkedOrganization(project.getOrganization());
            idOrganization = organizationImpl.searchLinkedOrganization(project.getOrganization().getName());
        }
        ResponsibleProjectDAOImpl responsibleImpl = new ResponsibleProjectDAOImpl();
        idResponsibleProject = responsibleImpl.searchResponsibleProject(project.getResponsible().getEmail());
        if(idResponsibleProject == 0) {
            responsibleImpl.updateResponsibleProject(project.getResponsible());
            idResponsibleProject = responsibleImpl.searchResponsibleProject(project.getResponsible().getEmail());
        }
        try{  
            connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("insert into Project(nameProject,description,objectiveGeneral,objectiveInmediate,objectiveMediate,Methodology,resources,status,activities,responsabilities,duration,quiantityPractitioner,idLinkedOrganization,idResponsibleProject) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
            sentenceProject.executeUpdate();
            result=1;
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return result;
    }

    @Override
    public int deleteProject (Project project) {
        int result=0;
        try{
            connection = connexion.getConnection();
            PreparedStatement  sentence = connection.prepareStatement("delete from Project where idProject=?");
            sentence.setInt(1, project.getIdProject());
            sentence.executeUpdate();
            result=1;
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return result;
    }

    @Override
    public int actualizationProject (Project project) {
        int result=0;
        int idResponsibleProject;
        int idOrganization;
        LinkedOrganizationDAOImpl organizationImpl = new LinkedOrganizationDAOImpl();
        idOrganization = organizationImpl.searchLinkedOrganization(project.getOrganization().getName());
        if(idOrganization == 0 || idOrganization == project.getOrganization().getIdLinkedOrganization()) {
            organizationImpl.actualizationOrganization(project.getOrganization());
        }else{
            project.getOrganization().setIdLinkedOrganization(idOrganization);
        }
        
        ResponsibleProjectDAOImpl responsibleImpl = new ResponsibleProjectDAOImpl();
        idResponsibleProject = responsibleImpl.searchResponsibleProject(project.getResponsible().getEmail());
        if(idResponsibleProject == 0 || idResponsibleProject == project.getResponsible().getIdResponsible()) {
            responsibleImpl.actualizationResponsibleProject(project.getResponsible());
        }else{
            project.getResponsible().setIdResponsible(idResponsibleProject);
        }
        try{
            connection = connexion.getConnection();
            String queryProject = "update Project set nameProject=?, description=?, objectiveGeneral=?, objectiveInmediate=?, objectiveMediate=?, methodology=?, resources=?,"
                    + " status=?, activities=?, responsabilities=?, duration=?, quiantityPractitioner=?, idLinkedOrganization=?, idResponsibleProject=?  where idProject=?";
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
            sentenceProject.executeUpdate();
            result=1;
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return result;
    }

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

    @Override
    public String assignProject (String enrollment, int idProject) {
        String status=null;
        int quiantityPractitioner=0;
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
                
                if(quiantityPractitioner == 0){
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
