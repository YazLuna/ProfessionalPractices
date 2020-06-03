package dataaccess;

import domain.ResponsibleProject;

/**
 * Interface of Responsible Project
 * @author MARTHA
 * @version 008/05/2020
 */
public interface IResponsibleProjectDAO {
    public ResponsibleProject getResponsibleProject (int idResponsible);
    public String addResponsibleProject (ResponsibleProject responsible);
    public String modifyResponsibleProject (ResponsibleProject responsible);
    public int getIdResponsibleProject(String email);
    public boolean searchResponsibleProject (String email);
}
