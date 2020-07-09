package dataaccess;

import java.util.List;
import domain.Project;

/**
 * Interface of Project
 * @author MARTHA
 * @version 08/05/2020
 */
public interface IProjectDAO {
    public boolean addProject (Project project);
    public List<Project> getAllProjects ();
    public List<Project> getAllProjectsAvailable ();
    public Project getProject (String nameProject);
    public int getIdProject (String name);
    public boolean modifyProject(Project project, List<String> datesUpdate);
    public boolean deleteProject (String name);
    public boolean validateRepeatProject (String nameProject);
    public boolean thereAreProjectAvailable();
    public boolean thereAreProject();
}
