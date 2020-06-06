package dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Search;

/**
 * DAO User
 * @author Yazmin
 * @version 05/06/2020
 */

public class UserMethodDAOImpl implements IUserMethodDAO{
    private final Connexion connexion;
    private Connection connection;
    private ResultSet resultSet;
    PreparedStatement preparedStatement;

    public UserMethodDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public int searchIdUser(String email, String alternateEmail, String phone) {
        int idUser = Search.NOTFOUND.getValue();
        try {
            connection = connexion.getConnection();
            String querySearchIdUser = "Select idUser from User where email=? AND alternateEmail=? AND phone=?";
            preparedStatement = connection.prepareStatement(querySearchIdUser);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, alternateEmail);
            preparedStatement.setString(3, phone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idUser = resultSet.getInt("idUser");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return idUser;
    }

    @Override
    public boolean addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender, String userName, File image) {
        boolean validate;
        boolean resultSet = false;
        validate = validateUser(email, alternateEmail, phone,userName);
        if(validate){
            try {
                connection = connexion.getConnection();
                String queryAddUser = "INSERT INTO User  (name, lastName, gender, email,  alternateEmail" +
                        ", phone, profilePicture)  VALUES (?,?, ?, ?, ?,?,?)";
                preparedStatement = connection.prepareStatement(queryAddUser);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, gender);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, alternateEmail);
                preparedStatement.setString(6, phone);
                if(image != null){
                    FileInputStream convertImage = new FileInputStream (image);
                    preparedStatement.setBinaryStream(7,convertImage,image.length());
                }else{
                    preparedStatement.setBinaryStream(7,null);
                }
                preparedStatement.executeUpdate();
                addRelations(email,alternateEmail,phone,status,userType,userName,password);
                resultSet = true;
            } catch (SQLException | FileNotFoundException ex) {
                Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connexion.closeConnection();
            }
        }
        return resultSet;
    }

    @Override
    public int searchIdUserStatus(String status) {
        int idUserStatus = Search.NOTFOUND.getValue();
        try {
            connection = connexion.getConnection();
            String querySearchIdUserStatus = "Select idStatus from Status where status =?";
            preparedStatement = connection.prepareStatement(querySearchIdUserStatus);
            preparedStatement.setString(1, status);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idUserStatus = resultSet.getInt("idStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return idUserStatus;
    }

    @Override
    public int searchStaffNumberCoordinator(int staffNumberSearch) {
        int staffNumber = Search.NOTFOUND.getValue();
        try {
            connection = connexion.getConnection();
            String querySearchStaffNumber =
                    "Select staffNumber from Coordinator where staffNumber=?";
            preparedStatement = connection.prepareStatement(querySearchStaffNumber);
            preparedStatement.setInt(1, staffNumberSearch);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                staffNumber = resultSet.getInt("staffNumber");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return staffNumber;
    }

    @Override
    public int searchStaffNumberTeacher(int staffNumberSearch)  {
        int staffNumber = Search.NOTFOUND.getValue();
        try {
            connection = connexion.getConnection();
            String querySearchStaffNumber =
                    "Select staffNumber from Teacher where staffNumber=?";
            preparedStatement = connection.prepareStatement(querySearchStaffNumber);
            preparedStatement.setInt(1, staffNumberSearch);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                staffNumber = resultSet.getInt("staffNumber");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return staffNumber;
    }

    @Override
    public boolean validateUser(String email, String alternateEmail, String phone, String userName) {
        boolean resultSet = false;
        boolean emailSearch = searchEmail(email);
        if(!emailSearch){
            boolean alternateEmailSearch = searchAlternateEmail(alternateEmail);
            if(!alternateEmailSearch){
                boolean phoneSearch = searchPhone(phone);
                if(!phoneSearch){
                    boolean userNameSearch = searchUserName(userName);
                    if(!userNameSearch){
                        resultSet = true;
                    }
                }
            }
        }
        return resultSet;
    }

    @Override
    public int searchStaffNumber(int staffNumberSearch) {
       int staffNumber = searchStaffNumberTeacher(staffNumberSearch);
       if(staffNumber == Search.NOTFOUND.getValue() ){
           staffNumber = searchStaffNumberCoordinator(staffNumberSearch);
       }
       return staffNumber;
    }

    private boolean searchAlternateEmail(String alternateEmail)  {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearchAlternateEmail = "Select alternateEmail from User where alternateEmail =?";
            preparedStatement = connection.prepareStatement(querySearchAlternateEmail);
            preparedStatement.setString(1, alternateEmail);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return search;
    }

    private boolean searchPhone(String phone) {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearchPhone = "Select phone from User where phone =?";
            preparedStatement = connection.prepareStatement(querySearchPhone);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return search;
    }

    private boolean searchEmail(String email)  {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearchEmail = "Select email from User where email =?";
            preparedStatement = connection.prepareStatement(querySearchEmail);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return search;
    }

    private void addRelations(String email, String alternateEmail, String phone, String status, String userType, String userName, String password) {
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserAdd = searchIdUser(email,alternateEmail,phone);
        int idUserStatusSearch = statusDAO.searchIdStatus(status);
        if(idUserStatusSearch == Search.NOTFOUND.getValue() ){
            statusDAO.addStatus(status);
            idUserStatusSearch = statusDAO.searchIdStatus(status);
        }
        int idUserTypeSearch = searchIdUserType(userType);
        if(idUserTypeSearch== Search.NOTFOUND.getValue()){
            addUserType(userType);
            idUserTypeSearch = searchIdUserType(userType);
        }
        addUserUserStatus(idUserAdd, idUserStatusSearch);
        addUserUserType(idUserAdd, idUserTypeSearch);
        createLoginAccount(userName,password,idUserAdd);
    }

    private void addUserUserType(int idUserAdd, int idUserTypeSearch) {
        try {
            connection = connexion.getConnection();
            String queryAddUserUserType = "INSERT INTO User_UserType (idUser, idUserType)  VALUES (?,?)";
            preparedStatement = connection.prepareStatement(queryAddUserUserType);
            preparedStatement.setInt(1, idUserAdd);
            preparedStatement.setInt(2, idUserTypeSearch);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
    }

    private void addUserUserStatus(int idUserAdd, int idUserStatus){
        try {
            connection = connexion.getConnection();
            String queryAddUserUserStatus = "INSERT INTO User_Status (idUser, idStatus)  VALUES (?,?)";
            preparedStatement = connection.prepareStatement(queryAddUserUserStatus);
            preparedStatement.setInt(1, idUserAdd);
            preparedStatement.setInt(2, idUserStatus);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
    }

    private void createLoginAccount(String userName, String password, int idUser) {
        try {
            connection = connexion.getConnection();
            String queryCreateLoginAccount = "INSERT INTO LoginAccount (userName,password,idUser,firstLogin)  VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(queryCreateLoginAccount);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, idUser);
            preparedStatement.setInt(4,Search.NOTFOUND.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
    }

    private void addUserType(String userType)  {
        try {
            connection = connexion.getConnection();
            String queryAddUserType = "INSERT INTO UserType (type)  VALUES (?)";
            preparedStatement = connection.prepareStatement(queryAddUserType);
            preparedStatement.setString(1, userType);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
    }

    private int searchIdUserType(String userType) {
        int idUserType = Search.NOTFOUND.getValue();
        try {
            connection = connexion.getConnection();
            String querySearchIdUserType = "Select idUserType from UserType where type =?";
            preparedStatement = connection.prepareStatement(querySearchIdUserType);
            preparedStatement.setString(1, userType);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idUserType = resultSet.getInt("idUserType");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return idUserType;
    }

    private boolean searchUserName (String userNameSearch) {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearchUserName = "Select userName from LoginAccount where userName=?";
            preparedStatement = connection.prepareStatement(querySearchUserName);
            preparedStatement.setString(1, userNameSearch);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return search;
    }

}
