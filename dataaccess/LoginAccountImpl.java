package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginAccountImpl implements ILoginAccount{
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    public LoginAccountImpl() {
        connexion = new Connexion();
    }

    @Override
    public String searchUserTypeWithLoginAccount(String user, String password) throws SQLException {
        String userType= null;
        try {
            connection = connexion.getConnection();
            String queryUserType = "Select type from UserType,LoginAccount,User where LoginAccount.idUser=User.idUser AND LoginAccount.userName=? AND LoginAccount.password=?";
            PreparedStatement sentence = connection.prepareStatement(queryUserType);
            sentence.setString(1, user);
            sentence.setString(2,password);
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
    public boolean firstLogin(String userName, String password) throws SQLException{
        boolean search = false;
        String userNameFound ;
        String passwordFound;
        try {
            connection = connexion.getConnection();
            String queryUserName =
                    "Select userName, password, status from LoginAccount, UserStatus where LoginAccount.userName=? AND LoginAccount.password=? AND UserStatus.status=? AND LoginAccount.firstLogin=?";
            PreparedStatement sentence = connection.prepareStatement(queryUserName);
            sentence.setString(1, userName);
            sentence.setString(2, password);
            sentence.setString(3,"Active");
            sentence.setInt(4,0);
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
    public boolean searchLoginAccount(String userName, String password) throws SQLException{
        boolean search = false;
        String userNameFound ;
        String passwordFound;
        try {
            connection = connexion.getConnection();
            String queryUserName =
                    "Select userName, password, status from LoginAccount, UserStatus where LoginAccount.userName=? AND LoginAccount.password=? AND UserStatus.status=?";
            PreparedStatement sentence = connection.prepareStatement(queryUserName);
            sentence.setString(1, userName);
            sentence.setString(2, password);
            sentence.setString(3,"Active");
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
    public boolean updateLoginAccount(String userNamePrevious, String passwordPrevious, String passwordNew, String userNameNew) throws SQLException{
        boolean update = false;
        try {
            connection = connexion.getConnection();
            String queryUserName =
                    "update userName, password from LoginAccount SET userName=?, password=?, firstLogin=? where userName=? AND password=? ";
            PreparedStatement sentence = connection.prepareStatement(queryUserName);
            sentence.setString(1, userNameNew);
            sentence.setString(2, passwordNew);
            sentence.setInt(3,1);
            sentence.setString(4,userNamePrevious);
            sentence.setString(5,passwordPrevious);
            result = sentence.executeQuery();
            while (result.next()) {
                update=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connection.close();
        }
        return update;
    }

}
