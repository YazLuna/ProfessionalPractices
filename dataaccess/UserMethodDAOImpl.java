package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public class UserMethodDAOImpl implements IUserMethodDAO{
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    public UserMethodDAOImpl() {
        connexion = new Connexion();
    }

    public int searchAlternateEmail(String alternateEmail) throws SQLException {
        String alternateEmailFound= null;
        int search = 0;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select alternateEmail from User where alternateEmail =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, alternateEmail);
            result = sentence.executeQuery();
            while (result.next()) {
                alternateEmailFound = result.getString("alternateEmail");
            }
            if (alternateEmailFound != null){
                search = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return search;
    }

    public int searchPhone(String phone) throws SQLException {
        String phoneFound= null;
        int search = 0;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select phone from User where phone =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, phone);
            result = sentence.executeQuery();
            while (result.next()) {
                phoneFound = result.getString("phone");
            }
            if (phoneFound != null){
                search = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return search;
    }

    public int searchEmail(String email) throws SQLException {
        String emailFound= null;
        int search = 0;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select email from User where email =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, email);
            result = sentence.executeQuery();
            while (result.next()) {
                emailFound = result.getString("email");
            }
            if (emailFound != null){
                search = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return search;
    }

    public void addUserUserTypeStatus(int idUser, int idUserType) throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserTypeStatus = "INSERT INTO User_UserTypeStatus (idUser, idUserTypeStatus) VALUES (?,?)";
            PreparedStatement sentenceAddUserTypeStatus = connection.prepareStatement(queryAddUserTypeStatus);
            sentenceAddUserTypeStatus.setInt(1,idUser);
            sentenceAddUserTypeStatus.setInt(2,idUserType);
            sentenceAddUserTypeStatus.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
    }

    public int searchIdUserType(String userType, String status)  throws SQLException{
        int idUserType = 0;
        try {
            connection = connexion.getConnection();
            String queryUserType = "Select idUserTypeStatus from UserTypeStatus where type =? AND status=?";
            PreparedStatement sentence = connection.prepareStatement(queryUserType);
            sentence.setString(1, userType);
            sentence.setString(2,status);
            result = sentence.executeQuery();
            while (result.next()) {
                idUserType = result.getInt("idUserTypeStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return idUserType;
    }

    public int searchIdUser(String name, String lastName, String email, String alternateEmail, String phone) throws SQLException {
        int idUser = 0;
        try {
            connection = connexion.getConnection();
            String queryUser = "Select idUser from User where name=? AND lastName=? AND email=? AND alternateEmail=? AND phone=?";
            PreparedStatement sentence = connection.prepareStatement(queryUser);
            sentence.setString(1, name);
            sentence.setString(2, lastName);
            sentence.setString(3, email);
            sentence.setString(4, alternateEmail);
            sentence.setString(5, phone);
            result = sentence.executeQuery();
            while (result.next()) {
                idUser = result.getInt("idUser");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return idUser;
    }

    public int addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender) throws SQLException {
        int result = 0;
        int idUserTypeSearch;
        idUserTypeSearch= searchIdUserType(userType, status);
        if(idUserTypeSearch == 0){
            addUserType(userType, status);
            idUserTypeSearch = searchIdUserType(userType, status);
        }
        if(idUserTypeSearch > 0){
            try {
                connection = connexion.getConnection();
                String queryAddUser = "INSERT INTO User  (name, lastName, gender, email,  alternateEmail" +
                        ", phone,password)  VALUES (?,?, ?, ?, ?, ?,?)";
                PreparedStatement sentenceAddUser = connection.prepareStatement(queryAddUser);
                sentenceAddUser.setString(1, name);
                sentenceAddUser.setString(2, lastName);
                sentenceAddUser.setInt(3, gender);
                sentenceAddUser.setString(4, email);
                sentenceAddUser.setString(5, alternateEmail);
                sentenceAddUser.setString(6, phone);
                sentenceAddUser.setString(7, password);
                sentenceAddUser.executeUpdate();
                result = 1;
            } catch (SQLException ex) {
                Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                connection.close();
            }
        }
        return result;
    }

    public void addUserType(String userType, String status)  throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserType = "INSERT INTO UserTypeStatus (type,status)  VALUES (?,?)";
            PreparedStatement sentenceAddUserType = connection.prepareStatement(queryAddUserType);
            sentenceAddUserType.setString(1, userType);
            sentenceAddUserType.setString(2, status);
            sentenceAddUserType.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
    }

    public int searchStaffNumber(int staffNumberSearch)  throws SQLException {
        int staffNumber = 0;
        try {
            connection = connexion.getConnection();
            String queryStaffNumber =
                    "Select staffNumber from Coordinator where staffNumber=?";
            PreparedStatement sentence = connection.prepareStatement(queryStaffNumber);
            sentence.setInt(1, staffNumberSearch);
            result = sentence.executeQuery();
            while (result.next()) {
                staffNumber = result.getInt("staffNumber");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
        return staffNumber;
    }

    public int searchStaffNumberTeacher(int staffNumberSearch) throws SQLException {
        int staffNumber = 0;
        try {
            connection = connexion.getConnection();
            String queryStaffNumber =
                    "Select staffNumber from Teacher where staffNumber=?";
            PreparedStatement sentence = connection.prepareStatement(queryStaffNumber);
            sentence.setInt(1, staffNumberSearch);
            result = sentence.executeQuery();
            while (result.next()) {
                staffNumber = result.getInt("staffNumber");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
        return staffNumber;
    }

}
