package domain;

import java.util.List;
import dataaccess.TeacherDAOImpl;

/**
 * Teacher
 * @author Yazmin
 * @version 06/06/2020
 */

public class Teacher extends User{
    private int staffNumber;
    private String registrationDate;
    private String dischargeDate;
    
    public Teacher () {
        setUserType("Teacher");
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

    public boolean addTeacher() {
        TeacherDAOImpl addTeacher = new TeacherDAOImpl();
        boolean result = addTeacher.addTeacher(this);
        return result;
    }

    public boolean updateTeacher(int staffNumberOrigin, List<String>DatesUpdate) {
        TeacherDAOImpl addTeacher = new TeacherDAOImpl();
        boolean result = addTeacher.updateTeacher(staffNumberOrigin,this,DatesUpdate);
        return result;
    }



    public boolean deleteTeacher(String status, String dischargeDate, int staffNumber) {
        TeacherDAOImpl deleteTeacher = new TeacherDAOImpl();
        boolean result = deleteTeacher.deleteTeacher(status, dischargeDate,staffNumber);
        return result;
    }

    public Teacher getTeacher() {
        TeacherDAOImpl getTeacher = new TeacherDAOImpl();
        Teacher Teacher = getTeacher.getTeacher(staffNumber);
        return Teacher;
    }

    public List<Teacher> getAllTeacher() {
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> Teachers = getAllTeacher.getTeachers();
        return Teachers;
    }

    public boolean recoverTeacher() {
        TeacherDAOImpl recoverTeacher = new TeacherDAOImpl();
        boolean result = recoverTeacher.recoverTeacher(this);
        return result;
    }

    public int activeTeachers() {
        TeacherDAOImpl activeTeacher = new TeacherDAOImpl();
        int result = activeTeacher.activeTeachers();
        return result;
    }

    public Teacher getTeacherSelected(int staffNumber) {
        TeacherDAOImpl getTeacher = new TeacherDAOImpl();
        Teacher Teacher = getTeacher.getTeacher(staffNumber);
        return Teacher;
    }

    public List<Teacher> getInformationAllTeacher() {
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> Teachers = getAllTeacher.getTeachersInformation();
        return Teachers;
    }

    public List<Teacher> getAllTeacherActive() {
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> Teachers = getAllTeacher.getTeachersActive();
        return Teachers;
    }

    public boolean isCoordinator() {
        TeacherDAOImpl isCoordinator = new TeacherDAOImpl();
        boolean result = isCoordinator.isCoordinator(this);
        return result;
    }
}