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
    public LinkedOrganization getLinkedOrganization (String nameOrganization);
    public LinkedOrganization getIdLinkedOrganization (int idOrganization);
    public int searchIdLinkedOrganization (String name, String email, String phoneNumber);
    public String addLinkedOrganization (LinkedOrganization organization);
    public String modifyLinkedOrganization (LinkedOrganization organization);
}
