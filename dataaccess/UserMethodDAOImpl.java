package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Search;

/**
 * UserMethodDAOImpl
 * @author Yazmin
 * @version 08/07/2020
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
    public boolean searchUserAcademic(String name, String lastName, String email, String alternateEmail, String phone, int gender) {
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearchUser = "SELECT idUser FROM User WHERE name =? AND lastName =? AND email =? AND alternateEmail =? AND phone =? AND gender =?";
            preparedStatement = connection.prepareStatement(querySearchUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, alternateEmail);
            preparedStatement.setString(5, phone);
            preparedStatement.setInt(6, gender);
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

    @Override
    public boolean addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender, String userName) {
        boolean result = false;
        try {
            connection = connexion.getConnection();
            String queryAddUser = "INSERT INTO User  (name, lastName, gender, email,  alternateEmail" +
                    ", phone)  VALUES (?,?, ?, ?,?,?)";
            preparedStatement = connection.prepareStatement(queryAddUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, gender);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, alternateEmail);
            preparedStatement.setString(6, phone);
            preparedStatement.executeUpdate();
            int idUserAdd = searchIdUser(email,alternateEmail,phone);
            addRelations(idUserAdd, status, userType);
            LoginAccountDAOImpl loginAccountDAO = new LoginAccountDAOImpl();
            result = loginAccountDAO.createLoginAccount(userName,password,idUserAdd);
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    @Override
    public boolean validateAcademicAdd(int staffNumber, String email, String alternateEmail, String phone, String userName) {
        boolean valid = validateUserAdd(email, alternateEmail, phone, userName);
        if(valid){
            valid = staffNumberValidateTwoAcademics(staffNumber);
        }
        return valid;
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
    public boolean staffNumberTeacherValidate(int staffNumberSearch)  {
        boolean valid = true;
        try {
            connection = connexion.getConnection();
            String querySearchStaffNumber =
                    "Select staffNumber from Teacher where staffNumber=?";
            preparedStatement = connection.prepareStatement(querySearchStaffNumber);
            preparedStatement.setInt(1, staffNumberSearch);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                valid = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return valid;
    }

    @Override
    public boolean staffNumberCoordinatorValidate(int staffNumberSearch)  {
        boolean valid = true;
        try {
            connection = connexion.getConnection();
            String querySearchStaffNumber =
                    "Select staffNumber from Coordinator where staffNumber=?";
            preparedStatement = connection.prepareStatement(querySearchStaffNumber);
            preparedStatement.setInt(1, staffNumberSearch);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                valid = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return valid;
    }

    @Override
    public boolean validateUser(String email, String alternateEmail, String phone) {
        boolean result = false;
        boolean emailSearch = emailValid(email);
        if(emailSearch){
            boolean alternateEmailSearch = alternateEmailValid(alternateEmail);
            if(alternateEmailSearch){
                boolean phoneValid = phoneValid(phone);
                if(phoneValid){
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean validateUserAdd(String email, String alternateEmail, String phone, String userName) {
        boolean valid = validateUser (email, alternateEmail, phone);
        if(valid){
            LoginAccountDAOImpl loginAccountDAO = new LoginAccountDAOImpl();
            valid = loginAccountDAO.validateUserName(userName);
        }
        return valid;
    }

    @Override
    public boolean staffNumberValidateTwoAcademics(int staffNumberSearch) {
        boolean valid = false;
        int staffNumber = searchStaffNumberTeacher(staffNumberSearch);
        if(staffNumber == Search.NOTFOUND.getValue() ){
            staffNumber = searchStaffNumberCoordinator(staffNumberSearch);
        }
        if (staffNumber == Search.NOTFOUND.getValue()){
            valid = true;
        }
        return valid;
    }

    @Override
    public boolean validateUserUpdate(String email, String alternateEmail, String phone) {
        boolean result = false;
        boolean emailValid = emailValid(email);
        if(emailValid){
            boolean alternateEmailValid = alternateEmailValid(alternateEmail);
            if(alternateEmailValid){
                boolean phoneSearch = phoneValid(phone);
                if(!phoneSearch){
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public int searchStaffNumberTwoAcademics(int staffNumberSearch) {
       int staffNumber = searchStaffNumberTeacher(staffNumberSearch);
       if(staffNumber == Search.NOTFOUND.getValue() ){
           staffNumber = searchStaffNumberCoordinator(staffNumberSearch);
       }
       return staffNumber;
    }

    @Override
    public boolean staffNumberTwoAcademicsValidate(int staffNumberSearch) {
        boolean resultValidate = false;
        boolean valid = staffNumberCoordinatorValidate(staffNumberSearch);
        if(valid) {
            valid = staffNumberTeacherValidate(staffNumberSearch);
            if(valid){
                resultValidate = true;
            }
        }
        return resultValidate;
    }

    @Override
    public void addRelations(int idUserAdd, String status, String userType) {
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatusSearch = statusDAO.searchIdStatus(status);
        int idUserTypeSearch = searchIdUserType(userType);
        addUserUserStatus(idUserAdd, idUserStatusSearch,idUserTypeSearch);
        addUserUserType(idUserAdd, idUserTypeSearch);
    }

    private boolean alternateEmailValid(String alternateEmail)  {
        boolean search = true;
        try {
            connection = connexion.getConnection();
            String querySearchAlternateEmail = "Select alternateEmail from User where alternateEmail =?";
            preparedStatement = connection.prepareStatement(querySearchAlternateEmail);
            preparedStatement.setString(1, alternateEmail);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                search = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return search;
    }

    private boolean phoneValid(String phone) {
        boolean search = true;
        try {
            connection = connexion.getConnection();
            String querySearchPhone = "Select phone from User where phone =?";
            preparedStatement = connection.prepareStatement(querySearchPhone);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                search = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return search;
    }

    private boolean emailValid(String email)  {
        boolean search = true;
        try {
            connection = connexion.getConnection();
            String querySearchEmail = "Select email from User where email =?";
            preparedStatement = connection.prepareStatement(querySearchEmail);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                search = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return search;
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

    private void addUserUserStatus(int idUserAdd, int idUserStatus, int idUserType){
        try {
            connection = connexion.getConnection();
            String queryAddUserUserStatus = "INSERT INTO User_Status (idUser, idStatus, idUserType)  VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(queryAddUserUserStatus);
            preparedStatement.setInt(1, idUserAdd);
            preparedStatement.setInt(2, idUserStatus);
            preparedStatement.setInt(3, idUserType);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
    }

    @Override
    public int searchIdUserType(String userType) {
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

}
