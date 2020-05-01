package dataAccess;

import java.util.List;

import domain.Project;

public interface IProjectDAO {
    public List<Project> getAllProjects ();
    public Project getProject (String nameProject);

    public int actualizationProject (Project project);
    public int updateProject (Project project);
    public int deleteProject (Project project);
    public String requestProject (String enrollment, int idProject);
    public String assignProject (String enrollment, int idProject);
    
    
}
