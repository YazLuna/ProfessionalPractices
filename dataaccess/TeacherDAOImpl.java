package dataaccess;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import domain.Number;
import domain.Teacher;
import domain.Search;

/**
 * TeacherDAOImpl
 * @author Yazmin
 * @version 05/07/2020
 */

public class TeacherDAOImpl extends UserMethodDAOImpl implements ITeacherDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public TeacherDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        boolean resultAdd = false;
        boolean validate = staffNumberTwoAcademicsValidate(teacher.getStaffNumber());
        boolean addUserValid;
        if(validate) {
            addUserValid = addUser(teacher.getName(), teacher.getLastName(), teacher.getEmail(), teacher.getAlternateEmail(),
                    teacher.getPhone(), teacher.getPassword(), teacher.getUserType(),
                    teacher.getStatus(), teacher.getGender(), teacher.getUserName());
        } else{
            addUserValid = isCoordinator(teacher);
        }
        if(addUserValid){
            int idUser = searchIdUser(teacher.getEmail(),teacher.getAlternateEmail(),teacher.getPhone());
            try {
                connection = connexion.getConnection();
                String queryAddTeacher = "INSERT INTO Teacher (staffNumber, registrationDate, idUser) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(queryAddTeacher);
                preparedStatement.setInt(1, teacher.getStaffNumber());
                preparedStatement.setString(2, teacher.getRegistrationDate());
                preparedStatement.setInt(3, idUser);
                preparedStatement.executeUpdate();
                resultAdd = true;
            } catch (SQLException ex) {
                Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connexion.closeConnection();
            }
        }
        return resultAdd;
    }

    private boolean isCoordinator(Teacher teacher) {
        boolean isCoordinator = searchUserAcademic(teacher.getName(), teacher.getLastName(), teacher.getEmail()
                , teacher.getAlternateEmail(), teacher.getPhone(), teacher.getGender());
        return isCoordinator;
    }

    @Override
    public boolean updateTeacher(int staffNumberOrigin, Teacher teacherEdit, List<String>DatesUpdate) {
        boolean result = false;
        String datesUpdate = DatesUpdate.get(0)+ "= ?, ";
        List<String> Change = new ArrayList<>();
        Change.add("get"+DatesUpdate.get(0));
        for (int indexDatesUpdate = 1 ; indexDatesUpdate < DatesUpdate.size();  indexDatesUpdate ++) {
            if ( indexDatesUpdate == DatesUpdate.size() -1){
                datesUpdate = datesUpdate + DatesUpdate.get(indexDatesUpdate)  + "= ?";
            } else {
                datesUpdate = datesUpdate + DatesUpdate.get( indexDatesUpdate)  + "= ?,";
            }
            Change.add("get"+DatesUpdate.get( indexDatesUpdate));
        }
        String sentence = "UPDATE Teacher INNER JOIN User ON Teacher.idUser = User.idUser SET " +datesUpdate + " WHERE Teacher.staffNumber = "+staffNumberOrigin;
        try{
            connection = connexion.getConnection();
            preparedStatement = connection.prepareStatement(sentence);
            Class classTeacher = teacherEdit.getClass();
            for(int indexPreparedStatement = 1 ; indexPreparedStatement <= DatesUpdate.size(); indexPreparedStatement++){
                Method methodTeacher;
                boolean isString = true;
                try {
                    methodTeacher = classTeacher.getMethod(Change.get(indexPreparedStatement - 1));
                    String isWord = (String) methodTeacher.invoke(teacherEdit, new Object[] {});
                } catch (ClassCastException e) {
                    isString = false;
                }
                if(isString){
                    methodTeacher = classTeacher.getMethod(Change.get(indexPreparedStatement - 1));
                    String word = (String) methodTeacher.invoke(teacherEdit, new Object[] {});
                    preparedStatement.setString(indexPreparedStatement,word);
                } else{
                    methodTeacher = classTeacher.getMethod(Change.get(indexPreparedStatement - 1));
                    int integer = (int) methodTeacher.invoke(teacherEdit, new Object[] {});
                    preparedStatement.setInt(indexPreparedStatement, integer);
                }
            }
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    @Override
    public boolean deleteTeacher(String status, String dischargeDate, int staffNumber) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(status);
        int idUserType = searchIdUserType("Teacher");
        try {
            connection = connexion.getConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE Teacher INNER JOIN User_Status SET User_Status.idStatus=?, Teacher.dischargeDate = ? WHERE" +
                    " User_Status.idUser = Teacher.idUser AND Teacher.staffNumber =? AND User_Status.idUserType =?");
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, dischargeDate);
            preparedStatement.setInt(3,staffNumber);
            preparedStatement.setInt(4,idUserType);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    @Override
    public boolean recoverTeacher(Teacher teacher) {
        boolean result = false;
        int activeTeachers = teacher.activeTeacher();
        if(activeTeachers <= Search.FOUND.getValue()){
            StatusDAOImpl statusDao = new StatusDAOImpl();
            int idUserStatus = statusDao.searchIdStatus(teacher.getStatus());
            int idUserType = searchIdUserType("Teacher");
            try {
                connection = connexion.getConnection();
                preparedStatement =
                        connection.prepareStatement("UPDATE Teacher INNER JOIN User_Status SET User_Status.idStatus=?, Teacher.dischargeDate = ? WHERE " +
                                "User_Status.idUser = Teacher.idUser AND Teacher.staffNumber =? AND User_Status.idUserType =?;");
                preparedStatement.setInt(1, idUserStatus);
                preparedStatement.setString(2, null);
                preparedStatement.setInt(3,teacher.getStaffNumber());
                preparedStatement.setInt(4,idUserType);
                preparedStatement.executeUpdate();
                result = true;
            } catch (SQLException ex) {
                Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connexion.closeConnection();
            }
        }
        return result;
    }

    @Override
    public Teacher getTeacher(int staffNumber) {
        Teacher teacher = new Teacher();
        try {
            connection = connexion.getConnection();
            String queryGetTeacherSelected =
                    "SELECT name,lastName,gender,email,alternateEmail,phone,staffNumber,status from Teacher, User, UserType,Status,User_Status WHERE " +
                            "Teacher.idUser=User.idUser AND UserType.type='Teacher' AND Teacher.staffNumber=? AND Status.idStatus = User_Status.idStatus AND " +
                            "User_Status.idUser = User.idUser ";
            preparedStatement = connection.prepareStatement(queryGetTeacherSelected);
            preparedStatement.setInt(1,staffNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teacher.setName(resultSet.getString("name"));
                teacher.setLastName(resultSet.getString("lastName"));
                teacher.setGender(resultSet.getInt("gender"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setAlternateEmail(resultSet.getString("alternateEmail"));
                teacher.setPhone(resultSet.getString("phone"));
                teacher.setStaffNumber(resultSet.getInt("staffNumber"));
                teacher.setStatus(resultSet.getString("status"));
                //image
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return teacher;
    }

    @Override
    public List<Teacher> getAllTeacher() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("Select name, lastName,staffNumber from Teacher INNER JOIN User ON Teacher.idUser =" +
                    " User.idUser");
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setName(resultSet.getString("name"));
                teacher.setLastName(resultSet.getString("lastName"));
                teacher.setStaffNumber(resultSet.getInt("staffNumber"));
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return teachers;
    }

    @Override
    public List<Teacher> getTeachersActive() {
        List<Teacher> teachers = new ArrayList<>();
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        int idUserType = searchIdUserType("Teacher");
        try {
            connection = connexion.getConnection();
            String queryTeacherActive = "SELECT name, lastName,staffNumber, email FROM Teacher,User, User_Status WHERE Teacher.idUser = User.idUser AND " +
                    "User_Status.idStatus =? AND User_Status.idUser=User.idUser AND idUserType =?";
            preparedStatement =connection.prepareStatement(queryTeacherActive);
            preparedStatement.setInt(1,idUserStatus);
            preparedStatement.setInt(2,idUserType);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setName(resultSet.getString("name"));
                teacher.setLastName(resultSet.getString("lastName"));
                teacher.setStaffNumber(resultSet.getInt("staffNumber"));
                teacher.setEmail(resultSet.getString("email"));
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return teachers;
    }

    @Override
    public List<Teacher> getInformationAllTeacher() {
        List<Teacher> teachers = new ArrayList<>();
        int idUserType = searchIdUserType("Teacher");
        try {
            connection = connexion.getConnection();
            String queryGetAllInformation = "SELECT name, lastName,staffNumber,email,alternateEmail,phone,status FROM Teacher,User" +
                    ", User_Status,Status WHERE Teacher.idUser = User.idUser AND User_Status.idUser = User.idUser AND" +
                    " User_Status.idUser = User.idUser AND Status.idStatus = User_Status.idStatus AND idUserType =?";
            preparedStatement = connection.prepareStatement(queryGetAllInformation);
            preparedStatement.setInt(1, idUserType);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setName(resultSet.getString("name"));
                teacher.setLastName(resultSet.getString("lastName"));
                teacher.setStaffNumber(resultSet.getInt("staffNumber"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setAlternateEmail(resultSet.getString("alternateEmail"));
                teacher.setPhone(resultSet.getString("phone"));
                teacher.setStatus(resultSet.getString("status"));
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return teachers;
    }

    @Override
    public int activeTeachers() {
        int isActive = Search.NOTFOUND.getValue();
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        try {
            connection = connexion.getConnection();
            String queryActiveTeacher =
                    "SELECT staffNumber FROM Teacher, UserType, User_Status, User_UserType WHERE User_Status.idUser =" +
                            " Teacher.idUser AND UserType.type='Teacher' AND User_UserType.idUser = Teacher.idUser AND" +
                            " User_UserType.idUserType = UserType.idUserType AND User_Status.idUserType =" +
                            " UserType.idUserType AND User_Status.idStatus =?";
            preparedStatement = connection.prepareStatement(queryActiveTeacher);
            preparedStatement.setInt(1,idUserStatus);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isActive++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return isActive;
    }

}