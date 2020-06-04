package domain;

import dataaccess.ResponsibleProjectDAOImpl;
import java.util.List;

public class ResponsibleProject{
    private String name;
    private String lastName;
    private String email;
    private String charge;
    private String status;
    private int idResponsible;
    
    public ResponsibleProject(){
        this.status="available";
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

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }
    
    public int getIdResponsible () {
        return idResponsible;
    }
    
    public void setIdResponsible (int idResponsible) {
        this.idResponsible = idResponsible;
    }

    public List<String> listCharge () {
        ResponsibleProjectDAOImpl listCharge = new ResponsibleProjectDAOImpl();
        return listCharge.getAllCharge();
    }

    public String addResponsibleProject () {
        String result;
        ResponsibleProjectDAOImpl addResponsible = new ResponsibleProjectDAOImpl();
        if(addResponsible.searchIdResponsibleProject(email) == Search.NOTFOUND.getValue()){
            result = addResponsible.addResponsibleProject(this);
        } else {
            result = "Existe un responsable del proyecto con el mismo correo electrónico registrado";
        }
        return result;
    }
    
}
