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
    public List<Project> getAllProjectsAvailable ();
    public List<Project> getAllProjectsAvailableNotAssing ();
    public Project getProject (String nameProject);
    public boolean actualizationProject (Project project);
    public boolean addProject (Project project);
    public boolean deleteProject (String name);
    public boolean requestProject (String enrollment, int idProject);
    public boolean assignProject (String enrollment, int idProject);
    public int getIdProject (String name);
    public boolean thereAreProjectAvailable();
    public boolean thereAreProject();
    public boolean thereAreProjectAvailableNotAssing();
}
