package domain;

import java.sql.SQLException;
import java.util.List;
import dataaccess.CoordinatorDAOImpl;
import dataaccess.ProjectDAOImpl;

/**
 * DAO User
 * @author Yazmin
 * @version 05/06/2020
 */

public class Coordinator extends User{
    private int staffNumber;
    private String registrationDate;
    private String dischargeDate;
    
    public Coordinator () {
        setUserType("Coordinator");
    }
    
    public int getStaffNumber () {
        return staffNumber;
    }
    
    public void setStaffNumber (int staffNumber) {
        this.staffNumber = staffNumber;
    }
    
    public String getRegistrationDate () {
        return registrationDate;
    }
    
    public void setRegistrationDate (String registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public String getDischargeDate () {
        return dischargeDate;
    }
    
    public void setDischargeDate (String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public boolean addCoordinator() {
        CoordinatorDAOImpl addCoordinator = new CoordinatorDAOImpl();
        boolean result = addCoordinator.addCoordinator(this);
        return result;
    }

    public boolean updateCoordinator(int staffNumberCoordinator) {
        CoordinatorDAOImpl updateCoordinator = new CoordinatorDAOImpl();
        boolean result = updateCoordinator.updateCoordinator(staffNumberCoordinator, this);
        return result;
    }

     public boolean deleteCoordinator(String status, String dischargeDate) {
         CoordinatorDAOImpl deleteCoordinator = new CoordinatorDAOImpl();
         boolean result = deleteCoordinator.deleteCoordinator(status, dischargeDate);
         return result;
     }

     public Coordinator getCoordinator() {
         CoordinatorDAOImpl getCoordinator = new CoordinatorDAOImpl();
         Coordinator coordinator = getCoordinator.getCoordinator();
         return coordinator;
     }

    public List<Coordinator> getAllCoordinator() {
        CoordinatorDAOImpl getAllCoordinator = new CoordinatorDAOImpl();
        List<Coordinator> coordinators = getAllCoordinator.getAllCoordinator();
        return coordinators;
    }

    public boolean recoverCoordinator() {
        CoordinatorDAOImpl recoverCoordinator = new CoordinatorDAOImpl();
        boolean result = recoverCoordinator.recoverCoordinator(this);
        return result;
    }

    public Project getProject (String name) {
        ProjectDAOImpl getProject = new ProjectDAOImpl();
        return getProject.getProject(name);
    }

    public boolean activeCoordinator() {
        CoordinatorDAOImpl activeCoordinator = new CoordinatorDAOImpl();
        boolean result = activeCoordinator.activeCoordinator();
        return result;
    }

    public Coordinator getCoordinatorSelected(int staffNumber) {
        CoordinatorDAOImpl getCoordinator = new CoordinatorDAOImpl();
        Coordinator coordinator = getCoordinator.getCoordinatorSelected(staffNumber);
        return coordinator;
    }

    public List<Coordinator> getInformationAllCoordinator() {
        CoordinatorDAOImpl getAllCoordinator = new CoordinatorDAOImpl();
        List<Coordinator> coordinators = getAllCoordinator.getInformationAllCoordinator();
        return coordinators;
    }

    public boolean areCoordinator() {
        CoordinatorDAOImpl activeCoordinator = new CoordinatorDAOImpl();
        boolean result = activeCoordinator.areCoordinator();
        return result;
    }
}