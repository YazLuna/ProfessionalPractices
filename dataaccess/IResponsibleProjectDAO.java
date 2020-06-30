package dataaccess;

import domain.ResponsibleProject;

import java.util.List;

/**
 * Interface of Responsible Project
 * @author MARTHA
 * @version 008/05/2020
 */
public interface IResponsibleProjectDAO {
    public ResponsibleProject getResponsibleProject (String email);
    public ResponsibleProject getIdResponsibleProject (int idResponsible);
    public int searchIdResponsibleProject(String email);
    public List<ResponsibleProject>  getAllResponsible();
    public List<ResponsibleProject> getAllResponsibleAvailable();
    public List<ResponsibleProject> getAllResponsibleAvailableNotAssing();
    public boolean addResponsibleProject (ResponsibleProject responsible);
    public boolean modifyResponsibleProject (ResponsibleProject responsibleEdit,List<String>DatesUpdate);
    public boolean deleteResponsibleProject (int idResponsibleProject);
    public boolean thereAreResponsibleProject ();
    public boolean thereAreResponsibleProjectAvailable();
    public boolean thereAreResponsibleProjectAvailableNotAssing();
}
