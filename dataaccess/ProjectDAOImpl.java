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
import domain.Project;
import domain.SchedulingActivities;
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
     * Method to add a Project
     * @param project The data of the Project
     * @return if the Project was added
     */
    @Override
    public boolean addProject (Project project) {
        boolean resultIsAddProject = false;
        int idTerm;
        TermDAOImpl termDAO = new TermDAOImpl();
        idTerm = termDAO.getIdTerm(project.getTerm());
        if(idTerm == Search.NOTFOUND.getValue()) {
            termDAO.addTerm(project.getTerm());
            idTerm =termDAO.getIdTerm(project.getTerm());
        }
        StatusDAOImpl status = new StatusDAOImpl();
        int idStatus = status.searchIdStatus(project.getStatus());
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        int idResponsibleProject = responsibleProjectDAO.getIdResponsibleProject(project.getResponsible().getEmail());
        LinkedOrganizationDAOImpl organizationDAO = new LinkedOrganizationDAOImpl();
        int idLinkedOrganization = organizationDAO.getIdLinkedOrganization(project.getOrganization().getName());
        int idProject = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("INSERT INTO Project(nameProject,description" +
                    ",objectiveGeneral,objectiveInmediate,objectiveMediate,methodology,resources,activitiesAndFunctions,responsabilities" +
                    ",daysHours,duration,quiantityPractitioner,placesAvailable" +
                    ",idLinkedOrganization,idResponsibleProject,idStatus"+
                    ",staffNumberCoordinator,idTerm) VALUES(?,?,?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?,?)");
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
            sentenceProject.setInt(12, project.getQuiantityPractitioner());
            sentenceProject.setInt(13,project.getPlacesAvailable());
            sentenceProject.setInt(14, idLinkedOrganization);
            sentenceProject.setInt(15,idResponsibleProject);
            sentenceProject.setInt(16,idStatus);
            sentenceProject.setInt(17,project.getStaffNumberCoordinator());
            sentenceProject.setInt(18, idTerm);
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
     * Method to get the Project list
     * @return The Project list
     */
    @Override
    public List<Project> getAllProjects (){
        List<Project> projects = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consultation  = connection.createStatement();
            results = consultation.executeQuery("SELECT nameProject FROM Project INNER JOIN Term ON Project.idTerm = Term.idTerm");
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

    /**
     * Method to get the Project available list
     * @return The Project available list
     */
    @Override
    public List<Project> getAllProjectsAvailable (){
        List<Project> projects = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            String queryAllProject = "SELECT nameProject FROM Project INNER JOIN Status ON Project.idStatus " +
                    "= Status.idStatus WHERE status=?";
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
            String queryProject = "SELECT * FROM Project INNER JOIN Term ON Project.idTerm = Term.idTerm " +
                    "INNER JOIN Status ON Project.idStatus = Status.idStatus WHERE nameProject=?";
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
                project.setActivitiesAndFunctions(results.getString("activitiesAndFunctions"));
                project.setResponsabilities(results.getString("responsabilities"));
                project.setDaysHours(results.getString("daysHours"));
                project.setTerm(results.getString("term"));
                project.setStatus(results.getString("status"));
                project.setDuration(results.getInt("duration"));
                project.setQuiantityPractitioner(results.getInt("quiantityPractitioner"));
                project.setPlacesAvailable(results.getInt("placesAvailable"));
                project.setStaffNumberCoordinator(results.getInt("staffNumberCoordinator"));
                project.setOrganization(implementOrganization.getLinkedOrganizationWithId(results.getInt("idLinkedOrganization")));
                project.setResponsible(implementResponsible.getResponsibleProjectWithId(results.getInt("idResponsibleProject")));
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
     * Method to get id project
     * @param nameProject defines the name of the Project
     * @return The idProject of the Project
     */
    @Override
    public int getIdProject (String nameProject){
        int idProject = Search.NOTFOUND.getValue();
        try{
            connection = connexion.getConnection();
            String queryAllProject = "SELECT idProject FROM Project WHERE nameProject=?";
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

    /**
     * Method to modify the project
     * @param projectEdit The data of the Project
     * @param datesUpdate The fields to be modified
     * @return if the Project was modify
     */
    @Override
    public boolean modifyProject(Project projectEdit, List<String> datesUpdate) {
        int idResponsibleProject;
        int idLinkedOrganization;
        boolean isModifyProject = false;
        String sentenceDatesUpdate="";
        List<String> change = new ArrayList<>();
        for (int indexDatesUpdate = Number.ZERO.getNumber(); indexDatesUpdate < datesUpdate.size(); indexDatesUpdate++) {
            if (indexDatesUpdate == datesUpdate.size() - 1) {
                if(datesUpdate.get(indexDatesUpdate).equals("LinkedOrganization")) {
                    sentenceDatesUpdate = sentenceDatesUpdate + "idLinkedOrganization = ?";
                }else {
                    if(datesUpdate.get(indexDatesUpdate).equals("ResponsibleProject")) {
                        sentenceDatesUpdate = sentenceDatesUpdate + "idResponsibleProject = ?";
                    }else {
                        sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?";
                    }
                }
            } else {
                if(datesUpdate.get(indexDatesUpdate).equals("LinkedOrganization")) {
                    sentenceDatesUpdate = sentenceDatesUpdate + "idLinkedOrganization = ?,";
                }else {
                    if(datesUpdate.get(indexDatesUpdate).equals("ResponsibleProject")) {
                        sentenceDatesUpdate = sentenceDatesUpdate + "idResponsibleProject = ?,";
                    }else {
                        sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?,";
                    }
                }
            }
            change.add("get" + datesUpdate.get(indexDatesUpdate));
        }
        String sentence = "UPDATE Project SET "+sentenceDatesUpdate+ " WHERE idProject " +
                "= "+ projectEdit.getIdProject();
        try{
            connection = connexion.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            Class classProject = projectEdit.getClass();
            for(int indexPreparedStatement = Number.ONE.getNumber() ; indexPreparedStatement
                    <= datesUpdate.size(); indexPreparedStatement++){
                Method methodProject;
                boolean isString = true;
                if(change.get(indexPreparedStatement-1).equals("getLinkedOrganization")){
                    LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
                    idLinkedOrganization= linkedOrganizationDAO.getIdLinkedOrganization(projectEdit.getOrganization().getName());
                        preparedStatement.setInt(indexPreparedStatement, idLinkedOrganization);
                } else{
                    if(change.get(indexPreparedStatement-1).equals("getResponsibleProject")) {
                        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
                        idResponsibleProject = responsibleProjectDAO.getIdResponsibleProject(projectEdit.getResponsible().getEmail());
                        preparedStatement.setInt(indexPreparedStatement, idResponsibleProject);
                    }else {
                        try {
                            methodProject = classProject.getMethod(change.get(indexPreparedStatement-1));
                            String word = (String) methodProject.invoke(projectEdit, new Object[]{});
                        } catch (ClassCastException e) {
                            isString = false;
                        }
                        if(isString){
                            methodProject = classProject.getMethod(change.get(indexPreparedStatement-1));
                            String word = (String) methodProject.invoke(projectEdit, new Object[]{});
                            preparedStatement.setString(indexPreparedStatement, word);
                        } else{
                            methodProject = classProject.getMethod(change.get(indexPreparedStatement-1));
                            int integer = (int) methodProject.invoke(projectEdit, new Object[]{});
                            preparedStatement.setInt(indexPreparedStatement, integer);
                        }
                    }
                }
            }
            preparedStatement.executeUpdate();
            isModifyProject = true;
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return isModifyProject;
    }

    /**
     * Method to delete a Project
     * @param nameProject The name of the Project
     * @return if the Project was deleted
     */
    @Override
    public boolean deleteProject (String nameProject) {
        boolean resultDeleteProject= false;
        int idStatus = Search.NOTFOUND.getValue();
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        idStatus = statusDAO.searchIdStatus("cancel");
        try{
            connection = connexion.getConnection();
            String queryDeleteProject = "UPDATE Project SET idStatus=? WHERE nameProject =?";
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
     * Method to validate if there is another project  with the same name
     * @param nameProject defines the name of the project
     * @return if the project exists
     */
    @Override
    public boolean validateRepeatProject (String nameProject) {
        boolean isRepeatProject = false;
        try{
            connection = connexion.getConnection();
            String queryResponsible= "SELECT idProject FROM Project WHERE nameProject=?";
            PreparedStatement sentence =connection.prepareStatement(queryResponsible);
            sentence.setString(1,nameProject);
            results= sentence.executeQuery();
            if(results.next()){
                isRepeatProject = true;
            }
        }catch(SQLException ex){
            Logger.getLogger(ProjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isRepeatProject;
    }

    /**
     * Method to know if there are project available
     * @return if there are project available
     */
    @Override
    public boolean thereAreProjectAvailable (){
        boolean areProject = false;
        try {
            connection = connexion.getConnection();
            String queryAreProjectAvailable = "SELECT nameProject FROM Project INNER JOIN Status " +
                    "ON Project.idStatus = Status.idStatus WHERE status =?";
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

    /**
     * Method to know if there are project
     * @return if there are project
     */
    @Override
    public boolean thereAreProject(){
        boolean areProject = false;
        try {
            connection = connexion.getConnection();
            String queryAreProject = "SELECT nameProject FROM Project";
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


}
