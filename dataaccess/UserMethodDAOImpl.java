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
 * @version 17/05/2020
 */

public class UserMethodDAOImpl implements IUserMethodDAO{
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    public UserMethodDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public boolean searchLoginAccount(String userName, String password) throws SQLException{
        boolean search = false;
        String userNameFound ;
        String passwordFound;
        try {
            connection = connexion.getConnection();
            String queryUserName =
                    "Select userName, password from LoginAccount where userName=? AND password=?";
            PreparedStatement sentence = connection.prepareStatement(queryUserName);
            sentence.setString(1, userName);
            sentence.setString(2, password);
            result = sentence.executeQuery();
            while (result.next()) {
                userNameFound = result.getString("userName");
                userNameFound = result.getString("password");
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
        return search;
    }

    @Override
    public int searchIdUser(String email, String alternateEmail, String phone) throws SQLException {
        int idUser = 0;
        try {
            connection = connexion.getConnection();
            String queryUser = "Select idUser from User where email=? AND alternateEmail=? AND phone=?";
            PreparedStatement sentence = connection.prepareStatement(queryUser);
            sentence.setString(1, email);
            sentence.setString(2, alternateEmail);
            sentence.setString(3, phone);
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

    @Override
    public boolean addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender, String userName) throws SQLException {
        boolean validate;
        boolean result = false;
        validate = validateUser(email, alternateEmail, phone,userName);
        if(validate){
            try {
                connection = connexion.getConnection();
                String queryAddUser = "INSERT INTO User  (name, lastName, gender, email,  alternateEmail" +
                        ", phone)  VALUES (?,?, ?, ?, ?,?)";
                PreparedStatement sentenceAddUser = connection.prepareStatement(queryAddUser);
                sentenceAddUser.setString(1, name);
                sentenceAddUser.setString(2, lastName);
                sentenceAddUser.setInt(3, gender);
                sentenceAddUser.setString(4, email);
                sentenceAddUser.setString(5, alternateEmail);
                sentenceAddUser.setString(6, phone);
                sentenceAddUser.executeUpdate();
                addRelations(email,alternateEmail,phone,status,userType,userName,password);
                result = true;
            } catch (SQLException ex) {
                Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public String searchUserType(int idUser) throws SQLException{
        String userType= null;
        try {
            connection = connexion.getConnection();
            String queryUserType = "Select type from UserType INNER JOIN User_UserType where User_UserType.idUser =?";
            PreparedStatement sentence = connection.prepareStatement(queryUserType);
            sentence.setInt(1, idUser);
            result = sentence.executeQuery();
            while (result.next()) {
                userType = result.getString("type");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return userType;
    }

    @Override
    public void addUserStatus(String status)  throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserStatus = "INSERT INTO UserStatus (status)  VALUES (?)";
            PreparedStatement sentenceAddUserStatus = connection.prepareStatement(queryAddUserStatus);
            sentenceAddUserStatus.setString(1, status);
            sentenceAddUserStatus.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
    }

    @Override
    public int searchIdUserStatus(String status)  throws SQLException{
        int idUserStatus = 0;
        try {
            connection = connexion.getConnection();
            String queryUserStatus = "Select idUserStatus from UserStatus where status =?";
            PreparedStatement sentence = connection.prepareStatement(queryUserStatus);
            sentence.setString(1, status);
            result = sentence.executeQuery();
            while (result.next()) {
                idUserStatus = result.getInt("idUserStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return idUserStatus;
    }

    @Override
    public int searchStaffNumberCoordinator(int staffNumberSearch)  throws SQLException {
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

    @Override
    public int searchStaffNumberTeacher(int staffNumberSearch)  throws SQLException {
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

    @Override
    public boolean validateUser(String email, String alternateEmail, String phone, String userName) throws SQLException{
        boolean emailSearch;
        boolean alternateEmailSearch;
        boolean phoneSearch;
        boolean userNameSearch;
        boolean result = false;
        emailSearch = searchEmail(email);
        if(!emailSearch){
            alternateEmailSearch = searchAlternateEmail(alternateEmail);
            if(!alternateEmailSearch){
                phoneSearch = searchPhone(phone);
                if(!phoneSearch){
                    userNameSearch = searchUserName(userName);
                    if(!userNameSearch){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int searchStaffNumber(int staffNumberSearch)  throws SQLException {
       int staffNumber;
       staffNumber = searchStaffNumberTeacher(staffNumberSearch);
       if(staffNumber == 0 ){
           staffNumber = searchStaffNumberCoordinator(staffNumberSearch);
       }
       return staffNumber;
    }

    private boolean searchAlternateEmail(String alternateEmail) throws SQLException {
        String alternateEmailFound= null;
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select alternateEmail from User where alternateEmail =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, alternateEmail);
            result = sentence.executeQuery();
            while (result.next()) {
                alternateEmailFound = result.getString("alternateEmail");
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return search;
    }

    private boolean searchPhone(String phone) throws SQLException {
        String phoneFound= null;
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select phone from User where phone =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, phone);
            result = sentence.executeQuery();
            while (result.next()) {
                phoneFound = result.getString("phone");
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return search;
    }

    private boolean searchEmail(String email) throws SQLException {
        String emailFound= null;
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select email from User where email =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, email);
            result = sentence.executeQuery();
            while (result.next()) {
                emailFound = result.getString("email");
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return search;
    }

    private void addRelations(String email, String alternateEmail, String phone, String status, String userType, String userName, String password) throws SQLException {
        int idUserAdd = 0;
        int idUserStatusSearch = 0;
        int idUserTypeSearch = 0;
        idUserAdd = searchIdUser(email,alternateEmail,phone);
        idUserStatusSearch = searchIdUserStatus(status);
        if(idUserStatusSearch==0){
            addUserStatus(status);
            idUserStatusSearch = searchIdUserStatus(status);
        }
        idUserTypeSearch = searchIdUserType(userType);
        if(idUserTypeSearch==0){
            addUserType(userType);
            idUserTypeSearch = searchIdUserType(userType);
        }
        addUserUserStatus(idUserAdd, idUserStatusSearch);
        addUserUserType(idUserAdd, idUserTypeSearch);
        createLoginAccount(userName,password,idUserAdd);
    }

    private void addUserUserType(int idUserAdd, int idUserTypeSearch) throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserUserType = "INSERT INTO User_UserType (idUser, idUserType)  VALUES (?,?)";
            PreparedStatement sentenceAddUserUserType = connection.prepareStatement(queryAddUserUserType);
            sentenceAddUserUserType.setInt(1, idUserAdd);
            sentenceAddUserUserType.setInt(2, idUserTypeSearch);
            sentenceAddUserUserType.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
    }

    private void addUserUserStatus(int idUserAdd, int idUserStatus) throws SQLException{
        try {
            connection = connexion.getConnection();
            String queryAddUserUserStatus = "INSERT INTO User_UserStatus (idUser, idUserStatus)  VALUES (?,?)";
            PreparedStatement sentenceAddUserUserStatus = connection.prepareStatement(queryAddUserUserStatus);
            sentenceAddUserUserStatus.setInt(1, idUserAdd);
            sentenceAddUserUserStatus.setInt(2, idUserStatus);
            sentenceAddUserUserStatus.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
    }

    private void createLoginAccount(String userName, String password, int idUser) throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserType = "INSERT INTO LoginAccount (userName,password,idUser)  VALUES (?,?,?)";
            PreparedStatement sentenceAddUserType = connection.prepareStatement(queryAddUserType);
            sentenceAddUserType.setString(1, userName);
            sentenceAddUserType.setString(2, password);
            sentenceAddUserType.setInt(3, idUser);
            sentenceAddUserType.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
    }

    private void addUserType(String userType)  throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserType = "INSERT INTO UserType (type)  VALUES (?)";
            PreparedStatement sentenceAddUserType = connection.prepareStatement(queryAddUserType);
            sentenceAddUserType.setString(1, userType);
            sentenceAddUserType.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
    }

    private int searchIdUserType(String userType)  throws SQLException{
        int idUserType = 0;
        try {
            connection = connexion.getConnection();
            String queryUserType = "Select idUserType from UserType where type =?";
            PreparedStatement sentence = connection.prepareStatement(queryUserType);
            sentence.setString(1, userType);
            result = sentence.executeQuery();
            while (result.next()) {
                idUserType = result.getInt("idUserType");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return idUserType;
    }

    private boolean searchUserName (String userNameSearch) throws SQLException{
        boolean search = false;
        String userNameFound = null;
        try {
            connection = connexion.getConnection();
            String queryUserName = "Select userName from LoginAccount where userName=?";
            PreparedStatement sentence = connection.prepareStatement(queryUserName);
            sentence.setString(1, userNameSearch);
            result = sentence.executeQuery();
            while (result.next()) {
                userNameFound = result.getString("userName");
            }
            if (userNameFound != null){
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
        return search;
    }

}
