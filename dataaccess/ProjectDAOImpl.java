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

import domain.*;


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
            results = consultation.executeQuery("select nameProject from Project inner join Lapse on Project.idLapse = Lapse.idLapse");
            while(results.next()){
                Project project = new Project();
                project.setNameProject(results.getString("nameProject"));
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
            String queryAllProject = "select nameProject from Project where status=?";
            PreparedStatement sentence = connection.prepareStatement(queryAllProject);
            sentence.setString(1, "available");
            results= sentence.executeQuery();
            while(results.next()){
                Project project = new Project();
                project.setNameProject(results.getString("nameProject"));
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
    public List<Project> getAllProjectsAvailableNotAssing (){
        List<Project> projects = new ArrayList<>();
        int idProject;
        try{
            connection = connexion.getConnection();
            String queryAllProjectAvailable = "select idProject,nameProject from Project inner join Status on " +
                    "Project.idStatus = Status.idStatus where status=?";
            PreparedStatement sentenceAllProjectAvailable = connection.prepareStatement(queryAllProjectAvailable);
            sentenceAllProjectAvailable.setString(1, "available");
            results= sentenceAllProjectAvailable.executeQuery();
            while(results.next()){
                idProject = results.getInt("idProject");
                String queryAssingProject = "select enrollment from Practitioner,Project where Practitioner.idProject = Project.idProject " +
                        "AND Project.idProject =?";
                PreparedStatement sentenceAssingProject = connection.prepareStatement(queryAssingProject);
                sentenceAssingProject.setInt(1, idProject);
                ResultSet resultAssingProject;
                resultAssingProject = sentenceAssingProject.executeQuery();
                if(resultAssingProject.next()) {
                    String queryPractitionerActive = "select enrollment from Practitioner,Project,User_Status,Status where Practitioner.idProject = Project.idProject " +
                            "AND Practitioner.idUser = User_Status.idUser AND Status.idStatus = User_Status.idStatus " +
                            "AND status=? AND Project.idProject =?";
                    PreparedStatement sentencePractitionerActive = connection.prepareStatement(queryPractitionerActive);
                    sentencePractitionerActive.setString(1,"Active");
                    sentencePractitionerActive.setInt(2, idProject);
                    ResultSet resultPractitionerActive;
                    resultPractitionerActive = sentencePractitionerActive.executeQuery();
                    if(!resultPractitionerActive.next()){
                        Project project = new Project();
                        project.setNameProject(results.getString("nameProject"));
                        projects.add(project);
                    }
                }else{
                    Project project = new Project();
                    project.setNameProject(results.getString("nameProject"));
                    projects.add(project);
                }
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
            String queryProject = "select * from Project inner join Lapse on Project.idLapse = Lapse.idLapse " +
                    "inner join Status on Project.idStatus = Status.idStatus where nameProject=?";
            PreparedStatement sentence = connection.prepareStatement(queryProject);
            sentence.setString(1,nameProject);
            results = sentence.executeQuery();
            LinkedOrganizationDAOImpl implementOrganization = new LinkedOrganizationDAOImpl();
            ResponsibleProjectDAOImpl implementResponsible = new ResponsibleProjectDAOImpl();
            SchedulingActivitiesDAOImpl implementSchedulingActivities = new SchedulingActivitiesDAOImpl();
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
                project.setActivitiesAndFunctions(results.getString("activities"));
                project.setResponsabilities(results.getString("responsabilities"));
                project.setDaysHours(results.getString("daysHours"));
                project.setLapse(results.getString("lapse"));
                project.setStatus(results.getString("status"));
                project.setDuration(results.getInt("duration"));
                project.setQuantityPractitioner(results.getInt("quiantityPractitioner"));
                project.setPlacesAvailable(results.getInt("placesAvailable"));
                project.setStaffNumberCoordinator(results.getInt("staffNumberCoordinator"));
                project.setOrganization(implementOrganization.getIdLinkedOrganization(results.getInt("idLinkedOrganization")));
                project.setResponsible(implementResponsible.getIdResponsibleProject(results.getInt("idResponsibleProject")));
                project.setSchedulingActivitiesProject(implementSchedulingActivities.getAllSchedulingActivities(project.getIdProject()));
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
    public boolean addProject (Project project) {
        boolean resultIsAddProject = false;
        int idLapse;
        LapseDAOImpl lapse = new LapseDAOImpl();
        idLapse = lapse.searchLapse(project.getLapse());
        if(idLapse == Search.NOTFOUND.getValue()) {
            lapse.addLapse(project.getLapse());
            idLapse=lapse.searchLapse(project.getLapse());
        }
        StatusDAOImpl status = new StatusDAOImpl();
        int idStatus = status.searchIdStatus(project.getStatus());
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        int idResponsibleProject = responsibleProjectDAO.searchIdResponsibleProject(project.getResponsible().getEmail());
        LinkedOrganizationDAOImpl organizationDAO = new LinkedOrganizationDAOImpl();
        int idLinkedOrganization = organizationDAO.searchIdLinkedOrganization(project.getOrganization().getName(),project.getOrganization().getEmail(),project.getOrganization().getPhoneNumber());
        int idProject = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("insert into Project(nameProject,description,objectiveGeneral,"+
                    "objectiveInmediate,objectiveMediate,methodology,resources,activities,"+
                    "responsabilities,daysHours,duration,quiantityPractitioner,placesAvailable," +
                    "idLinkedOrganization,idResponsibleProject,idStatus,"+
                    "staffNumberCoordinator,idLapse) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sentenceProject.setString(1, project.getNameProject());
            sentenceProject.setString(2, project.getDescription());
            sentenceProject.setString(3, project.getObjectiveGeneral());
            sentenceProject.setString(4, project.getObjectiveInmediate());
            sentenceProject.setString(5, project.getObjectiveMediate());
            sentenceProject.setString(6, project.getMethodology());
            sentenceProject.setString(7, project.getResources());
            sentenceProject.setString(8, project.getActivitiesAndFunctions());
            sentenceProject.setString(9, project.getResponsabilities());
            sentenceProject.setString(10,project.getDaysHours());
            sentenceProject.setInt(11, project.getDuration());
            sentenceProject.setInt(12, project.getQuantityPractitioner());
            sentenceProject.setInt(13,project.getPlacesAvailable());
            sentenceProject.setInt(14, idLinkedOrganization);
            sentenceProject.setInt(15,idResponsibleProject);
            sentenceProject.setInt(16,idStatus);
            sentenceProject.setInt(17,project.getStaffNumberCoordinator());
            sentenceProject.setInt(18,idLapse);
            sentenceProject.executeUpdate();
            idProject = getIdProject(project.getNameProject());
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        if(idProject!=Search.NOTFOUND.getValue()) {
            List<SchedulingActivities> listSchedulingActivities = project.getSchedulingActivitiesProject();
            SchedulingActivitiesDAOImpl addSchedulingActivities = new SchedulingActivitiesDAOImpl();
            for (int indexScheduling= Search.NOTFOUND.getValue();indexScheduling<listSchedulingActivities.size();indexScheduling++) {
                addSchedulingActivities.addSchedulingActivities(idProject, listSchedulingActivities.get(indexScheduling));
                resultIsAddProject = true;
            }
        }
        return resultIsAddProject;
    }

    /**
     * Method to delete a Project
     * @param nameProject The name of the Project
     * @return The message if the Project was deleted
     */
    @Override
    public boolean deleteProject (String nameProject) {
        boolean resultDeleteProject= false;
        int idStatus = Search.NOTFOUND.getValue();
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        idStatus = statusDAO.searchIdStatus("not available");
        try{
            connection = connexion.getConnection();
            String queryDeleteProject = "update Project set idStatus=? where nameProject =?";
            PreparedStatement sentence = connection.prepareStatement(queryDeleteProject);
            sentence.setInt(1, idStatus);
            sentence.setString(2, nameProject);
            sentence.executeUpdate();
            resultDeleteProject= true;
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
            return resultDeleteProject;
        }
    }

    /**
     * Update method of the Project
     * @param project The data of the Project
     * @return The message if the Project was updated
     */
    @Override
    public boolean actualizationProject (Project project) {
        boolean result = false;
        int idResponsibleProject;
        int idOrganization;
        int idLapse;
        LinkedOrganizationDAOImpl organizationImpl = new LinkedOrganizationDAOImpl();
        idOrganization = organizationImpl.searchIdLinkedOrganization(project.getOrganization().getName(),project.getOrganization().getEmail(),project.getOrganization().getPhoneNumber());
        ResponsibleProjectDAOImpl responsibleImpl = new ResponsibleProjectDAOImpl();
        idResponsibleProject = responsibleImpl.searchIdResponsibleProject(project.getResponsible().getEmail());
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
            sentenceProject.setString(9, project.getActivitiesAndFunctions());
            sentenceProject.setString(10, project.getResponsabilities());
            sentenceProject.setInt(11, project.getDuration());
            sentenceProject.setInt(12, project.getQuantityPractitioner());
            sentenceProject.setInt(13, project.getOrganization().getIdLinkedOrganization());
            sentenceProject.setInt(14, project.getResponsible().getIdResponsible());
            sentenceProject.setInt(15, project.getIdProject());
            sentenceProject.setInt(16, project.getStaffNumberCoordinator());
            sentenceProject.setInt(17,idLapse);
            sentenceProject.executeUpdate();
            result = true;
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
    public boolean requestProject (String enrollment, int idProject) {
        boolean result = false;
        try{  
            connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("insert into Request values(?,?,'active')");
            sentenceProject.setString(1, enrollment);
            sentenceProject.setInt(2, idProject);
            sentenceProject.executeUpdate();
            result = true;
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return result;
    }

    /**
     * Method for assigning a project
     * @param enrollment The enrollment parameter defines the enrollment of the Practitioner
     * @param idProject The idProject parameter defines the id of the Project
     * @return The message if the project assignment was made
     */
    @Override
    public boolean assignProject (String enrollment, int idProject) {
        String status=null;
        int quiantityPractitioner= Search.NOTFOUND.getValue();
        boolean result = false;
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
            if(status !="not available" || status != "no places"){
                PreparedStatement sentencePractitioner = connection.prepareStatement("update Practitioner set idProject=? where enrollment=?");
                sentencePractitioner.setInt(1, idProject);
                sentencePractitioner.setString(2, enrollment);
                sentencePractitioner.executeUpdate();
                quiantityPractitioner--;
                
                if(quiantityPractitioner == Search.NOTFOUND.getValue()){
                    PreparedStatement sentenceProject = connection.prepareStatement("update Project set status=? where idProject=?");
                    sentenceProject.setString(1, "no places");
                    sentenceProject.setInt(2, idProject);
                    sentenceProject.executeUpdate();
                }
                result= true;
            }
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
    public int getIdProject (String nameProject){
        int idProject = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryAllProject = "select idProject from Project where nameProject=?";
            PreparedStatement sentence = connection.prepareStatement(queryAllProject);
            sentence.setString(1, nameProject);
            results= sentence.executeQuery();
            while(results.next()){
                idProject = results.getInt("idProject");
            }
        }catch (SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return idProject;
    }

    @Override
    public boolean thereAreProjectAvailable (){
        boolean areProject = false;
        try {
            connection = connexion.getConnection();
            String queryAreProjectAvailable = "select nameProject from Project inner join Status on " +
                    "Project.idStatus = Status.idStatus where status =?";
            PreparedStatement sentence = connection.prepareStatement(queryAreProjectAvailable);
            sentence.setString(1, "available");
            results= sentence.executeQuery();
            while (results.next()) {
                areProject = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areProject;
    }

    @Override
    public boolean thereAreProject(){
        boolean areProject = false;
        try {
            connection = connexion.getConnection();
            String queryAreProject = "select nameProject from Project";
            PreparedStatement sentence = connection.prepareStatement(queryAreProject);
            results= sentence.executeQuery();
            while (results.next()) {
                areProject = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areProject;
    }

    @Override
    public boolean thereAreProjectAvailableNotAssing() {
        boolean areAssingProject = false;
        int idProject;
        try {
            connection = connexion.getConnection();
            String queryAllProjectAvailable = "select idProject,nameProject from Project inner join Status on " +
                    "Project.idStatus = Status.idStatus where status=?";
            PreparedStatement sentenceAllProjectAvailable = connection.prepareStatement(queryAllProjectAvailable);
            sentenceAllProjectAvailable.setString(1, "available");
            results= sentenceAllProjectAvailable.executeQuery();
            while(results.next() && !areAssingProject){
                idProject = results.getInt("idProject");
                String queryAssingProject = "select enrollment from Practitioner,Project where Practitioner.idProject = Project.idProject " +
                        "AND Project.idProject =?";
                PreparedStatement sentenceAssingProject = connection.prepareStatement(queryAssingProject);
                sentenceAssingProject.setInt(1, idProject);
                ResultSet resultAssingProject;
                resultAssingProject = sentenceAssingProject.executeQuery();
                if(resultAssingProject.next()) {
                    String queryPractitionerActive = "select enrollment from Practitioner,Project,User_Status,Status where Practitioner.idProject = Project.idProject " +
                            "AND Practitioner.idUser = User_Status.idUser AND Status.idStatus = User_Status.idStatus " +
                            "AND status=? AND Project.idProject =?";
                    PreparedStatement sentencePractitionerActive = connection.prepareStatement(queryPractitionerActive);
                    sentencePractitionerActive.setString(1,"Active");
                    sentencePractitionerActive.setInt(2, idProject);
                    ResultSet resultPractitionerActive;
                    resultPractitionerActive = sentencePractitionerActive.executeQuery();
                    if(!resultPractitionerActive.next()){
                        areAssingProject=true;
                    }
                }else{
                    areAssingProject=true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areAssingProject;
    }

}
