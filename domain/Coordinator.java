package domain;

import java.util.List;
import dataaccess.CoordinatorDAOImpl;
import dataaccess.ProjectDAOImpl;

/**
 * Coordinator Class
 * @author Yazmin
 * @version 09/07/2020
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

    /**
     * Method that calls the Coordinator DAO to adding a Coordinator
     * @param coordinator Object to add
     * @return true if successful false if not
     */
    public static boolean addCoordinator(Coordinator coordinator) {
        CoordinatorDAOImpl addCoordinator = new CoordinatorDAOImpl();
        boolean result = addCoordinator.addCoordinator(coordinator);
        return result;
    }

    /**
     * Method that calls the Coordinator DAO to obtain a Coordinator Active
     * @return Coordinator Object
     */
    public static Coordinator getCoordinator() {
        CoordinatorDAOImpl getCoordinator = new CoordinatorDAOImpl();
        Coordinator coordinator = getCoordinator.getCoordinator();
        return coordinator;
    }

    /**
     * Method that calls the Coordinator DAO to obtain a coordinator according to their staffNumber
     * @param staffNumber from coordinator
     * @return Coordinator Object
     */
    public static Coordinator getCoordinatorSelected(int staffNumber) {
        CoordinatorDAOImpl getCoordinator = new CoordinatorDAOImpl();
        Coordinator coordinator = getCoordinator.getCoordinatorSelected(staffNumber);
        return coordinator;
    }

    /**
     * Method that calls the Coordinator DAO to obtaining the list of Coordinators
     * @return Coordinators List
     */
    public static List<Coordinator> getCoordinators() {
        CoordinatorDAOImpl getAllCoordinator = new CoordinatorDAOImpl();
        List<Coordinator> coordinators = getAllCoordinator.getCoordinators();
        return coordinators;
    }

    /**
     * Method that calls the Coordinator DAO to obtaining information from all Coordinators
     * @return List with complete information of Coordinators
     */
    public static List<Coordinator> getCoordinatorsInformation() {
        CoordinatorDAOImpl getAllCoordinator = new CoordinatorDAOImpl();
        List<Coordinator> coordinators = getAllCoordinator.getCoordinatorsInformation();
        return coordinators;
    }

    /**
     * Method that calls the Coordinator DAO to modify a coordinator
     * @param staffNumberOrigin from Coordinator
     * @param coordinatorEdit Object with new information
     * @param datesUpdate Fields to modify
     * @return True if update, false if not
     */
    public static boolean updateCoordinator(int staffNumberOrigin, Coordinator coordinatorEdit, List<String>datesUpdate) {
        CoordinatorDAOImpl updateCoordinator = new CoordinatorDAOImpl();
        boolean result = updateCoordinator.updateCoordinator(staffNumberOrigin, coordinatorEdit, datesUpdate);
        return result;
    }

    /**
     * Method that calls the Coordinator DAO to recover a deleted Coordinator
     * @param staffNumber from Coordinator
     * @return True if recover, false if not
     */
    public static boolean recoverCoordinator(int staffNumber) {
        CoordinatorDAOImpl recoverCoordinator = new CoordinatorDAOImpl();
        boolean result = recoverCoordinator.recoverCoordinator(staffNumber);
        return result;
    }

    /**
     * Method that calls the Coordinator DAO to delete a Coordinator
     * @param status Inactive
     * @param dischargeDate from Coordinator
     * @return True if delete, False if not
     */
     public static boolean deleteCoordinator(String status, String dischargeDate) {
         CoordinatorDAOImpl deleteCoordinator = new CoordinatorDAOImpl();
         boolean result = deleteCoordinator.deleteCoordinator(status, dischargeDate);
         return result;
     }

    /**
     * Method that calls the Coordinator DAO to know if there is an active coordinator
     * @return True if exists, false if not
     */
    public static boolean activeCoordinator() {
        CoordinatorDAOImpl activeCoordinator = new CoordinatorDAOImpl();
        boolean result = activeCoordinator.activeCoordinator();
        return result;
    }

    /**
     * Method that calls the Coordinator DAO to know if there is any coordinator
     * @return True if exists, false if not
     */
    public static boolean areCoordinator() {
        CoordinatorDAOImpl activeCoordinator = new CoordinatorDAOImpl();
        boolean result = activeCoordinator.areCoordinator();
        return result;
    }

    /**
     * Method that calls the Coordinator DAO to know if that Coordinator is Teacher
     * @param coordinator Object
     * @return True if is Teacher, false if not
     */
    public static boolean isTeacher(Coordinator coordinator) {
        CoordinatorDAOImpl isTeacher = new CoordinatorDAOImpl();
        boolean result = isTeacher.isTeacher(coordinator);
        return result;
    }

    public static Project getProject (String name) {
        ProjectDAOImpl getProject = new ProjectDAOImpl();
        return getProject.getProject(name);
    }
}