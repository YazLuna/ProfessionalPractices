package dataaccess;

import domain.ResponsibleProject;

/**
 * Interface of Responsible Project
 * @author MARTHA
 * @version 008/05/2020
 */
public interface IResponsibleProjectDAO {
    public ResponsibleProject getResponsibleProject (int idResponsible);
    public String updateResponsibleProject (ResponsibleProject responsible);
    public String actualizationResponsibleProject (ResponsibleProject responsible);
    public int searchResponsibleProject (String email);
}
