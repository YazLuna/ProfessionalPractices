package domain;

import java.util.List;
import dataaccess.CityDAOImpl;
import dataaccess.LinkedOrganizationDAOImpl;
import dataaccess.SectorDAOImpl;
import dataaccess.StateDAOImpl;

/**
 * Class Linked organization
 * @author MARTHA
 * @version 08/05/2020
 */
public class LinkedOrganization{
    private String name;
    private String email;
    private String address;
    private String directUsers;
    private String indirectUsers;
    private String status;
    private String city;
    private String state;
    private String sector;
    private int idLinkedOrganization;
    private List<PhoneNumber> phoneNumbers;
    
    public LinkedOrganization () {
        this.status = "available";
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getEmail () {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getAddress () {
        return address;
    }
    
    public void setAddress (String adress) {
        this.address = adress;
    }
    
    public String getCity () {
        return city;
    }
    
    public void setCity (String city) {
        this.city = city;
    }
    
    public String getState () {
        return state;
    }
    
    public void setState (String state) {
        this.state = state;
    }
    
    public String getSector () {
        return sector;
    } 
    
    public void setSector (String sector) {
        this.sector =sector;
    }
    
    public String getDirectUsers () {
        return directUsers;
    }
    
    public void setDirectUsers (String directUsers) {
        this.directUsers = directUsers;
    }
    
    public String getIndirectUsers () {
        return indirectUsers;
    }
    
    public void setIndirectUsers (String indirectUsers) {
        this.indirectUsers = indirectUsers;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public int getIdLinkedOrganization () {
        return idLinkedOrganization;
    }
    
    public void setIdLinkedOrganization (int idLinkedOrganization) {
        this.idLinkedOrganization = idLinkedOrganization;
    }

    /**
     * Method for add the linked organization
     * @param linkedOrganization define the data of the linked organization
     * @return if the linked organization was added
     */
    public static boolean addLinkedOrganization (LinkedOrganization linkedOrganization) {
        boolean isAddLinkedOrganization;
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        isAddLinkedOrganization = linkedOrganizationDAO.addLinkedOrganization(linkedOrganization);
        return isAddLinkedOrganization;
    }

    /**
     * Method for get the list of states
     * @return The list of states
     */
    public static List<String> getListState() {
        StateDAOImpl stateDAO = new StateDAOImpl();
        List<String> listState = stateDAO.getAllState();
        return listState;
    }

    /**
     * Method for get the list of cities
     * @return The list of cities
     */
    public static List<String> getListCity() {
        CityDAOImpl cityDAO = new CityDAOImpl();
        List<String> listCity = cityDAO.getAllCity();
        return listCity;
    }

    /**
     * Method for get the list of sectors
     * @return The list of sectors
     */
    public static List<String> getListSector() {
        SectorDAOImpl sectorDAO = new SectorDAOImpl();
        List<String> listSector = sectorDAO.getAllSector();
        return listSector;
    }

    /**
     * Method for get the list of linked organization avalable
     * @return The list of the linked organization available
     */
    public static List<LinkedOrganization> getListOrganizationAvailable() {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        List<LinkedOrganization> listLinkedOrganization =  linkedOrganizationDAO.getAllLinkedOrganizationAvailable();
        return listLinkedOrganization;
    }

    /**
     * Method for get the list of linked organization available not assign
     * @return The list of the linked organization available not assign
     */
    public static List<LinkedOrganization> getListOrganizationAvailableNotAssign() {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        List<LinkedOrganization> listLinkedOrganization =  linkedOrganizationDAO.getAllLinkedOrganizationAvailableNotAssing();
        return listLinkedOrganization;
    }

    /**
     * Method for get the list of linked organization
     * @return The list of the linked organization
     */
    public static List<LinkedOrganization> getListOrganization() {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        List<LinkedOrganization> listLinkedOrganization = linkedOrganizationDAO.getAllLinkedOrganization();
        return listLinkedOrganization;
    }

    /**
     * Method for get the linked organization
     * @param name defines the name of the linked organization
     * @return The data of the linked organization
     */
    public static LinkedOrganization getOrganization (String name) {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        LinkedOrganization organization = linkedOrganizationDAO.getLinkedOrganization(name);
        return organization;
    }

    /**
     * Method for modify the linked organization
     * @param linkedOrganization the data of the linked organization
     * @param datesUpdate the fields to modify
     * @return if delete the linked organization
     */
    public static boolean modifyOrganization (LinkedOrganization linkedOrganization, List<String> datesUpdate) {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        boolean isModifyOrganization = linkedOrganizationDAO.modifyLinkedOrganization(linkedOrganization, datesUpdate);
        return isModifyOrganization;
    }

    /**
     * Method for delete the linked organization
     * @param name define name of the linked organization
     * @return if delete the linked organization
     */
    public static boolean deleteOrganization (String name) {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        boolean isDeleteOrganization = linkedOrganizationDAO.deleteLinkedOrganization(name);
        return isDeleteOrganization;
    }

    /**
     * Method to know if the linked organization is repeated
     * @param name defines the name of the linked organization
     * @param email defines the email of the linked organization
     * @return if the linked organization already exists
     */
    public static boolean validateRepeatLinkedOrganization(String name, String email) {
        boolean isValidRepeatLinkedOrganization;
        LinkedOrganizationDAOImpl seachOrganization = new LinkedOrganizationDAOImpl();
        isValidRepeatLinkedOrganization = seachOrganization.validateRepeatLinkedOrganization(name,email);
        return isValidRepeatLinkedOrganization;
    }

    /**
     * Method to know if there are linked organizations
     * @return If there is a linked organization
     */
    public static boolean thereAreLinkedOrganization () {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        boolean thereAreOrganization = linkedOrganizationDAO.thereAreLinkedOrganization();
        return thereAreOrganization;
    }

    /**
     * Method to know if there are linked organizations available
     * @return If there is a linked organization available
     */
    public static boolean thereAreLinkedOrganizationAvailable () {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        boolean thereAreOrganizationAvailable = linkedOrganizationDAO.thereAreLinkedOrganizationAvailable();
        return thereAreOrganizationAvailable;
    }

    /**
     * Method to know if there are linked organizations available not assign
     * @return If there is a linked organization available not assign
     */
    public static boolean thereAreLinkedOrganizationAvailableNotAssign() {
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        boolean thereAreOrganizationAvailable = linkedOrganizationDAO.thereAreLinkedOrganizationAvailableNotAssing();
        return thereAreOrganizationAvailable;
    }

    /**
     * Method to get the linked organization with the name
     * @param name defines the name of the linked organization
     * @return the linked organization
     */
    public static int searchIdLinkedOrganization(String name){
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        int idLinkedOrganization = linkedOrganizationDAO.getIdLinkedOrganization(name);
        return idLinkedOrganization;
    }

}
