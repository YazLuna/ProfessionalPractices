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
 * @version 08/05/2020
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
        int idUserType;
        Coordinator coordinator = new Coordinator();
        idUserType = searchIdUserType(coordinator.getUserType(),coordinator.getStatus());
        try {
            connection = connexion.getConnection();
            String queryGetCoordinator = "SELECT * from Coordinator, User, UserTypeStatus, User_UserTypeStatus WHERE Coordinator.idUser="+
                    "User.idUser AND User_UserTypeStatus.idUser = User.idUser AND User_UserTypeStatus.idUserTypeStatus=?" +
                    " AND UserTypeStatus.status='Active'";
            PreparedStatement sentence = connection.prepareStatement(queryGetCoordinator);
            sentence.setInt(1,idUserType);
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
    public int updateCoordinator(int staffNumber, Coordinator coordinatorEdit) throws SQLException {
        int result = 0;
        int emailExist;
        int phoneExist;
        int alternateEmailExist;
        emailExist = searchEmail(coordinatorEdit.getEmail());
        if(emailExist == 0){
            phoneExist = searchPhone(coordinatorEdit.getPhone());
            if(phoneExist == 0){
                alternateEmailExist = searchAlternateEmail(coordinatorEdit.getAlternateEmail());
                if(alternateEmailExist == 0){
                    try {
                        connection = connexion.getConnection();
                        String queryCoordinatorModify = "UPDATE Coordinator INNER JOIN User ON Coordinator.idUser =" +
                                " User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?" +
                                ", User.alternateEmail = ?, User.phone = ?, User.password = ?" +
                                ", Coordinator.staffNumber = ?  WHERE Coordinator.staffNumber = ?";
                        PreparedStatement sentence = connection.prepareStatement(queryCoordinatorModify);
                        sentence.setString(1, coordinatorEdit.getName());
                        sentence.setString(2, coordinatorEdit.getLastName());
                        sentence.setInt(3, coordinatorEdit.getGender());
                        sentence.setString(4, coordinatorEdit.getEmail());
                        sentence.setString(5, coordinatorEdit.getAlternateEmail());
                        sentence.setString(6, coordinatorEdit.getPhone());
                        sentence.setString(7, coordinatorEdit.getPassword());
                        sentence.setInt(8, coordinatorEdit.getStaffNumber());
                        sentence.setInt(9, staffNumber);
                        sentence.executeUpdate();
                        result = 1;
                    } catch (SQLException ex) {
                        Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int deleteCoordinator(Coordinator coordinator) throws SQLException {
        int result = 0;
        int idUserTypeStatus;
        coordinator.setStatus("Inactive");
        idUserTypeStatus = searchIdUserType(coordinator.getUserType(),coordinator.getStatus());
        if(idUserTypeStatus == 0){
            addUserType(coordinator.getUserType(),coordinator.getStatus());
            idUserTypeStatus = searchIdUserType(coordinator.getUserType(),coordinator.getStatus());
        }
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceDeleteCoordinator =
                    connection.prepareStatement("UPDATE Coordinator, User, User_UserTypeStatus SET User_UserTypeStatus.idUserTypeStatus=?" +
                            ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_UserTypeStatus.idUser = User.idUser");
            sentenceDeleteCoordinator.setInt(1, idUserTypeStatus);
            sentenceDeleteCoordinator.setString(2, coordinator.getDischargeDate());
            sentenceDeleteCoordinator.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int addCoordinator(Coordinator coordinator) throws SQLException {
        int resultAdd = 0;
        int idUser;
        int userAdd = 0;
        int staffNumber;
        int emailExist;
        int phoneExist;
        int alternateEmailExist;
        Coordinator coordinatorSearch = new Coordinator();
        coordinatorSearch = coordinatorSearch.getCoordinator();
        if(coordinatorSearch.getEmail()== null){
            idUser = searchIdUser(coordinator);
            staffNumber = searchStaffNumber(coordinator.getStaffNumber());
            if ((idUser == 0) && (staffNumber == 0)) {
                emailExist = searchEmail(coordinator.getEmail());
                if(emailExist == 0){
                    phoneExist = searchPhone(coordinator.getPhone());
                    if(phoneExist == 0){
                        alternateEmailExist = searchAlternateEmail(coordinator.getAlternateEmail());
                        if(alternateEmailExist == 0){
                            userAdd = addUser(coordinator);
                            idUser = searchIdUser(coordinator);
                        }
                    }
                }
            }
            if ((userAdd == 1) || (idUser > 0)) {
                addUserUserTypeStatus(idUser,searchIdUserType(coordinator.getUserType(),coordinator.getStatus()));
                try {
                    connection = connexion.getConnection();
                    String queryAddCoordinator = "INSERT INTO Coordinator (staffNumber, registrationDate, idUser) VALUES (?, ?, ?)";
                    PreparedStatement sentenceAddCoordinator = connection.prepareStatement(queryAddCoordinator);
                    sentenceAddCoordinator.setInt(1, coordinator.getStaffNumber());
                    sentenceAddCoordinator.setString(2, coordinator.getRegistrationDate());
                    sentenceAddCoordinator.setInt(3, idUser);
                    sentenceAddCoordinator.executeUpdate();
                    resultAdd = 1;
                } catch (SQLException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultAdd;
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
                coordinator.setStatus(result.getString("status"));
                coordinators.add(coordinator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coordinators;
    }

    @Override
    public int recoverCoordinator(Coordinator coordinator) throws SQLException {
        int result = 0;
        int idUserTypeStatus;
        if(coordinator.getCoordinator().getStaffNumber() == 0){
            coordinator.setStatus("Active");
            idUserTypeStatus = searchIdUserType(coordinator.getUserType(),coordinator.getStatus());
            try {
                connection = connexion.getConnection();
                PreparedStatement sentenceRecoverCoordinator =
                        connection.prepareStatement("UPDATE Coordinator, User, User_UserTypeStatus SET User_UserTypeStatus.idUserTypeStatus=?" +
                                ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_UserTypeStatus.idUser =" +
                                " User.idUser AND Coordinator.staffNumber =? ");
                sentenceRecoverCoordinator.setInt(1, idUserTypeStatus);
                sentenceRecoverCoordinator.setString(2, null);
                sentenceRecoverCoordinator.setInt(3, coordinator.getStaffNumber());
                sentenceRecoverCoordinator.executeUpdate();
                result = 1;
            } catch (SQLException ex) {
                Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}