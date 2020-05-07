package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import dataaccess.CoordinatorDAOImpl;
import dataaccess.ProjectDAOImpl;

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

    public int addCoordinator(){
        CoordinatorDAOImpl addCoordinator = new CoordinatorDAOImpl();
        int result = 0;
        try{
            result = addCoordinator.addCoordinator(this);
        }catch (SQLException ex){
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int updateCoordinator(int staffNumberCoordinator){
        int result = 0;
        CoordinatorDAOImpl updateCoordinator = new CoordinatorDAOImpl();
        try{
            result = updateCoordinator.updateCoordinator(staffNumberCoordinator, this);
        }catch (SQLException ex){
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

     public int deleteCoordinator(){
         CoordinatorDAOImpl deleteCoordinator = new CoordinatorDAOImpl();
         int result = 0;
         try{
             result = deleteCoordinator.deleteCoordinator(this);
         }catch (SQLException ex){
             Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
         return result;
     }

     public Coordinator getCoordinator(){
         CoordinatorDAOImpl getCoordinator = new CoordinatorDAOImpl();
         Coordinator coordinator = new Coordinator() ;
         try{
             coordinator = getCoordinator.getCoordinator();
         }catch (SQLException ex){
             Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
         return coordinator;
     }

    public List<Coordinator> getAllCoordinator(){
        CoordinatorDAOImpl getAllCoordinator = new CoordinatorDAOImpl();
        List<Coordinator> coordinators = new ArrayList<>();
        try{
            coordinators = getAllCoordinator.getAllCoordinator();
        }catch (SQLException ex){
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coordinators;
    }

    public int recoverCoordinator(){
        CoordinatorDAOImpl recoverCoordinator = new CoordinatorDAOImpl();
        int result = 0;
        try{
            result = recoverCoordinator.recoverCoordinator(this);
        }catch (SQLException ex){
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Project getProject (String name) {
        ProjectDAOImpl getProject = new ProjectDAOImpl();
        return getProject.getProject(name);
    }

}