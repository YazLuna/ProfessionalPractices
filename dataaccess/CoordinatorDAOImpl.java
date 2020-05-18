package dataaccess;

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

/**
 * DAO User
 * @author Yazmin
 * @version 17/05/2020
 */

public class CoordinatorDAOImpl extends UserMethodDAOImpl implements ICoordinatorDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    public CoordinatorDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public Coordinator getCoordinator() throws SQLException {
        Coordinator coordinator = new Coordinator();
        int idUserStatus;
        idUserStatus = searchIdUserStatus("Active");
        try {
            connection = connexion.getConnection();
            String queryGetCoordinator = "SELECT * from Coordinator, User, UserType, UserStatus, User_UserStatus WHERE Coordinator.idUser="+
                    "User.idUser AND User_UserStatus.idUser = User.idUser AND UserType.type='Coordinator'" +
                    " AND User_UserStatus.idUserStatus=?";
            PreparedStatement sentence = connection.prepareStatement(queryGetCoordinator);
            sentence.setInt(1,idUserStatus);
            result = sentence.executeQuery();
            while (result.next()) {
                coordinator.setName(result.getString("name"));
                coordinator.setLastName(result.getString("lastName"));
                coordinator.setGender(result.getInt("gender"));
                coordinator.setEmail(result.getString("email"));
                coordinator.setAlternateEmail(result.getString("alternateEmail"));
                coordinator.setPhone(result.getString("phone"));
                coordinator.setStaffNumber(result.getInt("staffNumber"));
                coordinator.setRegistrationDate(result.getString("registrationDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coordinator;
    }

    @Override
    public boolean updateCoordinator(int staffNumber, Coordinator coordinatorEdit) throws SQLException {
        boolean result = false;
        boolean validate;
        int staffNumberFound;
        staffNumberFound = searchStaffNumber(coordinatorEdit.getStaffNumber());
        if(staffNumberFound == 0 ){
            validate = validateUser(coordinatorEdit.getEmail(),coordinatorEdit.getAlternateEmail(),
                    coordinatorEdit.getPhone(),coordinatorEdit.getUserName());
            if(validate){
                try {
                    connection = connexion.getConnection();
                    String queryCoordinatorModify = "UPDATE Coordinator INNER JOIN User ON Coordinator.idUser =" +
                            " User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?" +
                            ", User.alternateEmail = ?, User.phone = ?, Coordinator.staffNumber = ?  WHERE Coordinator.staffNumber = ?";
                    PreparedStatement sentence = connection.prepareStatement(queryCoordinatorModify);
                    sentence.setString(1, coordinatorEdit.getName());
                    sentence.setString(2, coordinatorEdit.getLastName());
                    sentence.setInt(3, coordinatorEdit.getGender());
                    sentence.setString(4, coordinatorEdit.getEmail());
                    sentence.setString(5, coordinatorEdit.getAlternateEmail());
                    sentence.setString(6, coordinatorEdit.getPhone());
                    sentence.setInt(7, coordinatorEdit.getStaffNumber());
                    sentence.setInt(8, staffNumber);
                    sentence.executeUpdate();
                    result = true;
                } catch (SQLException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public boolean deleteCoordinator(String status, String dischargeDate) throws SQLException {
        boolean result = false;
        int idUserStatus;
        idUserStatus = searchIdUserStatus(status);
        if(idUserStatus==0){
            addUserStatus(status);
            idUserStatus = searchIdUserStatus(status);
        }
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceDeleteCoordinator =
                    connection.prepareStatement("UPDATE Coordinator, User, User_UserStatus SET User_UserStatus.idUserStatus=?" +
                            ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_UserStatus.idUser = User.idUser");
            sentenceDeleteCoordinator.setInt(1, idUserStatus);
            sentenceDeleteCoordinator.setString(2, dischargeDate);
            sentenceDeleteCoordinator.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean addCoordinator(Coordinator coordinator) throws SQLException {
        boolean resultAdd = false;
        int idUser;
        boolean validate;
        validate = validateCoordinator(coordinator);
        if(validate){
                idUser = searchIdUser(coordinator.getEmail(),coordinator.getAlternateEmail(),coordinator.getPhone());
                try {
                    connection = connexion.getConnection();
                    String queryAddCoordinator = "INSERT INTO Coordinator (staffNumber, registrationDate, idUser) VALUES (?, ?, ?)";
                    PreparedStatement sentenceAddCoordinator = connection.prepareStatement(queryAddCoordinator);
                    sentenceAddCoordinator.setInt(1, coordinator.getStaffNumber());
                    sentenceAddCoordinator.setString(2, coordinator.getRegistrationDate());
                    sentenceAddCoordinator.setInt(3, idUser);
                    sentenceAddCoordinator.executeUpdate();
                    resultAdd = true;
                } catch (SQLException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        return resultAdd;
    }

    private boolean validateCoordinator(Coordinator coordinator) throws SQLException {
        boolean validationStaffNumber;
        boolean validation = false;
        boolean addUserValidate;
        boolean teacher;
        Coordinator coordinatorSearch;
        coordinatorSearch = coordinator.getCoordinator();
        if(coordinatorSearch.getEmail() == null){
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
                        coordinator.getStatus(), coordinator.getGender(), coordinator.getUserName());
                if(addUserValidate){
                    validation = true;
                }
            }
        }
        return validation;
    }

    private boolean isTeacher(int staffNumber) throws SQLException {
        int staffNumberFound;
        boolean result = false;
        staffNumberFound = searchStaffNumberTeacher(staffNumber);
            if(staffNumberFound != 0 ){
              result = true;
            }
            return result;
    }

    private boolean validateStaffNumber(int staffNumber) throws SQLException {
        boolean result = false;
        int staffNumberFound;
            staffNumberFound = searchStaffNumberCoordinator(staffNumber);
            if(staffNumberFound == 0){
                result = true;
            }
        return result;
    }

    @Override
    public List<Coordinator> getAllCoordinator() throws SQLException {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            result = consult.executeQuery("Select * from Coordinator INNER JOIN User ON Coordinator.idUser =" +
                    " User.idUser");
            while (result.next()) {
                Coordinator coordinator = new Coordinator();
                coordinator.setName(result.getString("name"));
                coordinator.setLastName(result.getString("lastName"));
                coordinator.setGender(result.getInt("gender"));
                coordinator.setEmail(result.getString("email"));
                coordinator.setAlternateEmail(result.getString("alternateEmail"));
                coordinator.setPhone(result.getString("phone"));
                coordinator.setStaffNumber(result.getInt("staffNumber"));
                coordinator.setRegistrationDate(result.getString("registrationDate"));
                coordinator.setDischargeDate(result.getString("dischargeDate"));
                coordinators.add(coordinator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coordinators;
    }

    @Override
    public boolean recoverCoordinator(Coordinator coordinator) throws SQLException {
        boolean result = false;
        int idUserStatus;
           if(coordinator.getCoordinator().getEmail() == null){
                idUserStatus = searchIdUserStatus(coordinator.getStatus());
                if(idUserStatus == 0 ){
                    addUserStatus(coordinator.getStatus());
                    idUserStatus = searchIdUserStatus(coordinator.getStatus());
                }
                try {
                    connection = connexion.getConnection();
                    PreparedStatement sentenceRecoverCoordinator =
                            connection.prepareStatement("UPDATE Coordinator, User, User_UserStatus SET User_UserStatus.idUserStatus=?" +
                                    ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_UserStatus.idUser =" +
                                    " User.idUser AND Coordinator.staffNumber =? ");
                    sentenceRecoverCoordinator.setInt(1, idUserStatus);
                    sentenceRecoverCoordinator.setString(2, null);
                    sentenceRecoverCoordinator.setInt(3, coordinator.getStaffNumber());
                    sentenceRecoverCoordinator.executeUpdate();
                    result = true;
                } catch (SQLException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           return result;
    }
}