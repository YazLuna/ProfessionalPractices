package dataAccess;

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

public class CoordinatorDAOImpl implements ICoordinatorDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    public CoordinatorDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public Coordinator getCoordinator() throws SQLException {
        Coordinator coordinator = new Coordinator();
        try {
            connection = connexion.getConnection();
            String queryFoundCoordinator = "SELECT * from Coordinator INNER JOIN User on Coordinator.idUser = User.idUser WHERE User.status = 'Active'";
            PreparedStatement sentence = connection.prepareStatement(queryFoundCoordinator);
            result = sentence.executeQuery();
            while (result.next()) {
                coordinator.setName(result.getString("name"));
                coordinator.setLastName(result.getString("lastName"));
                coordinator.setGender(result.getInt("gender"));
                coordinator.setEmail(result.getString("email"));
                coordinator.setAlternateEmail(result.getString("alternateEmail"));
                coordinator.setPhone(result.getString("phone"));
                coordinator.setStaffNumber(result.getInt("staffNumber"));
                coordinator.setDischargeDate(result.getString("dischargeDate"));
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
        try {
            connection = connexion.getConnection();
            String queryCoordinatorModify = "UPDATE Coordinator INNER JOIN User ON Coordinator.idUser = User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?,"
                    + " User.alternateEmail = ?, User.phone = ?, Coordinator.staffNumber = ?, Coordinator.registrationDate = ?, Coordinator.dischargeDate = ?  WHERE Coordinator.staffNumber = ?";
            PreparedStatement sentence = connection.prepareStatement(queryCoordinatorModify);
            sentence.setString(1, coordinatorEdit.getName());
            sentence.setString(2, coordinatorEdit.getLastName());
            sentence.setInt(3, coordinatorEdit.getGender());
            sentence.setString(4, coordinatorEdit.getEmail());
            sentence.setString(5, coordinatorEdit.getAlternateEmail());
            sentence.setString(6, coordinatorEdit.getPhone());
            sentence.setInt(7, coordinatorEdit.getStaffNumber());
            sentence.setString(8, coordinatorEdit.getRegistrationDate());
            sentence.setString(9, coordinatorEdit.getDischargeDate());
            sentence.setInt(10, staffNumber);
            sentence.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteCoordinator(Coordinator coordinator) throws SQLException {
        int result = 0;
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceDeleteCoordinator = connection.prepareStatement("UPDATE Coordinator INNER JOIN User ON Coordinator.idUser = User.idUser SET status = 'Inactive' WHERE Coordinator.staffNumber = ?");
            sentenceDeleteCoordinator.setInt(1, coordinator.getStaffNumber());
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
        int staffNumber = searchStaffNumber(coordinator.getStaffNumber());
        if (staffNumber == 0) {
            int userAdd = addUser(coordinator);
            if (userAdd == 1) {
                int idUser = searchIdUser(coordinator);
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

    private int searchIdUser(Coordinator coordinator) {
        int idUser = 0;
        try {
            connection = connexion.getConnection();
            String queryUser = "Select idUser from User where name=? AND lastName=? AND email=? AND alternateEmail=? AND phone=?";
            PreparedStatement sentence = connection.prepareStatement(queryUser);
            sentence.setString(1, coordinator.getName());
            sentence.setString(2, coordinator.getLastName());
            sentence.setString(3, coordinator.getEmail());
            sentence.setString(4, coordinator.getAlternateEmail());
            sentence.setString(5, coordinator.getPhone());
            result = sentence.executeQuery();
            while (result.next()) {
                idUser = result.getInt("idUser");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idUser;
    }

    private int addUser(Coordinator coordinator) {
        int result = 0;
        try {
            connection = connexion.getConnection();
            String queryAddUser = "INSERT INTO User  (name, lastName, gender, status, email,  alternateEmail, phone)  VALUES (?,?, ?, ?, ?, ?, ?)";
            PreparedStatement sentenceAddUser = connection.prepareStatement(queryAddUser);
            sentenceAddUser.setString(1, coordinator.getName());
            sentenceAddUser.setString(2, coordinator.getLastName());
            sentenceAddUser.setInt(3, coordinator.getGender());
            sentenceAddUser.setString(4, coordinator.getStatus());
            sentenceAddUser.setString(5, coordinator.getEmail());
            sentenceAddUser.setString(6, coordinator.getAlternateEmail());
            sentenceAddUser.setString(7, coordinator.getPhone());
            sentenceAddUser.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private int searchStaffNumber(int staffNumberSearch) {
        int staffNumber = 0;
        int resultAdd = 0;
        try {
            connection = connexion.getConnection();
            String queryStaffNumber = "Select staffNumber from Coordinator where staffNumber=?";
            PreparedStatement sentence = connection.prepareStatement(queryStaffNumber);
            sentence.setInt(1, staffNumberSearch);
            result = sentence.executeQuery();
            while (result.next()) {
                staffNumber = result.getInt("staffNumber");
            }
            if (staffNumber != 0) {
                resultAdd = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultAdd;
    }

    @Override
    public List<Coordinator> getAllCoordinator() throws SQLException {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            result = consult.executeQuery("Select * from Coordinator INNER JOIN User ON Coordinator.idUser = User.idUser");
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

    public int recoverCoordinator(Coordinator coordinatorEdit) throws SQLException {
        int result = 0;
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceRecoverCoordinator = connection.prepareStatement("UPDATE Coordinator INNER JOIN User ON Coordinator.idUser = User.idUser SET User.status = 'Active' WHERE Coordinator.staffNumber = ?");
            sentenceRecoverCoordinator.setInt(1, coordinatorEdit.getStaffNumber());
            sentenceRecoverCoordinator.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}