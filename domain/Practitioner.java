package domain;

import dataAccess.PractitionerDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Practitioner extends User{
    private String enrollment;
    private String period;
    
    public Practitioner() {
        
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

    public int addPractitioner(){
        int result = 0;
        PractitionerDAOImpl addPractitioner = new PractitionerDAOImpl();
        try{
            result = addPractitioner.addPractitioner(this);
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int updatePractitioner(){
        PractitionerDAOImpl updatePractitioner = new PractitionerDAOImpl();
        int result = 0;
        try{
            result = updatePractitioner.updatePractitioner(enrollment, this);
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //modify enrollment
    public int updatePractitioner(String enrollmentPractitioner){
        PractitionerDAOImpl updatePractitioner = new PractitionerDAOImpl();
        int result = 0;
        try{
            result = updatePractitioner.updatePractitioner(enrollmentPractitioner, this);
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int recoverPractitioner(){
        PractitionerDAOImpl recoverPractitioner = new PractitionerDAOImpl();
        int result = 0;
        try{
            result = recoverPractitioner.recoverPractitioner(this);
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int deletePractitioner(){
        PractitionerDAOImpl deletePractitioner = new PractitionerDAOImpl();
        int result = 0;
        try{
            result = deletePractitioner.deletePractitioner(this);
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Practitioner getPractitioner(){
        PractitionerDAOImpl getPractitioner = new PractitionerDAOImpl();
        Practitioner practitioner = new Practitioner();
        try{
            practitioner = getPractitioner.getPractitioner(enrollment);
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return practitioner ;
    }

    public List<Practitioner> getAllPractitioner(){
        PractitionerDAOImpl getAllPractitioner = new PractitionerDAOImpl();
        List<Practitioner> practitioners = new ArrayList<>();
        try{
            getAllPractitioner.getAllPractitioner();
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return practitioners;
    }

    public List<Practitioner> getPractitionersActive(){
        PractitionerDAOImpl getAllPractitioner = new PractitionerDAOImpl();
        List<Practitioner> practitioners = new ArrayList<>();
        try{
            getAllPractitioner.getPractitionersActive();
        }catch (SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return practitioners;
    }
    
}