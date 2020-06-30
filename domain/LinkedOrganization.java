package domain;

import java.util.ArrayList;
import java.util.List;
import dataaccess.LinkedOrganizationDAOImpl;

public class LinkedOrganization{
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String directUsers;
    private String indirectUsers;
    private String status;
    private String city;
    private String state;
    private String sector;
    private int idLinkedOrganization;
    
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
    
    public String getPhoneNumber () {
        return phoneNumber;
    }
    
    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<String> listState () {
        LinkedOrganizationDAOImpl getAllState = new LinkedOrganizationDAOImpl();
        List<String> listState = getAllState.getAllState();
        return listState;
    }

    public List<String> listCity () {
        LinkedOrganizationDAOImpl getAllCity = new LinkedOrganizationDAOImpl();
        List<String> listCity = getAllCity.getAllCity();
        return listCity;
    }

    public List<String> listSector () {
        LinkedOrganizationDAOImpl getAllSector = new LinkedOrganizationDAOImpl();
        List<String> listSector = getAllSector.getAllSector();
        return listSector;
    }

    public boolean searchLinkedOrganization () {
        int idLinkegOrganizationFound;
        boolean isFoundLinkedOrganization;
        LinkedOrganizationDAOImpl seachOrganization = new LinkedOrganizationDAOImpl();
        idLinkegOrganizationFound = seachOrganization.searchIdLinkedOrganization(name,email,phoneNumber);
        if(idLinkegOrganizationFound != Search.NOTFOUND.getValue()){
            isFoundLinkedOrganization = true;
        }else{
            isFoundLinkedOrganization = false;
        }
        return isFoundLinkedOrganization;
    }

    public boolean addLinkedOrganization () {
        boolean isAddLinkedOrganization;
        LinkedOrganizationDAOImpl addOrganization = new LinkedOrganizationDAOImpl();
        isAddLinkedOrganization = addOrganization.addLinkedOrganization(this);
        return isAddLinkedOrganization;
    }

    public List<LinkedOrganization> listOrganizationAvailable() {
        LinkedOrganizationDAOImpl getAllOrganization = new LinkedOrganizationDAOImpl();
        List<LinkedOrganization> listLinkedOrganization =  getAllOrganization.getAllLinkedOrganizationAvailable();
        return listLinkedOrganization;
    }

    public List<LinkedOrganization> listOrganizationAvailableNotAssing() {
        LinkedOrganizationDAOImpl getAllOrganization = new LinkedOrganizationDAOImpl();
        List<LinkedOrganization> listLinkedOrganization =  getAllOrganization.getAllLinkedOrganizationAvailableNotAssing();
        return listLinkedOrganization;
    }

    public List<LinkedOrganization> listOrganization () {
        LinkedOrganizationDAOImpl getAllOrganization = new LinkedOrganizationDAOImpl();
        List<LinkedOrganization> listLinkedOrganization = getAllOrganization.getAllLinkedOrganization();
        return listLinkedOrganization;
    }

    public LinkedOrganization getOrganization () {
        LinkedOrganizationDAOImpl getOrganization = new LinkedOrganizationDAOImpl();
        LinkedOrganization organization = getOrganization.getLinkedOrganization(name);
        return organization;
    }

    public boolean deleteOrganization () {
        LinkedOrganizationDAOImpl deleteOrganization = new LinkedOrganizationDAOImpl();
        boolean isDeleteOrganization = deleteOrganization.deleteLinkedOrganization(idLinkedOrganization);
        return isDeleteOrganization;
    }

    public boolean thereAreLinkedOrganization () {
        LinkedOrganizationDAOImpl areLinkedOrganization = new LinkedOrganizationDAOImpl();
        boolean thereAreOrganization = areLinkedOrganization.thereAreLinkedOrganization();
        return thereAreOrganization;
    }

    public boolean thereAreLinkedOrganizationAvailable () {
        LinkedOrganizationDAOImpl areLinkedOrganizationAvailable = new LinkedOrganizationDAOImpl();
        boolean thereAreOrganizationAvailable = areLinkedOrganizationAvailable.thereAreLinkedOrganizationAvailable();
        return thereAreOrganizationAvailable;
    }

    public boolean thereAreLinkedOrganizationAvailableNotAssing () {
        LinkedOrganizationDAOImpl areLinkedOrganizationAvailable = new LinkedOrganizationDAOImpl();
        boolean thereAreOrganizationAvailable = areLinkedOrganizationAvailable.thereAreLinkedOrganizationAvailableNotAssing();
        return thereAreOrganizationAvailable;
    }
}
