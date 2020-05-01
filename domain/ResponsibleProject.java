package domain;


public class ResponsibleProject{
    private String name;
    private String lastName;
    private String email;
    private String charge;
    private int idResponsible;
    
    public ResponsibleProject(){
        
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getLastName () {
        return lastName;
    }
    
    public void setLastName (String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail () {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getCharge () {
        return charge;
    }
    
    public void setCharge (String charge) {
        this.charge = charge;
    }
    
    public int getIdResponsible () {
        return idResponsible;
    }
    
    public void setIdResponsible (int idResponsible) {
        this.idResponsible = idResponsible;
    }
    
}