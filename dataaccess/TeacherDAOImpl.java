package dataaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import domain.Teacher;
import domain.Search;

/**
 * TeacherDAOImpl
 * @author Yazmin
 * @version 07/06/2020
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
    public Teacher getTeacherSelected(int staffNumber) {
        Teacher teacher = new Teacher();
        try {
            connection = connexion.getConnection();
            String queryGetTeacherSelected =
                    "SELECT name,lastName,gender,email,alternateEmail,phone,profilePicture,staffNumber,status from Teacher, User, UserType,Status,User_Status WHERE " +
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
    public boolean updateTeacher(int staffNumber, Teacher teacherEdit)  {
        boolean result = false;
        int staffNumberFound = searchStaffNumber(teacherEdit.getStaffNumber());
        if(staffNumberFound == Search.NOTFOUND.getValue() ){
            boolean validate = validateUserAdd(teacherEdit.getEmail(),teacherEdit.getAlternateEmail(),
                    teacherEdit.getPhone(),teacherEdit.getUserName());
            if(validate){
                try {
                    connection = connexion.getConnection();
                    String queryTeacherUpdate = "UPDATE Teacher INNER JOIN User ON Teacher.idUser =" +
                            " User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?" +
                            ", User.alternateEmail = ?, User.phone = ?, User.profilePicture=?, Teacher.staffNumber = ?  WHERE Teacher.staffNumber = ?";
                    preparedStatement = connection.prepareStatement(queryTeacherUpdate);
                    preparedStatement.setString(1, teacherEdit.getName());
                    preparedStatement.setString(2, teacherEdit.getLastName());
                    preparedStatement.setInt(3, teacherEdit.getGender());
                    preparedStatement.setString(4, teacherEdit.getEmail());
                    preparedStatement.setString(5, teacherEdit.getAlternateEmail());
                    preparedStatement.setString(6, teacherEdit.getPhone());
                    if(teacherEdit.getProfilePicture() != null){
                        FileInputStream convertImage = new FileInputStream (teacherEdit.getProfilePicture());
                        preparedStatement.setBinaryStream(7,convertImage,teacherEdit.getProfilePicture().length());
                    }else{
                        preparedStatement.setBinaryStream(7,null);
                    }
                    preparedStatement.setInt(8, teacherEdit.getStaffNumber());
                    preparedStatement.setInt(9, staffNumber);
                    preparedStatement.executeUpdate();
                    result = true;
                } catch (SQLException | FileNotFoundException ex) {
                    Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    connexion.closeConnection();
                }
            }
        }
        return result;
    }

    @Override
    public boolean deleteTeacher(String status, String dischargeDate, int staffNumber) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(status);
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDao.addStatus(status);
            idUserStatus = statusDao.searchIdStatus(status);
        }
        try {
            connection = connexion.getConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE Teacher, User, User_Status SET User_Status.idStatus=?, Teacher.dischargeDate=? WHERE" +
                            " Teacher.idUser = User.idUser AND User_Status.idUser = User.idUser AND Teacher.staffNumber=?");
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, dischargeDate);
            preparedStatement.setInt(3,staffNumber);
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
    public boolean addTeacher(Teacher teacher) {
        boolean resultAdd = false;
        boolean validate = validateTeacher(teacher);
        if(validate){
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

    private boolean validateTeacher(Teacher teacher) {
        boolean validation = false;
        int activeTeacher= teacher.activeTeacher();
        if(activeTeacher <= Search.FOUND.getValue()){
            boolean validationStaffNumber = validateStaffNumber(teacher.getStaffNumber());
            if(!validationStaffNumber){
                boolean isTeacher = isTeacher(teacher.getStaffNumber());
                if(isTeacher){
                    validation = true;
                }
            }
            if(validationStaffNumber) {
                boolean addUserValidate = addUser(teacher.getName(), teacher.getLastName(), teacher.getEmail(), teacher.getAlternateEmail(),
                        teacher.getPhone(), teacher.getPassword(), teacher.getUserType(),
                        teacher.getStatus(), teacher.getGender(), teacher.getUserName(),teacher.getProfilePicture());
                if(addUserValidate){
                    validation = true;
                }
            }
        }
        return validation;
    }

    private boolean isTeacher(int staffNumber) {
        boolean resultSet = false;
        int staffNumberFound = searchStaffNumberTeacher(staffNumber);
        if(staffNumberFound != Search.NOTFOUND.getValue() ){
            resultSet = true;
        }
        return resultSet;
    }

    private boolean validateStaffNumber(int staffNumber) {
        boolean resultSet = false;
        int staffNumberFound = searchStaffNumberTeacher(staffNumber);
        if(staffNumberFound == Search.NOTFOUND.getValue()){
            resultSet = true;
        }
        return resultSet;
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
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDAO.addStatus("Active");
            idUserStatus = statusDAO.searchIdStatus("Active");
        }
        try {
            connection = connexion.getConnection();
            String queryTeacherActive = "Select name, lastName,staffNumber from Teacher,User, User_Status WHERE" +
                    " Teacher.idUser = User.idUser AND User_Status.idStatus=? AND User_Status.idUser=User.idUser";
            preparedStatement =connection.prepareStatement(queryTeacherActive);
            preparedStatement.setInt(1,idUserStatus);
            resultSet = preparedStatement.executeQuery();
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
    public List<Teacher> getInformationAllTeacher() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("Select name, lastName,staffNumber,email,alternateEmail,phone,status FROM " +
                    "Teacher,User, User_Status,Status WHERE Teacher.idUser = User.idUser AND User_Status.idUser=" +
                    "User.idUser AND User_Status.idStatus= Status.idStatus;");
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
    public boolean recoverTeacher(Teacher teacher) {
        boolean result = false;
        int activeTeachers = teacher.activeTeacher();
        if(activeTeachers <= Search.FOUND.getValue()){
            StatusDAOImpl statusDao = new StatusDAOImpl();
            int idUserStatus = statusDao.searchIdStatus(teacher.getStatus());
            if(idUserStatus == Search.NOTFOUND.getValue() ){
                statusDao.addStatus(teacher.getStatus());
                idUserStatus = statusDao.searchIdStatus(teacher.getStatus());
            }
            try {
                connection = connexion.getConnection();
                preparedStatement =
                        connection.prepareStatement("UPDATE Teacher, User, User_Status SET User_Status.idStatus=?" +
                                ", Teacher.dischargeDate=? WHERE Teacher.idUser = User.idUser AND User_Status.idUser =" +
                                " User.idUser AND Teacher.staffNumber =? ");
                preparedStatement.setInt(1, idUserStatus);
                preparedStatement.setString(2, null);
                preparedStatement.setInt(3, teacher.getStaffNumber());
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
    public int activeTeacher() {
        int isActive = Search.NOTFOUND.getValue();
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDAO.addStatus("Active");
            idUserStatus = statusDAO.searchIdStatus("Active");
        }
        try {
            connection = connexion.getConnection();
            String queryActiveTeacher =
                    "SELECT staffNumber from Teacher, User, UserType, User_Status WHERE Teacher.idUser= User.idUser AND" +
                            " User_Status.idUser = User.idUser AND UserType.type='Teacher' AND User_Status.idStatus=?";
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