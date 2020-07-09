package domain;

import java.util.List;
import dataaccess.PractitionerDAOImpl;

/**
 * DAO User
 * @author Yazmin
 * @version 11/06/2020
 */

public class  Practitioner extends User{
    private String enrollment;
    private String term;
    private int credits;
    
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

    public static boolean addPractitioner(Practitioner practitioner){
        PractitionerDAOImpl addPractitioner = new PractitionerDAOImpl();
        boolean result = addPractitioner.addPractitioner(practitioner);
        return result;
    }

    public static boolean validPractitionerAdd(Practitioner practitioner){
        PractitionerDAOImpl addPractitioner = new PractitionerDAOImpl();
        boolean result = addPractitioner.validPractitionerAdd(practitioner);
        return result;
    }

    public boolean updatePractitioner(String enrollmentPractitioner){
        PractitionerDAOImpl updatePractitioner = new PractitionerDAOImpl();
        boolean result = updatePractitioner.updatePractitioner(enrollmentPractitioner, this);
        return result;
    }

    public boolean recoverPractitioner(){
        PractitionerDAOImpl recoverPractitioner = new PractitionerDAOImpl();
        boolean result = recoverPractitioner.recoverPractitioner(this);
        return result;
    }

    public boolean deletePractitioner(){
        PractitionerDAOImpl deletePractitioner = new PractitionerDAOImpl();
        boolean result = deletePractitioner.deletePractitioner(enrollment,getStatus());
        return result;
    }

    public Practitioner getPractitioner(){
        PractitionerDAOImpl getPractitioner = new PractitionerDAOImpl();
        Practitioner practitioner =  getPractitioner.getPractitioner(enrollment);
        return practitioner ;
    }

    public List<Practitioner> getAllPractitioner(){
        PractitionerDAOImpl getAllPractitioner = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitioner.getPractitioners();
        return practitioners;
    }

    public List<Practitioner> getPractitionersActive(){
        PractitionerDAOImpl getAllPractitionerActive = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitionerActive.getPractitionersActive();
        return practitioners;
    }

    public List<Practitioner> getInformationPractitioner(){
        PractitionerDAOImpl getAllPractitioner = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitioner.getPractitionersInformation();
        return practitioners;
    }

    public boolean activePractitioner () {
        PractitionerDAOImpl areActive = new PractitionerDAOImpl();
        boolean active = areActive.activePractitioner();
        return active;
    }

    public boolean arePractitioner () {
        PractitionerDAOImpl arePractitioner = new PractitionerDAOImpl();
        boolean practitionerExist = arePractitioner.arePractitioners();
        return practitionerExist;
    }
}