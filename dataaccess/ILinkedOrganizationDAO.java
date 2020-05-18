package dataaccess;

import java.util.List;
import domain.LinkedOrganization;

/**
 * Interface of LinkedOrganization
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ILinkedOrganizationDAO {
    public List<LinkedOrganization> getAllLinkedOrganization ();
    public LinkedOrganization getLinkedOrganization (int idOrganization);
    public int searchLinkedOrganization (String name, String email);
    public String updateLinkedOrganization (LinkedOrganization organization);
    public String actualizationOrganization (LinkedOrganization organization);
}
