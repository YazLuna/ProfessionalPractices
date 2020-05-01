package dataAccess;

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

public class TeacherDAOImpl implements ITeacherDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consult;
    private ResultSet result;

    public TeacherDAOImpl() {
        connexion = new Connexion ();
    }

    @Override
    public Teacher getTeacher (int staffNumber) throws SQLException {
        Teacher Teacher = new Teacher ();
        try {
            connection = connexion.getConnection () ;
            String queryFoundTeacher = "Select * from Teacher INNER JOIN user ON Teacher.idUser = user.idUser where Teacher.staffNumber = ?";
            PreparedStatement sentence = connection.prepareStatement (queryFoundTeacher);
            sentence.setInt (1,staffNumber);
            result = sentence.executeQuery();
            while (result.next()){
                Teacher.setName(result.getString("name"));
                Teacher.setLastName(result.getString("lastName"));
                Teacher.setGender(result.getString("gender"));
                Teacher.setEmail(result.getString("email"));
                Teacher.setAlternateEmail(result.getString("alternateEmail"));
                Teacher.setPhone(result.getString("phone"));
                Teacher.setStaffNumber(result.getInt("staffNumber"));
                Teacher.setTurn(result.getString("turn"));
                Teacher.setRegistrationDate(result.getString("registrationDate"));
            }
        }catch(SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Teacher;
    }

    @Override
    public int updateTeacher (int staffNumber, Teacher TeacherEdit) throws SQLException {
        int result = 0;
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceUpdateTeacher = connection.prepareStatement ("UPDATE Teacher INNER JOIN user ON Teacher.idUser = user.idUser SET user.name = ?, user.lastName = ?, user.gender = ?, user.email = ?,"
                    + " user.alternateEmail = ?, user.phone = ?, Teacher.staffNumber = ?, Teacher.turn = ?, Teacher.registrationDate = ?  WHERE Teacher.staffNumber = ?");
            sentenceUpdateTeacher.setString(1, TeacherEdit.getName());
            sentenceUpdateTeacher.setString(2, TeacherEdit.getLastName());
            sentenceUpdateTeacher.setString(3, TeacherEdit.getGender());
            sentenceUpdateTeacher.setString(4, TeacherEdit.getEmail());
            sentenceUpdateTeacher.setString(5, TeacherEdit.getAlternateEmail());
            sentenceUpdateTeacher.setString(6, TeacherEdit.getPhone());
            sentenceUpdateTeacher.setInt(7, TeacherEdit.getStaffNumber());
            sentenceUpdateTeacher.setString(8, TeacherEdit.getTurn());
            sentenceUpdateTeacher.setString(9, TeacherEdit.getRegistrationDate());
            sentenceUpdateTeacher.setInt(10, staffNumber);
            sentenceUpdateTeacher.executeUpdate();
            result = 1;
        }catch(SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int recoverTeacher (Teacher TeacherEdit) throws SQLException {
        int result = 0;
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceRecoverTeacher = connection.prepareStatement ("UPDATE Teacher INNER JOIN user ON Teacher.idUser = user.idUser SET user.status = 'Active' WHERE Teacher.staffNumber = ?");
            sentenceRecoverTeacher.setInt(1, TeacherEdit.getStaffNumber());
            sentenceRecoverTeacher.executeUpdate();
            result = 1;
        }catch(SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteTeacher (Teacher Teacher) throws SQLException {
        int result = 0;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceDeleteTeacher=connection.prepareStatement("UPDATE Teacher INNER JOIN User ON Teacher.idUser = user.idUser SET status = 'Inactive' WHERE Teacher.staffNumber=?");
            sentenceDeleteTeacher.setInt(1,Teacher.getStaffNumber());
            sentenceDeleteTeacher.executeUpdate();
            result = 1;
        }catch(SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int addTeacher (Teacher teacher) throws SQLException {
        int resultAdd = 0;
        int staffNumber = searchStaffNumber(teacher.getStaffNumber());
        if (staffNumber == 0){
            int userAdd= addUser(teacher);
            if (userAdd == 1) {
                int idUser = searchIdUser(teacher);
                try{
                    connection = connexion.getConnection();
                    String queryAddTeacher = "INSERT INTO Teacher  (staffNumber, turn, registrationDate, idUser) VALUES (?, ?, ?, ?)";
                    PreparedStatement sentenceAdd = connection.prepareStatement(queryAddTeacher);
                    sentenceAdd.setInt(1, teacher.getStaffNumber());
                    sentenceAdd.setString(2, teacher.getTurn());
                    sentenceAdd.setString(3, teacher.getRegistrationDate());
                    sentenceAdd.setInt(4, idUser);
                    sentenceAdd.executeUpdate();
                    resultAdd = 1;
                }catch(SQLException ex){
                    Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    if(connexion!=null){
                        connexion.closeConnection();
                    }
                }
            }
        }
        return resultAdd;
    }

    private int searchStaffNumber(int staffNumberSearch) {
        int staffNumber = 0;
        int resultAdd = 0;
        try{
            connection = connexion.getConnection();
            String queryStaffNumber= "Select staffNumber from Teacher where staffNumber=?";
            PreparedStatement sentence =connection.prepareStatement(queryStaffNumber);
            sentence.setInt(1, staffNumberSearch);
            result= sentence.executeQuery();
            while(result.next()){
                staffNumber =result.getInt("staffNumber");
            }
            if(staffNumber != 0){
                resultAdd = 1;
            }
        }catch(SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return resultAdd;
    }

    public int addUser(Teacher teacher){
        int result = 0;
        try{
            connection = connexion.getConnection();
            String queryAddTeacherUser = "INSERT INTO user  (name, lastName, gender, status, email,  alternateEmail, phone)  VALUES (?,?, ?, ?, ?, ?, ?)";
            PreparedStatement sentenceAddUser = connection.prepareStatement(queryAddTeacherUser);
            sentenceAddUser.setString(1, teacher.getName());
            sentenceAddUser.setString(2, teacher.getLastName());
            sentenceAddUser.setString(3, teacher.getGender());
            sentenceAddUser.setString(4, teacher.getStatus());
            sentenceAddUser.setString(5, teacher.getEmail());
            sentenceAddUser.setString(6, teacher.getAlternateEmail());
            sentenceAddUser.setString(7, teacher.getPhone());
            sentenceAddUser.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return result;
    }

    public int searchIdUser (Teacher teacher){
        int idUser = 0;
        try{
            connection = connexion.getConnection();
            String queryUser= "Select idUser from user where name=? AND lastName=? AND email=? AND alternateEmail=? AND phone=?";
            PreparedStatement sentence =connection.prepareStatement(queryUser);
            sentence.setString(1, teacher.getName());
            sentence.setString(2, teacher.getLastName());
            sentence.setString(3, teacher.getEmail());
            sentence.setString(4, teacher.getAlternateEmail());
            sentence.setString(5, teacher.getPhone());
            result= sentence.executeQuery();
            while(result.next()){
                idUser =result.getInt("idUser");
            }
        }catch(SQLException ex){
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connexion!=null){
                connexion.closeConnection();
            }
        }
        return idUser;
    }

    @Override
    public List <Teacher> getAllTeacher () throws SQLException{
        List<Teacher> teachers = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consult = connection.createStatement();
            result = consult.executeQuery("Select * from Teacher INNER JOIN user ON Teacher.idUser = user.idUser");
            while(result.next()){
                Teacher teacher = new Teacher();
                teacher.setName(result.getString("name"));
                teacher.setLastName(result.getString("lastName"));
                teacher.setGender(result.getString("gender"));
                teacher.setEmail(result.getString("email"));
                teacher.setAlternateEmail (result.getString("alternateEmail"));
                teacher.setPhone(result.getString("phone"));
                teacher.setStaffNumber(result.getInt("staffNumber"));
                teacher.setTurn(result.getString("turn"));
                teacher.setRegistrationDate(result.getString("registrationDate"));
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }

    @Override
    public List <Teacher> getTeachersActive () throws SQLException{
        List<Teacher> teachers = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consult = connection.createStatement();
            result = consult.executeQuery("Select * from Teacher INNER JOIN user ON Teacher.idUser = user.idUser WHERE user.status = 'Active'");
            while(result.next()){
                Teacher teacher = new Teacher();
                teacher.setName(result.getString("name"));
                teacher.setLastName(result.getString("lastName"));
                teacher.setGender(result.getString("gender"));
                teacher.setEmail(result.getString("email"));
                teacher.setAlternateEmail (result.getString("alternateEmail"));
                teacher.setPhone(result.getString("phone"));
                teacher.setStaffNumber(result.getInt("staffNumber"));
                teacher.setTurn(result.getString("turn"));
                teacher.setRegistrationDate(result.getString("registrationDate"));
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }
}