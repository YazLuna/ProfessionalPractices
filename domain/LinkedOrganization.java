package domain;
 
public class LinkedOrganization{
    private String name;
    private String email;
    private String phoneNumber;
    private String adress;
    private String city;
    private String state;
    private String sector;
    private int directUsers;
    private int indirectUsers;
    private int idLinkedOrganization;
    
    public LinkedOrganization () {
        
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
    
    public String getAdress () {
        return adress;
    }
    
    public void setAdress (String adress) {
        this.adress = adress;
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
    
    public int getDirectUsers () {
        return directUsers;
    }
    
    public void setDirectUsers (int directUsers) {
        this.directUsers = directUsers;
    }
    
    public int getIndirectUsers () {
        return indirectUsers;
    }
    
    public void setIndirectUsers (int indirectUsers) {
        this.indirectUsers = indirectUsers;
    }
    
    public int getIdLinkedOrganization () {
        return idLinkedOrganization;
    }
    
    public void setIdLinkedOrganization (int idLinkedOrganization) {
        this.idLinkedOrganization = idLinkedOrganization;
    }
    
}
