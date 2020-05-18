package dataaccess;

import java.util.List;
import domain.Project;


/**
 * Interface of Project
 * @author MARTHA
 * @version 08/05/2020
 */
public interface IProjectDAO {
    public List<Project> getAllProjects ();
    public Project getProject (String nameProject);
    public List<Project> getAllProjectsAvailable ();
    public String actualizationProject (Project project);
    public String updateProject (Project project);
    public String deleteProject (Project project);
    public String requestProject (String enrollment, int idProject);
    public String assignProject (String enrollment, int idProject);
    
}
