package dataaccess;

import java.util.List;
import domain.Teacher;

/**
 * Interface DAO Teacher
 * @author Yazmin
 * @version 06/06/2020
 */

public interface ITeacherDAO {
    Teacher getTeacherSelected (int staffNumber) ;
    boolean updateTeacher (int staffNumber, Teacher teacher);
    boolean deleteTeacher(String status, String dischargeDate,int staffNumber) ;
    boolean addTeacher (Teacher teacher) ;
    List <Teacher> getAllTeacher();
    List <Teacher> getTeachersActive () ;
    boolean recoverTeacher(Teacher Teacher) ;
    List<Teacher> getInformationAllTeacher();
    int activeTeacher() ;
}