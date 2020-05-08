package dataaccess;

import domain.ResponsibleProject;
/**
 *
 * @author: Martha M. Ortiz
 * @version: 08/05/2020
 */

public interface IResponsibleProjectDAO {
    public ResponsibleProject getResponsibleProject (int idResponsible);
    public String updateResponsibleProject (ResponsibleProject responsible);
    public String actualizationResponsibleProject (ResponsibleProject responsible);
    public int searchResponsibleProject (String email);
}
