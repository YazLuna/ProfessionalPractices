package domain;

import dataaccess.TeacherDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public class Teacher extends User{
    private int staffNumber;
    private String registrationDate;
    private String dischargeDate;
    
    public Teacher () {
        
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

    public int addTeacher(){
        TeacherDAOImpl addTeacher = new TeacherDAOImpl();
        int result = 0;
        try{
            result = addTeacher.addTeacher(this);
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int updateTeacher(){
        TeacherDAOImpl updateTeacher = new TeacherDAOImpl();
        int result = 0;
        try{
            result = updateTeacher.updateTeacher(this.staffNumber,this);
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //modify staffNumber
    public int updateTeacher(int staffNumberTeacher){
        TeacherDAOImpl updateTeacher = new TeacherDAOImpl();
        int result = 0;
        try{
            result = updateTeacher.updateTeacher(staffNumberTeacher, this);
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int recoverTeacher(){
        TeacherDAOImpl recoverTeacher = new TeacherDAOImpl();
        int result = 0;
        try{
            result = recoverTeacher.recoverTeacher(this);
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int deleteTeacher(){
        TeacherDAOImpl deleteTeacher = new TeacherDAOImpl();
        int result = 0;
        try{
            result = deleteTeacher.deleteTeacher(this);
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  result;
    }

    public Teacher getTeacher(){
        TeacherDAOImpl getTeacher = new TeacherDAOImpl();
        Teacher teacher = new Teacher();
        try{
            teacher = getTeacher.getTeacher(this.staffNumber);
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacher;
    }

    public List<Teacher> getAllTeacher(){
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> teachers= new ArrayList<>();
        try{
            teachers = getAllTeacher.getAllTeacher();
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }

    public List<Teacher> getTeachersActive(){
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> teachers= new ArrayList<>();
        try{
            teachers = getAllTeacher.getTeachersActive();
        }catch (SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }
}