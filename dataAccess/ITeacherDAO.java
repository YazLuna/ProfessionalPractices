package dataAccess;

import java.util.List;
import java.sql.SQLException;

import domain.Teacher;
import domain.Teacher;

public interface ITeacherDAO {
    public Teacher getTeacher (int staffNumber) throws SQLException;
    public int updateTeacher (int staffNumber, Teacher teacher) throws SQLException;
    public int deleteTeacher (Teacher teacher) throws SQLException;
    public int addTeacher (Teacher teacher) throws SQLException;
    public List <Teacher> getAllTeacher() throws SQLException;
    public List <Teacher> getTeachersActive () throws SQLException;
    public int recoverTeacher(Teacher Teacher) throws  SQLException;
}