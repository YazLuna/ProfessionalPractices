package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Search;
import exception.Exception;

/**
 * LoginAccountDAO Implements
 * @author Yazmin
 * @version 04/06/2020
 */

public class LoginAccountDAOImpl implements ILoginAccountDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;
    private PreparedStatement sentence;

    /**
     * Constructor of LoginAccountDAOImpl
     */
    public LoginAccountDAOImpl() {
        connexion = new Connexion();
    }

    /**
     * Method that creates a login account associated with a user
     * @param userName from User
     * @param password from User
     * @param idUser from User
     * @return true id create, false if not
     */
    @Override
    public boolean createLoginAccount(String userName, String password, int idUser) {
        boolean result = false;
        try {
            connection = connexion.getConnection();
            String queryCreateLoginAccount = "INSERT INTO LoginAccount (userName, password, idUser, firstLogin)  VALUES (?,?,?,?)";
            sentence = connection.prepareStatement(queryCreateLoginAccount);
            sentence.setString(1, userName);
            sentence.setString(2, password);
            sentence.setInt(3, idUser);
            sentence.setInt(4,Search.NOTFOUND.getValue());
            sentence.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return result;
    }

    /**
     * Method that searches if it is the first login of that account
     * @param userName from User
     * @param password from User
     * @return True if it is your first login or false if not
     */
    @Override
    public boolean firstLogin(String userName, String password) {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String queryFirstLogin = "SELECT userName, password, status FROM LoginAccount, Status WHERE LoginAccount.userName =? AND" +
                            " LoginAccount.password =? AND Status.status =? AND LoginAccount.firstLogin =?";
            sentence = connection.prepareStatement(queryFirstLogin);
            sentence.setString(1, userName);
            sentence.setString(2, password);
            sentence.setString(3,"Active");
            sentence.setInt(4, Search.NOTFOUND.getValue());
            result = sentence.executeQuery();
            while (result.next()) {
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginAccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return search;
    }

    /**
     * Method that searches for user roles
     * @param user UserName from User
     * @param password from User
     * @return Role list
     */
    @Override
    public List<String> searchUserTypeWithLoginAccount(String user, String password) {
        List<String> userType = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            String queryUserType = "SELECT type FROM UserType, LoginAccount, User_UserType, User_Status, Status WHERE" +
                    " User_UserType.idUserType = UserType.idUserType AND User_UserType.idUser = LoginAccount.idUser AND" +
                    " User_Status.idUser = LoginAccount.idUser AND User_Status.idStatus = Status.idStatus AND" +
                    " Status.status = ? AND User_Status.idUserType = User_UserType.idUserType AND" +
                    " LoginAccount.userName =? AND LoginAccount.password =?";
            sentence = connection.prepareStatement(queryUserType);
            sentence.setString(1, "Active");
            sentence.setString(2, user);
            sentence.setString(3,password);
            result = sentence.executeQuery();
            while (result.next()) {
                userType.add(result.getString("type"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginAccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return userType;
    }

    /**
     * Method to Search if this account exists and is active
     * @param userName from User
     * @param password from User
     * @return True if found and false if not
     */
    @Override
    public boolean searchLoginAccount(String userName, String password) {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearchLoginAccount = "SELECT userName, password, status FROM LoginAccount INNER JOIN Status WHERE" +
                    " userName = ? AND password = ? AND status =?";
            sentence = connection.prepareStatement(querySearchLoginAccount);
            sentence.setString(1, userName);
            sentence.setString(2, password);
            sentence.setString(3,"Active");
            result = sentence.executeQuery();
            while (result.next()) {
                search = true;
            }
        } catch (SQLException ex) {
            new Exception().log(ex);
            Logger.getLogger(LoginAccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return search;
    }

    /**
     * Method to modify the login account when the teacher or coordinator first login
     * @param userNamePrevious assigned when registered
     * @param passwordPrevious assigned when registered
     * @param passwordNew assigned by the user who owns the account
     * @param userNameNew assigned by the user who owns the account
     * @return True if it could be modified, false if not
     */
    @Override
    public boolean updateLoginAccount(String userNamePrevious, String passwordPrevious, String passwordNew, String userNameNew){
        boolean update = false;
        boolean validUserName = validateUserName(userNameNew);
        try {
            if(validUserName){
                connection = connexion.getConnection();
                String queryUpdateLoginAccount = "UPDATE LoginAccount SET userName =?, password =?, firstLogin =? WHERE userName =? AND" +
                        " password =?";
                sentence = connection.prepareStatement(queryUpdateLoginAccount);
                sentence.setString(1, userNameNew);
                sentence.setString(2, passwordNew);
                sentence.setInt(3,Search.FOUND.getValue());
                sentence.setString(4,userNamePrevious);
                sentence.setString(5,passwordPrevious);
                sentence.executeUpdate();
                update=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginAccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return update;
    }

    /**
     * Method to modify the login account when the practitioner first login
     * @param userNamePrevious assigned when registered
     * @param passwordPrevious assigned when registered
     * @param passwordNew assigned by the user who owns the account
     * @return rue if it could be modified, false if not
     */
    @Override
    public boolean updateLoginAccountPractitioner(String userNamePrevious, String passwordPrevious, String passwordNew){
        boolean update = false;
        try {
            connection = connexion.getConnection();
            String queryUpdateLoginAccount = "UPDATE LoginAccount SET password =?, firstLogin =? WHERE userName =? AND password =?";
            sentence = connection.prepareStatement(queryUpdateLoginAccount);
            sentence.setString(1, passwordNew);
            sentence.setInt(2,Search.FOUND.getValue());
            sentence.setString(3,userNamePrevious);
            sentence.setString(4,passwordPrevious);
            sentence.executeUpdate();
            update=true;
        } catch (SQLException ex) {
            Logger.getLogger(LoginAccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return update;
    }

    /**
     * Method that seeks that the username to be assigned is not repeated
     * @param userName Username to be assigned
     * @return True if the name is available, false if not
     */
    @Override
    public boolean validateUserName(String userName){
        boolean search = true;
        try {
            connection = connexion.getConnection();
            String queryUserName = "SELECT userName FROM LoginAccount where userName =?";
            sentence = connection.prepareStatement(queryUserName);
            sentence.setString(1, userName);
            result = sentence.executeQuery();
            while (result.next()) {
                search = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginAccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return search;
    }

}
