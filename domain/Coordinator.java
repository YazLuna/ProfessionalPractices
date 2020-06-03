package domain;

import java.sql.SQLException;
import java.util.List;
import dataaccess.CoordinatorDAOImpl;
import dataaccess.ProjectDAOImpl;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
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

    public boolean addCoordinator() throws SQLException{
        CoordinatorDAOImpl addCoordinator = new CoordinatorDAOImpl();
        boolean result;
        result = addCoordinator.addCoordinator(this);
        return result;
    }

    public boolean updateCoordinator(int staffNumberCoordinator) throws SQLException{
        boolean result;
        CoordinatorDAOImpl updateCoordinator = new CoordinatorDAOImpl();
        result = updateCoordinator.updateCoordinator(staffNumberCoordinator, this);
        return result;
    }

     public boolean deleteCoordinator(String status, String dischargeDate) throws SQLException {
         CoordinatorDAOImpl deleteCoordinator = new CoordinatorDAOImpl();
         boolean result;
         result = deleteCoordinator.deleteCoordinator(status, dischargeDate);
         return result;
     }

     public Coordinator getCoordinator() throws SQLException {
         CoordinatorDAOImpl getCoordinator = new CoordinatorDAOImpl();
         Coordinator coordinator ;
         coordinator = getCoordinator.getCoordinator();
         return coordinator;
     }

    public List<Coordinator> getAllCoordinator() throws SQLException {
        CoordinatorDAOImpl getAllCoordinator = new CoordinatorDAOImpl();
        List<Coordinator> coordinators;
        coordinators = getAllCoordinator.getAllCoordinator();
        return coordinators;
    }

    public boolean recoverCoordinator() throws SQLException {
        CoordinatorDAOImpl recoverCoordinator = new CoordinatorDAOImpl();
        boolean result;
        result = recoverCoordinator.recoverCoordinator(this);
        return result;
    }

    public Project getProject (String name)  throws SQLException{
        ProjectDAOImpl getProject = new ProjectDAOImpl();
        return getProject.getProject(name);
    }

    public boolean activeCoordinator() throws SQLException {
        CoordinatorDAOImpl activeCoordinator = new CoordinatorDAOImpl();
        boolean result;
        result = activeCoordinator.activeCoordinator();
        return result;
    }

}