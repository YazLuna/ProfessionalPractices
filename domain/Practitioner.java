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
    private String period;
    
    public Practitioner() {
        setUserType("Practitioner");
    }
    
    public String getEnrollment () {
        return enrollment;
    }
    
    public void setEnrollment (String enrollment) {
        this.enrollment = enrollment;
    }
    
    public String getPeriod () {
        return period;
    }
    
    public void setPeriod (String period){
        this.period = period;
    }

    public boolean addPractitioner(){
        PractitionerDAOImpl addPractitioner = new PractitionerDAOImpl();
        boolean result = addPractitioner.addPractitioner(this);
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
        List<Practitioner> practitioners = getAllPractitioner.getAllPractitioner();
        return practitioners;
    }

    public List<Practitioner> getPractitionersActive(){
        PractitionerDAOImpl getAllPractitionerActive = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitionerActive.getPractitionersActive();
        return practitioners;
    }

    public List<Practitioner> getInformationPractitioner(){
        PractitionerDAOImpl getAllPractitioner = new PractitionerDAOImpl();
        List<Practitioner> practitioners = getAllPractitioner.getInformationPractitioner();
        return practitioners;
    }

    public boolean activePractitioner () {
        PractitionerDAOImpl areActive = new PractitionerDAOImpl();
        boolean active = areActive.activePractitioner();
        return active;
    }

    public boolean arePractitioner () {
        PractitionerDAOImpl arePractitioner = new PractitionerDAOImpl();
        boolean practitionerExist = arePractitioner.arePractitioner();
        return practitionerExist;
    }
}