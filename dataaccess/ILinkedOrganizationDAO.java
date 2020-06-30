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
    public List<LinkedOrganization> getAllLinkedOrganizationAvailable ();
    public List<LinkedOrganization> getAllLinkedOrganizationAvailableNotAssing();
    public LinkedOrganization getLinkedOrganization (String nameOrganization);
    public LinkedOrganization getIdLinkedOrganization (int idOrganization);
    public int searchIdLinkedOrganization (String name, String email, String phoneNumber);
    public boolean addLinkedOrganization (LinkedOrganization organization);
    public boolean modifyLinkedOrganization (LinkedOrganization organization);
    public boolean deleteLinkedOrganization (int idLinkedOrganization);
    public boolean thereAreLinkedOrganizationAvailable ();
    public boolean thereAreLinkedOrganizationAvailableNotAssing();
    public boolean thereAreLinkedOrganization ();
}
