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
import domain.Coordinator;
import domain.Search;

/**
 * DAO User
 * @author Yazmin
 * @version 04/06/2020
 */

public class CoordinatorDAOImpl extends UserMethodDAOImpl implements ICoordinatorDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public CoordinatorDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public Coordinator getCoordinator() {
        Coordinator coordinator = new Coordinator();
        int idUserStatus = searchIdUserStatus("Active");
        try {
            connection = connexion.getConnection();
            String queryGetCoordinator = "SELECT name,lastName,gender,email,alternateEmail,phone,profilePicture,staffNumber,registrationDate from Coordinator, User" +
                    ", UserType, Status, User_Status WHERE Coordinator.idUser=User.idUser AND User_Status.idUser = User.idUser AND" +
                    " UserType.type='Coordinator' AND User_Status.idStatus=?";
            preparedStatement = connection.prepareStatement(queryGetCoordinator);
            preparedStatement.setInt(1,idUserStatus);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setGender(resultSet.getInt("gender"));
                coordinator.setEmail(resultSet.getString("email"));
                coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
                coordinator.setPhone(resultSet.getString("phone"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                coordinator.setRegistrationDate(resultSet.getString("registrationDate"));
                //image
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinator;
    }

    @Override
    public Coordinator getCoordinatorSelected(int staffNumber) {
        Coordinator coordinator = new Coordinator();
        try {
            connection = connexion.getConnection();
            String queryGetCoordinatorSelected =
                    "SELECT name,lastName,gender,email,alternateEmail,phone,profilePicture,staffNumber from Coordinator, User, UserType WHERE " +
                            "Coordinator.idUser=User.idUser AND UserType.type='Coordinator' AND Coordinator.staffNumber=?";
            preparedStatement = connection.prepareStatement(queryGetCoordinatorSelected);
            preparedStatement.setInt(1,staffNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setGender(resultSet.getInt("gender"));
                coordinator.setEmail(resultSet.getString("email"));
                coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
                coordinator.setPhone(resultSet.getString("phone"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                //image
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinator;
    }

    @Override
    public boolean updateCoordinator(int staffNumber, Coordinator coordinatorEdit)  {
        boolean resultSet = false;
        int staffNumberFound = searchStaffNumber(coordinatorEdit.getStaffNumber());
        if(staffNumberFound == Search.NOTFOUND.getValue() ){
            boolean validate = validateUser(coordinatorEdit.getEmail(),coordinatorEdit.getAlternateEmail(),
                    coordinatorEdit.getPhone(),coordinatorEdit.getUserName());
            if(validate){
                try {
                    connection = connexion.getConnection();
                    String queryCoordinatorUpdate = "UPDATE Coordinator INNER JOIN User ON Coordinator.idUser =" +
                            " User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?" +
                            ", User.alternateEmail = ?, User.phone = ?, User.profilePicture=?, Coordinator.staffNumber = ?  WHERE Coordinator.staffNumber = ?";
                    preparedStatement = connection.prepareStatement(queryCoordinatorUpdate);
                    preparedStatement.setString(1, coordinatorEdit.getName());
                    preparedStatement.setString(2, coordinatorEdit.getLastName());
                    preparedStatement.setInt(3, coordinatorEdit.getGender());
                    preparedStatement.setString(4, coordinatorEdit.getEmail());
                    preparedStatement.setString(5, coordinatorEdit.getAlternateEmail());
                    preparedStatement.setString(6, coordinatorEdit.getPhone());
                    if(coordinatorEdit.getProfilePicture() != null){
                        FileInputStream convertImage = new FileInputStream (coordinatorEdit.getProfilePicture());
                        preparedStatement.setBinaryStream(7,convertImage,coordinatorEdit.getProfilePicture().length());
                    }else{
                        preparedStatement.setBinaryStream(7,null);
                    }
                    preparedStatement.setInt(8, coordinatorEdit.getStaffNumber());
                    preparedStatement.setInt(9, staffNumber);
                    preparedStatement.executeUpdate();
                    resultSet = true;
                } catch (SQLException | FileNotFoundException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    connexion.closeConnection();
                }
            }
        }
        return resultSet;
    }

    @Override
    public boolean deleteCoordinator(String status, String dischargeDate) {
        boolean resultSet = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(status);
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDao.addStatus(status);
            idUserStatus = statusDao.searchIdStatus(status);
        }
        try {
            connection = connexion.getConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE Coordinator, User, User_Status SET User_Status.idStatus=?" +
                            ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_Status.idUser = User.idUser");
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, dischargeDate);
            preparedStatement.executeUpdate();
            resultSet = true;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return resultSet;
    }

    @Override
    public boolean addCoordinator(Coordinator coordinator) {
        boolean resultSetAdd = false;
        boolean validate = validateCoordinator(coordinator);
        if(validate){
                int idUser = searchIdUser(coordinator.getEmail(),coordinator.getAlternateEmail(),coordinator.getPhone());
                try {
                    connection = connexion.getConnection();
                    String queryAddCoordinator = "INSERT INTO Coordinator (staffNumber, registrationDate, idUser) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(queryAddCoordinator);
                    preparedStatement.setInt(1, coordinator.getStaffNumber());
                    preparedStatement.setString(2, coordinator.getRegistrationDate());
                    preparedStatement.setInt(3, idUser);
                    preparedStatement.executeUpdate();
                    resultSetAdd = true;
                } catch (SQLException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    connexion.closeConnection();
                }
        }
        return resultSetAdd;
    }

    private boolean validateCoordinator(Coordinator coordinator) {
        boolean validation = false;
        boolean addUserValidate;
        boolean teacher;
        boolean activeCoordinator= coordinator.activeCoordinator();
        boolean validationStaffNumber;
        if(!activeCoordinator){
            validationStaffNumber = validateStaffNumber(coordinator.getStaffNumber());
            if(!validationStaffNumber){
                teacher = isTeacher(coordinator.getStaffNumber());
                if(teacher){
                    validation = true;
                }
            }
            if(validationStaffNumber) {
                addUserValidate = addUser(coordinator.getName(), coordinator.getLastName(), coordinator.getEmail(), coordinator.getAlternateEmail(),
                        coordinator.getPhone(), coordinator.getPassword(), coordinator.getUserType(),
                        coordinator.getStatus(), coordinator.getGender(), coordinator.getUserName(),coordinator.getProfilePicture());
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
        int staffNumberFound = searchStaffNumberCoordinator(staffNumber);
            if(staffNumberFound == Search.NOTFOUND.getValue()){
                resultSet = true;
            }
        return resultSet;
    }

    @Override
    public List<Coordinator> getAllCoordinator() {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("Select name, lastName,staffNumber from Coordinator INNER JOIN User ON Coordinator.idUser =" +
                    " User.idUser");
            while (resultSet.next()) {
                Coordinator coordinator = new Coordinator();
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                coordinators.add(coordinator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinators;
    }

    @Override
    public List<Coordinator> getInformationAllCoordinator() {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("Select name, lastName,staffNumber,email,alternateEmail" +
                    ",phone from Coordinator INNER JOIN User ON Coordinator.idUser =" +
                    " User.idUser");
            while (resultSet.next()) {
                Coordinator coordinator = new Coordinator();
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                coordinator.setEmail(resultSet.getString("email"));
                coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
                coordinator.setPhone(resultSet.getString("phone"));
                coordinators.add(coordinator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinators;
    }

    @Override
    public boolean recoverCoordinator(Coordinator coordinator) {
        boolean resultSet = false;
           if(coordinator.getCoordinator().getStaffNumber() == Search.NOTFOUND.getValue()){
                StatusDAOImpl statusDao = new StatusDAOImpl();
                int idUserStatus = statusDao.searchIdStatus(coordinator.getStatus());
                if(idUserStatus == Search.NOTFOUND.getValue() ){
                    statusDao.addStatus(coordinator.getStatus());
                    idUserStatus = statusDao.searchIdStatus(coordinator.getStatus());
                }
                try {
                    connection = connexion.getConnection();
                    preparedStatement =
                            connection.prepareStatement("UPDATE Coordinator, User, User_Status SET User_Status.idStatus=?" +
                                    ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_Status.idUser =" +
                                    " User.idUser AND Coordinator.staffNumber =? ");
                    preparedStatement.setInt(1, idUserStatus);
                    preparedStatement.setString(2, null);
                    preparedStatement.setInt(3, coordinator.getStaffNumber());
                    preparedStatement.executeUpdate();
                    resultSet = true;
                } catch (SQLException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    connexion.closeConnection();
                }
           }
           return resultSet;
    }

    @Override
    public boolean activeCoordinator() {
        boolean isActive = false;
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDAO.addStatus("Active");
            idUserStatus = statusDAO.searchIdStatus("Active");
        }
        try {
            connection = connexion.getConnection();
            String queryActiveCoordinator =
                    "SELECT staffNumber from Coordinator, User, UserType, Status, User_Status WHERE Coordinator.idUser= User.idUser AND" +
                    " User_Status.idUser = User.idUser AND UserType.type='Coordinator' AND User_Status.idStatus=?";
            preparedStatement = connection.prepareStatement(queryActiveCoordinator);
            preparedStatement.setInt(1,idUserStatus);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isActive = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return isActive;
    }

    @Override
    public boolean areCoordinator() {
        boolean areCoordinator = false;
        try {
            connection = connexion.getConnection();
            String queryActiveCoordinator =
                    "SELECT staffNumber from Coordinator, User, UserType WHERE Coordinator.idUser= User.idUser" +
                            " AND UserType.type='Coordinator'";
            preparedStatement = connection.prepareStatement(queryActiveCoordinator);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                areCoordinator = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areCoordinator;
    }
}