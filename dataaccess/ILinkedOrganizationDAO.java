package dataaccess;

import java.util.List;
import domain.LinkedOrganization;

/**
 * Interface of LinkedOrganization
 * @author MARTHA
 * @version 08/05/2020
 */
interface ILinkedOrganizationDAO {
    public boolean addLinkedOrganization (LinkedOrganization organization);
    public boolean addCity(String name);
    public boolean addSector(String name);
    public boolean addState(String name);
    public List<LinkedOrganization> getAllLinkedOrganization ();
    public List<LinkedOrganization> getAllLinkedOrganizationAvailable ();
    public List<LinkedOrganization> getAllLinkedOrganizationAvailableNotAssing();
    public LinkedOrganization getLinkedOrganization (String nameOrganization);
    public LinkedOrganization getLinkedOrganizationWithId(int idOrganization);
    public int getIdLinkedOrganization(String name);
    public int getIdCity(String name);
    public List<String> getAllCity();
    public int getIdSector(String name);
    public List<String> getAllSector();
    public int getIdState(String name);
    public List<String> getAllState();
    public boolean modifyLinkedOrganization (LinkedOrganization organization, List<String> datesUpdate);
    public boolean deleteLinkedOrganization (String name);
    public boolean validateAssignedProject(int idLinkedOrganization);
    public boolean validateAssignedProjectAvailable (int idLinkedOrganization);
    public boolean validateRepeatLinkedOrganization(String name, String email);
    public boolean thereAreLinkedOrganizationAvailableNotAssing();
    public boolean thereAreLinkedOrganization ();
    public boolean thereAreLinkedOrganizationAvailable ();
}
