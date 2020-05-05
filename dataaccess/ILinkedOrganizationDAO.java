package dataaccess;

import java.util.List;
import domain.LinkedOrganization;

public interface ILinkedOrganizationDAO {
    public List<LinkedOrganization> getAllLinkedOrganization ();
    public LinkedOrganization getLinkedOrganization (int idOrganization);
    public int searchLinkedOrganization (String name);
    public void updateLinkedOrganization (LinkedOrganization organization);
    public void actualizationOrganization (LinkedOrganization organization);
}
