package domain;

import java.util.List;
import dataaccess.ProjectDAOImpl;

/**
 * Class Project
 * @author MARTHA
 * @version 08/05/2020
 */
public class Project {
    private String nameProject;
    private String description;
    private String objectiveGeneral;
    private String objectiveInmediate;
    private String objectiveMediate;
    private String methodology;
    private String resources;
    private String activitiesAndFunctions;
    private String responsabilities;
    private String daysHours;
    private String term;
    private String status;
    private int staffNumberCoordinator;
    private int duration;
    private int quiantityPractitioner;
    private int placesAvailable;
    private int idProject;
    private LinkedOrganization organization;
    private ResponsibleProject responsible;
    private List<SchedulingActivities> schedulingActivitiesProject;

    public Project() {
        this.placesAvailable = this.quiantityPractitioner;
        this.status = "available";
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjectiveGeneral() {
        return objectiveGeneral;
    }

    public void setObjectiveGeneral(String objectiveGeneral) {
        this.objectiveGeneral = objectiveGeneral;
    }

    public String getObjectiveInmediate() {
        return objectiveInmediate;
    }

    public void setObjectiveInmediate(String objectiveImmediate) {
        this.objectiveInmediate = objectiveImmediate;
    }

    public String getObjectiveMediate() {
        return objectiveMediate;
    }

    public void setObjectiveMediate(String objectiveMediate) {
        this.objectiveMediate = objectiveMediate;
    }

    public String getMethodology() {
        return methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getActivitiesAndFunctions() {
        return activitiesAndFunctions;
    }

    public void setActivitiesAndFunctions(String activitiesAndFunctions) {
        this.activitiesAndFunctions = activitiesAndFunctions;
    }

    public String getResponsabilities() {
        return responsabilities;
    }

    public void setResponsabilities(String responsabilities) {
        this.responsabilities = responsabilities;
    }

    public void setDaysHours(String daysHours) {
        this.daysHours = daysHours;
    }

    public String getDaysHours() {
        return daysHours;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStaffNumberCoordinator() {
        return staffNumberCoordinator;
    }

    public void setStaffNumberCoordinator(int staffNumberCoordinator) {
        this.staffNumberCoordinator = staffNumberCoordinator;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getQuiantityPractitioner() {
        return quiantityPractitioner;
    }

    public void setQuiantityPractitioner(int quiantityPractitioner) {
        this.quiantityPractitioner = quiantityPractitioner;
    }

    public void setPlacesAvailable(int placesAvailable) {
        this.placesAvailable = placesAvailable;
    }

    public int getPlacesAvailable() {
        return placesAvailable;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public LinkedOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(LinkedOrganization organization) {
        this.organization = organization;
    }

    public ResponsibleProject getResponsible() {
        return responsible;
    }

    public void setResponsible(ResponsibleProject responsible) {
        this.responsible = responsible;
    }

    public void setSchedulingActivitiesProject(List<SchedulingActivities> schedulingActivitiesProject) {
        this.schedulingActivitiesProject = schedulingActivitiesProject;
    }

    public List<SchedulingActivities> getSchedulingActivitiesProject() {
        return schedulingActivitiesProject;
    }

    /**
     * Method for register the project
     * @param project define the data of the project
     * @return if the project was registered
     */
    public static boolean registerProject(Project project) {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        boolean isRegisterProject = projectDAO.addProject(project);
        return isRegisterProject;
    }

    /**
     * Method for get the list of project
     * @return The list of the project
     */
    public static List<Project> getListProject() {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        List<Project> listProject = projectDAO.getAllProjects();
        return listProject;
    }

    /**
     * Method for get the list of project available
     * @return The list of the project available
     */
    public static List<Project> getListProjectAvailable() {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        List<Project> listProjectAvailable = projectDAO.getAllProjectsAvailable();
        return listProjectAvailable;
    }

    /**
     * Method for get project
     * @param name defines the name of the project
     * @return The get project
     */
    public static Project getProject (String name) {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        Project project = projectDAO.getProject(name);
        return project;
    }

    /**
     * Method for modify the project
     * @param project define the data of the project
     * @param datesUpdate the fields to modify
     * @return if the project was modify
     */
    public static boolean modifyProject (Project project, List<String> datesUpdate) {
        boolean isModifyProject;
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        isModifyProject = projectDAO.modifyProject(project,datesUpdate);
        return isModifyProject;
    }

    /**
     * Method for delete the project
     * @param nameProject define the data of the project
     * @return if the project was deleted
     */
    public static boolean deleteProject (String nameProject) {
        boolean isDeleteProject;
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        isDeleteProject = projectDAO.deleteProject(nameProject);
        return isDeleteProject;
    }

    /**
     * Method for validate repeat of the project
     * @param nameProject define the data of the project
     * @return if valid the project
     */
    public static int validateRepeatProject (String nameProject) {
        int isValidProject;
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        isValidProject = projectDAO.validateRepeatProject(nameProject);
        return isValidProject;
    }

    /**
     * Method to know if there are project
     * @return If there is a project
     */
    public static boolean thereAreProject () {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        boolean thereAreProject = projectDAO.thereAreProject();
        return thereAreProject;
    }

    /**
     * Method to know if there are project available
     * @return If there is a project available
     */
    public static boolean thereAreProjectAvailable() {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        boolean thereAreProjectAvailable = projectDAO.thereAreProjectAvailable();
        return thereAreProjectAvailable;
    }

}