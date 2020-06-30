package domain;

import dataaccess.LinkedOrganizationDAOImpl;
import dataaccess.ProjectDAOImpl;
import dataaccess.LapseDAOImpl;

import java.util.List;

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
    private String lapse;
    private String status;
    private int staffNumberCoordinator;
    private int duration;
    private int quantityPractitioner;
    private int placesAvailable;
    private int idProject;
    private LinkedOrganization organization;
    private ResponsibleProject responsible;
    private List<SchedulingActivities> schedulingActivitiesProject;
    
    public Project(){
        this.placesAvailable = this.quantityPractitioner;
        this.status="available";
    }
    
    public String getNameProject () {
        return nameProject;
    }
    
    public void setNameProject (String nameProject) {
        this.nameProject = nameProject;
    }
    
    public String getDescription () {
        return description;
    }
    
    public void setDescription (String description) {
        this.description = description;
    }
    
    public String getObjectiveGeneral () {
        return objectiveGeneral;
    }
    
    public void setObjectiveGeneral (String objectiveGeneral) {
        this.objectiveGeneral = objectiveGeneral;
    }
    
    public String getObjectiveInmediate () {
        return objectiveInmediate;
    }
    
    public void setObjectiveInmediate (String objectiveImmediate) {
        this.objectiveInmediate = objectiveImmediate;
    }
    
    public String getObjectiveMediate () {
        return objectiveMediate;
    }
    
    public void setObjectiveMediate (String objectiveMediate) {
        this.objectiveMediate = objectiveMediate;
    }
    
    public String getMethodology () {
        return methodology;
    }
    
    public void setMethodology (String methodology) {
        this.methodology = methodology;
    }
    
    public String getResources () {
        return resources;
    }
    
    public void setResources (String resources) {
        this.resources = resources;
    }
    
    public String getActivitiesAndFunctions() {
        return activitiesAndFunctions;
    }
    
    public void setActivitiesAndFunctions(String activitiesAndFunctions) {
        this.activitiesAndFunctions = activitiesAndFunctions;
    }
    
    public String getResponsabilities () {
        return responsabilities;
    }
    
    public void setResponsabilities (String responsabilities) {
        this.responsabilities = responsabilities;
    }

    public void setDaysHours (String daysHours) {
        this.daysHours = daysHours;
    }

    public String getDaysHours () {
        return daysHours;
    }

    public String getLapse () {
        return lapse;
    }

    public void setLapse (String lapse) {
        this.lapse = lapse;
    }

    public String getStatus () {
        return status;
    }
    
    public void setStatus (String status){
        this.status=status;
    }

    public int getStaffNumberCoordinator () {
        return staffNumberCoordinator;
    }

    public void setStaffNumberCoordinator (int staffNumberCoordinator) {
        this.staffNumberCoordinator = staffNumberCoordinator;
    }
            
    public int getDuration () {
        return duration;
    }
    
    public void setDuration (int duration) {
        this.duration = duration;
    }
    
    public int getQuantityPractitioner () {
        return quantityPractitioner;
    }
    
    public void setQuantityPractitioner (int quantityPractitioner) {
        this.quantityPractitioner = quantityPractitioner;
    }

    public void setPlacesAvailable (int placesAvailable) {
        this.placesAvailable = placesAvailable;
    }

    public int getPlacesAvailable () {
        return placesAvailable;
    }

    public int getIdProject () {
     return idProject;   
    }
    
    public void setIdProject (int idProject) {
        this.idProject = idProject;
    }
    
    public LinkedOrganization getOrganization (){
        return organization;
    }
    
    public void setOrganization (LinkedOrganization organization){
        this.organization = organization;
    }
    
    public ResponsibleProject getResponsible () {
        return responsible;
    }
    
    public void setResponsible (ResponsibleProject responsible) {
        this.responsible = responsible;
    }

    public void setSchedulingActivitiesProject (List<SchedulingActivities> schedulingActivitiesProject) {
        this.schedulingActivitiesProject = schedulingActivitiesProject;
    }

    public List<SchedulingActivities> getSchedulingActivitiesProject () {
        return schedulingActivitiesProject;
    }

    public boolean searchProject () {
        int idProject;
        boolean isFoundProject = false;
        ProjectDAOImpl searchProject = new ProjectDAOImpl();
        idProject = searchProject.getIdProject(nameProject);
        if(idProject != Search.NOTFOUND.getValue()){
            isFoundProject = true;
        }
        return isFoundProject;
    }

    public boolean registerProject () {
        ProjectDAOImpl registerProject = new ProjectDAOImpl();
        boolean result = registerProject.addProject(this);
        return result;
    }

    public List<Project> listProjects () {
        ProjectDAOImpl getAllProject = new ProjectDAOImpl();
        List<Project> listProject = getAllProject.getAllProjects();
        return listProject;
    }

    public List<Project> listProjectsAvailableNotAssing() {
        ProjectDAOImpl getAllProjectAvailable = new ProjectDAOImpl();
        List<Project> listProjectAvailable = getAllProjectAvailable.getAllProjectsAvailableNotAssing();
        return listProjectAvailable;
    }

    public boolean modifyProject () {
        boolean result;
        Project projectExit = new Project();
        ProjectDAOImpl actualizationProject = new ProjectDAOImpl();
        projectExit = actualizationProject.getProject(this.getNameProject());
        result = actualizationProject.actualizationProject(this);
        return result;
    }

    public boolean requestProject (String enrollment) {
        boolean result;
        ProjectDAOImpl request = new ProjectDAOImpl();
        result = request.requestProject(enrollment,this.idProject);
        return result;
    }

    public boolean deleteProject () {
        boolean result;
        ProjectDAOImpl delete = new ProjectDAOImpl();
        result = delete.deleteProject(nameProject);
        return result;
    }

    public boolean assingProject (String enrollment) {
        boolean result;
        ProjectDAOImpl assing = new ProjectDAOImpl();
        result = assing.assignProject(enrollment, this.idProject);
        return result;
    }

    public Project getProject (String name) {
        ProjectDAOImpl getProject = new ProjectDAOImpl();
        Project project = getProject.getProject(name);
        return project;
    }

    public boolean thereAreProject () {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        boolean thereAreProject = projectDAO.thereAreProject();
        return thereAreProject;
    }

    public boolean thereAreProjectAvailableNotAssing() {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        boolean thereAreProjectAvailable = projectDAO.thereAreProjectAvailableNotAssing();
        return thereAreProjectAvailable;
    }

}