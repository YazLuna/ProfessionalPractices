package dataAccess;

import java.util.List;

import domain.LinkedOrganization;

public interface ILinkedOrganizationDAO {
    public List<LinkedOrganization> getAllLinkedOrganization ();
    public LinkedOrganization getLinkedOrganization (int idOrganization);
    public int searchLinkedOrganization (String name);
    public void updateLinkedOrganization (LinkedOrganization organization);
    public void actualizationOrganization (LinkedOrganization organization);
    
    public void updateCity (String name);
    public int searchCity (String name);
    public void updateState (String name);
    public int searchState (String name);
    public void updateSector (String name);
    public int searchSector (String name);
}
