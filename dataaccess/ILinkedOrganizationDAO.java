package dataaccess;

import java.util.List;
import domain.LinkedOrganization;

/**
 * Interface of LinkedOrganization
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ILinkedOrganizationDAO {
    public List<String> getAllLinkedOrganization ();
    public LinkedOrganization getLinkedOrganization (int idOrganization);
    public int searchLinkedOrganization (String name, String email);
    public String addLinkedOrganization (LinkedOrganization organization);
    public String modifyLinkedOrganization (LinkedOrganization organization);
}
