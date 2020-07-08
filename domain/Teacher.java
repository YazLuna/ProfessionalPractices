package domain;

import java.util.List;
import dataaccess.TeacherDAOImpl;

/**
 * Teacher Class
 * @author Yazmin
 * @version 06/07/2020
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

    /**
     * method that calls the TeacherDAO to add a teacher
     * @param teacher Object to add
     * @return true if successful false if not
     */
    public static boolean addTeacher(Teacher teacher) {
        TeacherDAOImpl addTeacher = new TeacherDAOImpl();
        boolean result = addTeacher.addTeacher(teacher);
        return result;
    }

    /**
     * Method that calls the TeacherDAO to get a teacher according to his personal number
     * @param staffNumber from teacher
     * @return Teacher Object
     */
    public static Teacher getTeacherSelected(int staffNumber) {
        TeacherDAOImpl getTeacher = new TeacherDAOImpl();
        Teacher Teacher = getTeacher.getTeacher(staffNumber);
        return Teacher;
    }

    /**
     * Method that calls the TeacherDAO to obtaining information from all teachers
     * @return List with complete information of teachers
     */
    public static List<Teacher> getTeachersInformation() {
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> Teachers = getAllTeacher.getTeachersInformation();
        return Teachers;
    }

    /**
     * Method that calls the TeacherDAO to obtaining the list of active teachers
     * @return List of active teachers
     */
    public static List<Teacher> getTeachersActive() {
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> Teachers = getAllTeacher.getTeachersActive();
        return Teachers;
    }

    /**
     * Method that calls the TeacherDAO to obtaining the list of teachers
     * @return Teachers List
     */
    public static List<Teacher> getTeachers() {
        TeacherDAOImpl getAllTeacher = new TeacherDAOImpl();
        List<Teacher> Teachers = getAllTeacher.getTeachers();
        return Teachers;
    }

    /**
     * Method that calls the TeacherDAO to modify a teacher
     * @param staffNumberOrigin from Teacher
     * @param teacher Object with new information
     * @param datesUpdate Fields to modify
     * @return True if update, false if not
     */
    public static boolean updateTeacher(int staffNumberOrigin, Teacher teacher, List<String>datesUpdate) {
        TeacherDAOImpl addTeacher = new TeacherDAOImpl();
        boolean result = addTeacher.updateTeacher(staffNumberOrigin, teacher, datesUpdate);
        return result;
    }

    /**
     * Method that calls the TeacherDAO to recover a deleted teacher
     * @param staffNumber from Teacher
     * @return True if recover, false if not
     */
    public static boolean recoverTeacher(int staffNumber) {
        TeacherDAOImpl recoverTeacher = new TeacherDAOImpl();
        boolean result = recoverTeacher.recoverTeacher(staffNumber);
        return result;
    }

    /**
     * Method that calls the TeacherDAO to delete a teacher
     * @param status Inactive
     * @param dischargeDate from Teacher
     * @param staffNumber from Teacher
     * @return True if delete, False if not
     */
    public static boolean deleteTeacher(String status, String dischargeDate, int staffNumber) {
        TeacherDAOImpl deleteTeacher = new TeacherDAOImpl();
        boolean result = deleteTeacher.deleteTeacher(status, dischargeDate,staffNumber);
        return result;
    }

    /**
     * Method that calls the TeacherDAO to know the number of active teachers
     * @return Number of active teachers
     */
    public static int activeTeachers() {
        TeacherDAOImpl activeTeacher = new TeacherDAOImpl();
        int result = activeTeacher.activeTeachers();
        return result;
    }

    /**
     * Method that calls the TeacherDAO to know if that teacher is coordinator
     * @param teacher Object
     * @return True if is Coordinator, false if not
     */
    public static boolean isCoordinator(Teacher teacher) {
        TeacherDAOImpl isCoordinator = new TeacherDAOImpl();
        boolean result = isCoordinator.isCoordinator(teacher);
        return result;
    }
}