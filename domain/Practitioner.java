package domain;

import java.util.List;
import dataaccess.PractitionerDAOImpl;

/**
 * Practitioner Class
 * @author Yazmin
 * @version 09/07/2020
 */
public class  Practitioner extends User{
    private String enrollment;
    private String term;
    private int credits;
    private int staffNumberCoordinator;
    
    public Practitioner() {
        setUserType("Practitioner");
    }
    
    public String getEnrollment () {
        return enrollment;
    }
    
    public void setEnrollment (String enrollment) {
        this.enrollment = enrollment;
    }
    
    public String getTerm() {
        return term;
    }
    
    public void setTerm(String term){
        this.term = term;
    }

    public int getCredits () {
        return credits;
    }

    public void setCredits (int credits) {
        this.credits = credits;
    }

    public int getStaffNumberCoordinator () {
        return  staffNumberCoordinator;
    }

    public void setStaffNumberCoordinator (int staffNumberCoordinator) {
        this.staffNumberCoordinator = staffNumberCoordinator;
    }

    /**
     * method that calls the PractitionerDAO to add a practitioner
     * @param practitioner Object to add
     * @return true if successful false if not
     */
    public static boolean addPractitioner(Practitioner practitioner){
        PractitionerDAOImpl addPractitioner = new PractitionerDAOImpl();
        boolean result = addPractitioner.addPractitioner(practitioner);
        return result;
    }

    /**
     * Method that calls the PractitionerDAO to get a practitioner according to his enrollment
     * @param enrollment from practitioner
     * @return Practitioner Object
     */
    public static Practitioner getPractitioner(String enrollment){
        PractitionerDAOImpl getPractitioner = new PractitionerDAOImpl();
        Practitioner practitioner =  getPractitioner.getPractitioner(enrollment);
        return practitioner ;
    }

    /**
     * Method that calls the PractitionerDAO to obtaining the list of Practitioner
     * @return Practitioner List
     */
    public static List<Practitioner> getPractitioners(){
        PractitionerDAOImpl getAllPractitioner = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitioner.getPractitioners();
        return practitioners;
    }

    /**
     * Method that calls the PractitionerDAO to obtaining the list of active Practitioner
     * @return List of active Practitioner
     */
    public static List<Practitioner> getPractitionersActive(){
        PractitionerDAOImpl getAllPractitionerActive = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitionerActive.getPractitionersActive();
        return practitioners;
    }

    /**
     * Method that calls the PractitionerDAO to obtaining information from all Practitioner
     * @return List with complete information of Practitioner
     */
    public static List<Practitioner> getInformationPractitioner(){
        PractitionerDAOImpl getAllPractitioner = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitioner.getPractitionersInformation();
        return practitioners;
    }

    /**
     * Method that calls the practitionerDAO to modify a practitioner
     * @param enrollmentOrigin from practitioner
     * @param practitioner Object with new information
     * @param datesUpdate Fields to modify
     * @return True if update, false if not
     */
    public static boolean updatePractitioner(String enrollmentOrigin, Practitioner practitioner, List<String>datesUpdate){
        PractitionerDAOImpl updatePractitioner = new PractitionerDAOImpl();
        boolean result = updatePractitioner.updatePractitioner(enrollmentOrigin, practitioner,datesUpdate);
        return result;
    }

    /**
     * Method that calls the PractitionerDAO to recover a deleted Practitioner
     * @param enrollment from Practitioner
     * @return True if recover, false if not
     */
    public static boolean recoverPractitioner(String enrollment){
        PractitionerDAOImpl recoverPractitioner = new PractitionerDAOImpl();
        boolean result = recoverPractitioner.recoverPractitioner(enrollment);
        return result;
    }

    /**
     * Method that calls the PractitionerDAO to delete a Practitioner
     * @param status Inactive
     * @param enrollment from Practitioner
     * @return True if delete, False if not
     */
    public static boolean deletePractitioner(String enrollment, String status){
        PractitionerDAOImpl deletePractitioner = new PractitionerDAOImpl();
        boolean result = deletePractitioner.deletePractitioner(enrollment,status);
        return result;
    }

    /**
     * Method that calls the PractitionerDAO to know the number of active Practitioner
     * @return if there is any active practitioner
     */
    public static int activePractitioner () {
        PractitionerDAOImpl areActive = new PractitionerDAOImpl();
        int active = areActive.activePractitioner();
        return active;
    }

    /**
     * Method that calls the PractitionerDAO to know if exist Practitioner
     * @return if there is any practitioner
     */
    public static int arePractitioner () {
        PractitionerDAOImpl arePractitioner = new PractitionerDAOImpl();
        int practitionerExist = arePractitioner.arePractitioners();
        return practitionerExist;
    }

    /**
     * Method to know if a Practitioner is valid to add
     * @param practitioner Object
     * @return 0 if is valid, 1 if not and -1 for exception
     */
    public static int validPractitionerAdd(Practitioner practitioner){
        PractitionerDAOImpl addPractitioner = new PractitionerDAOImpl();
        int result = addPractitioner.validPractitionerAdd(practitioner);
        return result;
    }

    /**
     * Method to know if a Practitioner is valid to update
     * @param practitioner Object
     * @return 0 if is valid, 1 if not and -1 for exception
     */
    public static int validPractitionerUpdate(Practitioner practitioner){
        PractitionerDAOImpl addPractitioner = new PractitionerDAOImpl();
        int result = addPractitioner.validPractitionerUpdate(practitioner);
        return result;
    }
}