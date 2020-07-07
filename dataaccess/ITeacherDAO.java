package dataaccess;

import java.util.List;

import domain.Coordinator;
import domain.Teacher;

/**
 * Interface DAO Teacher
 * @author Yazmin
 * @version 06/06/2020
 */

public interface ITeacherDAO {
    Teacher getTeacher(int staffNumber) ;
    boolean updateTeacher(int staffNumberOrigin, Teacher coordinatorEdit
            , List<String>DatesUpdate);
    boolean deleteTeacher(String status, String dischargeDate,int staffNumber) ;
    boolean addTeacher (Teacher teacher) ;
    List <Teacher> getAllTeacher();
    List <Teacher> getTeachersActive () ;
    boolean recoverTeacher(Teacher Teacher) ;
    List<Teacher> getInformationAllTeacher();
    int activeTeachers() ;
}