package domain;

import java.util.List;
import dataaccess.LinkedOrganizationDAOImpl;
import dataaccess.ResponsibleProjectDAOImpl;

public class LinkedOrganization{
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String sector;
    private String directUsers;
    private String indirectUsers;
    private String status;
    private int idLinkedOrganization;
    
    public LinkedOrganization () {
        this.status="available";
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
        return getAllState.getAllState();
    }

    public List<String> listCity () {
        LinkedOrganizationDAOImpl listCity = new LinkedOrganizationDAOImpl();
        return listCity.getAllCity();
    }

    public List<String> listSector () {
        LinkedOrganizationDAOImpl getAllSector = new LinkedOrganizationDAOImpl();
        return getAllSector.getAllSector();
    }

    public String addLinkedOrganization () {
        String result;
        LinkedOrganizationDAOImpl addOrganization = new LinkedOrganizationDAOImpl();
        if(addOrganization.searchLinkedOrganization(name,email) == Search.NOTFOUND.getValue()){
            result = addOrganization.addLinkedOrganization(this);
        } else {
            result = "Existe una organizacion vinculada con el mismo nombre o correo registrado";
        }
        return result;
    }

    public List<String> listOrganization () {
        LinkedOrganizationDAOImpl getAllOrganization = new LinkedOrganizationDAOImpl();
        return getAllOrganization.getAllLinkedOrganization();
    }
    
}
