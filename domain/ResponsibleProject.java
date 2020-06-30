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
        ResponsibleProjectDAOImpl getAllCharge = new ResponsibleProjectDAOImpl();
        List<String> listCharge = getAllCharge.getAllCharge();
        return  listCharge;
    }

    public boolean searchResponsibleProject () {
        int idResponsibleProjectFound;
        boolean isFoundResponsibleProject;
        ResponsibleProjectDAOImpl searchResponsible = new ResponsibleProjectDAOImpl();
        idResponsibleProjectFound = searchResponsible.searchIdResponsibleProject(email);
        if(idResponsibleProjectFound == Search.NOTFOUND.getValue()){
            isFoundResponsibleProject = false;
        }else {
            isFoundResponsibleProject= true;
        }
        return isFoundResponsibleProject;
    }

    public boolean addResponsibleProject () {
        boolean isAddResponsibleProject;
        ResponsibleProjectDAOImpl addResponsible = new ResponsibleProjectDAOImpl();
        isAddResponsibleProject = addResponsible.addResponsibleProject(this);
        return isAddResponsibleProject;
    }

    public ResponsibleProject getResponsible () {
        ResponsibleProjectDAOImpl getResponsible = new ResponsibleProjectDAOImpl();
        ResponsibleProject responsible = getResponsible.getResponsibleProject(email);
        return responsible;
    }

    public List<ResponsibleProject> listResponsibleProject () {
        ResponsibleProjectDAOImpl getAllResponsibleProject = new ResponsibleProjectDAOImpl();
        List<ResponsibleProject> listResponsiblesProject = getAllResponsibleProject.getAllResponsible();
        return listResponsiblesProject;
    }

    public List<ResponsibleProject> listResponsibleProjectAvailable () {
        ResponsibleProjectDAOImpl getAllResponsibleProject = new ResponsibleProjectDAOImpl();
        List<ResponsibleProject> listResponsiblesProject = getAllResponsibleProject.getAllResponsibleAvailable();
        return listResponsiblesProject;
    }

    public List<ResponsibleProject> listResponsibleProjectAvailableNotAssing () {
        ResponsibleProjectDAOImpl getAllResponsibleProject = new ResponsibleProjectDAOImpl();
        List<ResponsibleProject> listResponsiblesProject = getAllResponsibleProject.getAllResponsibleAvailableNotAssing();
        return listResponsiblesProject;
    }

    public boolean deleteResponsibleProject() {
        boolean isDeleteResponsibleProject;
        ResponsibleProjectDAOImpl deleteResponsible = new ResponsibleProjectDAOImpl();
        isDeleteResponsibleProject = deleteResponsible.deleteResponsibleProject(idResponsible);
        return isDeleteResponsibleProject;
    }

    public boolean thereAreResponsibleProject () {
        ResponsibleProjectDAOImpl areresponsibleProjectDAO = new ResponsibleProjectDAOImpl();
        boolean thereAreResponsible = areresponsibleProjectDAO.thereAreResponsibleProject();
        return thereAreResponsible;
    }

    public boolean thereAreResponsibleProjectAvailable () {
        ResponsibleProjectDAOImpl areresponsibleProjectDAO = new ResponsibleProjectDAOImpl();
        boolean thereAreResponsibleAvailable = areresponsibleProjectDAO.thereAreResponsibleProjectAvailable();
        return thereAreResponsibleAvailable;
    }

    public boolean thereAreResponsibleProjectAvailableNotAssing () {
        ResponsibleProjectDAOImpl areresponsibleProjectDAO = new ResponsibleProjectDAOImpl();
        boolean thereAreResponsibleAvailable = areresponsibleProjectDAO.thereAreResponsibleProjectAvailableNotAssing();
        return thereAreResponsibleAvailable;
    }
}
