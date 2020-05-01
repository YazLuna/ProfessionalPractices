package domain;

import dataAccess.ProjectDAOImpl;
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
    private String staffNumberCoordinator;
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

    public int registerProject () {
        ProjectDAOImpl registerProject = new ProjectDAOImpl();
        return registerProject.updateProject(this);
    }

    public List<Project> ListProjects () {
        ProjectDAOImpl registerProject = new ProjectDAOImpl();
        return registerProject.getAllProjects();
    }

    public int actualizationProject () {
        ProjectDAOImpl actualizationProject = new ProjectDAOImpl();
        return actualizationProject.actualizationProject(this);
    }

    public String requestProject (String enrollment) {
        ProjectDAOImpl request = new ProjectDAOImpl();
        return request.requestProject(enrollment,this.idProject);
    }

    public int deleteProject () {
        ProjectDAOImpl delete = new ProjectDAOImpl();
        return delete.deleteProject(this);
    }

    public String assingProject (String enrollment) {
        ProjectDAOImpl assing = new ProjectDAOImpl();
        return assing.assignProject(enrollment, this.idProject);
    }
}