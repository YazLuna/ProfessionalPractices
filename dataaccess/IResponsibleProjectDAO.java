package dataaccess;

import domain.ResponsibleProject;

import java.util.List;

/**
 * Interface of Responsible Project
 * @author MARTHA
 * @version 08/05/2020
 */
interface IResponsibleProjectDAO {
    public boolean addResponsibleProject (ResponsibleProject responsible);
    public boolean addCharge (String name);
    public ResponsibleProject getResponsibleProjectWithId(int idResponsible);
    public ResponsibleProject getResponsibleProject (String email);
    public int getIdResponsibleProject(String email);
    public List<ResponsibleProject> getAllResponsible();
    public List<ResponsibleProject> getAllResponsibleAvailable();
    public List<ResponsibleProject> getAllResponsibleAvailableNotAssing();
    public int getIdCharge(String name);
    public List<String> getAllCharge();
    public boolean modifyResponsibleProject (ResponsibleProject responsibleEdit,List<String>DatesUpdate);
    public boolean deleteResponsibleProject (String email);
    public int validateRepeatResponsibleProject (String email);
    public boolean validateAssignedProject(int idResponsibleProject);
    public boolean validateAssignedProjectAvailable (int idResponsibleProject);
    public boolean thereAreResponsibleProjectAvailable();
    public boolean thereAreResponsibleProjectAvailableNotAssing();
    public boolean thereAreResponsibleProject ();
}
