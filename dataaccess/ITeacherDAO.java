package dataaccess;

import java.util.List;
import domain.Teacher;

/**
 * Interface Teacher DAO
 * @author Yazmin
 * @version 07/07/2020
 */

public interface ITeacherDAO {
    boolean addTeacher (Teacher teacher) ;
    Teacher getTeacher(int staffNumber) ;
    List <Teacher> getTeachers();
    List <Teacher> getTeachersActive () ;
    List<Teacher> getTeachersInformation();
    boolean updateTeacher(int staffNumberOrigin, Teacher coordinatorEdit, List<String>DatesUpdate);
    boolean recoverTeacher(int staffNumber) ;
    boolean deleteTeacher(String status, String dischargeDate, int staffNumber) ;
    int activeTeachers() ;
    int areTeachers();
    boolean isCoordinator(Teacher teacher);
}