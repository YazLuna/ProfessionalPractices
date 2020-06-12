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

/**
 * LoginAccountDAOImpl
 * @author Yazmin
 * @version 04/06/2020
 */

public class LoginAccountDAOImpl implements ILoginAccountDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;
    private PreparedStatement sentence;

    public LoginAccountDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public List<String> searchUserTypeWithLoginAccount(String user, String password) {
        List<String> type = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            String queryUserType = "Select type from UserType,LoginAccount,User_UserType where User_UserType.idUserType=UserType.idUserType AND" +
                    " User_UserType.idUser=LoginAccount.idUser AND LoginAccount.userName=? AND LoginAccount.password=?";
            sentence = connection.prepareStatement(queryUserType);
            sentence.setString(1, user);
            sentence.setString(2,password);
            result = sentence.executeQuery();
            while (result.next()) {
                type.add(result.getString("type"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginAccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return type;
    }

    @Override
    public boolean firstLogin(String userName, String password) {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String queryFirstLogin =
                    "Select userName, password, status from LoginAccount, Status where LoginAccount.userName=? AND" +
                            " LoginAccount.password=? AND Status.status=? AND LoginAccount.firstLogin=?";
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

    @Override
    public boolean searchLoginAccount(String userName, String password) {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearchLoginAccount = "Select userName, password, status from LoginAccount, Status where LoginAccount.userName=? AND " +
                            "LoginAccount.password=? AND Status.status=?";
            sentence = connection.prepareStatement(querySearchLoginAccount);
            sentence.setString(1, userName);
            sentence.setString(2, password);
            sentence.setString(3,"Active");
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

    @Override
    public boolean updateLoginAccount(String userNamePrevious, String passwordPrevious, String passwordNew, String userNameNew){
        boolean update = false;
        boolean userExist = searchUserName(userNameNew);
        try {
            if(!userExist){
                connection = connexion.getConnection();
                String queryUpdateLoginAccount=
                        "update LoginAccount SET userName=?, password=?, firstLogin=? where userName=? AND password=? ";
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

    @Override
    public boolean updateLoginAccountPractitioner(String userNamePrevious, String passwordPrevious, String passwordNew){
        boolean update = false;
        try {
            connection = connexion.getConnection();
            String queryUpdateLoginAccount=
                    "update LoginAccount SET password=?, firstLogin=? where userName=? AND password=? ";
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

    @Override
    public boolean searchUserName(String userName){
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String queryUserName = "Select userName from LoginAccount where userName=?";
            sentence = connection.prepareStatement(queryUserName);
            sentence.setString(1, userName);
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
}
