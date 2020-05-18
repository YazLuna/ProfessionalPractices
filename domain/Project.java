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
    private String status;
    private String activities;
    private String responsabilities;
    private String lapse;
    private int staffNumberCoordinator;
    private int duration;
    private int quantityPractitioner;
    private int idProject;
    private LinkedOrganization organization;
    private ResponsibleProject responsible;
    
    public Project(){
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
    
    public String getActivities () {
        return activities;
    }
    
    public void setActivities (String activities) {
        this.activities = activities;
    }
    
    public String getResponsabilities () {
        return responsabilities;
    }
    
    public void setResponsabilities (String responsabilities) {
        this.responsabilities = responsabilities;
    }

    public int getStaffNumberCoordinator () {
        return staffNumberCoordinator;
    }

    public void setStaffNumberCoordinator (int staffNumberCoordinator) {
        this.staffNumberCoordinator = staffNumberCoordinator;
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

    public String registerProject () {
        String result;
        Project projectExit = new Project();
        ProjectDAOImpl registerProject = new ProjectDAOImpl();
        projectExit = registerProject.getProject(this.getNameProject());
        if(projectExit !=null){
            result = "There is a project with the same registered name";
        }else{
            result = registerProject.updateProject(this);
        }
        return result;
    }

    public List<Project> listProjects () {
        String result;
        ProjectDAOImpl registerProject = new ProjectDAOImpl();
        return registerProject.getAllProjects();
    }
    public List<Project> listProjectsAvailable () {
        String result;
        ProjectDAOImpl registerProject = new ProjectDAOImpl();
        return registerProject.getAllProjectsAvailable();
    }

    public String actualizationProject () {
        String result;
        Project projectExit = new Project();
        ProjectDAOImpl actualizationProject = new ProjectDAOImpl();
        projectExit = actualizationProject.getProject(this.getNameProject());
        result = actualizationProject.actualizationProject(this);
        if(projectExit.getNameProject() != null && this.idProject!= projectExit.getIdProject()){
            result = "There is a project with the same registered name";
        }else{
            result = actualizationProject.actualizationProject(this);
        }
        return result;
    }

    public String requestProject (String enrollment) {
        String result;
        ProjectDAOImpl request = new ProjectDAOImpl();
        result = request.requestProject(enrollment,this.idProject);
        return result;
    }

    public String deleteProject () {
        String result;
        ProjectDAOImpl delete = new ProjectDAOImpl();
        result = delete.deleteProject(this);
        return result;
    }

    public String assingProject (String enrollment) {
        String result;
        ProjectDAOImpl assing = new ProjectDAOImpl();
        result = assing.assignProject(enrollment, this.idProject);
        return result;
    }

    public Project getProject (String name) {
        ProjectDAOImpl getProject = new ProjectDAOImpl();
        return getProject.getProject(name);
    }

    public List<String> listLapse () {
        LapseDAOImpl listLapse= new LapseDAOImpl();
        return listLapse.getAllLapse();
    }
}